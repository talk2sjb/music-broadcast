package org.sjbanerjee.musicplayer.controller;

import org.sjbanerjee.musicplayer.service.StreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/broadcast")
public class PlayerController {

    @Autowired
    StreamingService streamingService;

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public ResponseEntity<StreamingResponseBody> play() throws Exception{

        Path path = Paths.get("C://Users/sb329e/Desktop/vm_share");
        String contentType = Files.probeContentType(path);
        FileSystemResource file = new FileSystemResource("C://Users/sb329e/Desktop/vm_share/test.mp4");
        return ResponseEntity
                .ok()
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType(contentType))
                .body(outputStream -> streamingService.stream(file, outputStream));
    }
}

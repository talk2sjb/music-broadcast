package org.sjbanerjee.musicplayer.controller;

import javafx.scene.media.MediaPlayer;
import org.sjbanerjee.musicplayer.MediaPlayingTask;
import org.sjbanerjee.musicplayer.application.MediaBroadcaster;
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
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/broadcast")
public class PlayerController {
    @Autowired
    StreamingService streamingService;

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public ResponseEntity<StreamingResponseBody> play() throws Exception{
        Callable<MediaPlayer> c = MediaBroadcaster.getCurrentTask();
        String mediaFile = ((MediaPlayingTask) c).getCurrentMediaFile();
        System.out.println("************" + mediaFile + "************");

        Path path = Paths.get(mediaFile);
        String contentType = Files.probeContentType(path);
        FileSystemResource file = new FileSystemResource(mediaFile);
        return ResponseEntity
                .ok()
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType(contentType))
                .body(outputStream -> streamingService.stream(file, outputStream));
    }

    @RequestMapping(value = "/seek", method = RequestMethod.GET)
    public String seek() throws Exception{
        Callable<MediaPlayer> c = MediaBroadcaster.getCurrentTask();
        double currentTime = ((MediaPlayingTask) c).getTimeForCurrentMedia();
        System.out.println("************" + currentTime + "************");
        return String.valueOf(currentTime);
    }
}

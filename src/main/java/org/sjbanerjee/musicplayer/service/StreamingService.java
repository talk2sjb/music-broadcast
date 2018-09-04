package org.sjbanerjee.musicplayer.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class StreamingService {
    private static final byte[] buf = new byte[8192];

    public void stream(FileSystemResource file, OutputStream outputStream) throws IOException {
            InputStream is = file.getInputStream();
            int c;

            while ((c = is.read(buf, 0, buf.length)) > 0) {
                outputStream.write(buf, 0, c);
                outputStream.flush();
            }

            outputStream.close();
            System.out.println("############stop#############");
            is.close();
    }
}

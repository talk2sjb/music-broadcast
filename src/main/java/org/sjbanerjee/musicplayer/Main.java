package org.sjbanerjee.musicplayer;

import org.sjbanerjee.musicplayer.application.MediaBroadcaster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class Main {

    public static void main(String[] args) {
        Thread mediaBroadcastThread = new Thread(new MediaBroadcaster());
        mediaBroadcastThread.start();
        SpringApplication.run(Main.class, args);
    }
}

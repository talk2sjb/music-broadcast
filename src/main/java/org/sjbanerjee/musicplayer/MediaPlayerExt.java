package org.sjbanerjee.musicplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class MediaPlayerExt  {

    public synchronized MediaPlayer play(String mediaFile) throws InterruptedException {
        System.out.println(Paths.get(mediaFile).toUri().toString());
        MediaPlayer player = new MediaPlayer(new Media(Paths.get(mediaFile).toUri().toString()));
        player.setOnStopped(new Runnable() {
            @Override
            public void run() {
                System.out.println("Media end!");
            }
        });

        player.play();
        Thread.sleep((lozplayer.getTotalDuration().toMillis().);
        player.stop();

        return player;
    }
}

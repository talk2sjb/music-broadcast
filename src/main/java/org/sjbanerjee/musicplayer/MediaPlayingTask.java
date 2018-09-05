package org.sjbanerjee.musicplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class MediaPlayingTask implements Callable {
    private static MediaPlayer player = null;
    private final String mediaFile;
    private static String currentPlayingMedia = null;

    public MediaPlayingTask(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    @Override
    public MediaPlayer call() {
        System.out.println("Playing media on thread : " + Thread.currentThread().getName());
        player = new MediaPlayer(new Media(Paths.get(mediaFile).toUri().toString()));

        play();
        return player;
    }

    private synchronized void play() {
        try {
            Thread.sleep(5000); //FIXME - HACK - Wait for the player to initialize
            System.out.println("Playing media " + mediaFile);
            currentPlayingMedia = mediaFile;

            player.play();

            //Blocking player
            while (player.getCurrentTime().toMillis() < player.getTotalDuration().toMillis()) {
                Thread.sleep(5000); //Check after 5 seconds
                System.out.println("Current time: " + player.getCurrentTime());
            }

            player.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getTimeForCurrentMedia() {
        if (player != null) {
            return player.getCurrentTime().toMillis();
        } else {
            return 0.0;
        }
    }

    public String getCurrentMediaFile() {
        return currentPlayingMedia;
    }
}

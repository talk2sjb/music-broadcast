package org.sjbanerjee.musicplayer;

import javafx.scene.media.MediaPlayer;

import java.util.concurrent.Callable;

public class StatusCheckingThread implements Callable {
    private static final int FIXED_INTERVAL = 30;
    private static MediaPlayer mediaPlayer;

    public StatusCheckingThread(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public String call() throws Exception {
        while (true) {
            Thread.sleep(FIXED_INTERVAL * 1000);
            System.out.println("Status checking thread : " + Thread.currentThread().getName() + " - Duration: " + mediaPlayer.getCurrentTime());
        }
    }
}

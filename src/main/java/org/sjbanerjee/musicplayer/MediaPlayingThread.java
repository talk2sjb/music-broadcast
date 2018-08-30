package org.sjbanerjee.musicplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class MediaPlayingThread implements Callable {
    private MediaPlayerExt player1 = null;
    private final String mediaFile;

    public MediaPlayingThread(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    public MediaPlayingThread(MediaPlayerExt player1, String mediaFile) {
        this.player1 = player1;
        this.mediaFile = mediaFile;
    }

    public MediaPlayerExt call1() {
        System.out.println("Playing media on thread : " + Thread.currentThread().getName());
        try {
            System.out.println("Playing media " + mediaFile);
            player1.play(mediaFile);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return player1;
    }

    @Override
    public MediaPlayer call() {
        MediaPlayer player = new MediaPlayer(new Media(Paths.get(mediaFile).toUri().toString()));
        player.play();
        return player;
    }
}

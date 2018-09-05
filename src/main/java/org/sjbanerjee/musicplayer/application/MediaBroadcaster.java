package org.sjbanerjee.musicplayer.application;

import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.sjbanerjee.musicplayer.MediaPlayingTask;
import org.sjbanerjee.musicplayer.MediaTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MediaBroadcaster extends Application implements Runnable{
    private static final String SOURCE = "C://Users/Public/Music/Sample Music";
//    private static final String SOURCE = "/media/sf_vm_share";
    private static final ExecutorService mediaExecutor = Executors.newSingleThreadExecutor();
//    private static final ExecutorService statusExecutor = Executors.newSingleThreadExecutor();
    private static final List<File> mediaList = new ArrayList<>();
//    private static MediaBroadcaster _instance = null;
    private static MediaPlayingTask _currentTask = null;

    /*public MediaBroadcaster(){
        _instance = this;
    }*/

    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        discoverMedia(SOURCE);

        if (!mediaList.isEmpty()) {
            //Iterate over the list and play songs
            mediaList.forEach(mediaFile -> {
                try {
                    startBroadcast(mediaFile.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Broadcast Exception!!");
                    e.printStackTrace();
                }
            });
        } else {
            System.out.println("Nothing to play!");
        }
    }

    private void startBroadcast(String mediaFile) throws Exception {
        //Start the media
        _currentTask = new MediaPlayingTask(mediaFile);
        Future<MediaPlayer> result = mediaExecutor.submit(_currentTask);

//        mediaExecutor.shutdown();
//        statusExecutor.shutdown();
    }

    public void discoverMedia(String source) {
        Path dir = Paths.get(source);
        try {
            Files.list(dir).forEach(path -> {
                if (Files.isDirectory(path)) {
                    discoverMedia(path.toAbsolutePath().toString());
                } else {
                    if (filterMediaFiles(path.getFileName().toString())) {
                        mediaList.add(path.toFile());
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Invalid Path : " + source);
        }
    }

    private boolean filterMediaFiles(String fileName) {
        for (MediaTypes mediaType : MediaTypes.values()) {
            if (fileName.toUpperCase().endsWith(mediaType.name())) {
                return true;
            }
        }

        return false;
    }

    public static MediaPlayingTask getCurrentTask(){
        return _currentTask;
    }
}

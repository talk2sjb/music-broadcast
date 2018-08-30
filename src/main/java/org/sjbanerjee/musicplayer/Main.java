package org.sjbanerjee.musicplayer;

import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

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

public class Main extends Application {
    private static final String SOURCE = "C://Users/Public/Music/Sample Music";
    private static final ExecutorService mediaExecutor = Executors.newSingleThreadExecutor();
   private static final ExecutorService statusExecutor = Executors.newSingleThreadExecutor();
    private static final List<File> mediaList = new ArrayList<>();
//    private MediaPlayerExt player = new MediaPlayerExt();

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        discoverMedia(SOURCE);

        if(!mediaList.isEmpty()) {
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

    private void startBroadcast(String mediaFile) throws Exception{
        //Start the media
//        ExecutorService mediaExecutor = Executors.newSingleThreadExecutor();
//        Callable<MediaPlayerExt> callable = new MediaPlayingThread(player, mediaFile);
        Callable<MediaPlayer> callable = new MediaPlayingThread(mediaFile);
        Future<MediaPlayer> result = mediaExecutor.submit(callable);

        //Start tracking the status
        Callable<String> statusChecker = new StatusCheckingThread(result.get());
        statusExecutor.submit(statusChecker);

//        mediaExecutor.shutdown();
//        statusExecutor.shutdown();
    }

    public void discoverMedia(String source){
        Path dir = Paths.get(source);
        try {
            Files.list(dir).forEach(path -> {
                if (Files.isDirectory(path)){
                    discoverMedia(path.toAbsolutePath().toString());
                } else {
                    if(filterMediaFiles(path.getFileName().toString())){
                        mediaList.add(path.toFile());
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Invalid Path : " + source);
        }
    }

    private boolean filterMediaFiles(String fileName){
        for (MediaTypes mediaType : MediaTypes.values()) {
            if (fileName.toUpperCase().endsWith(mediaType.name())){
                return true;
            }
        }

        return false;
    }
}

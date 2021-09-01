package com.bisxsh.whosthatpixelmon.managers;

import com.bisxsh.whosthatpixelmon.Whosthatpixelmon;
import org.spongepowered.api.scheduler.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TimeManager {

    private Whosthatpixelmon mainClass;

    public TimeManager(Whosthatpixelmon mainClass) {
        this.mainClass = mainClass;
    }

    private int getTimeInterval() throws IOException {
        ConfigManager configManager = new ConfigManager();
        configManager.loadTimeIntervals();
        int minTime = configManager.getMinTime()*60;
        int maxTime = configManager.getMaxTime()*60;

        Random random = new Random();
        int timeInterval = random.nextInt(maxTime-minTime)+minTime;
        return timeInterval;
    }

    public void setChatGameTimer() throws IOException {
        int timeInterval = getTimeInterval();
        timeInterval = 5; //for testing purposes

        Task startGameTask = Task.builder().delay(timeInterval, TimeUnit.SECONDS)
                .name("WhosThatPixelmon - Setting up timer for ChatGame").execute(
                        task -> {
                            ChatGameManager chatGameManager;
                            try {
                                chatGameManager = new ChatGameManager(mainClass);
                                chatGameManager.startChatGame();
                            } catch (IOException | URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                ).submit(mainClass);

        //
    }

}

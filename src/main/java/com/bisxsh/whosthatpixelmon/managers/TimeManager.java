package com.bisxsh.whosthatpixelmon.managers;

import com.bisxsh.whosthatpixelmon.Whosthatpixelmon;
import org.spongepowered.api.scheduler.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TimeManager {

    private Whosthatpixelmon mainClass;

    private static TimeManager INSTANCE = null;

    public static TimeManager getInstance() {
        return INSTANCE;
    }

    public TimeManager() {
        this.mainClass = Whosthatpixelmon.getInstance();
        INSTANCE = this;
    }

    private int getTimeInterval() throws IOException {
        ConfigManager configManager = new ConfigManager();
        configManager.loadTimeIntervals(mainClass);
        int minTime = configManager.getMinTime()*60;
        int maxTime = configManager.getMaxTime()*60;

        if (minTime == maxTime) {
            return minTime;
        }

        Random random = new Random();
        int timeInterval = random.nextInt(maxTime-minTime)+minTime;
        return timeInterval;
    }

    public void setChatGameTimer() throws IOException {
        int timeInterval = getTimeInterval();

        Task.builder().delay(timeInterval, TimeUnit.SECONDS)
                .name("WhosThatPixelmon - Setting up timer for ChatGame").execute(
                        task -> {
                            try {
                                ChatGameManager chatGameManager = new ChatGameManager();
                                chatGameManager.startChatGame();
                            } catch (IOException | URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                ).submit(mainClass);

        //
    }



}

package dev.manpreet.apache.sseclient.utils;

import dev.manpreet.apache.sseclient.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestUtils {

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException excObj) {
            log.error("Exception occurred while waiting for " + seconds + " seconds", excObj);
        }
    }

    public static void logEvent(Event event) {
        log.info("EVENT ID: " + event.getId());
        log.info("EVENT TYPE: " + (event.getEvent() == null ? "" : event.getEvent()));
        log.info("EVENT RETRY ms: " + event.getRetry());
        log.info("EVENT DATA: " + event.getData());
    }
}

package org.apache.client.sse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.file.Paths;

/**
 * Unit tests to check that the SseEntity processes the char buffers correctly.
 */
public class SseEntityTest {

    private final SseEntity sseEntity = new SseEntity(null);

    @BeforeClass
    public void pushBuffer() throws IOException {
        String eventsFilePath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "raw_events.txt").toString();
        String content = FileUtils.readFileToString(new File(eventsFilePath), "UTF-8");
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put(content);
        buffer.rewind();
        sseEntity.pushBuffer(buffer, null);
        System.out.println("Size: " + sseEntity.getEvents().size());
    }

    @Test(groups = { "unit" })
    public void testLastEventIDMatch() {
        Assert.assertEquals(sseEntity.getLastEventId(), "dsjfnwuis");
    }

    @Test(groups = { "unit" })
    public void testEventsCreated() {
        Assert.assertTrue(sseEntity.hasMoreEvents());
        Assert.assertEquals(sseEntity.getEvents().size(), 5);
    }

    @Test(groups = { "unit" }, dependsOnMethods = { "testEventsCreated" })
    public void testEventParsing() throws JsonProcessingException {
        Event firstEvent = sseEntity.getEvents().poll();
        Assert.assertNotNull(firstEvent);
        Assert.assertEquals(firstEvent.getId(), "sdjkf36ff");
        Assert.assertEquals(firstEvent.getEvent(), "added_account");
        Assert.assertEquals(firstEvent.getRetry(), 5000);

        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jsonNode = jsonMapper.readTree(firstEvent.getData());
        Assert.assertEquals(jsonNode.get("account_name").asText(), "event_entity_1");
        Assert.assertEquals(jsonNode.get("owner").asText(), "kings_landing@nfire.com");
        Assert.assertEquals(jsonNode.get("type").asText(), "basic");
    }

    @Test(groups = { "unit" }, dependsOnMethods = { "testEventParsing" })
    public void testEventQueue() {
        Assert.assertEquals(sseEntity.getEvents().size(), 4);
        Assert.assertEquals(sseEntity.getEvents().poll().getId(), "kjewf438sd");
        Assert.assertEquals(sseEntity.getEvents().poll().getId(), "sljf48fsmc");
        Assert.assertEquals(sseEntity.getEvents().poll().getId(), "jfu438fkjv");
        Assert.assertEquals(sseEntity.getEvents().poll().getId(), "dsjfnwuis");
        Assert.assertFalse(sseEntity.hasMoreEvents());
    }

}

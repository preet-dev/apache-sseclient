package dev.manpreet.apache.sseclient;

import lombok.extern.slf4j.Slf4j;
import dev.manpreet.apache.sseclient.utils.TestUtils;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class SseTest {

    private final String SSEUri = "http://abrandao.com/lab/Javascript/Javascript_SSEvents/sserver_cpu.php";
    private final int MAX_CONCURRENT_SSE_STREAMS = 6;
    private CloseableHttpAsyncClient asyncClient;
    private ApacheHttpSseClient sseClient;

    @Test(groups = { "system" })
    public void testSSEClient() throws ExecutionException, InterruptedException {
        //Initialize client
        asyncClient = HttpAsyncClients.createDefault();
        asyncClient.start();
        sseClient = new ApacheHttpSseClient(asyncClient, Executors.newFixedThreadPool(MAX_CONCURRENT_SSE_STREAMS));

        //Initialize request
        SseRequest sseRequest = new SseRequest(SSEUri);
        Future<SseResponse> sseResponseFuture = sseClient.execute(sseRequest);

        //Add a 10 second delay for some events to be processed by the streaming client
        TestUtils.sleep(10);

        //Validate events exists and log them
        SseResponse response = sseResponseFuture.get();
        SseEntity responseEntity = response.getEntity();
        Assert.assertTrue(responseEntity.hasMoreEvents(), "Expected event stream to have some events");
        BlockingQueue<Event> eventList = responseEntity.getEvents();
        for (Event eachEvent: eventList) {
            TestUtils.logEvent(eachEvent);
        }
    }

}

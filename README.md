# apache-sseclient

This is a wrapper around the Apache HTTP components async client to listen for and process [Server-Sent Events](https://www.w3.org/TR/eventsource/).

### Usage

```implementation 'dev.manpreet:apache-sseclient:1.0'```

Create an instance of the async client & start it.
```
CloseableHttpAsyncClient asyncClient = HttpAsyncClients.createDefault();
asyncClient.start();
```

Create the request.
```
SseRequest request = new SseRequest(YOUR_REQUEST_URI);
```

Create the SSE client instance and provide `ExecutorService` instance to run the requests in a new thread.
```
ApacheHttpSseClient sseClient = new ApacheHttpSseClient(asyncClient, Executors.newFixedThreadPool(CONCURRENT_SSE_STREAMS));
```

Execute the request and get the `Future` response instance.
```
Future<SseResponse> sseResponse = sseClient.execute(request);
```

### Authors

Created by [@ytoh](https://github.com/ytoh) & [@manpreet333](https://github.com/manpreet333) when in the middle of a project we realized Apache client doesn't have this functionality.

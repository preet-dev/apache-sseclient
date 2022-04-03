# apache-sseclient

[![Java CI with Gradle](https://github.com/autopreet/apache-sseclient/actions/workflows/gradle.yml/badge.svg)](https://github.com/autopreet/apache-sseclient/actions/workflows/gradle.yml)
![Sonar Coverage](https://img.shields.io/sonar/coverage/autopreet_apache-sseclient/master?server=https%3A%2F%2Fsonarcloud.io)
![GitHub last commit](https://img.shields.io/github/last-commit/autopreet/apache-sseclient)
![GitHub all releases](https://img.shields.io/github/downloads/autopreet/apache-sseclient/total)

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/autopreet/apache-sseclient)
![GitHub](https://img.shields.io/github/license/autopreet/apache-sseclient)
![GitHub Repo stars](https://img.shields.io/github/stars/autopreet/apache-sseclient?style=social)

This is a wrapper around the Apache HTTP components async client to send [Server-Sent Events](https://www.w3.org/TR/eventsource/).

### Usage

```implementation 'dev.manpreet:apache-sseclient:1.0'```

Create an instance of the async client & start it
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

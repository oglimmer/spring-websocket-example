# Websocket with @ServerEndpoint and Spring

This is a super minimalistic example how to use plain Java @ServerEndpoint with Spring and its Application Context

## Overview

* Java 11, Gradle, Spring and Websockets
* WebSocket endpoint under `/channels/{channelId}/{participantId}`

## How to run the server

* `./gradlew bootRun`

This opens the Websocket endpoint. Use the test client

## How to use the test client

There is a simple html client under src/html-test-client

* Step into src/html-test-client
* exec `npm i`
* exec `node index.js`

* open Firefox at http://localhost:3000/?channelId=1&participantId=1
* open Chrome at http://localhost:3000/?channelId=1&participantId=2


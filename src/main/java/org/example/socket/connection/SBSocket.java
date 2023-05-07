package org.example.socket.connection;

import org.example.socket.connection.listener.SBSocketListener;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public final class SBSocket {
    private static WebSocket ws;

    private SBSocket () {
        this.startSocketConnection();
    }

    public static synchronized WebSocket getSocketInstance () {
        if (ws == null) {
            new SBSocket();
        }
        return ws;
    }

    private void startSocketConnection () {
        try {
            CompletableFuture<WebSocket> webSocketCompletableFuture = HttpClient
                    .newHttpClient()
                    .newWebSocketBuilder()
                    .buildAsync(URI.create("wss://api-v2.blaze.com/replication/?EIO=3&transport=websocket"), new SBSocketListener());
            ws = webSocketCompletableFuture.get();
            while (!ws.isInputClosed()) {
                try {
                    sleep(2500);
                    ws.sendText("2", true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public WebSocket getWebSocket () {
        return ws;
    }
}

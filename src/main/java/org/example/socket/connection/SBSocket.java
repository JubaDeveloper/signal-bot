package org.example.socket.connection;

import org.example.bot.SignalBot;
import org.example.socket.connection.listener.SBSocketListener;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public final class SBSocket extends Thread {
    private static WebSocket ws;

    private SBSocket (SignalBot signalBot) {
        this.startSocketConnection(signalBot);
    }

    public static synchronized WebSocket getSocketInstance (SignalBot signalBot) {
        if (ws == null) {
            new SBSocket(signalBot);
        }
        return ws;
    }

    private void startSocketConnection (SignalBot signalBot) {
        try {
            CompletableFuture<WebSocket> webSocketCompletableFuture = HttpClient
                    .newHttpClient()
                    .newWebSocketBuilder()
                    .buildAsync(URI.create("wss://api-v2.blaze.com/replication/?EIO=3&transport=websocket"), new SBSocketListener(signalBot));
            ws = webSocketCompletableFuture.get();
            this.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (!ws.isInputClosed()) {
            try {
                sleep(2500);
                ws.sendText("2", true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public WebSocket getWebSocket () {
        return ws;
    }
}

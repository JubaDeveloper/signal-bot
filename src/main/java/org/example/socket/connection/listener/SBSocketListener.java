package org.example.socket.connection.listener;

import org.example.bot.SignalBot;

import static java.lang.Thread.sleep;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class SBSocketListener implements WebSocket.Listener {
    StringBuilder message = new StringBuilder();
    SignalBot signalBot;

    public SBSocketListener () {
        this.signalBot = new SignalBot();
    }
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        if (last) {
            if (message.length() > 0) {
                this.signalBot.onMessage(message.toString());
            }
            message.replace(0, message.length(), "");
        } else {
            message.append(data);
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        this.sendConnectionPackages(webSocket);
        WebSocket.Listener.super.onOpen(webSocket);
    }

    private void sendConnectionPackages (WebSocket ws) {
        ws.sendText("420[\"cmd\",{\"id\":\"subscribe\",\"payload\":{\"room\":\"double_v2\"}}]", true);
    }
}

package org.example.socket.connection.listener;

import org.example.bot.SignalBot;

import static java.lang.Thread.sleep;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class SBSocketListener implements WebSocket.Listener {
    private final StringBuilder message = new StringBuilder();
    String color;
    String status;
    private SignalEvent signalEvent;

    public SBSocketListener (SignalEvent signalEvent) {
        this.signalEvent = signalEvent;
    }
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        if (last) {
            if (message.length() > 0) {
                String[] splitResult = resolveMessage(message.toString()).split(",");
                if (!splitResult[0].equals(color) || !splitResult[1].equals(status)) {
                    this.color = splitResult[0];
                    this.status =splitResult[1];
                    this.signalEvent.onSignal(color, status);
                }
            }
            message.replace(0, message.length(), "");
        } else {
            message.append(data);
        }
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    private String resolveMessage (String message) {
        String status = message.split("status\":\"")[1].split("\"")[0];
        String color = message.split("\"color\":")[1].split("\"")[0]
                .replace(",", "")
                .replaceAll("\\s", "")
                .trim();
        return String.format("%s,%s", color, status);
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

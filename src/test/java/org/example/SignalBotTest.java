package org.example;

import org.example.bot.SignalBot;
import org.example.socket.connection.SBSocket;
import org.junit.Test;

import java.net.http.WebSocket;

public class SignalBotTest {

    @Test
    public void socketConnection () throws InterruptedException {
        SignalBot sg = new SignalBot();
        WebSocket webSocket = SBSocket.getSocketInstance(sg);
        Thread.sleep(500);
        assert (!webSocket.isInputClosed());
        webSocket.abort();
        assert (webSocket.isInputClosed());
    }
}

package org.example;

import org.example.bot.SignalBot;
import org.example.socket.connection.SBSocket;

public class App {
    public static void main (String[] args) {
        SignalBot sg = new SignalBot();
        SBSocket.getSocketInstance(sg);
    }
}

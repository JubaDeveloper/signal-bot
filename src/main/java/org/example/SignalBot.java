package org.example;

import org.example.socket.connection.SBSocket;

import java.net.http.WebSocket;

public class SignalBot
{
    public static void main(String[] args) {
        WebSocket sbSocket = SBSocket.getSocketInstance();
    }
}

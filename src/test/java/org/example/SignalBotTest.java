package org.example;

import org.example.socket.connection.SBSocket;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.assertTrue;

public class SignalBotTest {

    @Test
    public void connectionTest () {

    }

    public static void main (String[] args) {
        JUnitCore jUnitCore = new JUnitCore();
        jUnitCore.addListener(new TextListener(System.out));
        jUnitCore.run(SignalBotTest.class);
    }
}

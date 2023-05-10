package org.example.bot;

import org.example.socket.connection.listener.SignalEvent;

import java.util.HashMap;
import java.util.Map;

enum Status {
    rolling,
    waiting,
    complete
}
public class SignalBot implements SignalEvent {
    static Map<Integer, String> mappedColors = new HashMap<Integer, String>();
    static {
        mappedColors.put(1, "Red");
        mappedColors.put(2, "Black");
        mappedColors.put(3, "White");
    }
    public SignalBot () {}

    @Override
    public void onSignal(String color, String status) {
        System.out.format("%s %s \n",color, status);
    }
}

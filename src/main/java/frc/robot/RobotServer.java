package frc.robot;

import org.java_websocket.server.WebSocketServer;

public class RobotServer extends WebSocketServer {
    public void onStart() {
        System.out.println("WebSocket RobotServer started");
    }

    public void onClose() {
        System.out.println("Connection closed");
    }
}
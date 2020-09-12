package frc.robot.pi_client;

import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * WebSocket client class for connecting to the Pi (for vision)
 */
public class PiWebSocketClient extends WebSocketClient {
    public PiWebSocketClient(PiClient client, URI uri) {
        super(uri);
    }

    /*public static PiWebSocketClient create(String uri) {
        try {
            return new PiWebSocketClient(new URI(uri));
        } catch (Exception ex) {
            System.out.println("Could not create Pi client: " + ex);
            return null;
        }
    }*/

    public void onClose(int code, String message, boolean bool) {
        System.out.println("Connection closed");
    }

    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to Pi");
    }

    public void onMessage(String message) {
        // json parse the message

    }

    public void onError(Exception ex) {
        System.out.println("Pi Client Error: " + ex);
    }
}
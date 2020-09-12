package frc.robot.pi_client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import frc.robot.Constants;

public class PiClient {
    static final ObjectMapper objectMapper = new ObjectMapper();
    private List<Consumer<VisionStatus>> consumers = new ArrayList<>();
    private List<VisionObserver> observers = new ArrayList<>();
    private PiWebSocketClient socketClient;
    private VisionStatus status = new VisionStatus();
    private ObjectReader updateReader;

    private class PiWebSocketClient extends WebSocketClient {
        public PiWebSocketClient(URI uri) {
            super(uri);
        }

        public void onClose(int code, String message, boolean bool) {
            System.out.println("Connection closed");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            PiClient.this.connect();
        }

        public void onOpen(ServerHandshake handshake) {
            System.out.println("Connected to Pi");

        }

        public void onMessage(String message) {
            updateStatus(message);
        }

        public void onError(Exception ex) {
            System.out.println("Pi Client Error: " + ex);
        }
    }

    public PiClient() {
        this.updateReader = objectMapper.readerForUpdating(status);
        connect();
        /*new Thread(() -> {
            for (;;) {
                try {
                    this.socketClient.connect();
                    System.out.println("Connected to Pi!");
                    break;
                } catch (Exception ex) {
                    System.out.println("Connection failed: " + ex);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();   */
    }

    private void connect() {
        this.socketClient = new PiWebSocketClient(URI.create(Constants.PiClient.uri));
        this.socketClient.connect();
    }

    private void updateStatus(String newStatus) {
        try {
            this.updateReader.readValue(newStatus);
            System.out.println("Got new status: " + newStatus);

            dispatchUpdates();
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing JSON");
            e.printStackTrace();
        }
    }

    public void dispatchUpdates() {
        for (Consumer<VisionStatus> consumer : consumers) {
            consumer.accept(status);
        }

        for (VisionObserver observer : observers) {
            observer.onVisionUpdate(status);
        }
    }

    public void onVisionUpdate(Consumer<VisionStatus> consumer) {
        this.consumers.add(consumer);
    }

    public void onVisionUpdate(VisionObserver observer) {
        this.observers.add(observer);
    }
}
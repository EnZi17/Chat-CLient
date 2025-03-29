import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

public class JavaWebSocketClient {
    public static void main(String[] args) throws InterruptedException {
        String conversationId = "67e2073b435a0e089f051e37";

        // Tạo hai thread chạy song song hai WebSocket client
        Thread clientA = new Thread(() -> startWebSocketClient("67cac2e7e5dd0ec7666d2cc3", conversationId));
        Thread clientB = new Thread(() -> startWebSocketClient("67cac2eee5dd0ec7666d2cc5", conversationId));

        clientA.start();
        clientB.start();

        // Giữ chương trình chạy
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void startWebSocketClient(String clientId, String conversationId) {
        try {
            URI serverUri = new URI("ws://localhost:5000");
            CountDownLatch latch = new CountDownLatch(1); // Giữ chương trình chờ tin nhắn

            WebSocketClient client = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Client " + clientId + " connected to WebSocket server");
                    
                    // Gửi tin nhắn "joinRoom"
                    send("{\"type\": \"joinRoom\", \"conversationId\": \"" + conversationId + "\", \"id\": \"" + clientId + "\"}");

                    // Gửi tin nhắn "sendMessage"
                    send("{\"type\": \"sendMessage\", \"sender\": \"" + clientId + "\", \"content\": \"Hello from " + clientId + "!\", \"conversationId\": \"" + conversationId + "\"}");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Client " + clientId + " received: " + message);
                    latch.countDown(); // Đánh dấu nhận được tin nhắn
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Client " + clientId + " disconnected: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("Client " + clientId + " error: " + ex.getMessage());
                }
            };

            client.connectBlocking(); // Chờ kết nối thành công
            latch.await(); // Chờ tin nhắn đến
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class JavaWebSocketClient {
    public static void main(String[] args) throws InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket ws = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://localhost:5000"), new WebSocket.Listener() {
                    @Override
                    public void onOpen(WebSocket webSocket) {
                        System.out.println("Connected to WebSocket server");
                        webSocket.sendText("{\"type\": \"joinRoom\", \"conversationId\": \"room123\"}", true);
                        webSocket.sendText("{\"type\": \"sendMessage\", \"sender\": \"User1\", \"content\": \"Hello!\", \"conversationId\": \"room123\"}", true);
                    }

                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        System.out.println("Received: " + data);
                        return WebSocket.Listener.super.onText(webSocket, data, last);
                    }

                    @Override
                    public void onError(WebSocket webSocket, Throwable error) {
                        System.err.println("WebSocket error: " + error.getMessage());
                    }
                }).join();

        // Giữ kết nối 10 giây để nhận tin nhắn
        Thread.sleep(10000);
        ws.abort();
    }
}

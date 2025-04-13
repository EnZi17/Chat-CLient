package mysocket;

import java.net.URI;

import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import controller.IndexController;
import view.Index;



public class MySocket extends WebSocketClient{
	String id;
	
	public MySocket(URI serverUri, String id) {
		super(serverUri);
		this.id = id;
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub
		
	}


	public void onMessage(String message) {
	    JSONObject json = new JSONObject(message);
	    
	    if (json.getString("type").equals("newMessage")) {
	        JSONObject msg = json.getJSONObject("message");

	        String content = myUtil.SimpleAES.decrypt(msg.optString("content", ""));
	        boolean hasAttachments = msg.has("attachments") && msg.getJSONArray("attachments").length() > 0;

	        if (hasAttachments) {
	            // Lấy danh sách file base64
	            for (int i = 0; i < msg.getJSONArray("attachments").length(); i++) {
	                String base64 = msg.getJSONArray("attachments").getString(i);
	                IndexController.getInstance().chatBubblePanel.addFile(base64,  false);
	            }
	        } else {
	            IndexController.getInstance().chatBubblePanel.addMessage(content, false);
	        }

	        // Cuộn xuống cuối chat sau khi thêm tin nhắn
	        SwingUtilities.invokeLater(() -> {
	            JScrollBar verticalBar = IndexController.getInstance().scrollPane1.getVerticalScrollBar();
	            verticalBar.setValue(verticalBar.getMaximum());
	        });
	    }
	}


	@Override
	public void onOpen(ServerHandshake arg0) {
		// TODO Auto-generated method stub
		System.out.println("Client "+ " connected to WebSocket server");
	}
	
	public void joinRoom(String conversationId) {
		send("{\"type\": \"joinRoom\", \"conversationId\": \"" + conversationId + "\", \"id\": \"" + id + "\"}");
	}
	
	public void sendMessage(String content, String conversationId) {
		send("{\"type\": \"sendMessage\", \"sender\": \"" + id + "\", \"content\": \""+content+"\", \"conversationId\": \"" + conversationId + "\"}");
	}
	
	public void sendFile(String file, String conversationId) {
		send("{\"type\": \"sendMessage\", \"sender\": \"" + id +"\", \"content\": \""+" "+ "\",\"attachments\": [\""+file+"\"], \"conversationId\": \"" + conversationId + "\"}");
	}
}

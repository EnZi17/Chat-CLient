package service;

import java.awt.print.Printable;
import java.time.Instant;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import model.Conversation;
import model.Message;
import model.User;

public class AuthService {
    private static final String BASE_API = "http://localhost:5000";

    public static String login(String email, String password) {
        String url = BASE_API + "/users/login";
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);

        String response = myUtil.Util.postApi(url, requestBody.toString());

        // Kiểm tra lỗi phản hồi từ API
        switch (response) {
            case "ERROR! Response Code: 400":
                return "Email does not exist";
            case "ERROR! Response Code: 401":
                return "Wrong password";
            default:
                return response;
        }
    }

    public static String signUp(String email, String password, String username) {
        String url = BASE_API + "/users/register";

        // Tạo request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("username", username);

        // Gửi request
        String response = myUtil.Util.postApi(url, requestBody.toString());

        switch (response) {
		case "ERROR! Response Code: 500": {
			return "Email exited";
		}
		default:
			return "OK";
		}
    }
    
    public static void setStatus(String id, Boolean isOnline) {
		String  url = BASE_API+"/users/status";
		JSONObject requestBody = new JSONObject();
		requestBody.put("userId", id);
	    requestBody.put("isOnline", isOnline);
	    String response = myUtil.Util.postApi(url, requestBody.toString());
	}
    
    public static ArrayList<Conversation> getConversations(String id) {
    	String url = BASE_API + "/conversations/" + id;
    	ArrayList<Conversation> result = new ArrayList<>();
	    String response = myUtil.Util.getApi(url);
	    
	    System.out.println(response);
	    JSONArray jsonArray = new JSONArray(response);
	    for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String convoId = obj.getString("_id");

            JSONArray participantsJson = obj.getJSONArray("participants");
            ArrayList<String> participants = new ArrayList<>();
            for (int j = 0; j < participantsJson.length(); j++) {
                participants.add(participantsJson.getString(j));
            }

            boolean isGroup = obj.optBoolean("isGroup", false);
            String name=obj.optString("name","");
            Instant createdAt = Instant.parse(obj.getString("createdAt"));
            Instant updatedAt = Instant.parse(obj.getString("updatedAt"));

            Conversation conversation = new Conversation(convoId, participants, isGroup,name, createdAt, updatedAt);
            result.add(conversation);
    }
	    return result;
}
    
    public static ArrayList<Message> getMessages(String conversationId) {
        String url = BASE_API + "/messages/" + conversationId;
        ArrayList<Message> result = new ArrayList<>();

        String response = myUtil.Util.getApi(url);
        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String id = obj.getString("_id");
            JSONObject senderObj = obj.getJSONObject("sender");
            String sender = senderObj.getString("_id");

            String content = obj.getString("content");
            String convoId = obj.getString("conversation");

            JSONArray readByJson = obj.optJSONArray("readBy");
            ArrayList<String> readBy = new ArrayList<>();
            if (readByJson != null) {
                for (int j = 0; j < readByJson.length(); j++) {
                    readBy.add(readByJson.getString(j));
                }
            }

            JSONArray attachJson = obj.optJSONArray("attachments");
            ArrayList<String> attachments = new ArrayList<>();
            if (attachJson != null) {
                for (int j = 0; j < attachJson.length(); j++) {
                    attachments.add(attachJson.getString(j));
                }
            }

            Instant createdAt = Instant.parse(obj.getString("createdAt"));
            Instant updatedAt = Instant.parse(obj.getString("updatedAt"));

            Message message = new Message(id, sender, content, convoId, readBy, attachments, createdAt, updatedAt);
            result.add(message);
        }

        return result;
    }
    public static String getUserStatus(String conversationId, String currentUserId) {
        String url = BASE_API + "/users/status/by-conversation/" + conversationId + "?userId=" + currentUserId;
        String response = myUtil.Util.getApi(url);

        // Trả thẳng JSON string để xử lý ở UI/lớp khác
        return response;
    }
    public static User getUserByID(String id) {
        String url = BASE_API + "/users/" + id;
        String response = myUtil.Util.getApi(url);
        JSONObject obj = new JSONObject(response);

        String userId = obj.getString("_id");
        String username = obj.optString("username", "");
        String email = obj.optString("email", "");
        String password = obj.optString("password", ""); 
        String avatar = obj.optString("avatar", "");
        boolean isOnline = obj.optBoolean("isOnline", false);

        Instant lastOnline = null;
        if (!obj.isNull("lastOnline")) {
            lastOnline = Instant.parse(obj.getString("lastOnline"));
        }

        ArrayList<String> friends = new ArrayList<>();
        JSONArray friendsArray = obj.optJSONArray("friends");
        if (friendsArray != null) {
            for (int i = 0; i < friendsArray.length(); i++) {
                friends.add(friendsArray.getString(i));
            }
        }

        return new User(userId, username, email, password, avatar, isOnline, lastOnline, friends);
    }
    
    public static User getUserByEmail(String email) {
        String url = BASE_API + "/users/by-email/" + email;

        try {
            String response = myUtil.Util.getApi(url);
            JSONObject obj = new JSONObject(response);

            String userId = obj.getString("_id");
            String username = obj.optString("username", "");
            String userEmail = obj.optString("email", "");
            String password = obj.optString("password", "");
            String avatar = obj.optString("avatar", "");
            boolean isOnline = obj.optBoolean("isOnline", false);

            Instant lastOnline = null;
            if (!obj.isNull("lastOnline")) {
                lastOnline = Instant.parse(obj.getString("lastOnline"));
            }

            ArrayList<String> friends = new ArrayList<>();
            JSONArray friendsArray = obj.optJSONArray("friends");
            if (friendsArray != null) {
                for (int i = 0; i < friendsArray.length(); i++) {
                    friends.add(friendsArray.getString(i));
                }
            }

            return new User(userId, username, userEmail, password, avatar, isOnline, lastOnline, friends);

        } catch (Exception e) {
            // Kiểm tra nếu response là lỗi 404 (nếu Util ném ngoại lệ có thông tin này)
            if (e.getMessage() != null && e.getMessage().contains("404")) {
                return null;
            }

            // Log lỗi và có thể xử lý thêm nếu cần
            e.printStackTrace();
            return null;
        }
    }

    public static String createConversation(ArrayList<String> participantIds, boolean isGroup, String name) {
        String url = BASE_API + "/conversations";

        JSONObject requestBody = new JSONObject();
        requestBody.put("participants", new JSONArray(participantIds));
        requestBody.put("isGroup", isGroup);

        if (name != null && !name.isEmpty()) {
            requestBody.put("name", name);
        }

        try {
            String response = myUtil.Util.postApi(url, requestBody.toString());
            JSONObject responseObject = new JSONObject(response);

            String conversationId = responseObject.getString("_id"); // Lấy conversationId
            System.out.println("Tạo conversation thành công. ID: " + conversationId);
            return conversationId;

        } catch (Exception e) {
            System.err.println("Lỗi khi tạo conversation: " + e.getMessage());
            return null;
        }
    }

    

   
    public static String resetPassword(String email) {
        String url = BASE_API + "/users/forgot-password"; 

        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);

        
        String response =myUtil.Util.postApi(url, requestBody.toString());

       
        switch (response) {
            case "ERROR! Response Code: 400":
                return "Vui lòng cung cấp email hợp lệ";
            case "ERROR! Response Code: 404":
                return "Không tìm thấy người dùng với email này";
            case "ERROR! Response Code: 500":
                return "Có lỗi xảy ra trong quá trình xử lý";
            default:
                return "✅ Mật khẩu mới đã được gửi qua email";
        }
    }





}

package service;

import org.json.JSONObject;

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
                JSONObject json = new JSONObject(response);
                return json.getString("userId");
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
}

package service;

import org.json.JSONObject;

public class AuthService {
	 static String baseApi="http://localhost:5000";
	
    public static String login(String email, String password) {
        String url = baseApi+"/users/login";
        String response = myUtil.Util.postApi(url,"{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}" );
        System.out.println(1);
        if(response.equals("ERROR! Response Code: 400")) {
        	return "Email is not exist";
        }else if(response.equals("ERROR! Response Code: 401")) {
        	return "Wrong password";
        }
        JSONObject json = new JSONObject(response);
        String userId = json.getString("userId");
        return userId;
    }
}


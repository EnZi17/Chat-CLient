package myUtil;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {
	
	public static boolean checkEmail(String email) {
		return email.endsWith("@gmail.com");
	}
	
	public static boolean checkPassword(String password) {
	    if (password == null || password.length() < 6) {
	        return false;
	    }

	    boolean hasUppercase = false;
	    boolean hasSpecialChar = false;
	    boolean hasDigit = false;

	    for (char c : password.toCharArray()) {
	        if (Character.isUpperCase(c)) {
	            hasUppercase = true;
	        } else if (!Character.isLetterOrDigit(c)) {
	            hasSpecialChar = true;
	        } else if (Character.isDigit(c)) {
	            hasDigit = true;
	        }
	    }

	    return hasUppercase && hasSpecialChar && hasDigit;
	}

	
    public static String getApi(String api) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } else {
                return "ERROR! Response Code: " + responseCode;
            }
        } catch (Exception e) {
            return "ERROR! Exception: " + e.getMessage();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                // Ignore close exceptions
            }
        }
    }

    public static String postApi(String api, String jsonData) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);  

            
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            } else {
                return "ERROR! Response Code: " + responseCode;
            }
        } catch (Exception e) {
            return "ERROR! Exception: " + e.getMessage();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                // Ignore close exceptions
            }
        }
    }
}

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.User;
import view.Index;
import view.SignIn;
import view.SignUp;

public class SignInController implements ActionListener{
	SignIn signIn;
	
	public SignInController(SignIn signIn) {
		this.signIn= signIn;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String src = e.getActionCommand();
		switch (src) {
		case "Đăng Nhập": {
			SignInManagement();
			break;
		}
		case "Đăng Ký": {
			SignUpManagement();
			break;
		}
		
	}
	}
	private void SignUpManagement() {
		// TODO Auto-generated method stub
		signIn.closeSignIn();
        SignUp window = new SignUp();
        window.frame.setVisible(true);
	}


	private void SignInManagement() {
	    // Lấy thông tin đăng nhập từ người dùng
	    String email = signIn.getTxtEmail().getText();
	    String password = signIn.getTxtPassword().getText();
	    
	    // Gọi AuthService.login để nhận phản hồi từ server (JSON)
	    String response = service.AuthService.login(email, password);

	    // Kiểm tra nếu có lỗi đăng nhập
	    if (response.equals("Wrong password")) {
	        // Xử lý khi mật khẩu sai
	    } else if (response.equals("Email does not exist")) {
	        // Xử lý khi email không tồn tại
	    } else {
	        JSONObject jsonResponse = new JSONObject(response);
	        ArrayList<String> friends = new ArrayList<>();
	        JSONArray friendsJsonArray = jsonResponse.getJSONArray("friends");

	        for (int i = 0; i < friendsJsonArray.length(); i++) {
	            friends.add(friendsJsonArray.getString(i));
	        }
	        User user = new User(
	            jsonResponse.getString("_id"),
	            jsonResponse.getString("username"),
	            jsonResponse.getString("email"),
	            jsonResponse.getString("password"),  // Đảm bảo mật khẩu không được gửi trong thực tế
	            jsonResponse.optString("avatar", ""),  // Nếu không có avatar, đặt giá trị mặc định
	            jsonResponse.getBoolean("isOnline"),
	            Instant.parse(jsonResponse.getString("lastOnline")),
	            // Giả sử "friends" là một mảng các ID bạn bè
	            friends
	        );


	        // Đóng cửa sổ đăng nhập
	        signIn.closeSignIn();

	        // Tạo cửa sổ Index mới và truyền đối tượng User vào
	        Index window = new Index(user);  // Truyền user vào Index
	        service.AuthService.setStatus(user.getId(), true); // Cập nhật trạng thái online
	        window.frame.setVisible(true);  // Hiển thị cửa sổ Index
	    }
	}

	
	
}

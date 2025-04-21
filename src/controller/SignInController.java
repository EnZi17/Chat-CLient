package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
		this.signIn.closeSignIn();
        SignUp window = new SignUp();
        window.frame.setVisible(true);
	}


	private void SignInManagement() {
	    // Lấy thông tin đăng nhập từ người dùng
	    String email = signIn.getTxtEmail().getText();
	    String password = signIn.getTxtPassword().getText();
	    String newPassword =myUtil.SimpleAES.encrypt(password);

	    
	    // Gọi AuthService.login để nhận phản hồi từ server (JSON)
	    String response = service.AuthService.login(email, newPassword);
	    System.out.println(response);

	    // Kiểm tra nếu có lỗi đăng nhập
	    if (response.equals("Wrong password")) {
	    	int option = JOptionPane.showOptionDialog(
	                null,
	                "Mật khẩu không đúng. Bạn có muốn nhập lại hay quên mật khẩu?",
	                "Lỗi đăng nhập",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                new String[]{"Nhập lại", "Quên mật khẩu"},
	                "Nhập lại"
	            );

	            if (option == JOptionPane.YES_OPTION) {
	                
	            } else {
	                ForgotPassword();
	            }
	    } else if (response.equals("Email does not exist")) {
	        int option = JOptionPane.showOptionDialog(
	                null,
	                "Email không tồn tại. Bạn có muốn nhập lại hay đăng ký mới?",
	                "Lỗi đăng ký",
	                JOptionPane.YES_NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                new String[]{"Nhập lại", "Đăng ký"},
	                "Nhập lại"
	            );

	            if (option == JOptionPane.YES_OPTION) {
	                
	            } else {
	                SignUpManagement();
	            }
	        }
	    else {
	        JSONObject jsonResponse = new JSONObject(response);
	        ArrayList<String> friends = new ArrayList<>();
	        JSONArray friendsJsonArray = jsonResponse.getJSONArray("friends");

	        for (int i = 0; i < friendsJsonArray.length(); i++) {
	            friends.add(friendsJsonArray.getString(i));
	        }
	        String lastOnlineStr = jsonResponse.optString("lastOnline", null);
	        Instant lastOnline = null;
	        if (lastOnlineStr != null && !lastOnlineStr.isEmpty()) {
	            lastOnline = Instant.parse(lastOnlineStr);
	        }

	        User user = new User(
	            jsonResponse.getString("_id"),
	            jsonResponse.getString("username"),
	            jsonResponse.getString("email"),
	            jsonResponse.getString("password"),  // Đảm bảo mật khẩu không được gửi trong thực tế
	            jsonResponse.optString("avatar", ""),  // Nếu không có avatar, đặt giá trị mặc định
	            jsonResponse.getBoolean("isOnline"),
	            lastOnline,
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


	private void ForgotPassword() {
		// TODO Auto-generated method stub
		service.AuthService.resetPassword(this.signIn.getTxtEmail().getText());
		JOptionPane.showMessageDialog(null, "Mật khẩu mới đã được gửi tới email:"+this.signIn.getTxtEmail().getText(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
		this.signIn.getTxtPassword().setText("");
	}

	
	
}

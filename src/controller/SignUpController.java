package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.SignIn;
import view.SignUp;

public class SignUpController implements ActionListener{
	public SignUp signUp;
	
	public SignUp getSignUp() {
		return signUp;
	}

	public void setSignUp(SignUp signUp) {
		this.signUp = signUp;
	}

	public SignUpController(SignUp signUp) {
		this.signUp = signUp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String src = e.getActionCommand();
		System.out.println(src);
		switch (src) {
		case "Đăng Ký":{
			SignUpManagement();
			break;
		}
		case "Quay Lại":{
			ReturnManagement();
			break;
		}
		
		}
	}
		private void ReturnManagement() {
		// TODO Auto-generated method stub
			signUp.closeSignUp();
	        SignIn window = new SignIn();
	 
	        window.frame.setVisible(true);
	}

		private void SignUpManagement() {
		    String email = signUp.getTxtEmail().getText();
		    String password = signUp.getTxtPassword().getText();
		    String userName = signUp.getTxtUsername().getText();
		    
		    if (!myUtil.Util.checkEmail(email)) {
		        JOptionPane.showMessageDialog(null, "Email phải kết thúc bằng @gmail.com", "Lỗi đăng ký", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    if (!myUtil.Util.checkPassword(password)) {
		        JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 6 ký tự, gồm chữ HOA, ký tự đặc biệt và số.", "Lỗi đăng ký", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    String newPassword= myUtil.SimpleAES.encrypt(password);
		    String response = service.AuthService.signUp(email, newPassword, userName);
		    JOptionPane.showMessageDialog(null, response, "Kết quả đăng ký", JOptionPane.INFORMATION_MESSAGE);
		}


}

	

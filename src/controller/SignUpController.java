package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			// TODO Auto-generated method stub
			String email = signUp.getTxtEmail().getText();
			String password = signUp.getTxtPassword().getText();
			String userName = signUp.getTxtUsername().getText();
			String respone = service.AuthService.signUp(email,password,userName);
			System.out.println(respone);
		}

}

	

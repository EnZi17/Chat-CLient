package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Index;
import view.SignIn;
import view.SignUp;

public class SignInController implements ActionListener{
	Index index;
	SignIn signIn;
	SignUp signUp;
	
	public SignInController(SignIn signIn) {
		this.signIn= signIn;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String src = e.getActionCommand();
		System.out.println(src);
		switch (src) {
		case "Đăng Nhập": {
			SignInManagement();
			break;
		}
		case "Delete":{
			
			break;
		}
		
	}
	}
	private void SignInManagement() {
		// TODO Auto-generated method stub
		String email = signIn.getTxtEmail().getText();
		String password = signIn.getTxtPassword().getText();
		String respone=service.AuthService.login(email, password);
		
		if(respone.equals("Wrong password")) {
			
		}else if(respone.equals("Email is not exist")) {
			
		}else {
	
	        signIn.closeSignIn();
		}
	}
	
}

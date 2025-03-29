package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		System.out.println(src);
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
		// TODO Auto-generated method stub
		String email = signIn.getTxtEmail().getText();
		String password = signIn.getTxtPassword().getText();
		String respone=service.AuthService.login(email, password);
		System.out.println(respone);
		
		if(respone.equals("Wrong password")) {
			
		}else if(respone.equals("Email does not exist")) {
			
		}else {
	        signIn.closeSignIn();
	        Index window = new Index(respone);
            window.frame.setVisible(true);
		}
	}
	
	
}

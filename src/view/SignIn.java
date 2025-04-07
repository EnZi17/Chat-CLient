package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.SignInController;

public class SignIn {

    public JFrame frame;
	private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnSignUp;
    
    public void closeSignIn() {
    	this.frame.setVisible(false);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SignIn window = new SignIn();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SignIn() {
        initialize();
    }

    private void initialize() {
    	
    	SignInController controller=new SignInController(this);
        frame = new JFrame("Đăng Nhập");
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        // Panel bên trái để ảnh
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 102, 204));
        leftPanel.setBounds(0, 0, 300, 500);
        frame.getContentPane().add(leftPanel);
        leftPanel.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome Back!");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 22));
        lblWelcome.setBounds(70, 50, 200, 40);
        leftPanel.add(lblWelcome);

        // Panel bên phải chứa form đăng nhập
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(300, 0, 500, 500);
        rightPanel.setLayout(null);
        frame.getContentPane().add(rightPanel);

        JLabel lblTitle = new JLabel("Đăng Nhập");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(180, 50, 200, 30);
        rightPanel.add(lblTitle);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(100, 120, 100, 20);
        rightPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(100, 140, 300, 30);
        txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        rightPanel.add(txtEmail);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(100, 190, 100, 20);
        rightPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 210, 300, 30);
        txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        rightPanel.add(txtPassword);

        btnLogin = new JButton("Đăng Nhập");
        btnLogin.addActionListener(controller);
        btnLogin.setBounds(100, 270, 300, 40);
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.BLUE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightPanel.add(btnLogin);

        btnSignUp = new JButton("Đăng Ký");
        btnSignUp.addActionListener(controller);
        btnSignUp.setBounds(100, 330, 300, 40);
        btnLogin.setBackground(new Color(0, 102, 204));
        btnSignUp.setForeground(Color.BLUE);
        btnSignUp.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setFocusPainted(false);
        btnSignUp.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightPanel.add(btnSignUp);
    }
    
    public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	public JPasswordField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(JPasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JButton getBtnSignUp() {
		return btnSignUp;
	}

	public void setBtnSignUp(JButton btnSignUp) {
		this.btnSignUp = btnSignUp;
	}
}

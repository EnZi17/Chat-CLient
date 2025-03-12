package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SignUp {

    private JFrame frame;
    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SignUp window = new SignUp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SignUp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Đăng Ký");
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

        JLabel lblWelcome = new JLabel("Join Us!");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 22));
        lblWelcome.setBounds(110, 50, 150, 40);
        leftPanel.add(lblWelcome);

        // Panel bên phải chứa form đăng ký
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(300, 0, 500, 500);
        rightPanel.setLayout(null);
        frame.getContentPane().add(rightPanel);

        JLabel lblTitle = new JLabel("Đăng Ký");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(180, 50, 200, 30);
        rightPanel.add(lblTitle);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setBounds(100, 120, 100, 20);
        rightPanel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(100, 140, 300, 30);
        txtUsername.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        rightPanel.add(txtUsername);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(100, 190, 100, 20);
        rightPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(100, 210, 300, 30);
        txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        rightPanel.add(txtEmail);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(100, 260, 100, 20);
        rightPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 280, 300, 30);
        txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));
        rightPanel.add(txtPassword);

        btnRegister = new JButton("Đăng Ký");
        btnRegister.setBounds(100, 340, 300, 40);
        btnRegister.setBackground(new Color(0, 102, 204));
        btnRegister.setForeground(Color.BLUE);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightPanel.add(btnRegister);

        btnBack = new JButton("Quay Lại");
        btnBack.setBounds(100, 390, 300, 40);
        btnBack.setBackground(new Color(0, 102, 204));
        btnBack.setForeground(Color.BLUE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 16));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(new EmptyBorder(5, 5, 5, 5));
        rightPanel.add(btnBack);
        
    }

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTextField getTxtUsername() {
		return txtUsername;
	}

	public void setTxtUsername(JTextField txtUsername) {
		this.txtUsername = txtUsername;
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

	public JButton getBtnRegister() {
		return btnRegister;
	}

	public void setBtnRegister(JButton btnRegister) {
		this.btnRegister = btnRegister;
	}

	public JButton getBtnBack() {
		return btnBack;
	}

	public void setBtnBack(JButton btnBack) {
		this.btnBack = btnBack;
	}
}

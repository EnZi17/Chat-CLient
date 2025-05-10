package view;

import javax.swing.*;

import controller.IndexController;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;

public class Find {

    public JFrame frame;
    private ArrayList<String> recentSearches = new ArrayList<>();
    private JTextField textField;
    private JPanel panel;
    public IndexController controller;

    /**
     * Launch the application.
     */
    

    /**
     * Create the application.
     */
    public Find(IndexController controller) {
    	this.controller=controller;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	

        frame = new JFrame("Add friend");
        
        frame.getContentPane().setBackground(new Color(255, 255, 255));
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Find People");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(10, 11, 91, 30);
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 49, 401, 2);
        frame.getContentPane().add(separator);

        textField = new JTextField();
        
        textField.setBounds(88, 63, 282, 30);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Gmail");
        lblNewLabel_1.setBounds(20, 62, 58, 33);
        frame.getContentPane().add(lblNewLabel_1);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(177, 222, 89, 30);
        frame.getContentPane().add(btnCancel);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(276, 222, 94, 30);
        frame.getContentPane().add(btnSearch);

        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(20, 120, 350, 91);
        frame.getContentPane().add(panel);
        
        // Lắng nghe sự kiện nhấn nút Cancel
        btnCancel.addActionListener(e -> frame.dispose());

        // Lắng nghe sự kiện nhấn nút Search
        btnSearch.addActionListener(e -> performSearch());
        frame.setLocationRelativeTo(null);
    }

    /**
     * Thực hiện tìm kiếm và hiển thị kết quả
     */
    private void performSearch() {
        // Lấy giá trị email từ textField
        String email = textField.getText();

        // Giả sử chúng ta có một hàm getUserByEmail(email) để tìm người dùng
        // Dưới đây chỉ là ví dụ giả lập kết quả tìm kiếm:
        User user = service.AuthService.getUserByEmail(email);  // Lấy một người dùng duy nhất

        // Xóa các kết quả cũ trong panel
        panel.removeAll();

        if (user != null) {
            // Thêm nút mới cho người dùng tìm thấy
            JButton userButton = new JButton(user.getUsername());
            
            // Đặt kích thước cho nút để chiều rộng gần bằng chiều rộng của panel
            userButton.setPreferredSize(new Dimension(panel.getWidth() - 20, 40));  // Giảm bớt một chút để có lề

            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Bạn có thể xử lý sự kiện khi người dùng nhấn vào nút này
                    controller.addConverstation(user);
                    frame.setVisible(false);
                }
            });
            
            panel.add(userButton);
        } else {
            // Nếu không tìm thấy người dùng, hiển thị thông báo
            JLabel noResultLabel = new JLabel("Không tìm thấy người dùng");
            panel.add(noResultLabel);
        }

        // Cập nhật lại giao diện
        panel.revalidate();
        panel.repaint();
    }


    
}

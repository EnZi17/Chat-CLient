package view;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index {
    public String id;
    public JFrame frame;
    private JTextField textField;
    private final JPanel panel_3 = new JPanel();
    private JTextArea textField_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set LookAndFeel
                    Index window = new Index();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Index() {
        initialize();
    }

    public Index(String id) {
        this.id = id;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Index");
        frame.getContentPane().setBackground(new Color(18, 20, 22));
        frame.setBackground(new Color(18, 20, 22));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Hiển thị full màn hình
        frame.setUndecorated(false); // Nếu muốn loại bỏ thanh tiêu đề, đổi thành true
        frame.setLocationRelativeTo(null);
        frame.setSize(1080, 720);
        SpringLayout springLayout = new SpringLayout();
        frame.getContentPane().setLayout(springLayout);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, panel, 683, SpringLayout.NORTH, frame.getContentPane());
        panel.setBackground(new Color(18,20,22));
        frame.getContentPane().add(panel);
        
        JPanel panel_1 = new JPanel();
        springLayout.putConstraint(SpringLayout.WEST, panel_1, 64, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.WEST, panel_1);
        springLayout.putConstraint(SpringLayout.NORTH, panel_1, 86, SpringLayout.NORTH, frame.getContentPane());
        SpringLayout sl_panel = new SpringLayout();
        panel.setLayout(sl_panel);
        
       
        
        JButton btnNewButton = new JButton("New button");
        sl_panel.putConstraint(SpringLayout.NORTH, btnNewButton, 10, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, btnNewButton, 10, SpringLayout.WEST, panel);
        sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton, 54, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, panel);
        panel.add(btnNewButton);
        springLayout.putConstraint(SpringLayout.SOUTH, panel_1, 683, SpringLayout.NORTH, frame.getContentPane());
        panel_1.setBackground(new Color(34, 38, 43));
        frame.getContentPane().add(panel_1);
        
        JPanel panel_2 = new JPanel();
        springLayout.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.WEST, panel_2);
        springLayout.putConstraint(SpringLayout.WEST, panel_2, 295, SpringLayout.WEST, frame.getContentPane());
        
        

        

        
        JButton btnNewButton_1 = new JButton("New button");
        sl_panel.putConstraint(SpringLayout.NORTH, btnNewButton_1, -54, SpringLayout.SOUTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, btnNewButton_1, 0, SpringLayout.WEST, btnNewButton);
        sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton_1, -10, SpringLayout.SOUTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, btnNewButton_1, 54, SpringLayout.WEST, panel);
        panel.add(btnNewButton_1);
        springLayout.putConstraint(SpringLayout.EAST, panel_2, 0, SpringLayout.EAST, frame.getContentPane());
        panel_2.setBackground(new Color(18, 20, 22));
        frame.getContentPane().add(panel_2);
        SpringLayout sl_panel_2 = new SpringLayout();
        panel_2.setLayout(sl_panel_2);
        frame.getContentPane().add(panel_3);
        
        JPanel panel_9 = new JPanel();
        sl_panel_2.putConstraint(SpringLayout.NORTH, panel_9, 2, SpringLayout.NORTH, panel_2);
        panel_9.setBackground(new Color(128, 0, 64));
        sl_panel_2.putConstraint(SpringLayout.WEST, panel_9, 2, SpringLayout.WEST, panel_2);
        sl_panel_2.putConstraint(SpringLayout.SOUTH, panel_9, 526, SpringLayout.NORTH, panel_2);
        sl_panel_2.putConstraint(SpringLayout.EAST, panel_9, 771, SpringLayout.WEST, panel_2);
        ChatBubblePanel chatBubblePanel = new ChatBubblePanel();
        JScrollPane scrollPane1 = new JScrollPane(chatBubblePanel);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setPreferredSize(new Dimension(300, 400)); // Điều chỉnh kích thước nếu cần
        scrollPane1.setBorder(null);
        scrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0)); 
        

        panel_9.setLayout(new BorderLayout()); // Đảm bảo panel có layout phù hợp
        panel_9.add(scrollPane1, BorderLayout.CENTER);
        panel_2.add(panel_9);
        
        JPanel panel_4 = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel_4, 0, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, panel_4, 0, SpringLayout.EAST, panel);
        springLayout.putConstraint(SpringLayout.SOUTH, panel_4, -2, SpringLayout.NORTH, panel_1);
        springLayout.putConstraint(SpringLayout.EAST, panel_4, 0, SpringLayout.EAST, panel_1);
        panel_4.setBackground(new Color(34, 38, 43));
        frame.getContentPane().add(panel_4);
        
        JPanel panel_6 = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel_2, 0, SpringLayout.SOUTH, panel_6);
        springLayout.putConstraint(SpringLayout.WEST, panel_6, 2, SpringLayout.EAST, panel_4);
        springLayout.putConstraint(SpringLayout.EAST, panel_6, 0, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, panel_6, 0, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, panel_6, 73, SpringLayout.NORTH, frame.getContentPane());
        panel_6.setBackground(new Color(34, 38, 43));
        frame.getContentPane().add(panel_6);
        
        JPanel panel_5 = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel_5, 639, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, panel_5, 2, SpringLayout.EAST, panel_1);
        springLayout.putConstraint(SpringLayout.SOUTH, panel_5, 0, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, panel_5, 0, SpringLayout.EAST, frame.getContentPane());
        panel_5.setBackground(new Color(34, 38, 43));
        frame.getContentPane().add(panel_5);
        
        JPanel panel_7 = new JPanel();
        springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -1, SpringLayout.NORTH, panel_7);
        
        
        springLayout.putConstraint(SpringLayout.NORTH, panel_7, 600, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, panel_7, -2, SpringLayout.NORTH, panel_5);
        SpringLayout sl_panel_5 = new SpringLayout();
        panel_5.setLayout(sl_panel_5);
        
        JButton btnNewButton_3 = new JButton("New button");
        sl_panel_5.putConstraint(SpringLayout.NORTH, btnNewButton_3, 10, SpringLayout.NORTH, panel_5);
        sl_panel_5.putConstraint(SpringLayout.WEST, btnNewButton_3, -61, SpringLayout.EAST, panel_5);
        sl_panel_5.putConstraint(SpringLayout.SOUTH, btnNewButton_3, 34, SpringLayout.NORTH, panel_5);
        sl_panel_5.putConstraint(SpringLayout.EAST, btnNewButton_3, -10, SpringLayout.EAST, panel_5);
        panel_5.add(btnNewButton_3);
        
        textField_1 = new JTextArea();
        sl_panel_5.putConstraint(SpringLayout.SOUTH, textField_1, 0, SpringLayout.SOUTH, btnNewButton_3);
        textField_1.setCaretColor(Color.WHITE);
        textField_1.setFont(new Font("Arial", Font.PLAIN, 14));
        textField_1.setBorder(null);
        textField_1.setForeground(new Color(255, 255, 255));
        textField_1.setBackground(new Color(34, 38, 43));
        sl_panel_5.putConstraint(SpringLayout.NORTH, textField_1, 0, SpringLayout.NORTH, btnNewButton_3);
        sl_panel_5.putConstraint(SpringLayout.WEST, textField_1, 10, SpringLayout.WEST, panel_5);
        sl_panel_5.putConstraint(SpringLayout.EAST, textField_1, -6, SpringLayout.WEST, btnNewButton_3);
        panel_5.add(textField_1);
        textField_1.setColumns(10);
        
        
        
        springLayout.putConstraint(SpringLayout.WEST, panel_7, 2, SpringLayout.EAST, panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);
        
        JPanel panel_8 = new JPanel();
        panel_8.setBorder(null);
        panel_8.setBackground(new Color(34, 38, 43));
        panel_8.setLayout(new MigLayout("fill, insets 10", "[fill]", "0[]0[]0")); // Thêm padding

        // Tạo panel chứa danh sách user
        JPanel userListPanel = new JPanel(new MigLayout("fillx, wrap, insets 5")); // wrap để tự động xuống hàng
        userListPanel.setBorder(null);
        userListPanel.setBackground(new Color(34, 38, 43));

        for (int i = 1; i <= 20; i++) {
            JButton userButton = new JButton("User " + i);
            userButton.setPreferredSize(new Dimension(196, 50));

            // Thiết lập màu nền
            userButton.setBackground(new Color(44, 48, 53)); // Màu nền tối hơn
            userButton.setForeground(Color.WHITE);
            userButton.setFocusPainted(false);
            userButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Giúp màu nền có hiệu lực
            userButton.setContentAreaFilled(false);
            userButton.setOpaque(true);

            // Hiệu ứng hover
            userButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    userButton.setBackground(new Color(60, 64, 70)); // Màu sáng hơn khi hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    userButton.setBackground(new Color(44, 48, 53)); // Quay về màu cũ
                }
            });

            userListPanel.add(userButton, "growx"); // growx để button mở rộng hết chiều ngang
        }


        // Thêm userListPanel vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(userListPanel);
        
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0)); // Làm scrollbar nhỏ hơn
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(34, 38, 43));

        // Thêm JScrollPane vào panel_8
        panel_8.add(scrollPane, "grow");

        // Cấu hình GridBagConstraints cho panel_8
        GridBagConstraints gbc_panel_8 = new GridBagConstraints();
        gbc_panel_8.fill = GridBagConstraints.BOTH;
        gbc_panel_8.gridx = 0;
        gbc_panel_8.gridy = 0;
        gbc_panel_8.weightx = 1.0;
        gbc_panel_8.weighty = 1.0;

        // Thêm panel_8 vào panel_1
        panel_1.add(panel_8, gbc_panel_8);

        
        springLayout.putConstraint(SpringLayout.EAST, panel_7, 0, SpringLayout.EAST, frame.getContentPane());
        panel_7.setBackground(new Color(34, 38, 43));
        frame.getContentPane().add(panel_7);
        SpringLayout sl_panel_7 = new SpringLayout();
        panel_7.setLayout(sl_panel_7);

        
        JButton btnNewButton_2 = new JButton("New button");
        sl_panel_7.putConstraint(SpringLayout.NORTH, btnNewButton_2, 5, SpringLayout.NORTH, panel_7);
        sl_panel_7.putConstraint(SpringLayout.WEST, btnNewButton_2, 5, SpringLayout.WEST, panel_7);
        sl_panel_7.putConstraint(SpringLayout.SOUTH, btnNewButton_2, -5, SpringLayout.SOUTH, panel_7);
        sl_panel_7.putConstraint(SpringLayout.EAST, btnNewButton_2, 37, SpringLayout.WEST, panel_7);
        panel_7.add(btnNewButton_2);

        
    }
}

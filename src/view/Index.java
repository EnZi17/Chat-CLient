package view;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.IndexController;
import model.Conversation;
import model.Message;
import model.User;
import net.miginfocom.swing.MigLayout;


import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class Index {
    public User user;
    public JFrame frame;
    private JTextField textField;
    private final JPanel panel_3 = new JPanel();
    public JTextArea textField_1;
    public JButton btnNewButton_3;
    public ChatBubblePanel chatBubblePanel;
    public JScrollPane scrollPane1;
    public ArrayList<Conversation> conversations;
    public HashMap<String, ArrayList<Message>> conversationMessages;
    public JPanel userListPanel;
    public String currentConversationId;
    public JTextField textField_2;
    public CircleImagePanel panel_10;
    public CircleImagePanel panel_10_1;
    public JButton btnNewButton_1;
    public JLabel lblNewLabel;
    public JLabel dotJLabel;

    
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

    public Index(User user) {
        this.user = user;
        updateMess();
        
        initialize();
    }
    
    

    public void updateMess() {
		// TODO Auto-generated method stub
    	conversations = service.AuthService.getConversations(user.getId());
        conversationMessages = new HashMap<String, ArrayList<Message>>();
        for(Conversation conversation: conversations) {
        	String conversationId = conversation.getId();
        	ArrayList<Message> messages = service.AuthService.getMessages(conversationId);
        	conversationMessages.put(conversationId, messages);
        }
	}

	private void initialize() {
    	
    	IndexController controller = new IndexController(this);
    	
        frame = new JFrame("Index");
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(217,217,217));
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
        panel.setBackground(new Color(0, 90, 224));
        frame.getContentPane().add(panel);
        
        JPanel panel_1 = new JPanel();
        springLayout.putConstraint(SpringLayout.WEST, panel_1, 64, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.WEST, panel_1);
        springLayout.putConstraint(SpringLayout.NORTH, panel_1, 86, SpringLayout.NORTH, frame.getContentPane());
        SpringLayout sl_panel = new SpringLayout();
        panel.setLayout(sl_panel);
        
       
        ImageIcon icon = new ImageIcon("public/avatar.jpg");
        springLayout.putConstraint(SpringLayout.SOUTH, panel_1, 683, SpringLayout.NORTH, frame.getContentPane());
        panel_1.setBackground(new Color(34, 38, 43));
        frame.getContentPane().add(panel_1);
        
        JPanel panel_2 = new JPanel();
        springLayout.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.WEST, panel_2);
        springLayout.putConstraint(SpringLayout.WEST, panel_2, 295, SpringLayout.WEST, frame.getContentPane());
        
        

        

        
        btnNewButton_1 = new JButton(); // không truyền "Setting" ở đây
        btnNewButton_1.setBackground(new Color(0, 90, 224));

     // Đặt ActionCommand thủ công
        btnNewButton_1.setActionCommand("Setting");

     // Thêm icon từ file
        ImageIcon icons = new ImageIcon("public/setting.png");
        Image img = icons.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // chỉnh kích thước nếu cần
        btnNewButton_1.setIcon(new ImageIcon(img));

     // Không hiện text
        btnNewButton_1.setText(null);
        btnNewButton_1.addActionListener(controller);
        sl_panel.putConstraint(SpringLayout.WEST, btnNewButton_1, 10, SpringLayout.WEST, panel);
        sl_panel.putConstraint(SpringLayout.EAST, btnNewButton_1, -10, SpringLayout.EAST, panel);
        btnNewButton_1.addActionListener(controller);
        sl_panel.putConstraint(SpringLayout.NORTH, btnNewButton_1, -54, SpringLayout.SOUTH, panel);
        sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton_1, -10, SpringLayout.SOUTH, panel);
        panel.add(btnNewButton_1);
        springLayout.putConstraint(SpringLayout.EAST, panel_2, 0, SpringLayout.EAST, frame.getContentPane());
        panel_2.setBackground(new Color(217,217,217));
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
        chatBubblePanel  = new ChatBubblePanel();
        chatBubblePanel.setBackground(new Color(235, 236, 240));
        scrollPane1 = new JScrollPane(chatBubblePanel);
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
        
        String imageString = this.user.getAvatar() != "" ? this.user.getAvatar() : CircleImagePanel.imageToBase64("public/avatar.jpg");;
        panel_10 = new CircleImagePanel(imageString);
        sl_panel.putConstraint(SpringLayout.NORTH, panel_10, 10, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, panel_10, 0, SpringLayout.WEST, btnNewButton_1);
        sl_panel.putConstraint(SpringLayout.SOUTH, panel_10, 54, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, panel_10, 0, SpringLayout.EAST, btnNewButton_1);
        panel.add(panel_10);
        springLayout.putConstraint(SpringLayout.SOUTH, panel_4, -2, SpringLayout.NORTH, panel_1);
        springLayout.putConstraint(SpringLayout.EAST, panel_4, 0, SpringLayout.EAST, panel_1);
        panel_4.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(panel_4);
        
        JPanel panel_6 = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel_2, 0, SpringLayout.SOUTH, panel_6);
        springLayout.putConstraint(SpringLayout.WEST, panel_6, 2, SpringLayout.EAST, panel_4);
        SpringLayout sl_panel_4 = new SpringLayout();
        panel_4.setLayout(sl_panel_4);
        
        textField_2 = new JTextField();
        sl_panel_4.putConstraint(SpringLayout.NORTH, textField_2, 15, SpringLayout.NORTH, panel_4);
        sl_panel_4.putConstraint(SpringLayout.WEST, textField_2, 10, SpringLayout.WEST, panel_4);
        sl_panel_4.putConstraint(SpringLayout.SOUTH, textField_2, -41, SpringLayout.SOUTH, panel_4);
        sl_panel_4.putConstraint(SpringLayout.EAST, textField_2, -54, SpringLayout.EAST, panel_4);
        panel_4.add(textField_2);
        textField_2.setColumns(10);
        
        

        JButton btnNewButton_4 = new JButton(); // không truyền "Setting" ở đây
        btnNewButton_4.setActionCommand("Thêm bạn");
        ImageIcon iconf = new ImageIcon("public/person.png");
        Image imgf = iconf.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH); // chỉnh kích thước nếu cần
        btnNewButton_4.setIcon(new ImageIcon(imgf));
        btnNewButton_4.addActionListener(controller);
        sl_panel_4.putConstraint(SpringLayout.NORTH, btnNewButton_4, 0, SpringLayout.NORTH, textField_2);
        sl_panel_4.putConstraint(SpringLayout.WEST, btnNewButton_4, 6, SpringLayout.EAST, textField_2);
        sl_panel_4.putConstraint(SpringLayout.SOUTH, btnNewButton_4, 0, SpringLayout.SOUTH, textField_2);
        sl_panel_4.putConstraint(SpringLayout.EAST, btnNewButton_4, -10, SpringLayout.EAST, panel_4);
        panel_4.add(btnNewButton_4);
        springLayout.putConstraint(SpringLayout.EAST, panel_6, 0, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, panel_6, 0, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, panel_6, 73, SpringLayout.NORTH, frame.getContentPane());
        panel_6.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(panel_6);
        SpringLayout sl_panel_6 = new SpringLayout();
        panel_6.setLayout(sl_panel_6);
        
        panel_10_1 = new CircleImagePanel("");
        sl_panel_6.putConstraint(SpringLayout.NORTH, panel_10_1, 10, SpringLayout.NORTH, panel_6);
        sl_panel_6.putConstraint(SpringLayout.WEST, panel_10_1, 10, SpringLayout.WEST, panel_6);
        sl_panel_6.putConstraint(SpringLayout.SOUTH, panel_10_1, -10, SpringLayout.SOUTH, panel_6);
        sl_panel_6.putConstraint(SpringLayout.EAST, panel_10_1, 68, SpringLayout.WEST, panel_6);
        panel_6.add(panel_10_1);
        
        lblNewLabel = new JLabel("");
        sl_panel_6.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, panel_10_1);
        sl_panel_6.putConstraint(SpringLayout.WEST, lblNewLabel, 15, SpringLayout.EAST, panel_10_1);
        sl_panel_6.putConstraint(SpringLayout.SOUTH, lblNewLabel, 34, SpringLayout.NORTH, panel_6);
        sl_panel_6.putConstraint(SpringLayout.EAST, lblNewLabel, 254, SpringLayout.EAST, panel_10_1);
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel_6.add(lblNewLabel);
        panel_10_1.setLayout(null);
        
        dotJLabel = new JLabel(".");
        dotJLabel.setVisible(false);
        dotJLabel.setForeground(Color.green);
        dotJLabel.setBounds(33, -64, 50, 117);
        panel_10_1.add(dotJLabel);
        dotJLabel.setFont(new Font("Segoe UI", Font.PLAIN, 130));
        
        
        
        JPanel panel_5 = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel_5, 639, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, panel_5, 2, SpringLayout.EAST, panel_1);
        springLayout.putConstraint(SpringLayout.SOUTH, panel_5, 0, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, panel_5, 0, SpringLayout.EAST, frame.getContentPane());
        panel_5.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(panel_5);
        
        JPanel panel_7 = new JPanel();
        springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -1, SpringLayout.NORTH, panel_7);
        
        
        springLayout.putConstraint(SpringLayout.NORTH, panel_7, 600, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, panel_7, -2, SpringLayout.NORTH, panel_5);
        SpringLayout sl_panel_5 = new SpringLayout();
        panel_5.setLayout(sl_panel_5);
        
        btnNewButton_3 = new JButton("Gửi");
        btnNewButton_3.addActionListener(controller);
        sl_panel_5.putConstraint(SpringLayout.NORTH, btnNewButton_3, 10, SpringLayout.NORTH, panel_5);
        sl_panel_5.putConstraint(SpringLayout.WEST, btnNewButton_3, -61, SpringLayout.EAST, panel_5);
        sl_panel_5.putConstraint(SpringLayout.SOUTH, btnNewButton_3, 34, SpringLayout.NORTH, panel_5);
        sl_panel_5.putConstraint(SpringLayout.EAST, btnNewButton_3, -10, SpringLayout.EAST, panel_5);
        panel_5.add(btnNewButton_3);
        
        textField_1 = new JTextArea();
        textField_1.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "sendAction");
        textField_1.getActionMap().put("sendAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNewButton_3.doClick(); // Kích hoạt nút Gửi
            }
        });
        sl_panel_5.putConstraint(SpringLayout.EAST, textField_1, -50, SpringLayout.WEST, btnNewButton_3);
        sl_panel_5.putConstraint(SpringLayout.SOUTH, textField_1, 0, SpringLayout.SOUTH, btnNewButton_3);
        textField_1.setCaretColor(Color.BLACK);
        textField_1.setBorder(null);
        textField_1.setForeground(Color.BLACK);
        textField_1.setBackground(new Color(255, 255, 255));
        sl_panel_5.putConstraint(SpringLayout.NORTH, textField_1, 0, SpringLayout.NORTH, btnNewButton_3);
        sl_panel_5.putConstraint(SpringLayout.WEST, textField_1, 10, SpringLayout.WEST, panel_5);
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
        panel_8.setBackground(new Color(255, 255, 255));
        panel_8.setLayout(new MigLayout("fill, insets 10", "[fill]", "0[]0[]0")); // Thêm padding

        // Tạo panel chứa danh sách user
        userListPanel = new JPanel(new MigLayout("fillx, wrap, insets 5")); // wrap để tự động xuống hàng
        userListPanel.setBorder(null);
        userListPanel.setBackground(Color.white);
        controller.updateConverstations(conversations);



        // Thêm userListPanel vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(userListPanel);
        
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0)); // Làm scrollbar nhỏ hơn
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.white);

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
        panel_7.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(panel_7);
        SpringLayout sl_panel_7 = new SpringLayout();
        panel_7.setLayout(sl_panel_7);

        
        JButton btnNewButton_2 = new JButton("Emoji");
        sl_panel_7.putConstraint(SpringLayout.EAST, btnNewButton_2, 62, SpringLayout.WEST, panel_7);
        btnNewButton_2.addActionListener(controller);
        sl_panel_7.putConstraint(SpringLayout.NORTH, btnNewButton_2, 5, SpringLayout.NORTH, panel_7);
        sl_panel_7.putConstraint(SpringLayout.WEST, btnNewButton_2, 5, SpringLayout.WEST, panel_7);
        sl_panel_7.putConstraint(SpringLayout.SOUTH, btnNewButton_2, -5, SpringLayout.SOUTH, panel_7);
        panel_7.add(btnNewButton_2);
        
        JButton btnNewButton_2_1 = new JButton("File");
        btnNewButton_2_1.addActionListener(controller);
        sl_panel_7.putConstraint(SpringLayout.NORTH, btnNewButton_2_1, 0, SpringLayout.NORTH, btnNewButton_2);
        sl_panel_7.putConstraint(SpringLayout.WEST, btnNewButton_2_1, 13, SpringLayout.EAST, btnNewButton_2);
        sl_panel_7.putConstraint(SpringLayout.SOUTH, btnNewButton_2_1, 0, SpringLayout.SOUTH, btnNewButton_2);
        sl_panel_7.putConstraint(SpringLayout.EAST, btnNewButton_2_1, 78, SpringLayout.EAST, btnNewButton_2);
        panel_7.add(btnNewButton_2_1);
        
        JButton btnNewButton_2_2 = new JButton("Voice");
        btnNewButton_2_2.addActionListener(controller);
        sl_panel_7.putConstraint(SpringLayout.NORTH, btnNewButton_2_2, 0, SpringLayout.NORTH, btnNewButton_2);
        sl_panel_7.putConstraint(SpringLayout.WEST, btnNewButton_2_2, 13, SpringLayout.EAST, btnNewButton_2_1);
        sl_panel_7.putConstraint(SpringLayout.SOUTH, btnNewButton_2_2, 0, SpringLayout.SOUTH, btnNewButton_2);
        sl_panel_7.putConstraint(SpringLayout.EAST, btnNewButton_2_2, 78, SpringLayout.EAST, btnNewButton_2_1);
        panel_7.add(btnNewButton_2_2);
        
        
        Timer timer = new Timer(10000, new ActionListener() { // 5000ms = 5 giây
            @Override
            public void actionPerformed(ActionEvent e) {
            	conversations = service.AuthService.getConversations(user.getId());
                controller.updateConverstations(conversations);;
            }
        });
        timer.start();


        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Gọi chương trình bạn muốn chạy khi tắt cửa sổ
                service.AuthService.setStatus(user.getId(), false);
                controller.socket.close();
                // Sau khi chạy xong, đóng app
                frame.dispose(); // hoặc System.exit(0);
            }
        });
    }
}
package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NonReadableChannelException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Base64;
import java.util.List;



// Model Message của bạn
import model.Message;
import model.User;
import myUtil.AudioRecorder;
import model.Conversation;
import model.Message;
import mysocket.MySocket;
import view.CircleImagePanel;
import view.EmojiPickerView;
import view.Find;
import view.Index;


public class IndexController implements ActionListener{
	public static Index index;
	public MySocket socket;
	private AudioRecorder recorder;
	private boolean isRecording = false;
	
	public static Index getInstance() {
		return index;
	}
	
	public IndexController(Index index) {
		this.index = index;
		initSocket();
		
		
	}
	private void initSocket() {
		// TODO Auto-generated method stub
		try {
			URI serverUri = new URI("wss://chat-wtbk.onrender.com");
			socket = new MySocket(serverUri,index.user.getId());
			if(socket==null) {
				socket.connect();
			}
			
		} catch (URISyntaxException e) {
            e.printStackTrace();
        }
		try {
			socket.connectBlocking();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		for(Conversation conversation: index.conversations) {
			socket.joinRoom(conversation.getId());
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	    Object source = e.getSource();

	    if (source instanceof JButton) {
	        JButton btn = (JButton) source;
	        Object conversationId = btn.getClientProperty("conversationId");

	        if (conversationId != null) {
	            System.out.println("Clicked conversation ID: " + conversationId);
	            updateMessages(conversationId.toString());

	            SwingUtilities.invokeLater(() -> {
	                JScrollBar verticalBar = this.index.scrollPane1.getVerticalScrollBar();
	                verticalBar.setValue(verticalBar.getMaximum());
	            });
	            return;
	        }
	    }

	    String src = e.getActionCommand();
	    switch (src) {
	        case "Gửi":
	            sendManagement();
	            break;
	        case "Emoji":
	            popUpEmojiManagement();
	            break;
	        case "File":
	            fileManagement();
	            break;
	        case "Voice":
	            voiceManagement();
	            break;
	        case "Thêm bạn":
	            addFriendManagement();
	            break;
	        case "Setting":
	            settingManagement(index.btnNewButton_1);
	            break;
	        default:
	            System.out.println("Unrecognized button: " + src);
	    }
	}

	
	
	




	private void settingManagement(Component parentComponent) {
	    JPopupMenu popupMenu = new JPopupMenu();

	    JMenuItem changeAvatar = new JMenuItem("Thay đổi Avatar");
	    JMenuItem changeName = new JMenuItem("Thay đổi Tên");

	    popupMenu.add(changeAvatar);
	    popupMenu.add(changeName);

	    // Xử lý khi click "Thay đổi Avatar"
	    changeAvatar.addActionListener(e -> showImageChooser());

	    // Xử lý khi click "Thay đổi Tên"
	    changeName.addActionListener(e -> showChangeNameDialog());

	    // Hiển thị popup menu tại vị trí chuột
	    popupMenu.show(parentComponent, 0, parentComponent.getHeight()-100);
	}
	private void showImageChooser() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Chọn ảnh avatar");

	    // Lọc chỉ ảnh
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Ảnh", "jpg", "jpeg", "png"));

	    int result = fileChooser.showOpenDialog(null);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
	        String base64Image = CircleImagePanel.imageToBase64(selectedFile.getAbsolutePath());
	        
	        if (base64Image != null) {
	        	System.out.println(index.user.getId()+1);
	        	System.out.println(service.AuthService.updateAvatar(index.user.getId(), base64Image));
	            index.panel_10.setImage(base64Image);
	            index.panel_10.repaint();
	        }
	    }
	}
	
	private void showChangeNameDialog() {
	    String newName = JOptionPane.showInputDialog(null,"Tên cũ của bạn là: "+index.user.getUsername() + ", nhập tên mới:");
	    if (newName != null && !newName.trim().isEmpty()) {
	        String result = service.AuthService.updateUserName(index.user.getId(), newName);
	        index.user= service.AuthService.getUserByID(index.user.getId());
	        JOptionPane.showMessageDialog(null, result);
	    } else {
	        JOptionPane.showMessageDialog(null, "Tên mới không được để trống");
	    }
	}


	

	private void addFriendManagement() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(() -> {
            try {
                Find window = new Find(this);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	private void voiceManagement() {
	    try {
	        recorder = new AudioRecorder();
	        recorder.startRecording("recorded_audio.wav");
	        isRecording = true;

	        // Hiển thị thông báo blocking, chờ người dùng nhấn OK
	        JOptionPane.showMessageDialog(null, "🎙️ Đang ghi âm...\nNhấn OK để dừng và gửi tin nhắn thoại.");

	        // Sau khi nhấn OK -> tiếp tục dừng ghi và gửi
	        recorder.stopRecording();
	        isRecording = false;

	        File audio = recorder.getAudioFile();
	        byte[] fileData = Files.readAllBytes(audio.toPath());
	        String encodedFile = Base64.getEncoder().encodeToString(fileData);
	        String fileName = "recorded_audio.wav";

	        socket.sendFile(fileName + "|" + encodedFile, index.currentConversationId);
	        index.chatBubblePanel.addFile(fileName + "|" + encodedFile, true);

	        SwingUtilities.invokeLater(() -> {
	            JScrollBar verticalBar = index.scrollPane1.getVerticalScrollBar();
	            verticalBar.setValue(verticalBar.getMaximum());
	        });

	    } catch (LineUnavailableException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi bắt đầu ghi âm.");
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi gửi file ghi âm.");
	    }
	}


	private void fileManagement() {
	    JFileChooser fileChooser = new JFileChooser();
	    int returnVal = fileChooser.showOpenDialog(null);
	    
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	        File file = fileChooser.getSelectedFile();
	        try {
	            byte[] fileData = Files.readAllBytes(file.toPath());
	            String encodedFile = Base64.getEncoder().encodeToString(fileData);
	            String fileName=file.getName();

	            socket.sendFile(fileName+"|"+encodedFile, index.currentConversationId);
	            index.chatBubblePanel.addFile(fileName+"|"+encodedFile, true);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	    SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = index.scrollPane1.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
	}

	private void popUpEmojiManagement() {
		// TODO Auto-generated method stub
		EmojiPickerView emojiPicker = new EmojiPickerView(index.textField_1);

	    // Lấy vị trí nút "Emoji" trên màn hình
	    Component source = (Component) SwingUtilities.getRootPane(index.frame).getGlassPane().getParent();
	    Point location = index.frame.getMousePosition(); // Hoặc tự set vị trí nếu cần

	    if (location != null) {
	        emojiPicker.show(index.frame, location.x, location.y);
	    } else {
	        emojiPicker.show(index.frame, 100, 500); // fallback vị trí nếu không lấy được chuột
	    }
	}

	private void updateMessages(String converstationId) {
	    index.currentConversationId = converstationId;
	    index.updateMess();
	    
	    ArrayList<Message> messages = this.index.conversationMessages.get(converstationId);
	    
	    User user = service.AuthService.getOtherUserFromConversation(converstationId, index.user.getId());
	    String avatar = user.getAvatar();
	    String userName = user.getUsername();
	    index.panel_10_1.setImage(avatar!=""?avatar:CircleImagePanel.imageToBase64("public/avatar.jpg"));
	    index.panel_10_1.repaint(); 
	    index.lblNewLabel.setText(userName);
	    index.dotJLabel.setVisible(true);    
	    index.dotJLabel.setForeground(user.isOnline()?Color.GREEN:Color.gray);

	    // Xóa tất cả các message cũ trên giao diện
	    index.chatBubblePanel.removeAll();
	    index.chatBubblePanel.revalidate();
	    index.chatBubblePanel.repaint();
	    
	    
	    
	    if (messages == null) {
	        System.err.println("Danh sách messages bị null!");
	        return;
	    }

	    for (Message message : messages) {
	        boolean isSender = message.getSender().equals(index.user.getId());
	        if (message.getAttachments().isEmpty()) {
	            index.chatBubblePanel.addMessage(message.getContent(), isSender);
	        } else {
	            index.chatBubblePanel.addFile(message.getAttachments().get(0), isSender);
	        }
	    }
	}

	private void sendManagement() {
		// TODO Auto-generated method stub
		
		String message =index.textField_1.getText();
		if(!message.equals("")) {

			socket.sendMessage(message,index.currentConversationId);
			
			index.textField_1.setText("");
			index.chatBubblePanel.addMessage(message, true);
			SwingUtilities.invokeLater(() -> {
	            JScrollBar verticalBar = index.scrollPane1.getVerticalScrollBar();
	            verticalBar.setValue(verticalBar.getMaximum());
	        });
		}
		
	}
	
	
	
	public void updateConverstations(ArrayList<Conversation> conversations) {
	    this.index.userListPanel.removeAll();
	    for (Conversation conversation : conversations) {
	        String username = "";
	        String avatarBase64 = null;
	        Color textColor = Color.BLACK;
	        String conversationId = conversation.getId();
	        if (conversationId.equals(index.currentConversationId)) {
	        	User user = service.AuthService.getOtherUserFromConversation(conversationId, index.user.getId());
	        	index.dotJLabel.setForeground(user.isOnline()?Color.GREEN:Color.gray);
	        }

	        if (conversation.isGroup()) {
	            username = conversation.getName() != null ? conversation.getName() : "Group Chat";
	        } else {
	            for (String participantId : conversation.getParticipants()) {
	                if (!participantId.equals(index.user.getId())) {
	                    try {
	                        User otherUser = service.AuthService.getUserByID(participantId);
	                        username = otherUser.getUsername();
	                        avatarBase64 = otherUser.getAvatar(); // giả sử avatar là base64
	                        textColor = otherUser.isOnline() ? Color.GREEN : Color.BLACK;
	                        
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        username = "Unknown User";
	                    }
	                    break;
	                }
	            }
	        }
	        // Tạo panel chứa avatar và tên
	        JPanel panel = new JPanel(new BorderLayout(10, 0));
	        panel.setPreferredSize(new Dimension(196, 60));
	        panel.setBackground(Color.WHITE);
	        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

	        // Avatar
	        CircleImagePanel avatar = new CircleImagePanel(avatarBase64 != "" ? avatarBase64 : CircleImagePanel.imageToBase64("public/avatar.jpg"));
	        avatar.setPreferredSize(new Dimension(40, 40));

	        // Username
	        JLabel nameLabel = new JLabel(username);
	        nameLabel.setForeground(textColor);
	        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
	        nameLabel.setVerticalAlignment(SwingConstants.CENTER);

	        panel.add(avatar, BorderLayout.WEST);
	        panel.add(nameLabel, BorderLayout.CENTER);

	        // Tạo button vô hình để xử lý sự kiện click
	        JButton transparentButton = new JButton();
	        transparentButton.setLayout(new BorderLayout());
	        transparentButton.add(panel, BorderLayout.CENTER);
	        transparentButton.putClientProperty("conversationId", conversationId);
	        transparentButton.setPreferredSize(new Dimension(196, 60));
	        transparentButton.setContentAreaFilled(false);
	        transparentButton.setFocusPainted(false);
	        transparentButton.setBorderPainted(false);

	        transparentButton.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                panel.setBackground(new Color(235, 236, 240));
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                panel.setBackground(Color.WHITE);
	            }
	        });

	        transparentButton.addActionListener(this);
	        this.index.userListPanel.add(transparentButton, "growx");
	    }

	    this.index.userListPanel.revalidate();
	    this.index.userListPanel.repaint();
	}


	public void addConverstation(User user) {
		ArrayList<String> idStrings = new ArrayList<String>();
		idStrings.add(this.index.user.getId());
		idStrings.add(user.getId());
		String tmp=service.AuthService.createConversation(idStrings, false, null);
		this.index.conversations = service.AuthService.getConversations(this.index.user.getId());
		updateConverstations(this.index.conversations);
		updateMessages(tmp);
	}


	
	
	
}

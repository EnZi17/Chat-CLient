package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
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
			URI serverUri = new URI("ws://localhost:5000");
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
	        
	        default:
	            System.out.println("Unrecognized button: " + src);
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
	        String buttonText = "";
	        Color textColor = Color.BLACK;

	        if (conversation.isGroup()) {
	            buttonText = conversation.getName() != null ? conversation.getName() : "Group Chat";
	        } else {
	            for (String participantId : conversation.getParticipants()) {
	                if (!participantId.equals(index.user.getId())) {
	                    try {
	                        User otherUser = service.AuthService.getUserByID(participantId);
	                        buttonText = otherUser.getUsername();
	                        textColor = otherUser.isOnline() ? Color.GREEN : Color.BLACK;
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        buttonText = "Unknown User";
	                    }
	                    break;
	                }
	            }
	        }

	        JButton userButton = new JButton(buttonText);
	        userButton.putClientProperty("conversationId", conversation.getId()); // Gán ID thật
	        userButton.setPreferredSize(new Dimension(196, 50));
	        userButton.setBackground(Color.white);
	        userButton.setForeground(textColor);
	        userButton.setFocusPainted(false);
	        userButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        userButton.setContentAreaFilled(false);
	        userButton.setOpaque(true);

	        userButton.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                userButton.setBackground(new Color(235, 236, 240));
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                userButton.setBackground(Color.white);
	            }
	        });

	        userButton.addActionListener(this);
	        this.index.userListPanel.add(userButton, "growx");
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

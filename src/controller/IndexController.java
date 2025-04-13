package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import myUtil.AudioRecorder;
import myUtil.Stego;
import model.Conversation;
import model.Message;
import mysocket.MySocket;
import view.EmojiPickerView;
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
		// TODO Auto-generated method stub
		String src = e.getActionCommand();
		System.out.println(src);
		switch (src) {
		case "Gửi": {
			sendManagement();
			break;
		}
		case "Emoji": {
			popUpEmojiManagement();
			break;
		}
		case "File": {
			fileManagement();
			break;
		}
		case "Voice": {
			voiceManagement();
			break;
		}
		case "GTTA": {
			gttaManagement();
			break;
		}
		default:
			updateMessages(src);
			SwingUtilities.invokeLater(() -> {
	            JScrollBar verticalBar = this.index.scrollPane1.getVerticalScrollBar();
	            verticalBar.setValue(verticalBar.getMaximum());
	        });
		}
	}
	
	
	
	private void gttaManagement() {
	    JFileChooser fileChooser = new JFileChooser();
	    int returnVal = fileChooser.showOpenDialog(null);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	        File imageFile = fileChooser.getSelectedFile();

	        String secretMessage = JOptionPane.showInputDialog(null, "Nhập tin nhắn cần giấu trong ảnh:");

	        if (secretMessage != null && !secretMessage.isEmpty()) {
	            try {
	                BufferedImage originalImage = ImageIO.read(imageFile);

	                // Mã hóa tin ẩn
	                BufferedImage stegoImage = Stego.encodeTextToImage(secretMessage, originalImage);

	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                ImageIO.write(stegoImage, "png", baos);
	                baos.flush();
	                byte[] stegoImageBytes = baos.toByteArray();
	                baos.close();

	                String base64StegoImage = Base64.getEncoder().encodeToString(stegoImageBytes);
	                String fileName = "secret_" + imageFile.getName();
	                int secretLength = secretMessage.length();

	                // Định dạng mới: tên|base64|độ dài
	                String attachment = fileName + "|" + base64StegoImage + "|" + secretLength;

	                // Gửi file và hiển thị luôn
	                socket.sendFile(attachment, index.currentConversationId);
	                index.chatBubblePanel.addFile(attachment, true);

	                SwingUtilities.invokeLater(() -> {
	                    JScrollBar verticalBar = index.scrollPane1.getVerticalScrollBar();
	                    verticalBar.setValue(verticalBar.getMaximum());
	                });

	            } catch (IOException e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Lỗi khi xử lý ảnh.");
	            }
	        }
	    }
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
		// TODO Auto-generated method stub
		index.currentConversationId= converstationId;
		ArrayList<Message> messages = this.index.conversationMessages.get(converstationId);
		for(Message message: messages) {
			Boolean flag = message.getSender().equals(index.user.getId())?true:false;
			if(message.getAttachments().isEmpty()) {
				index.chatBubblePanel.addMessage(message.getContent(), flag);
			}else {
				index.chatBubblePanel.addFile(message.getAttachments().get(0), flag);
			}
			
		}
	}
	private void sendManagement() {
		// TODO Auto-generated method stub
		
		String message =index.textField_1.getText();
		if(!message.equals("")) {

			socket.sendMessage(myUtil.SimpleAES.encrypt(message),index.currentConversationId);
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
	        JButton userButton = new JButton(conversation.getId());
	        userButton.setPreferredSize(new Dimension(196, 50));

	        // Giao diện nút
	        userButton.setBackground(new Color(44, 48, 53));
	        userButton.setForeground(Color.WHITE);
	        userButton.setFocusPainted(false);
	        userButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        userButton.setContentAreaFilled(false);
	        userButton.setOpaque(true);

	        // Hover effect
	        userButton.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                userButton.setBackground(new Color(60, 64, 70));
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                userButton.setBackground(new Color(44, 48, 53));
	            }
	        });

	        // Gọi API để lấy trạng thái người dùng nếu không phải nhóm
	        if (!conversation.isGroup()) {
	            try {
	                String response = service.AuthService.getUserStatus(conversation.getId(), index.user.getId());

	                // Giả định response là JSON như: {"online": true}
	                JSONObject json = new JSONObject(response);
	                boolean isOnline = json.getBoolean("isOnline");

	                userButton.setForeground(isOnline ? Color.GREEN : Color.LIGHT_GRAY);
	            } catch (Exception e) {
	                e.printStackTrace(); // Log lỗi nếu parse thất bại
	                userButton.setForeground(Color.LIGHT_GRAY); // Mặc định nếu lỗi
	            }
	        }

	        userButton.addActionListener(this);
	        this.index.userListPanel.add(userButton, "growx");
	    }

	    this.index.userListPanel.revalidate();
	    this.index.userListPanel.repaint();
	}

	
	
	
}

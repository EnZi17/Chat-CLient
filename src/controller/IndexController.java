package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;


import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import model.Conversation;
import model.Message;
import mysocket.MySocket;
import view.Index;

public class IndexController implements ActionListener{
	public static Index index;
	public MySocket socket;
	
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
		default:
			updateMessages(src);
			SwingUtilities.invokeLater(() -> {
	            JScrollBar verticalBar = this.index.scrollPane1.getVerticalScrollBar();
	            verticalBar.setValue(verticalBar.getMaximum());
	        });
		}
	}
	
	
	
	private void updateMessages(String converstationId) {
		// TODO Auto-generated method stub
		index.currentConversationId= converstationId;
		ArrayList<Message> messages = this.index.conversationMessages.get(converstationId);
		for(Message message: messages) {
			Boolean flag = message.getSender().equals(index.user.getId())?true:false;
			index.chatBubblePanel.addMessage(message.getContent(), flag);
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
		for(Conversation conversation: conversations) {
			JButton userButton = new JButton(conversation.getId());
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
            userButton.addActionListener(this);
            this.index.userListPanel.add(userButton, "growx");
		}
		
	}
	
	
	
}

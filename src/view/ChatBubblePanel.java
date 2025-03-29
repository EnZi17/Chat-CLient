package view;

import javax.swing.*;
import java.awt.*;

public class ChatBubblePanel extends JPanel {
    public ChatBubblePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(34, 38, 43)); // Đổi màu nền của toàn bộ panel
        
        addMessage("Hello! How are you?", false);
        addMessage("I'm good, thanks! How about youaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaâ?", true);
        addMessage("I'm fine too. What are you doing?", false);
        addMessage("Just coding some Java Swing stuff!", true);
        addMessage("sadsađasad", false);
        addMessage("Hello! How are you?", false);
        addMessage("I'm good, thanks! How about youaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaâ?", true);
        addMessage("I'm fine too. What are you doing?", false);
        addMessage("Just coding some Java Swing stuff!", true);
        addMessage("sadsađasad", false);
        addMessage("Hello! How are you?", false);
        addMessage("I'm good, thanks! How about youaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaâ?", true);
        addMessage("I'm fine too. What are you doing?", false);
        addMessage("Just coding some Java Swing stuff!", true);
        addMessage("sadsađasad", false);
    }

    public void addMessage(String text, boolean isMine) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout(isMine ? FlowLayout.RIGHT : FlowLayout.LEFT));
        messagePanel.setBackground(new Color(34, 38, 43)); // Đổi màu nền của từng dòng chat

        JLabel messageLabel = new JLabel("<html><p style='max-width: 200px; word-wrap: break-word;'>" + text + "</p></html>");
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (isMine) {
            messageLabel.setBackground(new Color(173, 216, 230)); // Light Blue
        } else {
            messageLabel.setBackground(new Color(220, 220, 220)); // Light Gray
        }

        messagePanel.add(messageLabel);
        add(messagePanel);
        add(Box.createVerticalStrut(10));
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chat Bubble Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 500);
            
            ChatBubblePanel chatBubblePanel = new ChatBubblePanel();
            JScrollPane scrollPane = new JScrollPane(chatBubblePanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            
            frame.add(scrollPane);
            frame.setVisible(true);
        });
    }
}

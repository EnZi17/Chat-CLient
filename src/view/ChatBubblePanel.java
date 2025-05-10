package view;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import controller.IndexController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
public class ChatBubblePanel extends JPanel {
	private ArrayList<JPanel> emptyPanels = new ArrayList<>();
    public ChatBubblePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(235, 236, 240)); // Đổi màu nền của toàn bộ panel

    }

    public void addMessage(String text, boolean isMine) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout(isMine ? FlowLayout.RIGHT : FlowLayout.LEFT));
        messagePanel.setBackground(new Color(235, 236, 240)); // Đổi màu nền của từng dòng chat

        JLabel messageLabel = new JLabel("<html><p style='max-width: 200px; word-wrap: break-word;'>" + text + "</p></html>");
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        if (isMine) {
            messageLabel.setBackground(new Color(219,235,255)); // Light Blue
        } else {
            messageLabel.setBackground(Color.WHITE); // Light Gray
        }

        messagePanel.add(messageLabel);
        add(messagePanel);
        add(Box.createVerticalStrut(10));
        revalidate();
        repaint();
        
        
    }
    
    public String getFileType(String filename) {
        
        filename.toLowerCase();

        if (filename.matches(".*\\.(png|jpg|jpeg|gif)$")) {
            return "image";
        } else if (filename.matches(".*\\.(mp3|wav|ogg)$")) {
            return "audio";
        } else {
            return "other";
        }
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

    public void addFile(String attachmentString, boolean isMine) {
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout(isMine ? FlowLayout.RIGHT : FlowLayout.LEFT));
        filePanel.setBackground(new Color(235, 236, 240));

        String[] parts = attachmentString.split("\\|");
        String fileName = parts.length > 0 ? parts[0] : "unknown.png";
        String base64 = parts.length > 1 ? parts[1] : "";
        String lengthStr = parts.length > 2 ? parts[2] : "0";

        int length = 0;
        try {
            length = Integer.parseInt(lengthStr);
        } catch (NumberFormatException e) {
            length = 0; // fallback nếu không phải số
        }

        String type = getFileType(fileName);

        if (type.equals("image")) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(base64);
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
                ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(200, -1, Image.SCALE_SMOOTH));

                JLabel imageLabel = new JLabel(imageIcon);
                filePanel.add(imageLabel);

                

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (type.equals("audio")) {
            JButton playButton = new JButton("🔊 Nghe");
            playButton.addActionListener(e -> {
                try {
                    byte[] audioBytes = Base64.getDecoder().decode(base64);
                    File tempAudio = File.createTempFile("audio", ".wav");
                    try (FileOutputStream fos = new FileOutputStream(tempAudio)) {
                        fos.write(audioBytes);
                    }

                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(tempAudio);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Không thể phát âm thanh.");
                }
            });
            filePanel.add(playButton);

        } else {
            JLabel fileLabel = new JLabel("📎 " + fileName);
            fileLabel.setForeground(Color.black);

            JButton downloadButton = new JButton("Tải xuống");
            downloadButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(fileName));
                int result = fileChooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File saveFile = fileChooser.getSelectedFile();
                    try {
                        byte[] fileData = Base64.getDecoder().decode(base64);
                        try (FileOutputStream fos = new FileOutputStream(saveFile)) {
                            fos.write(fileData);
                            JOptionPane.showMessageDialog(null, "Đã lưu file thành công!");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi lưu file.");
                    }
                }
            });

            filePanel.add(fileLabel);
            filePanel.add(downloadButton);
        }

        add(filePanel);
        add(Box.createVerticalStrut(10));
        revalidate();
        repaint();
    }

    public void addEmptyMessage() {
        for(int i=0;i<10;i++) {
        	JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Căn trái hoặc phải tùy theo nhu cầu của bạn.
            emptyPanel.setBackground(new Color(235, 236, 240)); // Màu nền của panel

            // Cố định chiều cao và cho nó rỗng
            emptyPanel.setPreferredSize(new Dimension(getWidth(), 30)); // Chiều cao có thể thay đổi tùy vào nhu cầu

            add(emptyPanel);
            add(Box.createVerticalStrut(10)); // Khoảng cách giữa các messages
            revalidate();
            repaint();
        }
    }

    public void removeEmptyMessage() {
    	for(int i=0;i<10;i++) {
    		if (!emptyPanels.isEmpty()) {
                JPanel emptyPanel = emptyPanels.remove(emptyPanels.size() - 1); // Lấy panel cuối cùng trong danh sách
                remove(emptyPanel); // Xóa emptyPanel khỏi ChatBubblePanel
                revalidate();
                repaint();
            }
        }
        
    }


} 
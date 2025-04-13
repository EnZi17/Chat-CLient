package view;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import myUtil.Stego;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
public class ChatBubblePanel extends JPanel {
    public ChatBubblePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(34, 38, 43)); // Đổi màu nền của toàn bộ panel

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
        filePanel.setBackground(new Color(34, 38, 43));

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

                // 🔍 Giải mã tin ẩn nếu là ảnh có tiền tố secret_
                if (fileName.startsWith("secret_")) {
                    try {
                        String secret = Stego.decodeTextFromImage(img,length);
                        if (secret != null && !secret.isBlank()) {
                            JLabel secretLabel = new JLabel("🔐 " + secret);
                            secretLabel.setForeground(Color.YELLOW);
                            filePanel.add(secretLabel);
                        }
                    } catch (Exception e) {
                        // Không có tin hoặc lỗi giải mã
                    }
                }

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
            fileLabel.setForeground(Color.WHITE);

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

   




}

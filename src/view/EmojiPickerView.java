package view;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmojiPickerView extends JPopupMenu {
    private final JTextComponent target;

    public EmojiPickerView(JTextComponent target) {
        this.target = target;
        initUI();
    }

    private void initUI() {
        String[] emojis = {"😊", "😂", "❤️", "👍", "🎉", "😎", "😢", "🔥", "💯", "🙏"};

        JPanel emojiPanel = new JPanel(new GridLayout(2, 5, 5, 5)); // 2 hàng, 5 cột
        emojiPanel.setBackground(new Color(255, 255, 255));

        for (String emoji : emojis) {
            JButton button = new JButton(emoji);
            button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
            button.setMargin(new Insets(5, 5, 5, 5));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    target.setText(target.getText() + emoji);
                    setVisible(false); // Đóng popup sau khi chọn
                }
            });

            emojiPanel.add(button);
        }

        add(emojiPanel);
        pack();
    }
}

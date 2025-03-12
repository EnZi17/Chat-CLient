package view;

import java.awt.*;
import javax.swing.*;

public class Index {

    private JFrame frame;

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

    private void initialize() {
        frame = new JFrame("Index");
        frame.setBounds(100, 100, 800, 500); // Kích thước cửa sổ lớn hơn
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Căn giữa màn hình
        frame.getContentPane().setBackground(new Color(230, 240, 255)); // Tông màu xanh nhạt
    }
}

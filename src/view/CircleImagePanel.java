package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import javax.imageio.ImageIO;

public class CircleImagePanel extends JPanel {
    public BufferedImage image;
    private int borderSize = 0;
    private Color borderColor = new Color(60, 60, 60);

    // Constructor nhận chuỗi base64 thay vì path ảnh
    public CircleImagePanel(String base64Image) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            setPreferredSize(new Dimension(400, 400));
            setOpaque(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
		return image;
	}

	public void setImage(String base64Image) {
		byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        try {
			this.image = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Chuyển từ path ảnh sang chuỗi nhị phân base64
    public static String imageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = fis.readAllBytes();
            fis.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage getHighQualityCircleImage(int size) {
        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Làm trong suốt toàn bộ ảnh đầu tiên
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, size, size);

        // Chuyển sang chế độ vẽ ảnh
        g2.setComposite(AlphaComposite.SrcOver);

        int width = image.getWidth();
        int height = image.getHeight();
        int cropSize = Math.min(width, height);
        int x = (width - cropSize) / 2;
        int y = (height - cropSize) / 2;

        BufferedImage squareImage = image.getSubimage(x, y, cropSize, cropSize);

        // Tạo mask hình tròn
        BufferedImage mask = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gMask = mask.createGraphics();
        gMask.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gMask.setColor(Color.BLACK);
        gMask.fillOval(0, 0, size, size);
        gMask.dispose();

        // Resize ảnh và áp mask
        BufferedImage scaledImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg = scaledImage.createGraphics();
        gImg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        gImg.drawImage(squareImage, 0, 0, size, size, null);
        gImg.dispose();

        // Áp mask lên ảnh
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int alpha = (mask.getRGB(i, j) >> 24) & 0xff;
                int rgb = scaledImage.getRGB(i, j);
                rgb = (rgb & 0x00ffffff) | (alpha << 24);
                output.setRGB(i, j, rgb);
            }
        }

        g2.dispose();
        return output;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int size = Math.min(getWidth(), getHeight());
            BufferedImage circleImage = getHighQualityCircleImage(size);

            // Vẽ ảnh đã cắt vào JPanel với anti-aliasing
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(circleImage, 0, 0, null);

            // Vẽ viền nếu cần
            if (borderSize > 0) {
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(borderSize));
                g2d.drawOval(borderSize / 2, borderSize / 2, size - borderSize, size - borderSize);
            }
        }
    }
}

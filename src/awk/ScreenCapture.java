package awk;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ScreenCapture {
	public static void main(String args[]) {
		try {
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			Robot robot = new Robot();
			BufferedImage img = robot.createScreenCapture(new Rectangle(size));
			File save_path = new File("screen.jpg");
			ImageIO.write(img, "JPG", save_path);
		} catch (Exception e) {

		}
	}
}

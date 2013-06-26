package demo.jdk7;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.LayerUI;

public class Wallpaper {
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createUI();
      }
    });
  }

  public static void createUI() {
    JFrame f = new JFrame("Wallpaper");
    
    JPanel panel = createPanel();
    LayerUI<JComponent> layerUI = new WallpaperLayerUI();
    JLayer<JComponent> jlayer = new JLayer<JComponent>(panel, layerUI);
    
    f.add (jlayer);
    
    f.setSize(300, 200);
    f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo (null);
    f.setVisible (true);
  }

  private static JPanel createPanel() {
    JPanel p = new JPanel();

    ButtonGroup entreeGroup = new ButtonGroup();
    JRadioButton radioButton;
    p.add(radioButton = new JRadioButton("Beef", true));
    entreeGroup.add(radioButton);
    p.add(radioButton = new JRadioButton("Chicken"));
    entreeGroup.add(radioButton);
    p.add(radioButton = new JRadioButton("Vegetable"));
    entreeGroup.add(radioButton);

    p.add(new JCheckBox("Ketchup"));
    p.add(new JCheckBox("Mustard"));
    p.add(new JCheckBox("Pickles"));

    p.add(new JLabel("Special requests:"));
    p.add(new JTextField(20));

    JButton orderButton = new JButton("Place Order");
    p.add(orderButton);

    return p;
  }
}

class WallpaperLayerUI extends LayerUI<JComponent> {
	  @Override
	  public void paint(Graphics g, JComponent c) {
	    super.paint(g, c);

	    Graphics2D g2 = (Graphics2D) g.create();

	    int w = c.getWidth();
	    int h = c.getHeight();
	    g2.setComposite(AlphaComposite.getInstance(
	            AlphaComposite.SRC_OVER, .5f));
	    g2.setPaint(new GradientPaint(0, 0, Color.yellow, 0, h, Color.red));
	    g2.fillRect(0, 0, w, h);

	    g2.dispose();
	  }
	}

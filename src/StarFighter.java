// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

public class StarFighter extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 900;

    public StarFighter() {
        super("STARFIGHTER");
        setSize(WIDTH,HEIGHT);

        OuterSpace theGame = new OuterSpace();
        ((Component)theGame).setFocusable(true);

        getContentPane().add(theGame);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main( String args[] ) {
        StarFighter run = new StarFighter();
    }
    
    private static ImageFilter filter = new RGBImageFilter() {
        int transparentColor = Color.BLACK.getRGB() | 0xFF000000;
        
        @Override
        public final int filterRGB(int x, int y, int rgb) {
            if ((rgb | 0xFF000000) == transparentColor) {
                return 0x00FFFFFF & rgb;
            } else {
                return rgb;
            }
        }
    };
    
    public static Image filter(Image image) {
        ImageProducer filteredImgProd = new FilteredImageSource(image.getSource(), filter);
        Image transparentImg = Toolkit.getDefaultToolkit().createImage(filteredImgProd);
        return transparentImg;
    }
}
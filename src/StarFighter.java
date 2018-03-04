// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import javax.swing.JFrame;
import java.awt.Component;

public class StarFighter extends JFrame {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

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
}
// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
    private Ship ship;

    // uncomment once you are ready for this part
    private AlienHorde horde;
    private Bullets shots;
    

    private boolean[] keys;
    private BufferedImage back;

    public OuterSpace() {
        setBackground(Color.black);

        keys = new boolean[5];

        //instantiate other instance variables
        //Ship, Alien
        ship = new Ship(375, 475, 50, 50, 2);
        
        horde = new AlienHorde(50);
        
        shots = new Bullets();

        addKeyListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    @Override
    public void update(Graphics window) {
        paint(window);
    }

    @Override
    public void paint( Graphics g ) {
        //set up the double buffering to make the game animation nice and smooth
        Graphics2D g2D = (Graphics2D)g;
        
        //g2D.setPaint(Color.WHITE);
        //g2D.fillRect(0, 0, 50, 50);

        //take a snap shop of the current screen and same it as an image
        //that is the exact same width and height as the current screen
        if(back==null)
           back = (BufferedImage)(createImage(getWidth(),getHeight()));

        //create a graphics reference to the back ground image
        //we will draw all changes on the background image
        Graphics gBack = back.createGraphics();

        gBack.setColor(Color.BLUE);
        gBack.drawString("StarFighter ", 25, 50 );
        gBack.setColor(Color.BLACK);
        gBack.fillRect(0,0,800,600);

        //add code to move Ship, Alien, etc.
        if(keys[0]) {
            ship.move("LEFT");
        }
        if(keys[1]) {
            ship.move("RIGHT");
        }
        if(keys[2]) {
            ship.move("UP");
        }
        if(keys[3]) {
            ship.move("DOWN");
        }
        if(keys[4]) {
            Ammo shot = ship.shoot();
            if(shot != null) shots.add(shot);
        }
        
        shots.cleanEmUp();
        
        shots.moveEmAll();
        horde.removeDeadOnes(shots.getList());
        horde.moveEmAll();
        ship.checkForDeath(horde.getAliens());

        //add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
        ship.upCnt();
        horde.upCnt();
        
        ship.draw(gBack);
        horde.drawEmAll(gBack);
        shots.drawEmAll(gBack);

        g2D.drawImage(back, null, 0, 0);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = true;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = false;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //no code needed here
    }

    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(5);
                repaint();
            }
        } catch(Exception e){
        }
    }
}


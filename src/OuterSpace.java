// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class OuterSpace extends Canvas implements MouseListener, Runnable {
    private Ship ship;

    // uncomment once you are ready for this part
    private AlienHorde horde;
    private Bullets shots;
    
    private boolean shooting;
    
    private BufferedImage back;
    
    private Point lastValidPoint;

    public OuterSpace() {
        setBackground(Color.black);

        //instantiate other instance variables
        //Ship, Alien
        ship = new Ship(375, 475, 50, 50, 2);
        lastValidPoint = new Point(375, 475);
        
        horde = new AlienHorde(10);
        horde.setShip(ship);
        
        shots = new Bullets();

        addMouseListener(this);
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
        
        Point mouse = getMousePosition();
        if(mouse != null) lastValidPoint = mouse;

        gBack.setColor(Color.BLUE);
        gBack.drawString("StarFighter ", 25, 50 );
        gBack.setColor(Color.BLACK);
        gBack.fillRect(0,0,800,600);

        //add code to move Ship, Alien, etc.
        /*if(keys[0]) {
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
        }*/
        ship.moveTo(lastValidPoint);
        if(shooting) {
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
    public void run() {
        try {
            while(true) {
                Thread.sleep(5);
                repaint();
            }
        } catch(Exception e){
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Ammo shot = ship.shoot();
        if(shot != null) shots.add(shot);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        shooting = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shooting = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}


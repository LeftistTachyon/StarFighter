// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Ship extends MovingThing {
    protected int speed;
    private Image image;

    public Ship() {
        this(10,10,10,10,10);
    }

    public Ship(int x, int y) {
       //add code here
        this(x, y, 10, 10, 10);
    }

    public Ship(int x, int y, int s) {
       //add code here
        this(s, y, 10, 10, s);
    }

    public Ship( int x, int y, int w, int h, int s ) {
        super( x, y, w, h );
        speed = s;
        try {
            File f = new File("images/ship.jpg");
            image = ImageIO.read( f );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }


    @Override
    public void setSpeed(int s) {
       //add more code
        speed = s;
    }

    @Override
    public int getSpeed() {
       return speed;
    }

    @Override
    public void move(String direction) {
        //add code here
        switch(direction) {
            case "LEFT":
                xPos -= speed;
                break;
            case "UP":
                yPos -= speed;
                break;
            case "DOWN":
                yPos += speed;
                break;
            case "RIGHT":
                xPos += speed;
                break;
        }
        resetHitBox();
    }
    
    public Ammo shoot() {
        int middleX = xPos + (width / 2);
        return new Ammo(middleX, yPos, 3);
    }

    @Override
    public void draw( Graphics window ) {
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }

    @Override
    public String toString() {
        return super.toString() + " " + getSpeed();
    }
}

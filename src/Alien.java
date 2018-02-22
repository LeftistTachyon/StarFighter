// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Alien extends MovingThing {
    private int speed;
    private Image image;
    private String currentDirection = "RIGHT";

    public Alien() {
        this(0,0,30,30,0);
    }

    public Alien(int x, int y) {
        //add code here
        this(x, y, 30, 30, 0);
    }

    public Alien(int x, int y, int s) {
        //add code here
        this(x, y, 30, 30, s);
    }

    public Alien(int x, int y, int w, int h, int s) {
        super(x, y, w,h);
        speed=s;
        try {
            File f = new File("images/alien.jpg");
            image = ImageIO.read( f );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSpeed(int s) {
        //add code
        speed = s;
    }

    @Override
    public int getSpeed() {
       return speed;
    }

    @Override
    public void move(String direction)
    {
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

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }
    
    public void move() {
        move(currentDirection);
    }

    @Override
    public void draw( Graphics window ) {
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }

    @Override
    public String toString() {
        return "Alien at (" + xPos + ", " + yPos + ")";
    }
}

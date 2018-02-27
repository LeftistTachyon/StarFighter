// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;
import javax.imageio.ImageIO;

public class Alien extends MovingThing {
    private int speed, cntr = 0;
    private Image image;
    private Point toGo;
    private static Random r = new Random();

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
        speed = s;
        toGo = new Point(r.nextInt(800 - width), r.nextInt(height));
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
    
    public void move() {
        if(xPos == toGo.x && yPos == toGo.y) {
            return;
        }
        Point here = new Point(xPos, yPos);
        double wey = (speed)/here.distance(toGo);
        if(wey > 1) wey = 1;
        int xDiff = (int) Math.round(wey * (toGo.x - here.x)), 
                yDiff = (int) Math.round(wey * (toGo.y - here.y));
        xPos += xDiff;
        yPos += yDiff;
        resetHitBox();
    }
    
    public void setDestination(Point p) {
        toGo = p;
    }
    
    public boolean atDestination() {
        return xPos == toGo.x && yPos == toGo.y;
    }

    @Override
    public void draw( Graphics window ) {
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }
    
    public void upCnt() {
        cntr++;
    }

    public int getCnt() {
        return cntr;
    }
    
    public void resetCnt() {
        cntr = 0;
    }

    @Override
    public String toString() {
        return "Alien at (" + xPos + ", " + yPos + ")";
    }
}

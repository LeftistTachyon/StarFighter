// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;

public class Ammo extends MovingThing {
    private int speed;

    public Ammo() {
        this(0, 0, 1);
    }

    public Ammo(int x, int y) {
        //add code
        this(x, y, 1);
    }

    public Ammo(int x, int y, int s) {
        //add code
        super(x - 2, y, 4, 10);
        speed = s;
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
    public void draw( Graphics g ) {
        //add code to draw the ammo
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 4, 10);
    }

    @Override
    public void move( String direction ) {
        //add code to move the ammo
        yPos -= speed;
        resetHitBox();
    }

    @Override
    public String toString() {
        return "Shot at (" + xPos + ", " + yPos + ")";
    }
}

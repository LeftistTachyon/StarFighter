// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;

public class Ammo extends MovingThing {
    private int speed;
    public final boolean fromShip;

    public Ammo() {
        this(0, 0, 1, true);
    }

    public Ammo(int x, int y) {
        //add code
        this(x, y, 1, true);
    }
    
    public Ammo(int x, int y, int s, boolean byShip) {
        super(x - 2, y, 4, 10);
        speed = s;
        fromShip = byShip;
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
    public void drawThing( Graphics g ) {
        //add code to draw the ammo
        if(fromShip) g.setColor(Color.RED);
        else g.setColor(Color.YELLOW);
        g.fillRect(xPos, yPos, 4, 10);
    }

    @Override
    public void move( String direction ) {
        //add code to move the ammo
        if(fromShip) yPos -= speed;
        else yPos += speed;
        resetHitBox();
    }

    @Override
    public String toString() {
        return "Shot at (" + xPos + ", " + yPos + ")";
    }
}

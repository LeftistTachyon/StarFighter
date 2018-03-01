// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public abstract class MovingThing implements Moveable
{
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected boolean drawBounds = false;
    protected Rectangle2D shape;

    public MovingThing()
    {
        this(10, 10, 10, 10);
    }

    public MovingThing(int x, int y)
    {
        this(x, y, 10, 10);
    }

    public MovingThing(int x, int y, int w, int h)
    {
        //add code here
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        shape = new Rectangle2D.Double(x, y, w, h);
    }

    @Override
    public void setPos( int x, int y)
    {
        //add code here
        xPos = x;
        yPos = y;
    }

    @Override
    public void setX(int x)
    {
        //add code here
        xPos = x;
    }

    @Override
    public void setY(int y)
    {
        //add code here
        yPos = y;
    }

    @Override
    public int getX()
    {
        return xPos;   //finish this method
    }

    @Override
    public int getY()
    {
        return yPos;  //finish this method
    }

    @Override
    public void setWidth(int w)
    {
        //add code here
        width = w;
    }

    @Override
    public void setHeight(int h)
    {
        //add code here
        height = h;
    }

    @Override
    public int getWidth()
    {
        return width;  //finish this method
    }

    @Override
    public int getHeight()
    {
        return height;  //finish this method
    }
    
    protected void resetHitBox() {
        shape = new Rectangle2D.Double(xPos, yPos, width, height);
    }
    
    public void drawBounds(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) shape.getX(), (int) shape.getY(), (int) shape.getWidth(), (int) shape.getHeight());
    }
    
    public boolean intersects(MovingThing other) {
        return new Area(shape).intersects(other.shape);
    }

    public abstract void move(String direction);
    public abstract void draw(Graphics window);
}
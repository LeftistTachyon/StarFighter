// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;

public abstract class MovingThing implements Moveable
{
    protected int xPos;
    protected int yPos;
    private int width;
    private int height;

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

    public abstract void move(String direction);
    public abstract void draw(Graphics window);

    @Override
    public String toString()
    {
        return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
    }
}
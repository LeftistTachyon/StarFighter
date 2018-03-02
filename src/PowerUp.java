import java.awt.Color;
import java.awt.Graphics;

public class PowerUp extends MovingThing {
    private int speed;
    private Type type;
    
    public PowerUp(int x, int y) {
        this(x, y, 0, 0, 0);
    }

    public PowerUp(int x, int y, int w, int h) {
        this(x, y, w, h, 0);
    }

    public PowerUp(int x, int y, int w, int h, int s) {
        super(x, y, w, h);
        speed = s;
        int r = (int) (Math.random() * Type.values().length - 1);
        type = Type.values()[r];
    }

    @Override
    public void move(String direction) {
        move();
    }

    public void move() {
        yPos -= speed;
    }

    @Override
    public void draw(Graphics window) {
        switch(type) {
            case MULTISHOT:
                window.setColor(Color.WHITE);
                window.fillOval(xPos, yPos, width, height);
                window.setColor(Color.BLACK);
                window.fillOval(xPos + width/8, yPos, width*3/4, height*3/4);
                window.setColor(Color.WHITE);
                window.fillOval(xPos + width/4, yPos + height/4, width/2, height/2);
                break;
            case NULL:
            default:
                break;
        }
    }

    @Override
    public void setSpeed(int s) {
        speed = s;
    }

    @Override
    public int getSpeed() {
        return speed;
    }
    
    public enum Type {
        MULTISHOT(), NULL();
    }
}
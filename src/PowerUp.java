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
        type = Type.random();
    }

    @Override
    public void move(String direction) {
        move();
    }

    public void move() {
        yPos += speed;
        resetHitBox();
    }

    public Type getType() {
        return type;
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
            case ULTRASHOT:
                window.setColor(Color.RED);
                window.fillOval(xPos, yPos, width, height);
                window.setColor(Color.ORANGE);
                window.fillOval(xPos + width/4, yPos, width/2, height);
                window.setColor(Color.YELLOW);
                window.fillOval(xPos + width/4, yPos + height/4, width/2, height/2);
                break;
            case HEART:
                window.setColor(Color.WHITE);
                window.fillOval(xPos, yPos, width, height);
                window.drawImage(OuterSpace.getHeart(), xPos + width/8, yPos + height/8, width*3/4, height*3/4, null);
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
        MULTISHOT(1.0), ULTRASHOT(0.1), HEART(0.2), NULL(0.0);
        
        private final double weight;
        
        private Type(double w) {
            weight = w;
        }

        public double getWeight() {
            return weight;
        }
        
        public static double totalWeights() {
            double output = 0;
            for(Type type : values()) {
                output += type.weight;
            }
            return output;
        }
        
        public static Type random() {
            double r = Math.random() * totalWeights();
            for(Type type : values()) {
                if(r < type.weight) {
                    return type;
                } else r -= type.weight;
            }
            return NULL;
        }
    }
}
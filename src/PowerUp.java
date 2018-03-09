import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

public class PowerUp extends MovingThing {
    private int speed;
    private Type type;
    private GeneralPath shield;
    
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
        Graphics2D g2D = (Graphics2D) window;
        switch(type) {
            case MULTISHOT:
                g2D.setColor(Color.WHITE);
                g2D.fillOval(xPos, yPos, width, height);
                g2D.setColor(Color.BLACK);
                g2D.fillOval(xPos + width/8, yPos, width*3/4, height*3/4);
                g2D.setColor(Color.WHITE);
                g2D.fillOval(xPos + width/4, yPos + height/4, width/2, height/2);
                break;
            case ULTRASHOT:
                g2D.setColor(Color.RED);
                g2D.fillOval(xPos, yPos, width, height);
                g2D.setColor(Color.ORANGE);
                g2D.fillOval(xPos + width/4, yPos, width/2, height);
                g2D.setColor(Color.YELLOW);
                g2D.fillOval(xPos + width/4, yPos + height/4, width/2, height/2);
                break;
            case HEART:
                g2D.setColor(Color.WHITE);
                g2D.fillOval(xPos, yPos, width, height);
                g2D.drawImage(OuterSpace.getHeart(), xPos + width/4, yPos + height/4, width/2, height/2, null);
                break;
            case SHIELD:
                g2D.setColor(Color.WHITE);
                g2D.fillOval(xPos, yPos, width, height);
                g2D.setColor(Color.BLUE);
                shield = new GeneralPath();
                shield.moveTo(5 + xPos, yPos);
                shield.quadTo(10 + xPos, 10 + yPos, 15 + xPos, yPos);
                shield.quadTo(20 + xPos, 10 + yPos, 25 + xPos, yPos);
                shield.curveTo(15 + xPos, 30 + yPos, 15 + xPos, 30 + yPos, 5 + xPos, yPos);
                shield.closePath();
                g2D.fill(shield);
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
        MULTISHOT(1.0), ULTRASHOT(0.1), HEART(0.2), SHIELD(10000000), NULL(0.0);
        
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
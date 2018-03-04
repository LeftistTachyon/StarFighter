// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Ship extends MovingThing {
    private static int cntr = 0;
    private boolean dead = false;
    private int multishot = -1, ultrashot = -1;
    protected int speed;
    private Image image, explosion;

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
            f = new File("images/explosion.gif");
            explosion = ImageIO.read(f);
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
        if(dead) return;
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
        if(xPos < 0) xPos = 0;
        if(xPos > StarFighter.WIDTH - width) xPos = StarFighter.WIDTH - width;
        if(yPos < 0) yPos = 0;
        if(yPos > StarFighter.HEIGHT - width) yPos = StarFighter.HEIGHT - height;
        resetHitBox();
    }
    
    public void moveTo(Point p) {
        if(dead) return;
        xPos = p.x - width/2;
        yPos = p.y;
        resetHitBox();
    }
    
    public ArrayList<Ammo> shoot() {
        if(cntr != 50 || dead) {
            return null;
        } else {
            cntr = 0;
            ArrayList<Ammo> output = new ArrayList<>();
            int middleX = xPos + (width / 2);
            output.add(new Ammo(middleX, yPos, 3, true));
            if(multishot > 0 || ultrashot > 0) {
                output.add(new Ammo(xPos + (3*width/10), yPos + (height/4), 3, true));
                output.add(new Ammo(xPos + (7*width/10), yPos + (height/4), 3, true));
            }
            if (ultrashot > 0) {
                output.add(new Ammo(xPos + (width / 10), yPos + (height / 2), 3, true));
                output.add(new Ammo(xPos + (9 * width / 10), yPos + (height / 2), 3, true));
            }
            return output;
        }
    }
    
    public void checkForAlienDeath(List<Alien> aliens) {
        if(dead) return;
        for(int i = 0;i<aliens.size();i++) {
            if(intersects(aliens.get(i))) {
                aliens.remove(i);
                dead = true;
                cntr = 0;
                return;
            }
        }
    }
    
    public void checkForBulletDeath(List<Ammo> bullets) {
        if(dead) return;
        for(int i = 0;i<bullets.size();i++) {
            if(intersects(bullets.get(i))) {
                bullets.remove(i);
                dead = true;
                cntr = 0;
                return;
            }
        }
    }
    
    public void checkForPowerUps(List<PowerUp> powerUps) {
        if(dead) return;
        for(int i = powerUps.size() - 1; i >= 0; i--) {
            if(intersects(powerUps.get(i))) {
                PowerUp pu = powerUps.remove(i);
                powerUp(pu.getType());
            }
        }
    }

    @Override
    public void draw( Graphics window ) {
        if(!dead) window.drawImage(image, xPos, yPos, width, height, null); 
        else if(cntr != 100)
            window.drawImage(explosion, xPos - cntr, yPos - cntr, width + 2*cntr, height + 2*cntr, null);
    }

    @Override
    public String toString() {
        return super.toString() + " " + getSpeed();
    }
    
    public void upCnt() {
        if(dead) {
            if(cntr < 100) cntr++;
        } else if(cntr < 50) cntr++;
        if(multishot >= 0) multishot--;
        if(ultrashot >= 0) ultrashot--;
    }
    
    public void powerUp(PowerUp.Type type) {
        switch(type) {
            case MULTISHOT:
                multishot = 500;
                break;
            case ULTRASHOT:
                ultrashot = 250;
                break;
        }
    }

    public boolean isDead() {
        return dead;
    }
}

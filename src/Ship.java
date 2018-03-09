// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Ship extends MovingThing {
    private static int cntr1 = 0, cntr2 = 60;
    private boolean dead, shielded;
    private int multishot, ultrashot, lives, roll;
    protected int speed;
    private static Image top, bottom, sideL, sideR, explosion;
    
    static {
        try {
            File f = new File("images/top.jpg");
            top = StarFighter.filter(ImageIO.read( f ));
            f = new File("images/bottom.jpg");
            bottom = StarFighter.filter(ImageIO.read( f ));
            f = new File("images/sideR.png");
            sideR = StarFighter.filter(ImageIO.read( f ));
            f = new File("images/sideL.png");
            sideL = StarFighter.filter(ImageIO.read( f ));
            f = new File("images/explosion.gif");
            explosion = ImageIO.read(f);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

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
        multishot = -1; 
        ultrashot = -1; 
        lives = 3;
        dead = false;
        roll = 0;
        speed = s;
        shielded = false;
        if(cntr1 > 50) cntr1 = 50;
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
        if(xPos > OuterSpace.WINDOW_WIDTH - width) xPos = OuterSpace.WINDOW_WIDTH - width;
        if(yPos < 0) yPos = 0;
        if(yPos > OuterSpace.WINDOW_HEIGHT - width) yPos = OuterSpace.WINDOW_HEIGHT - height;
        resetHitBox();
    }
    
    public void moveTo(Point p) {
        if(dead || !OuterSpace.inWindow(p)) return;
        int newX = p.x - width/2;
        if(newX - xPos < 0) roll = -1;
        else roll = 1;
        xPos = newX;
        yPos = p.y;
        resetHitBox();
    }
    
    public ArrayList<Ammo> shoot() {
        if(cntr1 < 50 || dead) {
            return null;
        } else {
            cntr1 = 0;
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
    
    public void roll() {
        if(cntr2 >= 500 && !dead) {
            cntr2 = 0;
            roll = 2;
        }
    }
    
    public void checkForAlienDeath(List<Alien> aliens) {
        if(dead) return;
        for(int i = 0;i<aliens.size();i++) {
            if(intersects(aliens.get(i))) {
                aliens.remove(i);
                if(shielded) {
                    shielded = false;
                } else {
                    lives--;
                    if(lives == 0) dead = true;
                }
                cntr1 = 0;
                return;
            }
        }
    }
    
    public void checkForBulletDeath(List<Ammo> bullets) {
        if(dead) return;
        for(int i = 0;i<bullets.size();i++) {
            if(intersects(bullets.get(i))) {
                bullets.remove(i);
                if(shielded) {
                    shielded = false;
                } else {
                    lives--;
                    if(lives == 0) dead = true;
                }
                cntr1 = 0;
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
        if(!dead) {
            if(shielded) {
                Graphics2D g2D = (Graphics2D) window;
                RadialGradientPaint aura = new RadialGradientPaint(
                        xPos + width/2, yPos + height/2, width + height, 
                        new float[]{0.0f, 1.0f}, new Color[]{Color.BLACK, Color.WHITE}
                );
                g2D.fillOval(xPos - width/2, yPos - height/2, width*2, height*2);
            }
            if(roll == 2 || roll == 0)  {
                window.drawImage(top, xPos, yPos, width, height, null); 
            } else if(cntr2 < 20) {
                if(roll > 0) {
                    window.drawImage(sideL, xPos, yPos, width, height, null); 
                } else {
                    window.drawImage(sideR, xPos, yPos, width, height, null); 
                }
            } else if(cntr2 < 40) {
                window.drawImage(bottom, xPos, yPos, width, height, null); 
            } else if(cntr2 < 60) {
                if(roll > 0) {
                    window.drawImage(sideR, xPos, yPos, width, height, null); 
                } else {
                    window.drawImage(sideL, xPos, yPos, width, height, null); 
                }
            } else {
                window.drawImage(top, xPos, yPos, width, height, null); 
            }
        } else if(cntr1 != 100)
            window.drawImage(explosion, xPos - cntr1, yPos - cntr1, width + 2*cntr1, height + 2*cntr1, null);
    }

    @Override
    public String toString() {
        return super.toString() + " " + getSpeed();
    }
    
    public void upCnt() {
        if(dead) {
            if(cntr1 < 100) cntr1++;
        } else {
            if(cntr1 < 50) cntr1++;
            if(cntr2 < 500) cntr2++;
            if(cntr2 > 60) roll = 0;
        }
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
            case HEART:
                lives++;
                break;
            case SHIELD:
                shielded = true;
                break;
        }
    }
    
    @Override
    protected void resetHitBox() {
        if(dead) return;
        if(roll != 0) {
            shape = new Rectangle2D.Double(xPos, yPos, width*3/20, height);
        } else shape = new Rectangle2D.Double(xPos, yPos, width, height);
    }

    public boolean isDead() {
        return dead;
    }

    public int getLives() {
        return lives;
    }

    public static int getCntr1() {
        return cntr1;
    }

    public static int getCntr2() {
        return cntr2;
    }
}

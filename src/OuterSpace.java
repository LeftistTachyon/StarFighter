// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class - 
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class OuterSpace extends Canvas implements MouseListener, Runnable {
    public static final int WINDOW_WIDTH = StarFighter.WIDTH - 50, 
            WINDOW_HEIGHT = StarFighter.HEIGHT - 175, WINDOW_DX = 25, 
            WINDOW_DY = 75;
    
    private static BufferedImage scoreImage, heart;
    
    private static GradientPaint ry, bg;
    
    static {
        try {
            scoreImage = ImageIO.read(new File("images/score.jpg"));
            heart = ImageIO.read(new File("images/heart.png"));
        } catch (IOException ex) {
            scoreImage = null;
            heart = null;
            System.out.println("Cannot find image");
        }
        ry = new GradientPaint(StarFighter.WIDTH - 75, 0, Color.RED, StarFighter.WIDTH - 25, 0, Color.YELLOW);
        bg = new GradientPaint(StarFighter.WIDTH - 150, 0, Color.BLUE, StarFighter.WIDTH - 100, 0, Color.GREEN);
    }
    
    private Ship ship;

    // uncomment once you are ready for this part
    private AlienHorde horde;
    private Bullets shipShots, alienShots;
    
    private PowerUps powerUps;
    
    private boolean shooting, foughtBoss, beatBoss;
    
    private BufferedImage back, window;
    
    private Point lastValidPoint;
    
    private int score = 0;
    
    private int cntr = 0;

    public OuterSpace() {
        setBackground(Color.black);

        //instantiate other instance variables
        //Ship, Alien
        ship = new Ship(WINDOW_WIDTH/2 - 25, WINDOW_HEIGHT - 125, 50, 50, 2);
        lastValidPoint = new Point(375, 475);
        
        horde = new AlienHorde(30);
        horde.setShip(ship);
        
        shipShots = new Bullets();
        
        powerUps = new PowerUps();
        
        alienShots = new Bullets();
        
        foughtBoss = false;
        beatBoss = false;

        addMouseListener(this);
        new Thread(this).start();

        setVisible(true);
    }

    @Override
    public void update(Graphics window) {
        paint(window);
    }

    @Override
    public void paint( Graphics g ) {
        //set up the double buffering to make the game animation nice and smooth
        Graphics2D g2D = (Graphics2D)g;

        //take a snap shop of the current screen and same it as an image
        //that is the exact same width and height as the current screen
        if(back==null)
           back = (BufferedImage)(createImage(StarFighter.WIDTH, StarFighter.HEIGHT));
        
        if(window == null)
            window = (BufferedImage) (back.getSubimage(WINDOW_DX, WINDOW_DY, WINDOW_WIDTH, WINDOW_HEIGHT));

        //create a graphics reference to the back ground image
        //we will draw all changes on the background image
        Graphics2D gBack = back.createGraphics();
        gBack.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gBack.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        gBack.setColor(Color.WHITE);
        gBack.fillRect(0, 0, StarFighter.WIDTH, StarFighter.HEIGHT);
        gBack.setColor(Color.GRAY);
        gBack.fillRoundRect(WINDOW_DX - 2, WINDOW_DY - 2, WINDOW_WIDTH + 4, WINDOW_HEIGHT + 4, 17, 17);
        
        gBack.drawImage(scoreImage, null, 25, 15);
        Number.draw(score, 240, 15, gBack);
        
        for(int i = 0; i < ship.getLives(); i++) {
             gBack.drawImage(heart, WINDOW_WIDTH - 25 - (i * 50), 15, null);
        }
        
        gBack.setColor(Color.red);
        gBack.fillRoundRect(25, StarFighter.HEIGHT - 90, 100, 55, 15, 15);
        gBack.setColor(Color.WHITE);
        gBack.setFont(new Font("Consolas", Font.PLAIN, 30));
        gBack.drawString("Reset", 30, StarFighter.HEIGHT - 55);
        
        if(!ship.isDead()) {
            gBack.setPaint(ry);
            gBack.fillRect(StarFighter.WIDTH - 25 - Ship.getCntr1(), StarFighter.HEIGHT - 90, Ship.getCntr1(), 30);
        }
        gBack.setColor(Color.BLACK);
        gBack.setFont(new Font("Consolas", Font.PLAIN, 20));
        gBack.drawString("Reload", StarFighter.WIDTH - 90, StarFighter.HEIGHT - 40);
        
        if(score >= 150) {
            gBack.drawImage(heart, 433, StarFighter.HEIGHT - 95, 65, 65, null);
            gBack.setFont(new Font("Consolas", Font.BOLD, 55));
            gBack.setColor(Color.BLACK);
            gBack.drawString("L E G E N D", 450, StarFighter.HEIGHT - 45);
        }
        
        Graphics2D gWindow = window.createGraphics();
        gWindow.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gWindow.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Point mouse = getMousePosition();
        if(mouse != null) {
            mouse.translate(-WINDOW_DX, -WINDOW_DY);
            lastValidPoint = mouse;
        }
        
        gWindow.setColor(Color.BLACK);
        gWindow.fillRoundRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 15, 15);
        
        ship.moveTo(lastValidPoint);
        if(shooting) {
            ArrayList<Ammo> shot = ship.shoot();
            if(shot != null && !shot.isEmpty()) shipShots.addAll(shot);
        }
        
        shipShots.cleanEmUp();
        alienShots.cleanEmUp();
        powerUps.cleanUp();
        
        shipShots.moveEmAll();
        horde.removeDeadOnes(shipShots.getList());
        score += horde.getNumNewlyDead();
        powerUps.addAll(horde.getDrops());
        if(beatBoss) horde.moveAllToShip();
        else horde.moveEmAll();
        powerUps.moveAll();
        alienShots.moveEmAll();
        alienShots.addAll(horde.shootAll());
        alienShots.removeCollided(shipShots.getList());
        // horde.drawAllHitboxes(gBack);
        ship.checkForAlienDeath(horde.getAliens());
        ship.checkForBulletDeath(alienShots.getList());
        ship.checkForPowerUps(powerUps.getList());
        // ship.drawBounds(gBack);
        
        if(horde.allDead() && !ship.isDead()) {
            if (!foughtBoss) {
                foughtBoss = true;
                horde = new AlienHorde(0);
                int hordeSize = 50;
                int distance = (WINDOW_WIDTH - 50) / hordeSize;
                Alien.Path ap = new Alien.Path(AlienHorde.destinations1);
                for (int i = 0; i < hordeSize; i++) {
                    Alien a = new Alien(i * distance, 25, 50, 50, 2);
                    a.setPath(ap);
                    horde.add(a);
                }
            } else if(!beatBoss) {
                beatBoss = true;
                horde = new AlienHorde(0);
                horde.setShip(ship);
            }
        }
        
        if(beatBoss) {
            cntr++;
            int releaseAt = 10000 / score;
            if(cntr >= releaseAt) {
                cntr = 0;
                Alien a = new Alien((int) ((Math.random())*(WINDOW_WIDTH - 50)) - 25, 25, 50, 50, 1);
                horde.add(a);
            }
        }

        //add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship
        ship.upCnt();
        horde.upCnt();
        
        ship.draw(gWindow);
        horde.drawEmAll(gWindow);
        powerUps.drawAll(gWindow);
        shipShots.drawEmAll(gWindow);
        alienShots.drawEmAll(gWindow);
        
        
        g2D.drawImage(back, null, 0, 0);
    }
    
    public static boolean inWindow(Point p) {
        Point _p = new Point(p);
        _p.translate(WINDOW_DX, WINDOW_DY);
        return _p.x >= WINDOW_DX && _p.x <= WINDOW_DX + WINDOW_WIDTH && _p.y >= WINDOW_DY && _p.y <= WINDOW_DY + WINDOW_HEIGHT;
    }
    
    public static BufferedImage getHeart() {
        return heart;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(5);
                repaint();
            }
        } catch(Exception e){
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: // LEFT
                ArrayList<Ammo> shot = ship.shoot();
                if(shot != null && !shot.isEmpty()) 
                    shipShots.addAll(shot);
                break;
            case MouseEvent.BUTTON3: // RIGHT
                ship.roll();
                break;
            default:
                break;
        }
        
        if(e.getX() >= 25 && e.getX() <= 125 && e.getY() >= StarFighter.HEIGHT - 90 && e.getY() <= StarFighter.HEIGHT) {
            setBackground(Color.black);

            //instantiate other instance variables
            //Ship, Alien
            ship = new Ship(WINDOW_WIDTH/2 - 25, WINDOW_HEIGHT - 125, 50, 50, 2);
            lastValidPoint = new Point(375, 475);

            horde = new AlienHorde(30);
            horde.setShip(ship);

            shipShots = new Bullets();

            powerUps = new PowerUps();

            alienShots = new Bullets();

            foughtBoss = false;
            beatBoss = false;
            
            score = 0;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        shooting = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shooting = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
    enum Number {
        ZERO("images/0.jpg"), ONE("images/1.jpg"), TWO("images/2.jpg"), 
        THREE("images/3.jpg"), FOUR("images/4.jpg"), FIVE("images/5.jpg"), 
        SIX("images/6.jpg"), SEVEN("images/7.jpg"), EIGHT("images/8.jpg"), 
        NINE("images/9.jpg");
        
        private BufferedImage image;
        
        private Number(String location) {
            try {
                image = ImageIO.read(new File(location));
            } catch (IOException ex) {
                image = null;
                System.out.println("Cannot find file");
            }
        }
        
        public static Number fromInt(int i) {
            switch(i) {
                case 0:
                    return ZERO;
                case 1:
                    return ONE;
                case 2:
                    return TWO;
                case 3:
                    return THREE;
                case 4:
                    return FOUR;
                case 5:
                    return FIVE;
                case 6:
                    return SIX;
                case 7:
                    return SEVEN;
                case 8:
                    return EIGHT;
                case 9:
                    return NINE;
            }
            return null;
        }
        
        public void draw(int x, int y, Graphics2D g2D) {
            g2D.drawImage(image, null, x, y);
        }
        
        public static void draw(int i, int x, int y, Graphics2D g2d) {
            if(i == 0) {
                ZERO.draw(x, y, g2d);
                return;
            }
            x += 40 * (String.valueOf(i).length() - 1);
            while(i > 0){
                int digit = i % 10;
                Number n = fromInt(digit);
                n.draw(x, y, g2d);
                i /= 10;
                x -= 40;
            }
        }
    }
}
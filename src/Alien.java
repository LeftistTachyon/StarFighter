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
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Alien extends MovingThing {
    private int speed;
    private static Image image;
    private Path p;
    
    static {
        try {
            File f = new File("images/alien.jpg");
            image = StarFighter.filter(ImageIO.read( f ));
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public Alien() {
        this(0,0,30,30,0);
    }

    public Alien(int x, int y) {
        //add code here
        this(x, y, 30, 30, 0);
    }

    public Alien(int x, int y, int s) {
        //add code here
        this(x, y, 30, 30, s);
    }

    public Alien(int x, int y, int w, int h, int s) {
        super(x, y, w,h);
        p = new Path(new Point(x, y));
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
    public void move(String direction)
    {
       //add code here
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
        resetHitBox();
    }
    
    public void move() {
        if(p.atCurrent(xPos, yPos)) {
            return;
        }
        moveTo(p.current());
    }
    
    public void moveTo(Point p) {
        Point here = new Point(xPos, yPos);
        double wey = (speed)/here.distance(p);
        if(wey > 1) wey = 1;
        int xDiff = (int) Math.round(wey * (p.x - here.x)), 
                yDiff = (int) Math.round(wey * (p.y - here.y));
        xPos += xDiff;
        yPos += yDiff;
        resetHitBox();
    }

    @Override
    public void drawThing( Graphics window ) {
        window.drawImage(image,getX(),getY(),getWidth(),getHeight(),null);
    }

    @Override
    public String toString() {
        return "Alien at (" + xPos + ", " + yPos + ")";
    }

    public Path getPath() {
        return p;
    }

    public void setPath(Path p) {
        this.p = p;
    }
    
    public Ammo shoot() {
        if(Math.random() * 100 < 0.1) { // 0.1 percent chance
            return new Ammo(xPos + width/2, yPos + height, 3, false);
        }
        return null;
    }
    
    public PowerUp dropPowerUp() {
        if(Math.random() * 100 < 5) { // 5 percent chance
            return new PowerUp(xPos + width/2, yPos + height, 30, 30, 1);
        }
        return null;
    }
    
    public static class Path {
        private ArrayList<Point> path;
        private int at = 0;
        
        public Path(Point... p) {
            path = new ArrayList<>();
            path.addAll(Arrays.asList(p));
        }
        
        public Path(Path p) {
            path = new ArrayList<>(p.path);
            at = p.at;
        }
        
        public Point next() {
            at++;
            if(at == path.size()) at = 0;
            Point output = path.get(at);
            return output;
        }
        
        public boolean atCurrent(int x, int y) {
            return path.get(at).x == x && path.get(at).y == y;
        }
        
        public Point current() {
            return path.get(at);
        }
        
        public void addDestination(Point p) {
            path.add(p);
        }
        
        public void addDestination(int idx, Point p) {
            path.add(idx, p);
        }
        
        public int destinations() {
            return path.size();
        }
    }
}
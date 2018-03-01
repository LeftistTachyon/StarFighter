// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde {
    private static final int LIMIT = 50;
    
    private static final Point[] destinations = {
        new Point(725, 25), new Point(725, 100), new Point(25, 100), 
        new Point(25, 175), new Point(725, 175), new Point(725, 250), 
        new Point(25, 250), new Point(25, 25)
    };
    
    private static int cntr = 0;
    private List<Alien> aliens;
    private Ship ship;

    public AlienHorde(int size) {
        aliens = new ArrayList<>(size);
        int distance = 750 / size;
        for (int i = 0; i < size; i++) {
            Alien a = new Alien(
                    i * distance, 25, 
                    50, 50, 2);
            a.setPath(new Alien.Path(destinations));
            aliens.add(a);
        }
    }

    public void add(Alien al) {
        aliens.add(al);
    }

    public void drawEmAll( Graphics window ) {
        for(Alien alien : aliens) {
            alien.draw(window);
        }
    }

    public void moveEmAll() {
        for (Alien alien : aliens) {
            Alien.Path path = alien.getPath();
            if(path.atCurrent(alien.getX(), alien.getY())) {
                path.next();
            }
            alien.move();
            alien.resetHitBox();
        }
        if(cntr == LIMIT && ship != null && !aliens.isEmpty()) {
            cntr = 0;
            Alien.Path path = aliens.get((int) (aliens.size() * Math.random())).getPath();
            path.addDestination((int) (Math.random() * path.destinations()), new Point(ship.getX(), ship.getY()));
        }
    }
    
    public void drawAllHitboxes(Graphics g) {
        for (Alien alien : aliens) {
            alien.drawBounds(g);
        }
    }

    public void removeDeadOnes(List<Ammo> shots) {
        try {
            for (int i = 0; i < aliens.size(); i++) {
                for (int j = shots.size() - 1; j >= 0; j--) {
                    if(aliens.isEmpty()) {
                        return;
                    }
                    while(aliens.get(i)
                            .intersects(shots.get(j)) && shots.get(j).fromShip) {
                        aliens.remove(i);
                        shots.remove(j);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("HELP: " + e.toString());
        }
    }

    public List<Alien> getAliens() {
        return aliens;
    }
    
    public void setShip(Ship s) {
        ship = s;
    }

    @Override
    public String toString() {
        return "";
    }
    
    public void upCnt() {
        if(cntr < LIMIT) cntr++;
    }
}

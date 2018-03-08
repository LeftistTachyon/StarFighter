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
    
    public static final Point[] destinations1 = {
        new Point(OuterSpace.WINDOW_WIDTH - 75, 25), new Point(OuterSpace.WINDOW_WIDTH - 75, 100), new Point(25, 100), 
        new Point(25, 175), new Point(OuterSpace.WINDOW_WIDTH - 75, 175), new Point(OuterSpace.WINDOW_WIDTH - 75, 250), 
        new Point(25, 250), new Point(25, 25)
    };
    
    public static final Point[] destinations2 = {
        new Point(25, 100), new Point(25, 175), new Point(OuterSpace.WINDOW_WIDTH - 75, 175), 
        new Point(OuterSpace.WINDOW_WIDTH - 75, 250), new Point(25, 250), new Point(25, 25), 
        new Point(OuterSpace.WINDOW_WIDTH - 75, 25), new Point(OuterSpace.WINDOW_WIDTH - 75, 100)
    };
    
    private static int cntr = 0;
    private List<Alien> aliens, newlyDead;
    private Ship ship;

    public AlienHorde(int size) {
        aliens = new ArrayList<>(size);
        newlyDead = new ArrayList<>();
        if(size == 0) return;
        int distance = (OuterSpace.WINDOW_WIDTH - 50) / size;
        for (int i = 0; i < size; i++) {
            Alien a;
            if(i % 2 == 0) {
                a = new Alien(i * distance, 25, 50, 50, 2);
                a.setPath(new Alien.Path(destinations1));
            } else {
                a = new Alien(i * distance, 100, 50, 50, 2);
                a.setPath(new Alien.Path(destinations2));
            }
            aliens.add(a);
        }
    }

    public void add(Alien al) {
        aliens.add(al);
    }
    
    public void addAll(ArrayList<Alien> as) {
        aliens.addAll(as);
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
        if(cntr == LIMIT && ship != null && !ship.isDead() && !aliens.isEmpty()) {
            cntr = 0;
            Alien.Path path = aliens.get((int) (aliens.size() * Math.random())).getPath();
            path.addDestination((int) (Math.random() * path.destinations()), new Point(ship.getX(), ship.getY()));
        }
    }
    
    public void moveAllToShip() {
        if(ship == null) return;
        for(Alien alien : aliens) {
            alien.moveTo(new Point(ship.xPos, ship.yPos));
        }
    }
    
    public int getNumNewlyDead() {
        return newlyDead.size();
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
                        newlyDead.add(aliens.remove(i));
                        if(aliens.isEmpty()) return;
                        if(i == aliens.size()) i--;
                        shots.remove(j);
                        if(shots.isEmpty()) return;
                        if(j == shots.size()) j--;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("HELP: " + e.toString());
        }
    }
    
    public ArrayList<PowerUp> getDrops() {
        ArrayList<PowerUp> output = new ArrayList<>();
        for(Alien a : newlyDead) {
            PowerUp pu = a.dropPowerUp();
            if(pu != null) output.add(pu);
        }
        newlyDead = new ArrayList<>();
        return output;
    }
    
    public ArrayList<Ammo> shootAll() {
        ArrayList<Ammo> output = new ArrayList<>();
        for (Alien alien : aliens) {
            Ammo a = alien.shoot();
            if(a != null) output.add(a);
        }
        return output;
    }

    public List<Alien> getAliens() {
        return aliens;
    }
    
    public boolean allDead() {
        return aliens.isEmpty();
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

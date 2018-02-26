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
    private static int cntr = 0;
    private List<Alien> aliens;
    private Ship ship;

    public AlienHorde(int size) {
        aliens = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            aliens.add(new Alien(
                    (int) (Math.random() * 750), (int) (Math.random() * 10), 
                    50, 50, 1));
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
            alien.move();
        }
        if(cntr == 10 && ship != null) {
            cntr = 0;
            aliens.get((int) (aliens.size() * Math.random())).setDestination(new Point(ship.getX(), ship.getY()));
        }
    }

    public void removeDeadOnes(List<Ammo> shots) {
        try {
            for (int i = 0; i < aliens.size(); i++) {
                for (Ammo a : shots) {
                    if(aliens.isEmpty()) {
                        return;
                    }
                    while(aliens.get(i)
                            .intersects(a)) {
                        aliens.remove(i);
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
        if(cntr < 10) cntr++;
    }
}

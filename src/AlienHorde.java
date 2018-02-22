// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde {
    private List<Alien> aliens;

    public AlienHorde(int size) {
        aliens = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            aliens.add(new Alien(i*50 + 10, 10, 50, 50, 1));
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
            if(alien.getX() < 10 + alien.getWidth() || alien.getX() > 800 - (10 + alien.getWidth())) {
                alien.setCurrentDirection("DOWN");
            } else {
                int row = alien.getY() / alien.getHeight();
                if(row % 2 == 0) {
                    alien.setCurrentDirection("RIGHT");
                } else {
                    alien.setCurrentDirection("DOWN");
                }
            }
            alien.move();
        }
    }

    public void removeDeadOnes(List<Ammo> shots) {
        try {
            for(int i = aliens.size() - 1; i >= 0; i--) {
                for(Ammo a:shots) {
                    if(aliens.get(i).intersects(a)) {
                        aliens.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(shots + " " + aliens);
        }
    }

    @Override
    public String toString()
    {
            return "";
    }
}

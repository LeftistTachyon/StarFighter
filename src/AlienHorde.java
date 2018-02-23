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
            add(new Alien(i*50 + 10, 10, 50, 50, 1));
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
    }

    public void removeDeadOnes(List<Ammo> shots) {
        for(int i = aliens.size() - 1; i >= 0; i--) {
            for(Ammo a:shots) {
                if(aliens.isEmpty()) return;
                if(aliens.get(i)
                        .intersects(a)) {
                    aliens.remove(i);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "";
    }
}

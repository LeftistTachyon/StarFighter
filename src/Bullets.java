// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class -
//Lab  -

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Bullets {
    private List<Ammo> ammo;

    public Bullets() {
        ammo = new ArrayList<>();
    }

    public void add(Ammo al) {
        ammo.add(al);
    }
    
    public void addAll(ArrayList<Ammo> as) {
        ammo.addAll(as);
    }

    //post - draw each Ammo
    public void drawEmAll( Graphics g ) {
        for(Ammo a:ammo) {
            a.draw(g);
        }
    }

    public void moveEmAll() {
        for(Ammo a : ammo) {
            a.move(null);
        }
    }

    public void cleanEmUp() {
        for(int i = ammo.size()-1;i>=0;i--) {
            Ammo a = ammo.get(i);
            if(a.getY() <= -a.getHeight() || a.getY() >= OuterSpace.WINDOW_HEIGHT + a.getHeight()) {
                ammo.remove(i);
            }
        }
    }
    
    public void removeCollided(List<Ammo> bullets) {
        if(ammo == null || ammo.isEmpty() || bullets == null || bullets.isEmpty()) return;
        for(int i = ammo.size()-1; i >= 0; i--) {
            for(int j = bullets.size()-1; j >= 0; j--) {
                if(ammo.get(i).intersects(bullets.get(j))) {
                    ammo.remove(i);
                    bullets.remove(j);
                    if(i == ammo.size()) i--;
                    if(i == -1) return;
                    if(j == bullets.size()) j--;
                    if(j == -1) return;
                }
            }
        }
    }

    public List<Ammo> getList() {
        return ammo;
    }

    public void removeAll(List<Ammo> a) {
        ammo.removeAll(a);
    }

    @Override
    public String toString() {
        return ammo.toString();
    }
}

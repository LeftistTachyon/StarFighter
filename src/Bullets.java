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
            if(ammo.get(i).getY() <= -10 || ammo.get(i).getY() >= 660) {
                ammo.remove(i);
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

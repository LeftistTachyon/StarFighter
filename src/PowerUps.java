
import java.awt.Graphics;
import java.util.ArrayList;

public class PowerUps {
    private ArrayList<PowerUp> powerUps;
    
    public PowerUps() {
        powerUps = new ArrayList<>();
    }
    
    public void add(PowerUp pu) {
        powerUps.add(pu);
    }
    
    public void addAll(ArrayList<PowerUp> pus) {
        powerUps.addAll(pus);
    }
    
    public void moveAll() {
        for(PowerUp powerUp : powerUps) {
            powerUp.move();
        }
    }
    
    public void cleanUp() {
        for(int i = powerUps.size()-1; i >= 0; i--) {
            PowerUp current = powerUps.get(i);
            if(current.getY() > OuterSpace.WINDOW_HEIGHT + current.getHeight()) {
                powerUps.remove(i);
            }
        }
    }
    
    public void drawAll(Graphics g) {
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }
    }
    
    public ArrayList<PowerUp> getList() {
        return powerUps;
    }
}
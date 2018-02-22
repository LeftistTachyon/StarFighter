
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Class -
//Lab  - 

public class Shiptest
{
    public static void main( String args[] )
    {
        MovingThing test = new Ship();
        System.out.println("Ship 1 " + test);

        Ship test2 = new Ship(50,75);
        System.out.println("Ship 2 " + test2);

        Ship test3 = new Ship(7,7,6,5,1);
        test3.setX(3);
        test3.setY(5);
        System.out.println("Ship 3 " + test3);
        
        Rectangle2D rect1 = new Rectangle2D.Double(0, 0, 10, 10);
        Rectangle2D rect2 = new Rectangle2D.Double(5, 5, 10, 10);
        System.out.println(new Area(rect1).intersects(rect2));
    }
}

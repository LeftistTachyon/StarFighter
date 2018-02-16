// A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date - 
//Class -
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde
{
	private List<Alien> aliens;

	public AlienHorde(int size)
	{
            aliens = new ArrayList<>(size);
	}

	public void add(Alien al)
	{
            aliens.add(al);
	}

	public void drawEmAll( Graphics window )
	{
            for(Alien alien : aliens) {
                alien.draw(window);
            }
	}

	public void moveEmAll()
	{
	}

	public void removeDeadOnes(List<Ammo> shots)
	{
	}

        @Override
	public String toString()
	{
		return "";
	}
}

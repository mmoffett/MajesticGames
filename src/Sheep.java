import java.awt.Image;
import java.awt.Toolkit;
import java.util.Vector;


public class Sheep 
{
	private Vector<Image> sheep=new Vector<Image>();
	public Sheep()
	{
		addSheep("src/sheepGif.gif");
    	addSheep("src/sheepGifR.gif");	
	}
	public Vector<Image> getSheep()
	{
		return sheep;
	}
	private void addSheep(String path)
	{
		 Image sheepA = Toolkit.getDefaultToolkit().createImage(path);
         sheep.add(sheepA);
	}
}

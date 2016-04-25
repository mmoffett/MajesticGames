import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;


public class Cloud
{
	private Vector<Integer> heights=new Vector<Integer>();
	private Vector<Integer> widths=new Vector<Integer>();
	private Vector<Image> cloud=new Vector<Image>();
	private Vector<Integer> cloudXPos=new Vector<Integer>();
	private Vector<Integer> cloudYPos=new Vector<Integer>();
	private Random rand=new Random();
	private int backWidth;
	private int backHeight;
	public Cloud(int w, int h)
	{
		backWidth=w;
		backHeight=h;
		addCloud("src/Cloud3.png");	
		makeCloudPos();
	}
	public Vector<Integer> getCloudHeights()
	{
		return heights;
	}
	public Vector<Integer> getCloudWidths()
	{
		return widths;
	}
	public Vector<Image> getClouds()
	{
		return cloud;
	}
	public Vector<Integer> getCloudXPos()
	{
		return cloudXPos;
	}
	public Vector<Integer> getCloudYPos()
	{
		return cloudYPos;
	}
	private void addCloud(String path)
	{
		Image cloudA = Toolkit.getDefaultToolkit().createImage(path);
		ImageIcon temp=new ImageIcon(cloudA);
		widths.add(temp.getIconWidth());
		heights.add(temp.getIconHeight());
		cloud.add(cloudA);
	}
	private void makeCloudPos()
	{
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		for(int i=300; i>-backHeight+300 ; i=i-205)
		{
			for(int k=0; k<4; k++)
			{
				int x=(int)screenSize.getWidth()-widths.get(0)-(rand.nextInt((int) backWidth));
				cloudXPos.add(x);
				cloudYPos.add(i-rand.nextInt(300));
			}
		}
	}
}

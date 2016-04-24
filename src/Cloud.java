import java.awt.Image;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.ImageIcon;


public class Cloud
{
	private Vector<Integer> heights=new Vector<Integer>();
	private Vector<Integer> widths=new Vector<Integer>();
	private Vector<Image> cloud=new Vector<Image>();
	private Vector<Integer> cloudXPos=new Vector<Integer>();
	private Vector<Integer> cloudYPos=new Vector<Integer>();
	public Cloud()
	{
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
		cloudXPos.add(800);
		cloudYPos.add(20);
		cloudXPos.add(100);
		cloudYPos.add(320);
		cloudXPos.add(200);
		cloudYPos.add(-280);
		cloudXPos.add(-100);
		cloudYPos.add(-680);
	}
}

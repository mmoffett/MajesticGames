import java.awt.Image;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.ImageIcon;


public class Cloud
{
	private Vector<Integer> heights=new Vector<Integer>();
	private Vector<Integer> widths=new Vector<Integer>();
	private Vector<Image> cloud=new Vector<Image>();
	public Cloud()
	{
		addCloud("src/Cloud3.png");	
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
	private void addCloud(String path)
	{
		Image cloudA = Toolkit.getDefaultToolkit().createImage(path);
		ImageIcon temp=new ImageIcon(cloudA);
		widths.add(temp.getIconWidth());
		heights.add(temp.getIconHeight());
		cloud.add(cloudA);
	}
}

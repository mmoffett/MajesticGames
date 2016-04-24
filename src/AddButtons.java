import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class AddButtons 
{
	public AddButtons()
	{
		
	}
	/**
     * Adds a button to a container.
     * 
     * @param c          the container
     * @param title      the button title
     * @param listener   the action listener for the button
     */
    public void addButton(Container c, String file, ActionListener listener) 
    {
      JButton button = new JButton();
      c.add(button);
      button.addActionListener(listener);
      Icon i=new ImageIcon(file);
 	  button.setIcon(i);
    }
    
    
}

import java.awt.Container;
import java.awt.event.ActionListener;

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
    public void addButton(Container c, String title, ActionListener listener) 
    {
      JButton button = new JButton(title);
      c.add(button);
      button.addActionListener(listener);
    }
    
    
}

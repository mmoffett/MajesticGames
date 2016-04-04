import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
/**
*An animation of a sheep going back and forth across the screen.
* 
* @author (Marissa Moffett) 
* @version (3/2/16)
*/
public class Animation
{
    /**
    * main - the main function of the class that is executed to create animation
    * 
    */
    public static void main(String[] args)
    {
        new Animation();
    }
    /**
    * AnimatedSheep - animates the image by creating an event queue and running the animation in a created window
    * 
    * @param  g    The Graphics we plan to use
    */
    public Animation() 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            /**
             * run - creates the frame in which the animation will occur
             * 
             */
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Majestic Games");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new AnimationPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }

        });
    }

	public void moveRight() 
	{	
		
	}
	public void moveLeft()
	{
		
	}
}

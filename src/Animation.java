import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    /**
     *looks at file that contains image and performs the action to animate the image and move it back and forth across the screen.
     * 
     * @author (Marissa Moffett) 
     * @version (3/2/16)
     */
    @SuppressWarnings("serial")
	public class AnimationPane extends JPanel 
    {

        /**
		 * 
		 */
		private BufferedImage sheepA;
		private BufferedImage sheepB;
        private int xPos = 0;
        private int direction = 1;
        private int current = 1;
        Vector<BufferedImage> sheep =new Vector<BufferedImage>();
        /**
         * Constructor for objects of class AnimationPane
         */
        public AnimationPane()
        {
            try 
            {
            	String path = "C:\\Users\\MarissaRoseM\\Documents\\GitHub\\MajesticGames\\src\\sheep2a.png";
            	String path2= "C:\\Users\\MarissaRoseM\\Documents\\GitHub\\MajesticGames\\src\\sheep2b.png";
                File file = new File(path);
                File file2 = new File(path2);
                sheepA = ImageIO.read(file);
                sheepB = ImageIO.read(file2);
                sheep.add(sheepA);
                sheep.add(sheepB);
                Timer timer = new Timer(2, new ActionListener() 
                {
                    /**
                     * actionPerformed - an overridden version of the actionPerformed() function from ActionListener interface that is invoked when an action occurs
                     * 
                     * @param  e    an event that indicates that something has occurred
                     */
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        xPos += direction;
                        if (xPos + sheep.get(current).getWidth() > getWidth()+(sheep.get(current).getWidth()/2))
                        {
                            xPos = getWidth() - sheep.get(current).getWidth()+(sheep.get(current).getWidth()/2);;
                            direction *= -1;
                            current=0;
                        } else if (xPos < -(sheep.get(current).getWidth()/2))
                        {
                            xPos = -(sheep.get(current).getWidth()/2);
                            direction *= -1;
                            current=1;
                        }
                        repaint();
                    }

                });
                timer.setRepeats(true);
                timer.setCoalesce(true);
                timer.start();
            } catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
        /**
         * paintComponent - find the sheep height and draw the sheep at the current x position at a height above the bottom such that it moves along the bottom
         * 
         * @param  g    The Graphics we plan to use
         */
        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            setBackground(Color.black);
            int y = getHeight() - sheep.get(current).getHeight();
            g.drawImage(sheep.get(current), xPos, y, this);

        }

    }

}

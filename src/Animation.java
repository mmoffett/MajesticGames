import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Container;
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
     *looks at file that contains image and performs the action to 
     *animate the image and move it back and forth across the screen.
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
    	
        
		private /*Buffered*/Image sheepA;
		private /*Buffered*/Image sheepB;
        private int xPos = 0;
        private int direction = 1;
        private int current = 1;
        private boolean animation=false;
        private boolean scores=false;
        SQLiteJDBC data=new SQLiteJDBC();
        Timer timer = new Timer(2, new ActionListener() 
		 {
            /**
             * actionPerformed - an overridden version of the actionPerformed() function from 
             * ActionListener interface that is invoked when an action occurs
             * 
             * @param  e    an event that indicates that something has occurred
             */
    			 @Override
    			 public void actionPerformed(ActionEvent e)
    			 {
    				 getSheep();
    			 }
    		 });
        Vector</*Buffered*/Image> sheep =new Vector</*Buffered*/Image>();
        /**
         * Constructor for objects of class AnimationPane
         */
        public AnimationPane()
        {
            //try 
            {
            	String path = "src/sheepGif.gif";
            	String path2= "src/sheepGif.gif";
                //File file = new File(path);
                //File file2 = new File(path2);
                
                sheepA = /*ImageIO.read(file)*/Toolkit.getDefaultToolkit().createImage(path);
                sheepB = /*ImageIO.read(file2)*/Toolkit.getDefaultToolkit().createImage(path2);
                sheep.add(sheepA);
                sheep.add(sheepB);
                
                JPanel buttonPanel = new JPanel();
                
                               
                addButton(buttonPanel, "Start", new ActionListener()
                {
                	/**
                     * actionPerformed - an overridden version of the actionPerformed() function from 
                     * ActionListener interface that is invoked when an action occurs
                     * 
                     * @param  e    an event that indicates that something has occurred
                     */ 
                	@Override
                     public void actionPerformed(ActionEvent e)
                     {
                		 scores=false;
                		 timer.setRepeats(true);
      	                 timer.setCoalesce(true);
                		 if(animation==false)
                		 {
	                		animation=true;	 
			                timer.start();
                		 }
                		 else
                		 {
                			timer.restart();
                		 }
                		 
                     }
                });
                addButton(buttonPanel, "High Scores", new ActionListener()
                {
                	/**
                     * actionPerformed - an overridden version of the actionPerformed() function from 
                     * ActionListener interface that is invoked when an action occurs
                     * 
                     * @param  e    an event that indicates that something has occurred
                     */
                	@Override
                	public void actionPerformed(ActionEvent e)
                	{
                		scores=true;
                		animation=false;
                		addHighScores();
                	}
                	
                });
                addButton(buttonPanel, "Exit", new ActionListener()
                {
                	/**
                     * actionPerformed - an overridden version of the actionPerformed() function from 
                     * ActionListener interface that is invoked when an action occurs
                     * 
                     * @param  e    an event that indicates that something has occurred
                     */
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        System.exit(0);
                    }
                });
                             
                
                add(buttonPanel, BorderLayout.SOUTH);
            } //catch (IOException ex) 
            {
                //ex.printStackTrace();
            }
        }
        
        /**
         * getSheep - sets the animation to run back and forth across the screen
         */
        public void getSheep()
        {
        	xPos += direction;
            ImageObserver paintingChild = null;
			if (xPos + sheep.get(current).getWidth(paintingChild) > getWidth()+(sheep.get(current).getWidth(paintingChild)/2))
            {
                xPos = getWidth() - sheep.get(current).getWidth(paintingChild)+(sheep.get(current).getWidth(paintingChild)/2);;
                direction *= -1;
                current=0;
            } else if (xPos < -(sheep.get(current).getWidth(paintingChild)/2))
            {
                xPos = -(sheep.get(current).getWidth(paintingChild)/2);
                direction *= -1;
                current=1;
            }
            repaint();
        }
        public void addHighScores()
        {
        	repaint();
        }
        
        /**
         * paintComponent - draw the screen with the current setting
         * 
         * @param  g    The Graphics we plan to use
         */
        @Override
        protected void paintComponent(Graphics g) 
        {
        	super.paintComponent(g);
            setBackground(Color.black);
            g.setColor(Color.white);
            drawSheep(g);
            drawHighScores(g);

        }
        /**
         * drawSheep - find the sheep height and draw the sheep at the current 
         * x position at a height above the bottom such that it moves along the bottom
         * 
         * @param  g    The Graphics we plan to use
         */
        private void drawSheep(Graphics g)
        {
        	if(animation==true)
            {
            ImageObserver paintingChild = null;
			int y = getHeight() - sheep.get(current).getHeight(paintingChild);
            g.drawImage(sheep.get(current), xPos, y, this);
            }
        }
        /**
         * drawHighScores - display the high scores
         * 
         * @param  g    The Graphics we plan to use
         */
        private void drawHighScores(Graphics g)
        {
        	if(scores==true)
            {
            	data.output(g);
            }
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
	public void moveRight() 
	{	
		
	}
	public void moveLeft()
	{
		
	}
}

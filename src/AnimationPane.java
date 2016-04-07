import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.Vector;

import javax.swing.JPanel;

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
		private Image sheepA;
		private Image sheepB;
        private int xPos = 0;
        private int direction = 1;
        private int current = 1;
        private boolean animation=false;
        private boolean scores=false;
        SQLiteJDBC data=new SQLiteJDBC();
		
        Vector<Image> sheep =new Vector<Image>();
        /**
         * Constructor for objects of class AnimationPane
         */
        public AnimationPane()
        {
            	String path = "src/sheepGif.gif";
            	String path2= "src/sheepGif.gif";
                
                sheepA = Toolkit.getDefaultToolkit().createImage(path);
                sheepB = Toolkit.getDefaultToolkit().createImage(path2);
                sheep.add(sheepA);
                sheep.add(sheepB);
                
                JPanel buttonPanel = new JPanel();
                
                
                AddButtons b=new AddButtons();
                               
                b.addButton(buttonPanel, "Start", new ActionListener()
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
                		 animation=true;	 
	                	 getSheep();	                		               		 
                     }
                });
                b.addButton(buttonPanel, "High Scores", new ActionListener()
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
                b.addButton(buttonPanel, "Exit", new ActionListener()
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
           	 
        }
        
        /**
         * getSheep - sets the animation to run back and forth across the screen
         */
        public void getSheep()
        {
        	for(int i=0; i<10; i++)
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
        
    	public void moveRight() 
    	{	
    		if (direction==-1)
    		{
    			direction=1;
    			current=0;
    		}
    		getSheep();
    	}
    	public void moveLeft()
    	{
    		if(direction==1)
    		{
    			direction=-1;
    			current=1;
    		}
    		getSheep();
    	}
 }
    
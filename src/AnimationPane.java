import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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
        private int yPos;
        private int direction = 1;
        private int directX=1;
        private int directY=1;
        private int current = 1;
        private boolean first=true;
        private boolean jumping=false;
        
        private int cloudX=800;
        private int cloudY=20;
        private int backX;
        private int backY;
        private int backWidth;
        private int backHeight;
        
        private boolean animation=false;
        private boolean scores=false;
        SQLiteJDBC data=new SQLiteJDBC();
        
        private BufferedImage cloudA;
        Image dimg=Toolkit.getDefaultToolkit().createImage("src/temp_2.jpg");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        Vector<Image> sheep =new Vector<Image>();
        /**
         * Constructor for objects of class AnimationPane
         */
        public AnimationPane()
        {
        		//temporarily establishes an image icon to find the height and width of image
        		ImageIcon temp=new ImageIcon(dimg);
				backWidth=temp.getIconWidth();
				backHeight=temp.getIconHeight();
				
				String cloudPath="src/Cloud1.png";
				File cloudFile=new File(cloudPath);
				try
				{
				cloudA=ImageIO.read(cloudFile);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
				backX=(int)(screenSize.getWidth())-backWidth;
				backY=(int)(screenSize.getHeight())-backHeight;
        	
            	String path = "src/sheepGif.gif";
            	String path2= "src/sheepGifR.gif";
                
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
           	 
                //Arrow Key reading
                InputMap im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
                ActionMap am = this.getActionMap();

                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");

                am.put("RightArrow", new ArrowAction("RightArrow"));
                am.put("LeftArrow", new ArrowAction("LeftArrow"));
                am.put("UpArrow", new ArrowAction("UpArrow"));
                
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
            g.drawImage(dimg, backX, backY, this);
            g.setColor(Color.white);
            drawSheep(g);
            drawHighScores(g);
            g.drawImage(cloudA,cloudX,cloudY,this);
        }
        private void changeBack()
        {
        	if(backX==(int)(screenSize.getWidth())-backWidth)
        		directX=1;
        	else if(backX==0)
        		directX=-1;
        	if(backY==(int)(screenSize.getHeight())-backHeight)
        		directY=1;
        	else if(backY==0)
        		directY=-1;
        	backX+=directX;
        	backY+=directY;
        	cloudX+=directX;
        	cloudY+=directY;
        }
        private int getLowY()
        {
        	ImageObserver paintingChild = null;
    		int y=getHeight() - sheep.get(current).getHeight(paintingChild);
    		first=false;
    		return y;
        }
        private void sheepJump()
        {
        		yPos-=10;
        		repaint();
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
        		if(first)
        		{
        			yPos=getLowY();
        		}
        		if(yPos<=getLowY()&&jumping==false)
        			yPos+=10;
        		g.drawImage(sheep.get(current), xPos, yPos, this);
            }
        	jumping=false;
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
    			current=1;
    		}
    		getSheep();
    	}
    	public void moveLeft()
    	{
    		if(direction==1)
    		{
    			direction=-1;
    			current=0;
    		}
    		getSheep();
    	}
    	public void moveUp()
    	{
    		changeBack();
    		sheepJump();
    		repaint();
    	}
    	public class ArrowAction extends AbstractAction 
    	{

            private String cmd;

            public ArrowAction(String cmd) 
            {
                this.cmd = cmd;
            }

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (cmd.equalsIgnoreCase("LeftArrow")) 
                {
                    moveLeft();
                } 
                else if (cmd.equalsIgnoreCase("RightArrow")) 
                {
                    moveRight();
                } 
                else if (cmd.equalsIgnoreCase("UpArrow")) 
                {
                	jumping=true;
                    moveUp();
                } 
            }
        }
 }
    
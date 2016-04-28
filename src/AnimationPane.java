import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Vector;

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
        private int xPos = 0;
        private int yPos;
        private int direction = 1;
        private int directX=1;
        private int directY=1;
        private int current = 1;
        private boolean first=true;
        private boolean jumping=false;
        private boolean grassIn=true;
        private int grassNum=182;
        
        private Vector<Integer> cloudX= new Vector<Integer>();
        private Vector<Integer> cloudY= new Vector<Integer>();
        private int backX;
        private int backY;
        private int backWidth;
        private int backHeight;
        
        private boolean animation=false;
        private boolean scores=false;
        SQLiteJDBC data=new SQLiteJDBC();
        
        Image dimg=Toolkit.getDefaultToolkit().createImage("src/sky.png");
        Image grass=Toolkit.getDefaultToolkit().createImage("src/grass.png");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        Vector<Image> sheep =new Vector<Image>();
        Vector<Image> cloud =new Vector<Image>();
        Vector<Integer> cloudWidth =new Vector<Integer>();
        Vector<Integer> cloudHeight =new Vector<Integer>();
        /**
         * Constructor for objects of class AnimationPane
         */
        public AnimationPane()
        {
        		//temporarily establishes an image icon to find the height and width of image
        		ImageIcon temp=new ImageIcon(dimg);
				backWidth=temp.getIconWidth();
				backHeight=temp.getIconHeight();
				
				Cloud clouds=new Cloud(backWidth, backHeight);
				cloud=clouds.getClouds();
				cloudWidth=clouds.getCloudWidths();
				cloudHeight=clouds.getCloudHeights();
				cloudX=clouds.getCloudXPos();
				cloudY=clouds.getCloudYPos();
				
				startBack();        	
                sheep=new Sheep().getSheep();
                
                JPanel buttonPanel = new JPanel();
                
                buttonPanel.setOpaque(false);
                
                AddButtons b=new AddButtons();
                               
                b.addButton(buttonPanel, "src/StartButton.jpg", new ActionListener()
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
                		if(scores)
                		{
                			restart();
                		}
                		 animation=true;
	                	 getSheep();	                		               		 
                     }
                });
                b.addButton(buttonPanel, "src/HighScoresButton.png", new ActionListener()
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
                		startBack();
                		scores=true;
                		first=true;
                		animation=false;
                		addHighScores();
                	}
                	
                });
                b.addButton(buttonPanel, "src/PauseButton.jpg", new ActionListener()
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
                    	if(animation)
                    		animation=false;
                    	else if(first==false)
                    		animation=true;
                    	else
                    	{
                    		restart();
                		    animation=true;
                    	    getSheep();	
	                	}
                    }
                });
                             
                
                add(buttonPanel, BorderLayout.SOUTH);
           	 
                //Arrow Key reading
                InputMap im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
                ActionMap am = this.getActionMap();

                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
                im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0) ,"Space");

                am.put("RightArrow", new ArrowAction("RightArrow"));
                am.put("LeftArrow", new ArrowAction("LeftArrow"));
                am.put("UpArrow", new ArrowAction("UpArrow"));
                am.put("Space", new ArrowAction("Space"));

        }
        
        public void restart()
        {
        	startBack();
    		scores=false;
    		xPos=0;
    		grassIn=true;
			grassNum=182;
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
        	for(int i=0; i<cloudX.size(); i++)
        	{
        		cloudX.set(i, cloudX.get(i)+directX);
        		cloudY.set(i, cloudY.get(i)+directY);
        	}
        }
        private int getLowY()
        {
        	ImageObserver paintingChild = null;
    		int y=getHeight() - sheep.get(current).getHeight(paintingChild);
    		first=false;
    		return y;
        }
        private void startBack()
        {
			backX=(int)(screenSize.getWidth())-backWidth;
			backY=(int)(screenSize.getHeight())-backHeight;
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
        		if(grassIn==true)
                	g.drawImage(grass,0, ((int)screenSize.getHeight())-grassNum,this);
                if(grassNum==0)
                	grassIn=false;
                for(int i=0; i<cloudX.size(); i++)
                	g.drawImage(cloud.get(0),cloudX.get(i),cloudY.get(i),this);
        		if(first)
        		{
        			yPos=getLowY();
        		}
        		if(yPos<=getLowY()&&jumping==false&&!checkForPlatform())
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
        private boolean checkForPlatform()
        {
        	boolean platform=false;
        	for(int i=0; i<cloudX.size()&&platform==false; i++)
        	{
        		if(xPos>=cloudX.get(i)-cloudWidth.get(0)/2&&xPos<=cloudX.get(i)+cloudWidth.get(0)-50&&yPos>=cloudY.get(i)-cloudHeight.get(0)&&yPos<=cloudY.get(i))
        			platform=true;
        	}
        	return platform;
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
    		if(animation==true)
    		{
    		changeBack();
    		sheepJump();
    		repaint();
    		}
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
                	if(animation)
                		moveLeft();
                } 
                else if (cmd.equalsIgnoreCase("RightArrow")) 
                {
                	if(animation)
                		moveRight();
                } 
                else if (cmd.equalsIgnoreCase("UpArrow")||cmd.equalsIgnoreCase("Space")) 
                {
                	if(animation)
                	{
                		jumping=true;
                		moveUp();
                		grassNum--;
                	}
                } 
            }
        }
 }
    
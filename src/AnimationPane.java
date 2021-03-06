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
import java.util.Random;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JLabel;
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
        private boolean levelingUp=false;
        private int grassNum;
        
    	Random rand=new Random();
        
        private int prob=700;
        
        private int fallDist=0;
        
        private int time=0;
        
        private Vector<Integer> cloudX= new Vector<Integer>();
        private Vector<Integer> cloudY= new Vector<Integer>();
        private int backX;
        private int backY;
        private int backWidth;
        private int backHeight;
        private int obstacleH;
        private int obstacleW;
        
        private int score=0;
        private int level=1;
        private boolean repeat=false;
        
        private boolean animation=false;
        private boolean scores=false;
        SQLiteJDBC data=new SQLiteJDBC();
        
        Image dimg=Toolkit.getDefaultToolkit().createImage("src/sky.png");
        Image grass=Toolkit.getDefaultToolkit().createImage("src/grass.png");
        Image levelChange=Toolkit.getDefaultToolkit().createImage("src/himym.gif");
        Image obstacleImage=Toolkit.getDefaultToolkit().createImage("src/clock.gif");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        Vector<Image> sheep =new Vector<Image>();
        Vector<Image> cloud =new Vector<Image>();
        Vector<Integer> obstacleX=new Vector<Integer>();
        Vector<Integer> obstacleY=new Vector<Integer>();
        Vector<Integer> cloudWidth =new Vector<Integer>();
        Vector<Integer> cloudHeight =new Vector<Integer>();
        
        JLabel scoreKeeper;
        /**
         * Constructor for objects of class AnimationPane
         */
        public AnimationPane()
        {
        		establishImageProperties();
				getClouds();
				
				startBack();        	
                sheep=new Sheep().getSheep();
                                
                JPanel buttonPanel = createButtonPanel();               
                add(buttonPanel, BorderLayout.SOUTH);
                
                makeScoreKeeper();
                add(scoreKeeper);
           	 
                startKeys();
        }
        private void makeScoreKeeper()
        {
            scoreKeeper = new JLabel("Score: " + score);
            scoreKeeper.setForeground(new Color(0x556B2F));
            scoreKeeper.setOpaque(true);
            scoreKeeper.setBackground(new Color(255, 255, 224, 100));
        }
        private JPanel createButtonPanel()
        {
        	JPanel buttonPanel = new JPanel();
	        buttonPanel.setOpaque(false);       
	        AddButtons b=new AddButtons();
	                       
	        b.addButton(buttonPanel, "src/StartButton.png", new ActionListener()
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
	        		if(scores||first)
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
	        		scores=true;
	        		first=true;
	        		animation=false;
	        		repaint();
	        	}
	        	
	        });
	        b.addButton(buttonPanel, "src/PauseButton.png", new ActionListener()
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
	        return buttonPanel;
        }
        
        public void establishImageProperties()
        {
        	//temporarily establishes an image icon to find the height and width of image
    		ImageIcon temp=new ImageIcon(dimg);
			backWidth=temp.getIconWidth();
			backHeight=temp.getIconHeight();
			ImageIcon temp2=new ImageIcon(obstacleImage);
			obstacleW=temp2.getIconWidth();
			obstacleH=temp2.getIconHeight();
        }
        private void startGrass()
        {
			ImageIcon temp2=new ImageIcon(grass);
			grassNum=temp2.getIconHeight();
        }
        public void restart()
        {
        	getClouds();
        	startBack();
    		scores=false;
    		xPos=0;
    		grassIn=true;
			startGrass();
			fallDist=0;
			score=0;
			scoreKeeper.setText("Score: "+score);
			level=1;
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
            levelUp(g);
            makeObstacle(g);
        }
        private void changeBack()
        {
        	if(backY==(int)(screenSize.getHeight())-backHeight)
        		directY=2;
        	else if(backY>=0)
        		levelingUp=true;
        	backY+=directY;
        	for(int i=0; i<cloudX.size(); i++)
        	{
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
			startGrass();
        }
        private void sheepJump()
        {
        	if(yPos>=20)
        		yPos-=20;
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
            	checkLoss();
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
        		{
        			yPos+=20;
        			fallDist+=1;
        		}
        		g.drawImage(sheep.get(current), xPos, yPos, this);
            }
        	jumping=false;
        }
        private void levelUp(Graphics g)
        {
        	if(levelingUp&&time<20)
        	{
        		ImageIcon temp=new ImageIcon(levelChange);
        		int width=temp.getIconWidth();
        		animation=false;
        		g.drawImage(levelChange, (int)(screenSize.getWidth()/2)-((int)width/2), 200, this);
        		time++;
        	}
        	else if(levelingUp&&!animation)
        	{
        		time=0;
        		score=score+100;
        		level++;
        		scoreKeeper.setText("Score: "+score);
        		levelingUp=false;
        		backY=(int)(screenSize.getHeight())-backHeight;
        		getClouds();
        		grassIn=true;
        		startGrass();
        		yPos=getLowY();
        		animation=true;
        	}
        }
        private void checkLoss()
        {
        	if(fallDist>=15||hitObstacle())
        	{
        		animation=false;
        		first=true;
        		scores=true;
        		HighScores high=new HighScores(score, level);
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
        private void getClouds()
        {
        	Cloud clouds=new Cloud(backWidth, backHeight);
			cloud=clouds.getClouds();
			cloudWidth=clouds.getCloudWidths();
			cloudHeight=clouds.getCloudHeights();
			cloudX=clouds.getCloudXPos();
			cloudY=clouds.getCloudYPos();
        }
        private void makeObstacle(Graphics g) 
        {
        	if(checkForObstacle())
        	{
        		prob=700;
        		obstacleX.add(0);
        		obstacleY.add(rand.nextInt((int)screenSize.getHeight()));
        	}
        	for(int i=0;i<obstacleX.size();i++)
        	{
        		obstacleX.set(i, obstacleX.get(i)+(5*level));
        		g.drawImage(obstacleImage, obstacleX.get(i), obstacleY.get(i), this);
        	}
        		
        }
        private boolean checkForObstacle()
        {
        	int k=rand.nextInt(prob);
        	prob--;
        	return k==1;     	
        }
        private boolean checkForPlatform()
        {
        	boolean platform=false;
        	for(int i=0; i<cloudX.size()&&platform==false; i++)
        	{
        		if(xPos>=cloudX.get(i)-cloudWidth.get(0)/2&&xPos<=cloudX.get(i)+cloudWidth.get(0)-50&&yPos>=cloudY.get(i)-cloudHeight.get(0)&&yPos<=cloudY.get(i))
        		{
        			platform=true;
        			fallDist=0;
        		}
        	}
        	if(!platform)
        		repeat=false;
        	if (platform&&!repeat)
        	{
        		score++;
        		scoreKeeper.setText("Score: "+score);
        		repeat=true;
        	}
        	return platform;
        }
        private boolean hitObstacle()
        {
        	ImageObserver paintingChild = null;
            boolean obstacle=false;
        	for(int i=0; i<obstacleX.size()&&obstacle==false; i++)
        	{
        		if(xPos>=obstacleX.get(i)-obstacleW-200&&xPos<=obstacleX.get(i)+200&&yPos>=obstacleY.get(i)-obstacleH-200&&yPos<=obstacleY.get(i)+200)
        		{
        			obstacle=true;
        		}
        	}
        	System.out.println(obstacle);
        	return obstacle;
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
    	
    	 public void startKeys()
         {
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
                		for(int i=0;i<=4&&fallDist>=0; i++)
                		   fallDist--;
                	}
                } 
            }
        }
 }
    
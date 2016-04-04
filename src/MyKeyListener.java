import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class MyKeyListener implements KeyListener
{
	AnimationPane a;
	
	public MyKeyListener()
	{
		a=new AnimationPane();
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		 if(event.getKeyCode() == KeyEvent.VK_RIGHT){
		        System.out.println("Pressed RIGHT");
		        a.moveRight();
		    }
		    if(event.getKeyCode() == KeyEvent.VK_LEFT){
		        System.out.println("Pressed LEFT");
		        a.moveLeft(); 
		    }
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		// TODO 
		
	}

	@Override
	public void keyTyped(KeyEvent event) 
	{
		// TODO 
		
	}
}
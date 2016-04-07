import java.awt.BorderLayout;

import javax.swing.JFrame;


public class StartRun implements Runnable
{

	@Override
	public void run() 
	{
		JFrame frame = new JFrame("Majestic Games");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        AnimationPane a=new AnimationPane();
        frame.add(a);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}

}

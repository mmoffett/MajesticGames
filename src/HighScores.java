import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class HighScores extends JPanel
{
	final int score1;
	final int level1;
	final JFrame f;
	JButton button;
	JTextArea a;
	public HighScores(int score, int level)
	{
		f = new JFrame("Score Entry");
        f.setLayout(new BorderLayout());
		f.pack();
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setSize(400,100);	
        
		score1=score;
		level1=level;
		makePanel();
	}
	private void makePanel()
	{
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);       
        
        JLabel NameEntry = new JLabel("Enter Your Name: ");
        NameEntry.setForeground(new Color(0x556B2F));
        NameEntry.setOpaque(true);
        NameEntry.setBackground(new Color(255, 255, 224, 100));
        
        a = new JTextArea("     ");
               
        button = new JButton("Enter");
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(makeListener());
        button.setFocusable(true);
        
        buttonPanel.add(NameEntry);
        buttonPanel.add(a);
        buttonPanel.add(button);
        

        f.add(buttonPanel, BorderLayout.SOUTH);      
	}
        
    private ActionListener makeListener()
    {
	    ActionListener listener=new ActionListener()
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
	      		final String name = a.getText();
	       		SQLiteJDBC data=new SQLiteJDBC();
	       		data.addHighScore(name, score1,level1); 
	       		f.dispose();
	        }
	   };
	   return listener;
        }
}

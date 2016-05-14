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
	int score1;
	int level1;
	public HighScores(final int score, final int level)
	{
		final JFrame frame1= new JFrame("Score Entry");
        frame1.setLayout(new BorderLayout());
		frame1.pack();
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
        frame1.setSize(400,100);	
		
		score1=score;
		level1=level;
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);       
        
        JLabel NameEntry = new JLabel("Enter Your Name: ");
        NameEntry.setForeground(new Color(0x556B2F));
        NameEntry.setOpaque(true);
        NameEntry.setBackground(new Color(255, 255, 224, 100));
        
        final JTextArea a = new JTextArea("     ");
        
        
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
        		data.addHighScore(name, score,level); 
        		frame1.dispose();
             }
        };
        
        JButton button = new JButton("Enter");
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(listener);
        button.setFocusable(true);
        
        buttonPanel.add(NameEntry);
        buttonPanel.add(a);
        buttonPanel.add(button);
        
        frame1.add(buttonPanel, BorderLayout.SOUTH);
	}

}

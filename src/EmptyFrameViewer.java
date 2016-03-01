import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class EmptyFrameViewer 
{
    public static void main(String[] args) throws IOException 
    {
        String path = "C:\\Users\\MarissaRoseM\\Documents\\GitHub\\MajesticGames\\src\\sheep.png";
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        JLabel label = new JLabel(new ImageIcon(image));
        
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(label);
        frame.pack();
        
        frame.getContentPane().setBackground(Color.black);
        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //frame.setSize(900,900);
        frame.setVisible(true);
        frame.setTitle("Majestic Games");
    }
}

package cushionn;
import java.awt.Color;
import javax.swing.*;


public class Firsting extends JPanel
{
    private static Firsting world = null;
   
    public static void main(String[] args) 
    {        
    	Firsting applet = Firsting.getInstance();
        JFrame frame = new JFrame();
        frame.add(applet);
        frame.setTitle("Jupi: Carom Billiards");
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//main


  
    public Firsting()    
    {
    	// prevent more than one world from being constructed:
    	if (world != null)
    		throw new RuntimeException();
        int pixelsPerInch = BilliardsConstant.PIXELS_PER_INCH;
        final CushionTable table = new CushionTable();
       
        table.setPixelsPerInch(pixelsPerInch);
        this.add(table);
        
        //Game Thread
        Thread gameThread = new Thread(new Runnable()
        {
        	public void run()
        	{
        		try
        		{
        			while(true)
        			{
        				table.update();        				
        				Thread.sleep(BilliardsConstant.TIME_SLICE);
        			}
        		}
        		catch(InterruptedException ex )
        		{        			
        		}
        	}
        });
        gameThread.start();

    }//World()


    public static synchronized Firsting getInstance() 
    {
        if (world == null)
            world = new Firsting();
        return world;
    }
    
}//World

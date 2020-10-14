
package cushionn;

import javax.swing.*;


public class AppContainer extends JFrame {

  
    private static AppContainer mainFrame;
    private static JPanel currentPanel = new JPanel();
    
    private AppContainer() {
    	
        super("Cushion Billiards");
        mainFrame = this;
         setResizable(false);
        currentPanel = new JPanel();
        add(currentPanel);
        pack();
        setVisible(true);
       mainFrame.setSize(1500,1500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private AppContainer(JPanel initial) {
        this();
        changePanel(initial);
          mainFrame.setSize(1500,1500);
      
    }

    public static AppContainer getInstance() {
        if (mainFrame == null) {
            mainFrame = new AppContainer();
        }
        return mainFrame;
    }

    public static void changePanel(JPanel newPanel) {
        if (mainFrame == null) {
            mainFrame = new AppContainer();
        }
        mainFrame.remove(currentPanel);
        currentPanel = newPanel;
        mainFrame.add(currentPanel);
        mainFrame.pack();
          mainFrame.setSize(1500,1500);
    }

    public static void main(String[] args) {
        new AppContainer(new Menu());
      
    }
}

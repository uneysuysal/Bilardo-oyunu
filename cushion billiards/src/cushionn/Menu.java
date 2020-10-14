
package cushionn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.*;


public class Menu extends JPanel {


    private JLabel jLabel; // cushon label

    private JButton jButton1;//new game

    private JButton jButton3;    // "Help" button

    private JButton jButton4;     // "Exit" button

    public Menu() {
        initComponents();
       
    }

    private void initComponents() {
        // Title
        jLabel = new JLabel("Cushion");
        jLabel.setFont(new Font("", 3, 36));
        jLabel.setForeground(new Color(255, 204, 51));

        jButton1 = new JButton("New Game");
      
        jButton1.addActionListener(new ActionListener() {   // Go to Menu
            @Override
            public void actionPerformed(ActionEvent e) {
                AppContainer.changePanel(new Firsting());
            	
            	
            }
        });

      

        jButton3 = new JButton("Help");
    
        jButton3.addActionListener(new ActionListener() {     // Go to Help
            @Override
            public void actionPerformed(ActionEvent e) {
              AppContainer.changePanel(new Help());
            }
        });

        jButton4 = new JButton("Exit");
        // Exit program
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Layout
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(110, Short.MAX_VALUE)
                                .addComponent(jLabel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100))
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(jButton1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                        .addComponent(jButton3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                        .addComponent(jButton4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGap(40, 40, 40)
                                .addComponent(jButton1)
                                .addGap(20, 20, 20)
                                .addComponent(jButton3)
                                .addGap(20, 20, 20)
                                .addComponent(jButton4)
                                .addContainerGap(60, Short.MAX_VALUE))
        );
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cushionn;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.GroupLayout.*;

/**
 *
 * @author CREAT10N
 */
public class Help extends JPanel {

	

    private JButton jButton;

    private JTextArea jTextArea;

    public Help() {
        initComponents();

       
    }

    private void initComponents() {
        jButton = new JButton("Back");
        // Return to Menu 
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppContainer.changePanel(new Menu());
            }
        });

        jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setRows(20);

        // Contents of the game rules
        String str = "\t      CUSHÝON BÝLLÝARDS    \n";
      
       
        str += " Player  the cue ball to hit a minimum of 3 cushions and collide with two  balls on table in one strike.";
        str += " 2 players swap turn after each strike, scores 10 points first to win the match.";
        jTextArea.setText(str);

        // Layout
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(jButton, Alignment.TRAILING)
                                        .addComponent(jTextArea, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTextArea, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton)
                                .addContainerGap())
        );
    }

  
}

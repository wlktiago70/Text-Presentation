/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpresentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author User
 */
public class TextPresentation {

    /**
     * @param args the command line arguments
     */
    private static JFrame mainWindow = new JFrame("Presentation");
    
    public static void main(String[] args) {
        prepareWindow();       
    }
    private static void prepareWindow(){
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = d.width / 2, centerY = d.height / 2, x = 600, y = 400;
        mainWindow.setBounds(centerX - x / 2, centerY - y / 2, x, y);
        mainWindow.setLayout(new BorderLayout());
        mainWindow.add(new MainPainel(),BorderLayout.CENTER);
        mainWindow.setVisible(true);
        mainWindow.setResizable(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

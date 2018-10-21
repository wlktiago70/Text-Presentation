/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpresentation;

/**
 *
 * @author wkuan
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;
 
/* ColorChooser.java requires no other files. */
public class ColorChooser extends JPanel{
    private MainPainel parentMain;
    private JTabbedPane tabbedPane;
    private JButton confirmButton;
    private static JFrame frame = null;
    protected JColorChooser tcc;
    protected JColorChooser bcc;
    protected JLabel banner;
 
    public ColorChooser(MainPainel parent) {
        super(new BorderLayout());

        parentMain = parent;

        //Set up the banner at the top of the window
        banner = new JLabel("Color Test 顏色測試",
                            JLabel.CENTER);
        banner.setForeground(Color.yellow);
        banner.setBackground(Color.blue);
        banner.setOpaque(true);
        banner.setFont(new Font("SansSerif", Font.BOLD, 24));
        banner.setPreferredSize(new Dimension(100, 65));
 
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.add(banner, BorderLayout.CENTER);
        bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));

        tabbedPane = new JTabbedPane();

        //Set up color chooser for setting text color
        tcc = new JColorChooser(banner.getForeground());
        tcc.getSelectionModel().addChangeListener(new ForegroundListener());
        tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));
        tabbedPane.addTab("Text Color", tcc);
        
        //Set up color chooser for setting background color
        bcc = new JColorChooser(banner.getBackground());
        bcc.getSelectionModel().addChangeListener(new BackgroundListener());
        bcc.setBorder(BorderFactory.createTitledBorder("Choose Background Color"));
        tabbedPane.addTab("Background Color", bcc);
        
        confirmButton = new JButton("OK");
        confirmButton.addActionListener(new ConfirmButtonListener());

        add(bannerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);
    }
    
    private class ConfirmButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            parentMain.getEditorPane().setForeground(banner.getForeground());
            parentMain.getEditorPane().setBackground(banner.getBackground());
            parentMain.getMargin().setBackground(banner.getBackground());
        }
        
    }
    
    private class ForegroundListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            Color newColor = tcc.getColor();
            banner.setForeground(newColor);
        }
        
    }

    private class BackgroundListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            Color newColor = bcc.getColor();
            banner.setBackground(newColor);
        }
        
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI(MainPainel parent) {
        if(frame == null){
            //Create and set up the window.
            frame = new JFrame("Color Chooser");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            //Create and set up the content pane.
            JComponent newContentPane = new ColorChooser(parent);
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);
            frame.pack();
            frame.setVisible(true);
        }     
        else{
            //Display the window.        
            frame.setVisible(true);
        }
    }
}

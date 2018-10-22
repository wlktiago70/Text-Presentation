/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpresentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author User
 */
public class MainPainel extends JPanel{
    private int currPos = -1;
    private int showPos = -1;
    private List<String> pages = new ArrayList<>();
    private JTextArea content = new JTextArea();
    private JScrollPane contentScrollPane = new JScrollPane(content);
    private JPanel sidePanel = new JPanel(new GridLayout(5,1));
    private JButton showPrev = new JButton("Show previous");
    private JButton showNext = new JButton("Show next");
    private JButton getPrev = new JButton("<<");
    private JButton getNext = new JButton(">>");
    private JButton removePg = new JButton("Remove");
    private JButton sendPg = new JButton("Send");
    private JButton addPg = new JButton("Add");
    private JButton colorChooser = new JButton("Color chooser");
    private JFrame presentation = new JFrame("Presentation");
    private JEditorPane editorPane = new JEditorPane();
    private JSlider fontSize = new JSlider(JSlider.VERTICAL,10,200,80);
    private JPanel contentEditorPane = new JPanel(new BorderLayout());
    private Label status = new Label();
    private Label margin = new Label("");

    public MainPainel(){
        super(new BorderLayout());
        setKeyStrokesAndActions();
        sidePanel.add(removePg);
        sidePanel.add(getNext);
        sidePanel.add(getPrev);
        sidePanel.add(addPg);
        sidePanel.add(sendPg);
        fontSize.setMajorTickSpacing(10);
        fontSize.setMinorTickSpacing(5);
        fontSize.setPaintTicks(true);
        fontSize.setPaintLabels(true);
        fontSize.addChangeListener(new fontChangeAction());
        content.setBorder(new EmptyBorder(10,10,10,10));
        content.setWrapStyleWord(true);
        contentEditorPane.add(colorChooser,BorderLayout.NORTH);
        contentEditorPane.add(status,BorderLayout.SOUTH);
        contentEditorPane.add(new Label(""),BorderLayout.EAST);
        contentEditorPane.add(new Label(""),BorderLayout.WEST);
        contentEditorPane.add(contentScrollPane,BorderLayout.CENTER);
        this.add(showPrev,BorderLayout.NORTH);
        this.add(showNext,BorderLayout.SOUTH);
        this.add(sidePanel,BorderLayout.EAST);
        this.add(fontSize,BorderLayout.WEST);
        this.add(contentEditorPane,BorderLayout.CENTER);
        colorChooser.addActionListener(new showColorChooser(this));
        showPrev.addActionListener(new showPreviousAction());
        showNext.addActionListener(new showNextAction());
        removePg.addActionListener(new removeAction());
        getNext.addActionListener(new getNextAction());
        getPrev.addActionListener(new getPreviousAction());
        addPg.addActionListener(new addAction());
        sendPg.addActionListener(new sendAction());
        updateStatus();
        preparePresentationWindow();
    }
    
    private void addComponentsToFrame(JFrame presentation){
        presentation.setLayout(new BorderLayout());
        presentation.add(editorPane,BorderLayout.CENTER);
        presentation.add(margin,BorderLayout.NORTH);
        presentation.add(margin,BorderLayout.SOUTH);
        presentation.add(margin,BorderLayout.EAST);
        presentation.add(margin,BorderLayout.WEST);
        presentation.addWindowStateListener(new myWindowStateListener(presentation));
        presentation.setResizable(true);
        presentation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void prepareInitialPresentationWindow(JFrame frame){
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = d.width / 2, centerY = d.height / 2, x = 900, y = 600;
        margin.setBackground(Color.BLUE);
        addComponentsToFrame(frame);
        frame.setBounds(centerX - x / 2, centerY - y / 2, x, y);
        frame.setVisible(true);
    }
    
    private void preparePresentationWindow(){
        prepareInitialPresentationWindow(presentation);
        editorPane.setEditable(false);
        editorPane.setBackground(Color.BLUE);
        editorPane.setForeground(Color.YELLOW);
        editorPane.setFont(new Font("Times New Roman",Font.PLAIN,150));
        editorPane.setContentType("text/html;charset=UTF-8");
    }   

    public Label getMargin() {
        return margin;
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }
    
    private String formatText(String str){
        String htmlText =   "<html>\n" +
                            "<style>\n" +
                            "body {\n" +
                            "    color: #"+Integer.toHexString(editorPane.getForeground().getRGB()).substring(2)+";\n" +
                            "    font-size: " + fontSize.getValue() + "px;\n" +
                            "    font-weight: normal;\n" +
                            "}\n" +
                            "</style>\n" +
                            "<body>\n" +
                            str +
                            "</body>\n" +
                            "</html>";
        return htmlText;
    }
    
    private void updateStatus(){
        status.setText("          Showing: "+(showPos+1)+" / "+pages.size()+"          Editing: "+(currPos+1)+" / "+pages.size()+"          Font size: "+fontSize.getValue()+"px");
    }
    
    private void showPreviousOperation(){
        System.out.println("showPreviousAction");
        if(!pages.isEmpty() && showPos-1>=0)
        {
            editorPane.setText(formatText(pages.get(--showPos)));
        }
        updateStatus();
    }
    
    private void showNextOperation(){
        System.out.println("showNextAction");
        if(!pages.isEmpty() && showPos+1<pages.size())
        {
            editorPane.setText(formatText(pages.get(++showPos)));
        }
        updateStatus();
    }
    
    private void getPreviousOperation(){
        System.out.println("getPreviousAction");
        if(!pages.isEmpty() && currPos-1>=0)
        {
            content.setText(pages.get(--currPos));
        }
        updateStatus();
    }
    
    private void getNextOperation(){
        System.out.println("getNextAction");
        if(!pages.isEmpty() && currPos+1<pages.size())
        {
            content.setText(pages.get(++currPos));
        }
        updateStatus();
    }
    
    private void removeOperation(){
        System.out.println("removeAction");
        if(!pages.isEmpty())
            pages.remove(currPos);
        content.setText("");
        currPos = pages.size()-1;
        updateStatus();
    }
    
    private void addOperation(){
        System.out.println("addAction");
        content.setText("");
        currPos = pages.size()-1;
        updateStatus();
    }
    
    private void sendOperation(){
        System.out.println("sendAction");            
        editorPane.setText(formatText(content.getText()));
        if(currPos == pages.size()-1){
            pages.add(content.getText());
            currPos++;
        }
        else{
            pages.set(currPos,content.getText());
        }
        showPos = currPos;
        updateStatus();
    }
  
    private class myWindowStateListener implements WindowStateListener{
        private JFrame source;
        public myWindowStateListener(JFrame source){
            this.source = source;
        }
        @Override
        public void windowStateChanged(WindowEvent e) {
            // minimized
            if ((e.getNewState() & JFrame.ICONIFIED) == JFrame.ICONIFIED){
                System.out.println("minimized");
            }
            // maximized
            else if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH){
                System.out.println("maximized");
                source.dispose();
                source = new JFrame(source.getTitle());
                addComponentsToFrame(source);
                source.setUndecorated(true);
                source.setExtendedState(JFrame.MAXIMIZED_BOTH);
                source.setVisible(true);
            }
        }
        
    }

    private class showColorChooser extends AbstractAction implements ActionListener{
        private MainPainel mainParent;
        public showColorChooser(MainPainel parent){
            mainParent = parent;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            ColorChooser.createAndShowGUI(mainParent);
        }
    
    }
    private class showPreviousAction extends AbstractAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            showPreviousOperation();
        }
    
    }
    private class showNextAction extends AbstractAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            showNextOperation();
        }
    
    }
    private class getPreviousAction extends AbstractAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            getPreviousOperation();
        }
    
    }
    private class getNextAction extends AbstractAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            getNextOperation();
        }
    
    }
    private class removeAction extends AbstractAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            removeOperation();
        }
    
    }
    private class addAction extends AbstractAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            addOperation();
        }
    
    }
    private class sendAction extends AbstractAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            sendOperation();
        }
    
    }
    private class fontChangeAction implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent ce) {
            updateStatus();
        }
    
    }
    private class typingAction extends AbstractAction{      
        
        public static final String LINEBREAK = "<br/>\n";
        public static final String BOLD = "b>";
        public static final String ITALIC = "i>";
        public static final String INSERTED = "u>";
        public static final String DELETED = "s>";
        public static final String SUBSCRIPTED  = "sub>";
        public static final String SUPERSCRIPTED = "sup>";
        private int flag = 0;
        private String tag;
        public typingAction(String str){
            if(str.equalsIgnoreCase(LINEBREAK))flag = 0;
            else if(str.equalsIgnoreCase(BOLD))flag = 1;
            else if(str.equalsIgnoreCase(ITALIC))flag = 2;
            else if(str.equalsIgnoreCase(INSERTED))flag = 3;
            else if(str.equalsIgnoreCase(DELETED))flag = 4;
            else if(str.equalsIgnoreCase(SUBSCRIPTED))flag = 5;
            else if(str.equalsIgnoreCase(SUPERSCRIPTED))flag = 6;
            tag = str.toLowerCase();
        }
        
        private void process(){
            int caretPos = content.getCaretPosition();
            switch(flag){
                case 0: 
                    content.setText(content.getText().substring(0, caretPos)+LINEBREAK+content.getText().substring(caretPos));
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    content.setText(content.getText().substring(0, caretPos)+"<"+tag+"</"+tag+content.getText().substring(caretPos));
                    content.setCaretPosition(caretPos+tag.length()+1);
                    break;
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            process();
        }
        
    }
    private class escAction extends AbstractAction implements ActionListener{
        private JFrame source;
        public escAction(JFrame source){
            this.source = source;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            source.dispose();
            source = new JFrame(source.getTitle());
            prepareInitialPresentationWindow(source);
        }
    
    }
    private void setKeyStrokesAndActions(){
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,InputEvent.CTRL_DOWN_MASK), "sendAction");
        content.getActionMap().put("sendAction", new sendAction());
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "linebreak");
        content.getActionMap().put("linebreak", new typingAction(typingAction.LINEBREAK));
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,InputEvent.CTRL_DOWN_MASK), "showPreviousAction");
        content.getActionMap().put("showPreviousAction", new showPreviousAction());
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,InputEvent.CTRL_DOWN_MASK), "showNextAction");
        content.getActionMap().put("showNextAction", new showNextAction());
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,InputEvent.CTRL_DOWN_MASK), "getPreviousAction");
        content.getActionMap().put("getPreviousAction", new getPreviousAction());
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,InputEvent.CTRL_DOWN_MASK), "getNextAction");
        content.getActionMap().put("getNextAction", new getNextAction());
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,InputEvent.CTRL_DOWN_MASK), "removeAction");
        content.getActionMap().put("removeAction", new removeAction());
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_DOWN_MASK), "addAction");
        content.getActionMap().put("addAction", new addAction());
        
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1,InputEvent.CTRL_DOWN_MASK), "bold");
        content.getActionMap().put("bold", new typingAction(typingAction.BOLD));
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2,InputEvent.CTRL_DOWN_MASK), "italic");
        content.getActionMap().put("italic", new typingAction(typingAction.ITALIC));
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3,InputEvent.CTRL_DOWN_MASK), "inserted");
        content.getActionMap().put("inserted", new typingAction(typingAction.INSERTED));
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_4,InputEvent.CTRL_DOWN_MASK), "deleted");
        content.getActionMap().put("deleted", new typingAction(typingAction.DELETED));
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_5,InputEvent.CTRL_DOWN_MASK), "subscripted");
        content.getActionMap().put("subscripted", new typingAction(typingAction.SUBSCRIPTED));
        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_6,InputEvent.CTRL_DOWN_MASK), "superscripted");
        content.getActionMap().put("superscripted", new typingAction(typingAction.SUPERSCRIPTED));

        content.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "restorePresentation");
        content.getActionMap().put("restorePresentation", new escAction(presentation));
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "restorePresentation");
        this.getActionMap().put("restorePresentation", new escAction(presentation));
    }
}

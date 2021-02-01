package pong;

/**
 *
 * @author Nate Heppard
 */

import javax.swing.JFrame;

public class Driver{
    
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(()->{
            Engine engine=new Engine();
            init(engine);
        });
    }
    
    public static void init(Engine engine){
        JFrame frame=new JFrame("PONG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        frame.getContentPane().add(engine);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

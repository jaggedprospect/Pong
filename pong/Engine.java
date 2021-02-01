package pong;

/**
 *
 * @author Nate Heppard
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Engine extends JPanel{
    
    private final int WIDTH=800,HEIGHT=600;
    private final int DELAY=10;
    private final int JUMP=5;
    
    private Timer mainTimer;
    private Input input;
    private Paddle player1;
    private Paddle player2;
    private Ball ball;
    
    private int cx,cy;
    private int moveX,moveY;
    private int score1,score2;
    private boolean isFirst;
    
    public Engine(){
        mainTimer=new Timer(DELAY,new TimerTask());
        input=new Input();
        
        cx=WIDTH/2;
        cy=HEIGHT/2;
        moveX=5;
        moveY=3;
        isFirst=true;
        
        player1=new Paddle(51,cy-25,10,50);
        player2=new Paddle(WIDTH-60,cy-25,10,50);
        ball=new Ball(cx,cy,10,10);
        
        addKeyListener(input); 
        
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        mainTimer.start();
    }
    
    public void drawBackground(Graphics g){
        g.setColor(Color.WHITE);
        
        g.drawLine(50,0,50,HEIGHT);
        g.drawLine(WIDTH-50,0,WIDTH-50,HEIGHT);
        
        Graphics2D g2d=(Graphics2D)g.create();

        Stroke dashed=new BasicStroke(3,BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,0,new float[]{9},0);
        g2d.setStroke(dashed);
        g2d.drawLine(cx,0,cx,HEIGHT);

        g2d.dispose();
        
        g.setFont(new Font("Calibri",Font.PLAIN,32));
        
        g.drawString(Integer.toString(score1),100,50);
        g.drawString(Integer.toString(score2),WIDTH-100,50);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.WHITE);
        
        drawBackground(g);
        
        player1.draw(g);
        player2.draw(g);
        ball.draw(g);
    }
    
    private class TimerTask implements ActionListener{
        
        @Override 
        public void actionPerformed(ActionEvent event){
            
            // ball movement
            int tempX=ball.getX();
            int tempY=ball.getY();
            
            ball.setX(ball.getX()+moveX);
            ball.setY(ball.getY()+moveY);

            if(ball.getX()<0-ball.getSize()){
                System.out.println("Player 2 scored");
                score2++;
                resetBall();

            }else if(ball.getX()>WIDTH){
                System.out.println("Player 1 scored");
                score1++;
                resetBall();
            }

            if(score1==10){
                System.out.println("Player 1 wins!");
                exitProgram();

            }else if(score2==10){
                System.out.println("Player 2 wins!");
                exitProgram();

            }
            
            if(ball.intersects(player1)){
                System.out.println("HIT player 1");
                ball.setX(tempX+1);
                moveX*=-1;
            }else if(ball.intersects(player2)){
                System.out.println("HIT player 2");
                ball.setX(tempX-1);
                moveX*=-1;
            }
            
            if(ball.getY()<=0 || ball.getY()>=HEIGHT-ball.getSize())
                moveY*=-1;
                
            // player 1 movement
            if(input.isKey(KeyEvent.VK_W)){
                player1.setY(player1.getY()-JUMP);
                
                if(player1.getY()<=0)
                    player1.setY(0);
                
            }else if(input.isKey(KeyEvent.VK_S)){
                player1.setY(player1.getY()+JUMP);
                
                if(player1.getY()>=HEIGHT-player1.getHeight())
                    player1.setY(HEIGHT-player1.getHeight());
            }
            
            // player 2 movement
            if(input.isKey(KeyEvent.VK_UP)){
                player2.setY(player2.getY()-JUMP);
                
                if(player2.getY()<=0)
                    player2.setY(0);
                
            }else if(input.isKey(KeyEvent.VK_DOWN)){
                player2.setY(player2.getY()+JUMP);
                
                if(player2.getY()>=HEIGHT-player2.getHeight())
                    player2.setY(HEIGHT-player2.getHeight());
            }
            
            player1.updatePerimeter();
            player2.updatePerimeter();
            ball.updatePerimeter();
            input.update();
            repaint();
        }
        
        public void resetBall(){
            try{
                Thread.sleep(1000);
                ball.setX(cx);
                ball.setY(cy);
            }catch(InterruptedException e){
                System.err.println(e);
            }
        }
        
        public void exitProgram(){
            try{
                mainTimer.stop();
                Thread.sleep(1000);
                System.exit(1);
            }catch(InterruptedException e){
                System.err.println(e);
            }
        }
    }  
}

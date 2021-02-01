package pong;

/**
 *
 * @author Nate Heppard
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Entity{    
    
    private Color color;
    private ArrayList<Point> perimeter; // perimeter Point list
    private int x,y;                    // entity origin coordinates
    private int width;
    private int height;
    
    public Entity(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        
        perimeter=new ArrayList<>();
        perimeter.add(new Point(x-1,y-1));                          // Point 1, index=0
        perimeter.add(new Point(x+this.width+1,y-1));               // Point 2, index=1
        perimeter.add(new Point(x-1,y+this.height+1));              // Point 3, index=2
        perimeter.add(new Point(x+this.width+1,y+this.height+1));   // Point 4, index=3
    }
    
    public int getX(){
        return x;
    }
    
    public void setX(int x){
        this.x=x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int y){
        this.y=y;
    }
    
    public int getWidth(){
        return width;
    }
    
    public void setWidth(int width){
        this.width=width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void getHeight(int height){
        this.height=height;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        this.color=color;
    }
    
    public ArrayList<Point> getPerimeter(){ 
        return perimeter;
    }

    public Point getPerimeterPoint(int index){
        if(index>=0 && index<perimeter.size())
            return perimeter.get(index);
        else{
            System.out.println("POINT Index Not In Range");
            return null;
        }
    }
   
    public void updatePerimeter(){
        perimeter.set(0,new Point(this.x,this.y));
        perimeter.set(1,new Point(this.x+this.width,this.y));
        perimeter.set(2,new Point(this.x,this.y+this.height));
        perimeter.set(3,new Point(this.x+this.width,this.y+this.height));
    }
    
    public void changePerimeter(int x,int y){
        perimeter.add(new Point(x-1,y-1));                         
        perimeter.add(new Point(x+this.width+1,y-1));               
        perimeter.add(new Point(x-1,y+this.height+1));              
        perimeter.add(new Point(x+this.width+1,y+this.height+1));
    }

    public boolean intersects(Entity e){
        int px,py;
        Point p1=e.getPerimeterPoint(0); // origin
        Point p2=e.getPerimeterPoint(1); // max x point
        Point p3=e.getPerimeterPoint(2); // max y point
        
        for(Point p : perimeter){
            px=(int)p.getX();
            py=(int)p.getY();
            
            if((px>=p1.getX() && px<=p2.getX()) && (py>=p1.getY() && py<=p3.getY())){
                System.out.println("INTERSECTION:\nplayer("+px+","+py+")\n"
                        + "enemy{("+p1+"),("+p2+"),("+p3+")}"); // print for debugging
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isValidMove(Entity entity,int jump,boolean isX){
        // set phantom origin coordinates
        int tempX=getX(),tempY=getY();
        
        for(int i=1;i<=jump;i++){
            // increase phantom x or y
            if(isX)
                tempX+=i;
            else
                tempY+=i;

            changePerimeter(tempX,tempY); // the 'phantom move'

            if(intersects(entity)){
                updatePerimeter(); // update perimeter to previous points
                return false;
            }
        }
        
        return true;
    }
    
    public void draw(Graphics g){}
}

class Paddle extends Entity{

    public Paddle(int x,int y,int width,int height){
        super(x,y,width,height);
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(getX(),getY(),getWidth(),getHeight());
    }
}

class Ball extends Entity{
    
    private int size;

    public Ball(int x,int y,int width,int height){
        super(x,y,width,height);
        this.size=width;
    }
    
    public int getSize(){
        return size;
    }
    
    @Override
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(getX(),getY(),size,size);
    }
}


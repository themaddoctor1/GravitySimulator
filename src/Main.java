
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WHS-D11B0W20
 */
public class Main extends Applet implements KeyListener, MouseListener, MouseMotionListener{
    private static Graphics graphics;
    private static Main applet = new Main();
    private static JFrame frame;
    
    private static double baryX = 0, baryY = 0, totalMass = 0;
    
    private static double 
            magnitude = 0, 
            direction = 0, 
            mass = 1/(6.673 * Math.pow(10,-11)), 
            radius = 5;
    
    private int mouseX = 0, mouseY = 0;
    
    private static boolean paused = false, helping = false, drawBarycenter;
    private static String mode = "MASS";
    
    public Main(){
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    
    private static ArrayList<Entity> list = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        String title = "";
         //Creates a JFrame with a title
        frame = new JFrame(title);
        //Puts the Main object into thhe JFrame
        frame.addKeyListener(applet);
	frame.add(applet);
        //Sets the size of the applet to be 400 pixels wide  by 400 pixels high
	frame.setSize(807, 628);
        //Makes the applet visible
	frame.setVisible(true);
        //Sets the applet so that it can't be resized
        frame.setResizable(false);
        //This will make the program close when the red X in the top right is
        //clicked on
	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
        list.add(new Entity(100,0,25,0,0,Math.pow(10,17)));
        list.get(0).setVelY(140);
        list.add(new Entity(-100,0,25,0,0,Math.pow(10,17)));
        list.get(1).setVelY(-140);
        */
        
        while(true){
            printScreen();
            if(direction < Math.toRadians(0)){
                direction += Math.toRadians(360);
            }
            if(!paused) {
                for(int i = list.size() - 1; i >= 0; i--){
                    for(int j = list.size() - 1; j >= 0; j--){
                        if(i != j){
                            list.get(i).gravitate(list.get(j));
                        }
                    }
                }
                for(int i = list.size() - 1; i >= 0; i--){
                    for(int j = list.size() - 1; j >= 0; j--){
                        if(i != j){
                            list.get(i).collide(list.get(j));
                        }
                    }
                }
                
                baryX = 0;
                baryY = 0;
                totalMass = 0;
                
                for(Entity e : list){
                    e.move();
                }
                
                
                
                
                for(int i = list.size() - 1; i >= 0; i--){
                    if(list.get(i).getMass() <= 0){
                        list.remove(i);
                    } else if(Math.abs(list.get(i).getX()) > (frame.getWidth()/2.0) + 60){
                        list.remove(i);
                    } else if(Math.abs(list.get(i).getY()) > (frame.getHeight()/2.0) + 60){
                        list.remove(i);
                    }
                }
                
                for(Entity e : list){
                    baryX += e.getMass() * e.getX();
                    baryY += e.getMass() * e.getY();
                    totalMass += e.getMass();
                }
                
                if(totalMass > 0){
                    baryX /= totalMass;
                    baryY /= totalMass;
                }
            }
            
            Thread.sleep(5);
            userRequests();
        }
    }
    
    public static void userRequests(){
        
    }
    
    /**
     * This methods is used to call the update() method. To be honest, I am
     * not sure of how this is, but I know that the way it does this prevents
     * flickering images.
     * @throws InterruptedException
     */
    public static void printScreen() throws InterruptedException{
        applet.repaint();
    }
    
    public void update(Graphics g) {
        Image image = null;
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            graphics = image.getGraphics();
        }
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,  0,  this.getWidth(),  this.getHeight());
        graphics.setColor(getForeground());
        
        paint(graphics);
        g.drawImage(image, 0, 0, this);
    }
    
    
    /**
     * This method outputs the shapes and stuff onto the applet
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        int x = frame.getWidth()/2;
        int y = frame.getHeight()/2;
        
        //Draws the xy axes
        g2.drawLine(0,y,2 * x,y);
        g2.drawLine(x,0,x,2 * y);
        
        //Draws the Entities
        for(Entity e : list){
            g2.setColor(e.getColor());
            g2.fillOval((int)(x - e.getRadius() + e.getX()),(int) (y - e.getRadius() - e.getY()), (int)(2 * e.getRadius()), (int)(2 * e.getRadius()));
        }
        g2.setColor(Color.WHITE);
        
        //Draws the interface
        g2.drawOval((int)(mouseX - radius), (int)(mouseY - radius), (int)(2 * radius), (int)(2 * radius));
        g2.drawLine(mouseX, mouseY, (int)(mouseX + Math.sqrt(magnitude) * Math.cos(direction)), (int) (mouseY - Math.sqrt(magnitude) * Math.sin(direction)));
        
        if(drawBarycenter && totalMass > 0){
            g2.setColor(Color.RED);
            g2.drawOval(((int) baryX) + (frame.getWidth()/2) - 1, (frame.getHeight()/2) - 1 - ((int) baryY), 2, 2);
            g2.setColor(Color.WHITE);
        }
        
        g2.setFont(new Font("Courier New", Font.BOLD, 16));
        g2.drawString("Setting: " + mode, 12, 12);
        if(paused){
            g2.drawString("Paused", frame.getWidth() - 100, 12);
        }
        if(helping){
            g2.setFont(new Font("Courier New", Font.BOLD, 24));
            g2.drawString("Help Menu", 12, 40);
            g2.setFont(new Font("Courier New", Font.BOLD, 16));
            g2.drawString("Key    Description", 12, 60);
            g2.drawString(" B     Toggle Barycenter Display", 12, 72);
            g2.drawString(" H     Shows help menu", 12, 84);
            g2.drawString(" M     Sets input mode to Mass", 12, 96);
            g2.drawString(" P     Pauses the simulator", 12, 108);
            g2.drawString(" R     Sets input mode to Radius", 12, 120);
            g2.drawString(" -     Lowers value for current setting", 12, 132);
            g2.drawString(" =     Increases value for current setting", 12, 144);
            
        } else {
            g2.setFont(new Font("Courier New", Font.BOLD, 24));
            g2.drawString("Press H for Help Menu", 12, 40);
            g2.setFont(new Font("Courier New", Font.BOLD, 16));
        }
        
        g2.setFont(new Font("Courier New", Font.BOLD, 24));
        g2.drawString("Current Settings", 12, frame.getHeight() - 102);
        g2.setFont(new Font("Courier New", Font.BOLD, 16));
        g2.drawString("Velocity: " + magnitude + "m/s", 12, frame.getHeight() - 84);
        g2.drawString("Direction: " + (int)(Math.toDegrees(direction)% 360.0 + 0.5) + " degrees", 12, frame.getHeight() - 72);
        g2.drawString("Mass: " + (long)(mass * 6.673 * Math.pow(10,-11) + 0.1) + "/G kilograms", 12, frame.getHeight() - 60);
        g2.drawString("Radius: " + radius + " meters", 12, frame.getHeight() - 48);
        g2.drawString("Showing Barycenter: " + drawBarycenter, 12, frame.getHeight() - 36);
        
        g2.setFont(new Font("Courier New", Font.BOLD, 24));
        g2.drawString("Statistics", frame.getWidth() - 250, frame.getHeight() - 102);
        g2.setFont(new Font("Courier New", Font.BOLD, 16));
        g2.drawString("Entities Spawned: " + list.size(), frame.getWidth() - 250, frame.getHeight() - 84);
        
        
    }
    
    
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_LEFT){
            direction += Math.toRadians(1);
        } else if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
            direction -= Math.toRadians(1);
        } if(ke.getKeyCode() == KeyEvent.VK_UP){
            magnitude += 5;
        } else if(ke.getKeyCode() == KeyEvent.VK_DOWN){
            if(magnitude > 0)
                magnitude -= 5;
        }
        if(ke.getKeyCode() == KeyEvent.VK_EQUALS){
            if(mode.equals("MASS") && mass < Math.pow(10,9)/(6.673 * Math.pow(10,-11))){
                mass *= 10;
            } else if(mode.equals("RADIUS")){
                radius++;
            }
        } else if(ke.getKeyCode() == KeyEvent.VK_MINUS){
            if(mode.equals("MASS") && mass > 1/(6.673 * Math.pow(10,-11))){
                mass /= 10;
            } else if(mode.equals("RADIUS") && radius > 1){
                radius--;
            }
        }
        if(ke.getKeyCode() == KeyEvent.VK_M){
            mode = "MASS";
        } else if(ke.getKeyCode() == KeyEvent.VK_R){
            mode = "RADIUS";
        } else if(ke.getKeyCode() == KeyEvent.VK_P){
            paused = !paused;
        } else if(ke.getKeyCode() == KeyEvent.VK_H){
            helping = !helping;
        } else if(ke.getKeyCode() == KeyEvent.VK_B){
            drawBarycenter = !drawBarycenter;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        list.add(new Entity(mouseX - frame.getWidth()/2, -mouseY + frame.getHeight()/2, radius, magnitude, direction, mass));
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //list.add(new Entity(mouseX - frame.getWidth()/2, mouseY - frame.getHeight()/2, radius, magnitude, direction, mass));
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }

    @Override
    public void mouseExited(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();}

    @Override
    public void mouseDragged(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }
}

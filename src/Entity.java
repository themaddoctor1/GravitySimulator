
import java.awt.Color;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Christopher Hittner
 */
public class Entity {
    private double mass, radius;
    private double x, y;
    private double velX, velY;
    private Color color;
    
    public Entity(double X, double Y, double R, double magnitude, double direction, double massOfObject){
        x = X;
        y = Y;
        velX = magnitude * Math.cos(direction);
        velY = magnitude * Math.sin(direction);
        mass = massOfObject;
        radius = R;
        Random gen = new Random();
        color = new Color(64 + gen.nextInt(192), 127 + gen.nextInt(128), 127 + gen.nextInt(128));
    }
    
    public void move(){
        x += velX/200.0;
        y += velY/200.0;
    }
    
    public void gravitate(Entity other){
        double relX = this.x - other.x;
        double relY = this.y - other.y;
        double rel = Math.sqrt(Math.pow(relX,2) + Math.pow(relY,2));
        if(rel == 0){
            return;
        }
        relX /=rel;
        relY /=rel;
        double force = (6.673 * Math.pow(10,-11) * this.mass * other.mass)/Math.pow(rel,2);
        double acceleration = force / this.mass;
        
        
        velX -= (acceleration * relX) / 200.0;
        velY -= (acceleration * relY) / 200.0;
        
        
    }
    
    public boolean testCollision(Entity other){
        double distX = this.x - other.x;
        double distY = this.y - other.y;
        double dist = Math.sqrt(Math.pow(distX,2) + Math.pow(distY,2));
        
        if(dist < this.radius + other.radius){
            return true;
        } else {
            return false;
        }
    }
    
    public void collide(Entity other){
        if(!(testCollision(other))){
            return;
        }
        double massSum = this.mass + other.mass;
        
        if(this.mass == 0 || other.mass == 0){
            return;
        }
        
        double momentumX = this.mass * velX + other.mass * other.velX;
        double momentumY = this.mass * velY + other.mass * other.velY;
        
        velX = momentumX/massSum;
        velY = momentumY/massSum;
        other.velX = momentumX/massSum;
        other.velY = momentumY/massSum;
        
        x = (x * mass + other.x * other.mass) / (mass + other.mass);
        y = (y * mass + other.y * other.mass) / (mass + other.mass);
        other.x = x;
        other.y = y;
        mass = massSum;
        other.mass = 0;
        
        double thisVolume = (4/3 * Math.PI * Math.pow(radius,3));
        double otherVolume = (4/3 * Math.PI * Math.pow(other.radius,3));
        double volume = thisVolume + otherVolume;
        
        Color otherColor = other.color;
        int red = (int)((color.getRed() * thisVolume + otherColor.getRed() * otherVolume)/volume);
        int green = (int)((color.getGreen() * thisVolume + otherColor.getGreen() * otherVolume)/volume);
        int blue = (int)((color.getBlue() * thisVolume + otherColor.getBlue() * otherVolume)/volume);
        
        this.setColor(new Color(red,green,blue));
        other.setColor(new Color(red, green, blue));
        
        other.radius = 0;
        radius = Math.cbrt(volume/((4/3) * Math.PI));
        
    }
    
    public double getX(){
        return x;
    }
    public void setVelX(double X){
        velX = X;
    }
    public void setVelY(double Y){
        velY = Y;
    }
    public void setColor(Color c){
        color = c;
    }
    public double getY(){
        return y;
    }
    public double getRadius(){
        return radius;
    }
    public double getMass(){
        return mass;
    }
    public Color getColor(){
        return color;
    }
}

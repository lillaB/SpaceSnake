package controller;

import model.IGravity;
import model.Moveable;
import model.WorldCollection;
import model.WorldObject;
import model.objects.SnakePart;

/**
 * Write a description of class GameThread here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhysicsEngine extends Thread
{
    private long interval;
    private WorldCollection data;
    private boolean setPaused;

    public PhysicsEngine(WorldCollection data, long interval){
    	this.data = data;
        this.interval = interval;
    }
    public void collisionResolve(){
    	
    }
    public void run(){
    	Thread thisThread = Thread.currentThread();
        while ( ! isInterrupted() ) {
            try{ 
                sleep(interval);
                synchronized(this) {
                	while(setPaused) {
                		thisThread.wait();
                	}
                }
            }
            catch(InterruptedException e){
                break;
            }
           
            for(WorldObject obj : data.getCollection()){
            	if(obj instanceof IGravity ){
            		((IGravity)obj).gravityPull(data);
            	}       	
            }
            for(WorldObject obj : data.getCollection()){
            	if(obj instanceof Moveable)
            		((Moveable)obj).collision(data);
            }
            for(WorldObject obj : data.getCollection()){
            	if(obj instanceof Moveable){
            		((Moveable)obj).move();
            	}
            }
            for(WorldObject obj : data.getCollection()){
            	if(obj instanceof SnakePart ){
            		((SnakePart)obj).pullAtNext();
            	}
            }
            
            
        }
    }
    public void setPaused() {
    	setPaused = true;
    }
    
    public void setResumed() {
    	setPaused = false;
    }
}
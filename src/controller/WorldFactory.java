package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Double;

import model.Floater;
import model.WorldCollection;
import model.WorldObject;
import model.objects.BlackHole;
import model.objects.Edible;
import model.objects.SnakeHead;
import model.objects.SnakeTail;
import util.Config;
import util.Vector2D;

/**
 * The class that creates objects and puts them in the world.
 * 
 * @author Ingrid, Micaela
 * @version 2016-02-28
 */

public class WorldFactory {
	
	private static final int MAX_SPAWN = 40;
	private static final int MIN_SPAWN = 20;
	private static final int ZERO = 0;
	
	SnakeHead head;
	SnakeTail tail;
	SnakeTail tail2;
	GameController parent;
	WorldCollection worldCollection;
	
	/**
	 * Constructor that creates game objects and the snake.
	 * @param	parent			Reference to GameController
	 * 			worldCollection	the game world 
	 */
	public WorldFactory(GameController parent, WorldCollection worldCollection) {
		this.parent = parent;
		this.worldCollection = worldCollection;
		createSnake();
		createObjects();
	}
	
	/**
	 * Adds a snake and  objects to the world
	 */
	public void createObjects() {
		ArrayList<WorldObject> gameObjects = new ArrayList<WorldObject>();
		addSnake(gameObjects, head, tail,tail2);
		addToWorld(gameObjects, randomSpawns());
		for (WorldObject worldObject: gameObjects) {
			worldCollection.add(worldObject);
		}
	}
	
	/**
	 * Creates a snake with a set position
	 */
	public void createSnake(){
		head = new SnakeHead(worldCollection, new Vector2D(1,-7), new Vector2D(20,100),10,40);
		tail = new SnakeTail(worldCollection, new Vector2D(0,0),new Vector2D(-30,100),5,30);
		tail2 = new SnakeTail(worldCollection, new Vector2D(0,2),new Vector2D(-70,100),5,30);
		
		head.addTail(tail);
		tail.addTail(tail2);
	}
	
	/**
	 * Adds the snake to the world
	 * @param 	gameObjects	List that holds objects in the world
	 * 			head	the head of the snake
	 * 			tail	the first tail of the snake
	 * 			tail2	the second tail of the snake
	 */
	public void addSnake(ArrayList<WorldObject> gameObjects, SnakeHead head, SnakeTail tail, SnakeTail tail2){
		gameObjects.add(head);
		gameObjects.add(tail);
		gameObjects.add(tail2);
	}
	
	/**
	 * Randomizes how many objects that should be created, and of which type, based on the size of the World.
	 * @return spawns	A hashmap of types of objects and how many of them are to be created
	 * TODO: 	Polish the randomization of how many objects are to be created of different types.
	 * 			Possibly create difficulties?
	 */
	public Map<String,Integer> randomSpawns() {		
		Map<String,Integer> spawns = new HashMap<String,Integer>();
		Random random = new Random();
		Double worldSize = worldCollection.getWorldSize();
		
		int totalObjects = random.nextInt(MAX_SPAWN)+ MIN_SPAWN;
		int floater = ZERO;
		int edible = ZERO;
		int blackHole = ZERO;
		
		if(worldSize.intValue() <= 6000 ){
			
			floater = totalObjects/8;
			edible = 5;
			blackHole = totalObjects/5 ;
		}
	
		else if( worldSize.intValue() >= 6001 && worldSize.intValue() <= 8500 ){
			
			floater = totalObjects/4;
			edible = 10;
			blackHole = totalObjects/4 ;
		}
		
		else if( worldSize.intValue() >= 8501 && worldSize.intValue() <= 10000 ){
			
			floater = totalObjects/4;
			edible = 10;
			blackHole = totalObjects/4 ;
		}
		
		else if( worldSize.intValue() >= 10001 && worldSize.intValue() <= 12000 ){
			
			floater = totalObjects/2;
			edible = 10;
			blackHole = totalObjects/2 ;
		}
		
		else {
			
			floater = totalObjects;
			edible = 10;
			blackHole = totalObjects/2 ;
		}
		 
			spawns.put("Floater",floater);
			spawns.put("Edible",edible);
			spawns.put("BlackHole",blackHole);
			System.out.println("total objects="+worldSize);
			System.out.println("total objects="+totalObjects);
		
		return spawns;
	}
	
	/**
	 * Creates objects with randomized coordinates that are added to an arraylist
	 * @param 	gameObjects		List that can hold objects that will be put in the world
	 * 			spawn			HashMap that holds keys mapped to the amount of certain objects that should be created.
	 * TODO: Move randomization of position to a new separate method randomizePosition() 
	 * 		that returns a Vector2D of random position, which also checks if it's in the map
	 */
	public void addToWorld(ArrayList<WorldObject> gameObjects, Map<String,Integer> spawn){
		Vector2D speed;
		Vector2D pos;
		double mass;
		double radius;
		
		if( spawn.containsKey("Floater") ){
			for(int i=0; i< spawn.get("Floater"); i++){
				speed = randomSpeed();
				pos = randomPosition();
				mass = Double.parseDouble(Config.get("floater_mass"));
				radius = Double.parseDouble(Config.get("floater_size"));
				
				gameObjects.add( new Floater(worldCollection, speed, pos, mass, radius) );
			}
		}
		if( spawn.containsKey("BlackHole") ){
			for(int i=0; i< spawn.get("BlackHole"); i++){
				pos = randomPosition();
				mass = Double.parseDouble(Config.get("black_hole_mass"));
				radius = Double.parseDouble(Config.get("black_hole_size"));
				
				while(!checkPos(pos,radius,gameObjects)){
					pos = randomPosition();
				}
				gameObjects.add( new BlackHole(worldCollection, pos, mass, radius) );
			}
		}
		if( spawn.containsKey("Edible") ){
			for(int i=0; i< spawn.get("Edible"); i++){
				speed = randomSpeed();
				pos = randomPosition();
				mass = Double.parseDouble(Config.get("edible_mass"));
				radius = Double.parseDouble(Config.get("edible_size"));

				gameObjects.add( new Edible(worldCollection, speed, pos, mass, radius) );
			}
		}
	}
	
	/**
	 * Checks if a position is free for the object
	 * @param 	pos		the x and y coordinates of the object
	 * 			radius	the radius of the object
	 * 			gameObjects		the array that holds objects
	 * @return	true	if there is a free space for the object
	 * 			false	if there is no free space for the object
	 */
	public boolean checkPos(Vector2D pos, double radius, ArrayList<WorldObject> gameObjects){
		for(WorldObject objects : gameObjects){
			double lengthsqr = pos.sub(objects.getPosition()).lengthsquared();
			double radlength = radius + objects.getRadius();
			
			if(lengthsqr<radlength*radlength){
				return false;
			}			
		}
		return true;
	}
	
	/**
	 * Randomizes position coordinates
	 * @return pos	Vector with x and y coordinates
	 */
	public Vector2D randomPosition() {
		Vector2D pos = new Vector2D(Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2, Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2);
		while ( !isInsideWorld(pos) ) {
			pos = new Vector2D(Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2, Math.random() * worldCollection.getWorldSize() - worldCollection.getWorldSize()/2);
		}
		return pos;
	}
	
	/**
	 * Randomizes speed values
	 * @return speed	Vector with two speed values
	 */
	public Vector2D randomSpeed() {
		double speedMultiplier = 50;
		Vector2D speed = new Vector2D((Math.random() * speedMultiplier) - speedMultiplier/2, (Math.random() * speedMultiplier) - speedMultiplier/2);
		return speed;
	}
	
	/**
	 * Checks if a coordinates is inside the map
	 * @param pos	Vector with x and y coordinates that are to be checked
	 * @return 	true if coordinates are inside of the map
	 * 			false if coordinates are outside of the map
	 */
	public boolean isInsideWorld(Vector2D pos) {
		double worldRadius = worldCollection.getWorldSize()/2;
		if (pos.getX()*pos.getX() + pos.getY()*pos.getY() < worldRadius*worldRadius) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Randomizes the size of the map
	 * @return worldSize	Randomized double
	 */
	public static double randomWorldSize() {
		return (Math.random() * 10000) + 5000;
	}
}

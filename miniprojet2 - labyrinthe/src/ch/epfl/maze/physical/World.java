package ch.epfl.maze.physical;

import java.util.List;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * World that is represented by a labyrinth of tiles in which an {@code Animal}
 * can move.
 * 
 */

public abstract class World {

	/* tiles constants */
	public static final int FREE = 0;
	public static final int WALL = 1;
	public static final int START = 2;
	public static final int EXIT = 3;
	public static final int NOTHING = -1;

	int[][] World;
	
	/**
	 * Constructs a new world with a labyrinth. The labyrinth must be rectangle.
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */

	public World(int[][] labyrinth) {
		World = new int[labyrinth.length][labyrinth[0].length];
		for (int i = 0; i < labyrinth.length; i++) {
			for (int j = 0; j < labyrinth[0].length; j++) {
				if (labyrinth[i][j] == 0)
					World[i][j] = FREE;
				else if (labyrinth[i][j] == 1)
					World[i][j] = WALL;
				else if (labyrinth[i][j] == 2)
					World[i][j] = START;
				else if (labyrinth[i][j] == 3)
					World[i][j] = EXIT;
				else
					World[i][j] = NOTHING;
			}
		}
	}

	/**
	 * Determines whether the labyrinth has been solved by every animal.
	 * 
	 * @return <b>true</b> if no more moves can be made, <b>false</b> otherwise
	 */

	abstract public boolean isSolved();

	/**
	 * Resets the world as when it was instantiated.
	 */

	abstract public void reset();

	/**
	 * Returns a copy of the list of all current animals in the world.
	 * 
	 * @return A list of all animals in the world
	 */

	abstract public List<Animal> getAnimals();

	/**
	 * Checks in a safe way the tile number at position (x, y) in the labyrinth.
	 * 
	 * @param x
	 *            Horizontal coordinate
	 * @param y
	 *            Vertical coordinate
	 * @return The tile number at position (x, y), or the NONE tile if x or y is
	 *         incorrect.
	 */

	public final int getTile(int x, int y) {
		if (x>World[0].length-1 || y>World.length-1 || x<0 || y<0)
			return -1;
		else{
			if (World[y][x] == FREE)
				return 0;
			else if (World[y][x] == WALL)
				return 1;
			else if (World[y][x] == START)
				return 2;
			else if (World[y][x] == EXIT)
				return 3;
			else
				return -1;
		}
		
	}

	/**
	 * Determines if coordinates are free to walk on.
	 * 
	 * @param x
	 *            Horizontal coordinate
	 * @param y
	 *            Vertical coordinate
	 * @return <b>true</b> if an animal can walk on tile, <b>false</b> otherwise
	 */

	public final boolean isFree(int x, int y) {
		if (x>World[0].length-1 || y>World.length-1 || x<0 || y<0)
			return false;
		else{
			if (World[y][x] == FREE)
				return true;
			else if(World[y][x] == START)	
				return true;
			else if(World[y][x] == EXIT)
				return true;
			else
				return false;
		}
	}

	/**
	 * Computes and returns the available choices for a position in the
	 * labyrinth. The result will be typically used by {@code Animal} in
	 * {@link ch.epfl.maze.physical.Animal#move(Direction[]) move(Direction[])}
	 * 
	 * @param position
	 *            A position in the maze
	 * @return An array of all available choices at a position
	 */

	public final Direction[] getChoices(Vector2D position) {
		int i = 0;
		Direction a = null , b = null, c = null, d = null, e = null;
		if (isFree(position.getX(), position.getY()-1)){
			i++;
			a=Direction.UP;}
		if (isFree(position.getX()+1, position.getY())){
			i++;
			b=Direction.RIGHT;}
		if (isFree(position.getX(), position.getY()+1)){			
			i++;
			c=Direction.DOWN;}
		if (isFree(position.getX()-1, position.getY())){			
			i++;
			d=Direction.LEFT;}
		if (i==0){
			i++;
			e=Direction.NONE;}
		
		Direction[] dir = new Direction[i];
		int j=0;
		if (a != null){
			dir[j] = Direction.UP;
			j++;}
		if (b != null){
			dir[j] = Direction.RIGHT;
			j++;}
		if (c != null){
			dir[j] = Direction.DOWN;
			j++;}
		if (d != null){
			dir[j] = Direction.LEFT;
			j++;}
		if (e != null){
			dir[j] = Direction.NONE;
		}
		
		return dir;

	}
	
	/**
	 * Returns horizontal length of labyrinth.
	 * 
	 * @return The horizontal length of the labyrinth
	 */

	public final int getWidth() {
		return World[0].length;
	}

	/**
	 * Returns vertical length of labyrinth.
	 * 
	 * @return The vertical length of the labyrinth
	 */

	public final int getHeight() {
		return World.length;
	}

	/**
	 * Returns the entrance of the labyrinth at which animals should begin when
	 * added.
	 * 
	 * @return Start position of the labyrinth, null if none.
	 */

	public final Vector2D getStart() {
		int x = 0 ;
		int y = 0 ;
		Vector2D vect = new Vector2D(NOTHING, NOTHING);
		for (int i = 0; i < World.length; i++) {
			for (int j = 0; j < World[0].length; j++) {
				if (World[i][j] == START){
					x=j;
					y=i;
					vect = new Vector2D(x, y);
				}
			}
		}
		return vect;
	}	

	/**
	 * Returns the exit of the labyrinth at which animals should be removed.
	 * 
	 * @return Exit position of the labyrinth, null if none.
	 */

	public final Vector2D getExit() {
		int x = 0 ;
		int y = 0 ;
		Vector2D vect = new Vector2D(NOTHING, NOTHING);
		for (int i = 0; i < World.length; i++) {
			for (int j = 0; j < World[0].length; j++) {
				if (World[i][j] == EXIT){
					x=j;
					y=i;
					vect = new Vector2D(x, y);	
				}
			}
		}
		return vect;
	}
	
}

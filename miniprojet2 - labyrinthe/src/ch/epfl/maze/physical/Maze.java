package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Maze in which an animal starts from a starting point and must find the exit.
 * Every animal added will have its position set to the starting point. The
 * animal is removed from the maze when it finds the exit.
 * 
 */

public final class Maze extends World {

	private ArrayList<Animal> presentAnimal;
	private ArrayList<Animal> removedAnimal;
	
	
	
	/**
	 * Constructs a Maze with a labyrinth structure.
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */

	public Maze(int[][] labyrinth) {
		super(labyrinth);
		presentAnimal = new ArrayList<Animal>();
		removedAnimal = new ArrayList<Animal>();
	}

	@Override
	public boolean isSolved() {
		if (presentAnimal.isEmpty())
			return true;
		else
			return false;
	}

	@Override
	public List<Animal> getAnimals() {
		return presentAnimal;
	}

	/**
	 * Determines if the maze contains an animal.
	 * 
	 * @param a
	 *            The animal in question
	 * @return <b>true</b> if the animal belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasAnimal(Animal a) {
		if (getAnimals().contains(a))
			return true;
		else return false;
	}

	/**
	 * Adds an animal to the maze.
	 * 
	 * @param a
	 *            The animal to add
	 */

	public void addAnimal(Animal a) {
		presentAnimal.add(a);
		for (int i = 0; i < presentAnimal.size(); i++) {
			(presentAnimal.get(i)).setPosition(getStart());
		}
	}

	/**
	 * Removes an animal from the maze.
	 * 
	 * @param a
	 *            The animal to remove
	 */

	public void removeAnimal(Animal a) {
		presentAnimal.remove(a);
		removedAnimal.add(a);
	}
	
	

	@Override
	public void reset() {
		ArrayList<Animal> temp = new ArrayList<>();
		temp.addAll(presentAnimal);
		temp.addAll(removedAnimal);
		presentAnimal.clear();
		removedAnimal.clear();
		for (int i = 0; i < temp.size(); i++) {
			presentAnimal.add(temp.get(i).copy());
			presentAnimal.get(i).setPosition(getStart());
		}
		
	}
}

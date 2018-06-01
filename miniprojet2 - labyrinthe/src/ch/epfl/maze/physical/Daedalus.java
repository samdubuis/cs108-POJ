package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Daedalus in which predators hunt preys. Once a prey has been caught by a
 * predator, it will be removed from the daedalus.
 * 
 */

public final class Daedalus extends World {
	
	private ArrayList<Predator> presentPredators;
	private ArrayList<Prey> presentPreys;
	private ArrayList<Predator> removedPredators;
	private ArrayList<Prey> removedPreys;


	/**
	 * Constructs a Daedalus with a labyrinth structure
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */

	public Daedalus(int[][] labyrinth) {
		super(labyrinth);
		presentPredators = new ArrayList<Predator>();
		presentPreys = new ArrayList<Prey>();
		removedPredators = new ArrayList<Predator>();
		removedPreys = new ArrayList<Prey>();
		}

	@Override
	public boolean isSolved() {
		if (presentPreys.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * Adds a predator to the daedalus.
	 * 
	 * @param p
	 *            The predator to add
	 */

	public void addPredator(Predator p) {
		presentPredators.add(p);
	}

	/**
	 * Adds a prey to the daedalus.
	 * 
	 * @param p
	 *            The prey to add
	 */

	public void addPrey(Prey p) {
		presentPreys.add(p);
	}

	/**
	 * Removes a predator from the daedalus.
	 * 
	 * @param p
	 *            The predator to remove
	 */

	public void removePredator(Predator p) {
		presentPredators.remove(p);
		removedPredators.add(p);
		
	}

	/**
	 * Removes a prey from the daedalus.
	 * 
	 * @param p
	 *            The prey to remove
	 */

	public void removePrey(Prey p) {
		presentPreys.remove(p);
		removedPreys.add(p);
	}

	@Override
	public List<Animal> getAnimals() {
		ArrayList<Animal> temp = new ArrayList<Animal>();
		temp.addAll(presentPredators);
		temp.addAll(presentPreys);
		return temp;
	}

	/**
	 * Returns a copy of the list of all current predators in the daedalus.
	 * 
	 * @return A list of all predators in the daedalus
	 */

	public List<Predator> getPredators() {
		return presentPredators;
	}

	/**
	 * Returns a copy of the list of all current preys in the daedalus.
	 * 
	 * @return A list of all preys in the daedalus
	 */

	public List<Prey> getPreys() {
		return presentPreys;
	}

	/**
	 * Determines if the daedalus contains a predator.
	 * 
	 * @param p
	 *            The predator in question
	 * @return <b>true</b> if the predator belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPredator(Predator p) {
		if (getPredators().contains(p)) {
			return true;
		}
		else 
			return false;
	}

	/**
	 * Determines if the daedalus contains a prey.
	 * 
	 * @param p
	 *            The prey in question
	 * @return <b>true</b> if the prey belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPrey(Prey p) {
		if (getPreys().contains(p)) {
			return true;
		}
		else 
			return false;
	}

	@Override
	public void reset() {
		presentPredators.addAll(removedPredators);
		presentPreys.addAll(removedPreys);
		removedPredators.clear();
		removedPreys.clear();
		for (int i = 0; i < presentPredators.size(); i++) {
			presentPredators.get(i).copy();
			presentPredators.get(i).setPosition(presentPredators.get(i).getCaseMaison());;
		}	
		for (int i = 0; i < presentPreys.size(); i++) {
			presentPreys.get(i).copy();
			presentPreys.get(i).setPosition(presentPreys.get(i).getCaseMaison());
		}
	}
}

package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Orange ghost from the Pac-Man game, alternates between direct chase if far
 * from its target and SCATTER if close.
 * 
 */

public class Clyde extends Predator {

	/**
	 * Constructs a Clyde with a starting position.
	 * 
	 * @param position
	 *            Starting position of Clyde in the labyrinth
	 */

	public Clyde(Vector2D position) {
		super(position);
		// TODO
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		// TODO
		return Direction.NONE;
	}

	@Override
	public Animal copy() {
		// TODO
		return null;
	}
}

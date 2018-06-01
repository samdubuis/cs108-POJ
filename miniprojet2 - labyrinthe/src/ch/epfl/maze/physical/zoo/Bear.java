package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Bear A.I. that implements the Pledge Algorithm.
 * 
 */

public class Bear extends Animal {

	/**
	 * Constructs a bear with a starting position.
	 * 
	 * @param position
	 *            Starting position of the bear in the labyrinth
	 */

	public Bear(Vector2D position) {
		super(position);
		// TODO
	}

	/**
	 * Moves according to the <i>Pledge Algorithm</i> : the bear tries to move
	 * towards a favorite direction until it hits a wall. In this case, it will
	 * turn right, put its paw on the left wall, count the number of times it
	 * turns right, and subtract to this the number of times it turns left. It
	 * will repeat the procedure when the counter comes to zero, or until it
	 * leaves the maze.
	 */

	@Override
	public Direction move(Direction[] choices) {
		// TODO
		return Direction.NONE;
	}

	@Override
	public Animal copy() {
		// TODO
		return null;
	}
}

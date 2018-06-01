package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

import java.util.ArrayList;
import java.util.Random;
/**
 * Mouse A.I. that remembers only the previous choice it has made.
 * 
 */

public class Mouse extends Animal {
	

	/**
	 * Constructs a mouse with a starting position.
	 * 
	 * @param position
	 *            Starting position of the mouse in the labyrinth
	 */

	public Mouse(Vector2D position) {
		super(position);
	}

	/**
	 * Moves according to an improved version of a <i>random walk</i> : the
	 * mouse does not directly retrace its steps.
	 */

	@Override
	public Direction move(Direction[] choices) {
		Random random = new Random();
		if (getPreviousChoice()==null){
			int rand = random.nextInt(choices.length);
			setPreviousChoice(choices[rand]);
			return choices[rand];
		}
		if (getPreviousChoice()!=null && choices.length == 1){
			setPreviousChoice(choices[0]);
			return choices[0];}

		if (getPreviousChoice() != null && choices.length>1){
			ArrayList<Direction> temp = new ArrayList<Direction>();
			for (int i = 0; i < choices.length; i++) {
				temp.add(choices[i]);
			}
			Direction opposite = getPreviousChoice().reverse();
			temp.remove(opposite);

			int rand = random.nextInt(temp.size());
			setPreviousChoice(temp.get(rand));
			return temp.get(rand);
		}
		else
			return Direction.NONE;
	}

	@Override
	public Animal copy() {
		return new Mouse(this.getPosition());
	}
}

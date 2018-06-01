package ch.epfl.maze.physical.pacman;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pac-Man character, from the famous game of the same name.
 * 
 */

public class PacMan extends Prey {


	public PacMan(Vector2D position) {
		super(position);
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {

		ArrayList<Direction> temp = new ArrayList<>();

		for (int i = 0; i < choices.length; i++) {
			temp.add(choices[i]);
		}

		ArrayList<Predator> pred = new ArrayList<>();
		for (int i = 0; i < daedalus.getPredators().size(); i++) {
			pred.add(daedalus.getPredators().get(i));
		}

		for (int j = 0; j < pred.size(); j++) {
			for (int i = 0; i < temp.size(); i++) {
				if (pred.get(j).getEmplacementPredator().equals(getPosition().addDirectionTo((choices[i])))
						|| pred.get(j).equals(getEmplacement().add(choices[i].toVector().mul(2)))) {
					temp.remove(i);
				}
			}
		}

		Direction[] temp2 = new Direction[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			temp2[i] = temp.get(i);
		}
		if (temp2.length==choices.length) {
			Direction best = move(temp2);
			return best;
		}
		if (temp2.length == 0) {
			Direction best = move(choices);
			return best;
		}
		if (temp2.length != choices.length) {
			Direction best = move2(temp2);
			return best;
		} 
		else
			return Direction.NONE;



	}

	@Override
	public Animal copy() {
		return new PacMan(this.getPosition());
	}

}

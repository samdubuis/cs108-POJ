package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

import java.util.Random;

/**
 * Red ghost from the Pac-Man game, chases directly its target.
 * 
 */

public class Blinky extends Predator {

	Random random = new Random();

	int k;
	int i;


	/**
	 * Constructs a Blinky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Blinky in the labyrinth
	 */

	public Blinky(Vector2D position) {
		super(position);
		k= 0;
		i=0;
	}


	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		if (daedalus.getPreys().size()>0){
			int size = daedalus.getPreys().size();
			setAnimalCible(daedalus.getPreys().get(size-1));
			setCaseCible(getAnimalCible().getEmplacement());

			if (k==0) {
				if (i == CHASE_DURATION-1) {
					k=1;
					i=0;
				}
				i++;
				Direction choix = deplacement(choices, getCaseCible());
				return choix;
			}
			else {
				if (i == SCATTER_DURATION-1) {
					k=0;
					i=0;
				}
				i++;
				Direction choix = deplacement(choices, getCaseMaison());
				return choix;
			}
		}
		else{
			Direction choix = move(choices);
			return choix;
		}
	}



	@Override
	public Animal copy() {
		return new Blinky(this.getPosition());
	}
}

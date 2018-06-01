package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Blue ghost from the Pac-Man game, targets the result of two times the vector
 * from Blinky to its target.
 * 
 */

public class Inky extends Predator {

	Vector2D posBlinky;
	int k ;
	int i ;
	
	/**
	 * Constructs a Inky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Inky in the labyrinth
	 */

	public Inky(Vector2D position) {
		super(position);
		posBlinky = null;
		k=0;
		i=0;
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		if (daedalus.getPreys().size()>0){
			int size = daedalus.getPreys().size();
			setAnimalCible(daedalus.getPreys().get(size-1));
			setCaseCible(getAnimalCible().getEmplacement());

			for (int i = 0; i < daedalus.getPredators().size(); i++) {
				Animal temp = daedalus.getPredators().get(i);
				if (temp instanceof Blinky) {
					posBlinky = temp.getPosition();
				}
			}
			
			Vector2D posC = getAnimalCible().getEmplacement().mul(2);

			setCaseCible(posC.sub(posBlinky));
			
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
		return new Inky(this.getPosition());
	}
}

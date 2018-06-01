package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */

public class Hamster extends Animal {

	ArrayList<Vector2D> impasse;
	/**
	 * Constructs a hamster with a starting position.
	 * 
	 * @param position
	 *            Starting position of the hamster in the labyrinth
	 */

	public Hamster(Vector2D position) {
		super(position);
		impasse = new ArrayList<>();
	}

	/**
	 * Moves without retracing directly its steps and by avoiding the dead-ends
	 * it learns during its journey.
	 */

	@Override
	public Direction move(Direction[] choices) {
		
		Random random = new Random();
		
		ArrayList<Direction> temp = new ArrayList<Direction>();
		for (int i = 0; i < choices.length; i++) {
			temp.add(choices[i]);
		}
		
		if (temp.size() == 0){
			return Direction.NONE;
		}
		
		if (temp.size() == 1){
			for (int j = 0; j < temp.size(); j++) {
				if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
					temp.remove(j);
				}
			}
			if (temp.size() == 0){
				return Direction.NONE;
			}
			else{
				impasse.add(getPosition());
				setPreviousChoice(temp.get(0));
				return getPreviousChoice();
			}
		}
		
		if (temp.size() == 2){
			for (int j = 0; j < temp.size(); j++) {
				if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
					temp.remove(j);
				}
			}
			if (temp.size() == 0){
				return Direction.NONE;
			}
			if (temp.size() == 1){
				for (int j = 0; j < temp.size(); j++) {
					if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
						temp.remove(j);
					}
				}
				if (temp.size() == 0){
					return Direction.NONE;
				}
				else{
					impasse.add(getPosition());
					setPreviousChoice(temp.get(0));
					return getPreviousChoice();
				}
			}
			else {
				temp.remove(getPreviousChoice().reverse());
				int rand = random.nextInt(temp.size());
				setPreviousChoice(temp.get(rand));
				return getPreviousChoice();
			}
		}
		
		if (temp.size() == 3){
			for (int j = 0; j < temp.size(); j++) {
				if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
					temp.remove(j);
				}
			}
			if (temp.size() == 0){
				return Direction.NONE;
			}
			if (temp.size() == 1){
				for (int j = 0; j < temp.size(); j++) {
					if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
						temp.remove(j);
					}
				}
				if (temp.size() == 0){
					return Direction.NONE;
				}
				else{
					impasse.add(getPosition());
					setPreviousChoice(temp.get(0));
					return getPreviousChoice();
				}
			}
			if (temp.size() == 2){
				for (int j = 0; j < temp.size(); j++) {
					if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
						temp.remove(j);
					}
				}
				if (temp.size() == 0){
					return Direction.NONE;
				}
				if (temp.size() == 1){
					for (int j = 0; j < temp.size(); j++) {
						if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
							temp.remove(j);
						}
					}
					if (temp.size() == 0){
						return Direction.NONE;
					}
					else{
						impasse.add(getPosition());
						setPreviousChoice(temp.get(0));
						return getPreviousChoice();
					}
				}
				else {
					temp.remove(getPreviousChoice().reverse());
					int rand = random.nextInt(temp.size());
					setPreviousChoice(temp.get(rand));
					return getPreviousChoice();
				}
			}
			else {
				temp.remove(getPreviousChoice().reverse());
				int rand = random.nextInt(temp.size());
				setPreviousChoice(temp.get(rand));
				return getPreviousChoice();
			}
		}
		
		if (temp.size() == 4){
			for (int j = 0; j < temp.size(); j++) {
				if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
					temp.remove(j);
				}
			}
			if (temp.size() == 0){
				return Direction.NONE;
			}
			if (temp.size() == 1){
				for (int j = 0; j < temp.size(); j++) {
					if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
						temp.remove(j);
					}
				}
				if (temp.size() == 0){
					return Direction.NONE;
				}
				else{
					impasse.add(getPosition());
					setPreviousChoice(temp.get(0));
					return getPreviousChoice();
				}
			}
			if (temp.size() == 2){
				for (int j = 0; j < temp.size(); j++) {
					if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
						temp.remove(j);
					}
				}
				if (temp.size() == 0){
					return Direction.NONE;
				}
				if (temp.size() == 1){
					for (int j = 0; j < temp.size(); j++) {
						if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
							temp.remove(j);
						}
					}
					if (temp.size() == 0){
						return Direction.NONE;
					}
					else{
						impasse.add(getPosition());
						setPreviousChoice(temp.get(0));
						return getPreviousChoice();
					}
				}
				else {
					temp.remove(getPreviousChoice().reverse());
					int rand = random.nextInt(temp.size());
					setPreviousChoice(temp.get(rand));
					return getPreviousChoice();
				}
			}
			if (temp.size() == 3){
				for (int j = 0; j < temp.size(); j++) {
					if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
						temp.remove(j);
					}
				}
				if (temp.size() == 0){
					return Direction.NONE;
				}
				if (temp.size() == 1){
					for (int j = 0; j < temp.size(); j++) {
						if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
							temp.remove(j);
						}
					}
					if (temp.size() == 0){
						return Direction.NONE;
					}
					else{
						impasse.add(getPosition());
						setPreviousChoice(temp.get(0));
						return getPreviousChoice();
					}
				}
				if (temp.size() == 2){
					for (int j = 0; j < temp.size(); j++) {
						if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
							temp.remove(j);
						}
					}
					if (temp.size() == 0){
						return Direction.NONE;
					}
					if (temp.size() == 1){
						for (int j = 0; j < temp.size(); j++) {
							if (impasse.contains(getPosition().addDirectionTo(temp.get(j)))){
								temp.remove(j);
							}
						}
						if (temp.size() == 0){
							return Direction.NONE;
						}
						else{
							impasse.add(getPosition());
							setPreviousChoice(temp.get(0));
							return getPreviousChoice();
						}
					}
					else {
						temp.remove(getPreviousChoice().reverse());
						int rand = random.nextInt(temp.size());
						setPreviousChoice(temp.get(rand));
						return getPreviousChoice();
					}
				}
				else {
					temp.remove(getPreviousChoice().reverse());
					int rand = random.nextInt(temp.size());
					setPreviousChoice(temp.get(rand));
					return getPreviousChoice();
				}			
			}
			else {
				temp.remove(getPreviousChoice().reverse());
				int rand = random.nextInt(temp.size());
				setPreviousChoice(temp.get(rand));
				return getPreviousChoice();
			}
		}
		return Direction.NONE;	
	}	
	
	@Override
	public Animal copy() {
		Animal a = new Hamster(getPosition());
		return a;
	}

}

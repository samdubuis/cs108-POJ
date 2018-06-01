package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetTest {

    public static void main(String[] args) {
        Set<Integer> temp = new HashSet<>();
        temp.add(1);
        temp.add(15);
        temp.add(48);
        temp.add(95);
        
        for (Integer integer : temp) {
            System.out.println(integer);
        }
        
        List<Character> temp2 = new ArrayList<>();
        temp2.add('a');
        temp2.add('f');
        temp2.add('g');
        temp2.add('z');
        
        for (Character character : temp2) {
            System.out.println(character);
        }
        for (int i = 0; i < temp2.size(); i++) {
            System.out.println(temp2.get(i));
        }
        
        
    }

}

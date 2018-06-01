/**
 * permet d'écouter les événements clavier et accepter une action si la touche pressé est contenu dans une map de touche acceptées
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import ch.epfl.xblast.PlayerAction;

public final class KeyboardEventHandler extends KeyAdapter  {    
    private final Map<Integer, PlayerAction> map;
    private final Consumer<PlayerAction> consumer;
    
    /**
     * constructeur de la classe
     * @param map
     * @param consumer
     */
    public KeyboardEventHandler(Map<Integer, PlayerAction> map, Consumer<PlayerAction> consumer) {
        this.map = map;
        this.consumer= consumer;
    }
    
    /**
     * redefinition de la methode keyPressed de la classe KeyAdapter
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (map.containsKey(e.getKeyCode())) {
            consumer.accept(map.get(e.getKeyCode()));
        }        
    }    
}

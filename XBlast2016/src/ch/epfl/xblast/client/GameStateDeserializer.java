/**
 * Classe servant à désérialiser l'etat du jeu envoyé par le serveur
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.client;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.RunLengthEncoder;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.client.GameState.Player;
import ch.epfl.xblast.client.GameState;

public final class GameStateDeserializer {
    private GameStateDeserializer() {}
    /**
     * methode servant à désérialiser l'état à partir d'une list de byte
     * @param list
     * @return
     */
    public static GameState deserializeGameState(List<Byte> list){
        List<Byte> serial = new ArrayList<>(list);   

        /**
         * deserialisation du board
         */
        int numberOfByteBoard = Byte.toUnsignedInt(serial.get(0));
        serial.remove(0);
        List<Byte> deserializedBoard = new ArrayList<>(serial.subList(0, numberOfByteBoard));
        List<Byte> board = new ArrayList<>(RunLengthEncoder.decode(deserializedBoard));
        serial.subList(0, numberOfByteBoard).clear();

        /**
         * deserialisation des bombes et des explosions
         */
        int numberOfByteTnt = Byte.toUnsignedInt(serial.get(0));
        serial.remove(0);
        List<Byte> deserializedBombsAndExplosions = new ArrayList<>(serial.subList(0, numberOfByteTnt));
        List<Byte> bombsAndExplosions = new ArrayList<>(RunLengthEncoder.decode(deserializedBombsAndExplosions));
        serial.subList(0, numberOfByteTnt).clear();

        /**
         * deserialisation des players
         */
        List<Byte> players = new ArrayList<>(serial.subList(0, 16));
        serial.subList(0, 16).clear();

        /**
         * deserialisation du temps
         */
        List<Byte> temps = new ArrayList<>(serial);

        /**
         * ordre de lecture pour le board
         */
        List<Byte> boardRowMajorOrder = new ArrayList<>();       
        for (int i = 0; i < 195; i++) {
            boardRowMajorOrder.add(null);
        }
        int compteur = 0;
        for (Cell cell : Cell.SPIRAL_ORDER) {
            int position = cell.rowMajorIndex();
            boardRowMajorOrder.set(position, board.get(compteur));
            compteur++;
        }
        
        /**
         * creation de la list de byte pour le temps restant 
         */
        List<Byte> tempsRestant = new ArrayList<>();
        for (int i = 0; i < temps.get(0); i++){
            tempsRestant.add((byte) 21);
        }
        for (int i = 0; i < 60 -temps.get(0); i++) {
            tempsRestant.add((byte) 20);
        }
        
        /**
         * creation de la list de byte pour le scores
         */
        List<Byte> scores = new ArrayList<>();
        int compteur1 = 0;
        for (int i = 0; i < 16; i+=4) {
            if(players.get(i) > 0){
                scores.add((byte) compteur1);
                scores.add((byte) 10);
                scores.add((byte) 11);
                compteur1+=2;
            }
            else{
                scores.add((byte) (compteur1 +1));
                scores.add((byte) 10);
                scores.add((byte) 11);
                compteur1+=2;
            }
        }
        for (int i = 6; i < 14; i++) {
            scores.add(i, (byte) 12);
        }
        
        /**
         * creation des joueurs pour le gamestate
         */
        List<PlayerID> playersID = new ArrayList<>(Arrays.asList(PlayerID.PLAYER_1, PlayerID.PLAYER_2, PlayerID.PLAYER_3, PlayerID.PLAYER_4));
        List<Player> playersGamestate = new ArrayList<>();
        for (int i = 0; i < 16; i+=4) {
            Image playerImage = ImageCollection.p.image(players.get(i+3));
            SubCell subCell = new SubCell(Byte.toUnsignedInt(players.get(i+1)), Byte.toUnsignedInt(players.get(i+2)));
            PlayerID id = playersID.get(i/4);
            int lives = Byte.toUnsignedInt(players.get(i));
            Player player = new Player(id, lives, subCell, playerImage);
            playersGamestate.add(player);
        }
              
        /**
         * creation de la list d'image pour le board
         */
        List<Image> boardImage = new ArrayList<>();
        for (int i = 0; i < boardRowMajorOrder.size(); i++) {            
            boardImage.add(ImageCollection.b.image(boardRowMajorOrder.get(i)));
        }
        
        /**
         * creation de la list d'image pour les bombes et les explosions
         */
        List<Image> bombsAndExplosionsImage = new ArrayList<>();
        for (int i = 0; i < bombsAndExplosions.size(); i++) {
            bombsAndExplosionsImage.add(ImageCollection.ex.image(bombsAndExplosions.get(i)));
        }
        
        /**
         * creation de la list d'image pour le temps restant
         */
        List<Image> tempsImage = new ArrayList<>();
        for (int i = 0; i < tempsRestant.size(); i++) {
            tempsImage.add(ImageCollection.s.image(tempsRestant.get(i)));
        }
        
        /**
         * creation de la list d'image pour le scores
         */
        List<Image> scoresImage = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            scoresImage.add(ImageCollection.s.image(scores.get(i)));
        }
        
        /**
         * creation du gamestate
         */
        GameState gs = new GameState(playersGamestate, boardImage, bombsAndExplosionsImage, scoresImage, tempsImage);
        
        return gs;
    }
}
 
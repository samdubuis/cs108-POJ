/**
 * main du client 
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.client;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.server.GameStateSerializer;
import ch.epfl.xblast.server.Level;

public class Main {

    static final private String DEFAULT_SERVER_ADDRESS = "localhost";
    static final private int MAX_SIZE_BUFFER = 409+1;
    static final private int ONESEC_IN_MS = 1000;
    
    private static ByteBuffer bufferRecuSuivant;
    private static final ByteBuffer bufferEnvoiPlayerAction  = ByteBuffer.allocate(1);
    private static ByteBuffer bufferEnvoi;
    private static InetSocketAddress addressServeur;

    private static DatagramChannel channel;

    private static XBlastComponent xbc ;
    private static GameState gsClient;
    private static PlayerID id=null;


    /**
     * crée la fenetre avec le composant et les touches correspondantes
     */
    private static void createUI() {
        Map<Integer, PlayerAction> kb = new HashMap<>();

        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_DOWN, PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_SHIFT, PlayerAction.STOP);

        Consumer<PlayerAction> c = x -> {
            bufferEnvoiPlayerAction.put((byte)x.ordinal());
            bufferEnvoiPlayerAction.flip();
            try {
                channel.send(bufferEnvoiPlayerAction, addressServeur);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bufferEnvoiPlayerAction.clear();
        };

        JFrame j = new JFrame("XBlast 2016");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        xbc = new XBlastComponent();
        j.setSize(xbc.getPreferredSize()); 
        xbc.addKeyListener(new KeyboardEventHandler(kb, c));
        j.getContentPane().add(xbc);
        j.setVisible(true);
        xbc.requestFocusInWindow();


    }
    
    /**
     * main du joueur gerant les interaction avec le serveur
     * @param args
     * @throws IOException
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {

        channel = DatagramChannel.open(StandardProtocolFamily.INET);

        /**
         * Configuration de l'adresse du serveur
         */
        for (String string : args) {
            System.out.println(string);
        }
        if (args.length>0) {
            addressServeur = new InetSocketAddress(args[0], 2016);
        }
        else {
            addressServeur = new InetSocketAddress(DEFAULT_SERVER_ADDRESS, 2016);
        }
        System.out.println("Adresse du serveur : "+addressServeur);



        /**
         * Envoi en boucle du JOIN GAME
         */     
        channel.configureBlocking(false);
        bufferRecuSuivant = ByteBuffer.allocate(MAX_SIZE_BUFFER);

        SwingUtilities.invokeAndWait(() -> createUI());          
        SocketAddress addressTest = null;

        while (Objects.isNull(addressTest = channel.receive(bufferRecuSuivant))) {
            System.out.println("Tried to join game");
            bufferEnvoiPlayerAction.put((byte)PlayerAction.JOIN_GAME.ordinal());
            bufferEnvoiPlayerAction.flip();
            channel.send(bufferEnvoiPlayerAction, addressServeur);
            bufferEnvoiPlayerAction.clear();

            Thread.sleep(ONESEC_IN_MS);

        }

        channel.configureBlocking(true);

        /**
         * Création des GameState
         */
        
        while (Objects.nonNull(addressTest)) {
            bufferRecuSuivant.flip();

            List<Byte> gsSerialized2 = new ArrayList<>();


            if (Objects.isNull(id)) {
                int numeroID = bufferRecuSuivant.get();
                for (PlayerID playerid : PlayerID.values()) {
                    if (playerid.ordinal()==numeroID) {
                        id=playerid;
                    }
                }
                while (bufferRecuSuivant.hasRemaining()) {
                    gsSerialized2.add(bufferRecuSuivant.get());
                }
                gsClient = GameStateDeserializer.deserializeGameState(gsSerialized2);
                SwingUtilities.invokeLater(()->xbc.setGameState(gsClient, id));
            }
            else{
                if (bufferRecuSuivant.get()==id.ordinal()) {
                    while (bufferRecuSuivant.hasRemaining()) {
                        gsSerialized2.add(bufferRecuSuivant.get());
                    }
                    gsClient = GameStateDeserializer.deserializeGameState(gsSerialized2);
                    SwingUtilities.invokeLater(()->xbc.setGameState(gsClient, id));
                }    
            }

            bufferRecuSuivant.clear();
            channel.receive(bufferRecuSuivant);

        }


    }

}

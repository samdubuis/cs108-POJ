/**
 * main du serveur gerant le jeu
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerAction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.Time;

public class Main {

    private final static int DEFAULT_NUMBER_CLIENT = 4;
    
    private static final ByteBuffer bufferRecu  = ByteBuffer.allocate(1);
    private static ByteBuffer bufferEnvoi;

    private static int nombreDeClient;
    private static GameState  gs = Level.DEFAULT_LEVEL.getGameState();
    private static BoardPainter bp = Level.DEFAULT_LEVEL.getBoardpainter();

    /**
     * main du serveur gerant les interactions avec les clients et le jeu
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException{


        if (args.length>0 && Integer.parseInt(args[0])<DEFAULT_NUMBER_CLIENT) {
            nombreDeClient = Integer.parseInt(args[0]);
        }
        else {
            nombreDeClient = DEFAULT_NUMBER_CLIENT;
        }
        System.out.println("Number of expected players : "+nombreDeClient);



        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.bind(new InetSocketAddress(2016));

        channel.configureBlocking(true);


        Map<PlayerID, SocketAddress> mapIdAddress = new HashMap<>();  

        int temp = 0;
        while (temp<nombreDeClient) {
            for (PlayerID id : PlayerID.values()) {

                SocketAddress senderAddress = channel.receive(bufferRecu);
                bufferRecu.flip();
                if (bufferRecu.hasRemaining()) {
                    if (!mapIdAddress.containsValue(senderAddress) && bufferRecu.get(0)==PlayerAction.JOIN_GAME.ordinal()) {
                        mapIdAddress.putIfAbsent(id, senderAddress);
                        temp++;
                        System.out.println(id.name()+" has joined with address : "+senderAddress);
                    }

                    bufferRecu.clear();
                }                
            }
        } 

        System.out.println("All players have joined");


        channel.configureBlocking(false);

        Map<SocketAddress, PlayerID> mapAddressID = new HashMap<>();
        for (Entry<PlayerID, SocketAddress> e : mapIdAddress.entrySet()) {
            mapAddressID.put(e.getValue(), e.getKey());
        }

        System.out.println("Start");

        long T = System.nanoTime();

        while (!gs.isGameOver()) {
            bufferEnvoi = ByteBuffer.allocate(GameStateSerializer.serialize(bp, gs).size()+1);
            for (Entry<PlayerID, SocketAddress> e : mapIdAddress.entrySet()) {
                bufferEnvoi.put((byte)e.getKey().ordinal());
                for (Byte b : GameStateSerializer.serialize(bp, gs)) {
                    bufferEnvoi.put(b);
                }
                bufferEnvoi.flip();
                channel.send(bufferEnvoi, e.getValue());
                bufferEnvoi.clear();
            }


            long elapsedTime = System.nanoTime()-T;
            long nextTick = (long) gs.ticks()*Ticks.TICK_NANOSECOND_DURATION;
            if (elapsedTime<nextTick) {
                Thread.sleep((nextTick-elapsedTime)/(Time.NS_PER_S/Time.MS_PER_S));
            }

            Map<PlayerID, Optional<Direction>> mapPlayerNext = new HashMap<>();
            Set<PlayerID> setPlayerBomb = new HashSet<>();

            SocketAddress senderaddress;

            while (Objects.nonNull(senderaddress = channel.receive(bufferRecu))){
                bufferRecu.flip();
                Byte action = bufferRecu.get();

                if (action == PlayerAction.MOVE_E.ordinal()) {
                    mapPlayerNext.put(mapAddressID.get(senderaddress), Optional.of(Direction.E));
                }
                else if (action == PlayerAction.MOVE_N.ordinal()) {
                    mapPlayerNext.put(mapAddressID.get(senderaddress), Optional.of(Direction.N));
                }
                else if (action == PlayerAction.MOVE_S.ordinal()) {
                    mapPlayerNext.put(mapAddressID.get(senderaddress), Optional.of(Direction.S));
                }
                else if (action == PlayerAction.MOVE_W.ordinal()) {
                    mapPlayerNext.put(mapAddressID.get(senderaddress), Optional.of(Direction.W));
                }
                else if (action == PlayerAction.MOVE_N.ordinal()) {
                    mapPlayerNext.put(mapAddressID.get(senderaddress), Optional.of(Direction.N));
                }
                else if (action == PlayerAction.STOP.ordinal()) {
                    mapPlayerNext.put(mapAddressID.get(senderaddress), Optional.empty());
                }
                else if (action == PlayerAction.DROP_BOMB.ordinal()) {
                    setPlayerBomb.add(mapAddressID.get(senderaddress));
                }
                bufferRecu.clear();
            }                

            for (PlayerID playerID : PlayerID.values()) {
                if (!mapPlayerNext.containsKey(playerID)) {
                    mapPlayerNext.put(playerID, null);
                }
            }


            gs = gs.next(mapPlayerNext, setPlayerBomb);
        } 

        if (!gs.winner().isPresent()) {
            System.out.println("Nobody won !");
        }
        else {
            System.out.println("The winner is : " + gs.winner().get());
        }







    }

}

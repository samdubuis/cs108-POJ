package ch.epfl.xblast.testConnexion;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class TestMainServer {

    public static void main(String[] args)throws IOException {


        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        channel.bind(new InetSocketAddress(2016));

        ByteBuffer buffer = ByteBuffer.allocate(2);
        SocketAddress senderAddress1 = channel.receive(buffer);

        System.out.printf("Reçu l'octet " +  buffer.get(0) + " de : "+ senderAddress1);

        SocketAddress senderAddress2 = channel.receive(buffer);
        
        System.out.println("Reçu l'octet " + buffer.get(1)+ " de : " + senderAddress2);


    }

}

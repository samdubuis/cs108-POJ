package ch.epfl.xblast.testConnexion;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class TestMainClient2 {

    public static void main(String[] args) throws IOException{
        DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET);
        SocketAddress address = new InetSocketAddress("player2", 2016);

        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte)4);
        buffer.flip();

        channel.send(buffer, address);

    }

}

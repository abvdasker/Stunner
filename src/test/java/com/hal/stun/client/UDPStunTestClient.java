import com.hal.stun.client;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;

import java.net.DatagramPacket;

public class UDPStunTestClient implements StunTestClient {
  public byte[] send(byte[] data) throws IOException {
    DatagramSocket sendSocket = new DatagramSocket(StunMessageSocket.DEFAULT_PORT + 1);
    DatagramPacket request = new DatagramPacket(data, data.length, serverAddress);
    socket.send(request);

    byte[] responseData = new byte[StunMessageSocket.MAX_PACKET_SIZE_BYTES];
    DatagramPacket response = new DatagramPacket(responseData, responseData.length);
    socket.receive(response);

    return UDPStunMessageSocket.getDataFromPacket(response);
  }
}

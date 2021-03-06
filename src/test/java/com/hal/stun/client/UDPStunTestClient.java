package com.hal.stun.client;

import com.hal.stun.socket.StunMessageSocket;
import com.hal.stun.socket.UDPStunMessageSocket;

import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.SocketException;

public class UDPStunTestClient extends StunTestClient {

    private DatagramSocket socket;

    public UDPStunTestClient(InetSocketAddress serverAddress) throws SocketException {
        super(serverAddress);
        socket = new DatagramSocket(StunMessageSocket.DEFAULT_PORT + 1);
    }

    public byte[] send(byte[] data) throws IOException, SocketTimeoutException {
        DatagramPacket request = new DatagramPacket(data, data.length, serverAddress);
        socket.send(request);

        byte[] responseData = new byte[StunMessageSocket.MAX_PACKET_SIZE_BYTES];
        DatagramPacket response = new DatagramPacket(responseData, responseData.length);
        socket.receive(response);

        return UDPStunMessageSocket.getDataFromPacket(response);
    }

    public void close() {
        socket.disconnect();
        socket.close();
    }
}

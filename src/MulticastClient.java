import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class MulticastClient {
	public String ip;
	public int port;

	public MulticastClient(int port, String ip) {
		this.port = port;
		this.ip = ip;
	}

	public void sendMessage(String message) throws Exception {
		String[] msg = message.split("\\ ");
		msg[msg.length - 1] = "$" + msg[msg.length - 1];
		for (int i = 0; i < msg.length; i++) {
			msg[i] = msg[i] + "|" + i;
			DatagramSocket udpSocket = new DatagramSocket();
			InetAddress mcIPAddress = InetAddress.getByName(ip);
			byte[] m = msg[i].getBytes();
			DatagramPacket packet = new DatagramPacket(m, m.length);
			packet.setAddress(mcIPAddress);
			packet.setPort(port);
			udpSocket.send(packet);
			udpSocket.close();
		}
		System.out.println("\""+ip + "\": sent message: \n["+message+"] at port: \""+port+"\"\n" );
	}
}
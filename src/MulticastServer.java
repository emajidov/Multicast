import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.net.InetAddress;

public class MulticastServer extends Thread {
	public int port;
	public String ip;
	ArrayList<Message> list = new ArrayList<Message>();

	MulticastSocket mcSocket = null;
	InetAddress mcIPAddress = null;
	
	public MulticastServer(int port, String ip) throws IOException {
		this.port = port;
		this.ip = ip;
		mcIPAddress = InetAddress.getByName(ip);
		mcSocket = new MulticastSocket(port);
		mcSocket.joinGroup(mcIPAddress);
		System.out.println("Multicast Server is running...");
	}

	public Message getMessage() {
		String[] ms = new String[2];
		try {
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			mcSocket.receive(packet);
			String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
			ms = msg.split("\\|");
		} catch (Exception e) {

		}
		return new Message(Integer.parseInt(ms[1]), ms[0]);
	}

	public boolean check() {
		int l = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).m.contains("@")) {
				String[] c = list.get(i).m.split("\\|");
				l = Integer.parseInt(c[1]);
				
			}
		}
		return (l + 1 == list.size());
	}

	public void print(ArrayList<Message> list) {
		list.sort(new Compare());
		String msg = "";
		if (check()) {
			for (int i = 0; i < list.size(); i++) {
				msg += list.get(i).m + " ";
			}
			for(int i  =0; i < msg.length();i++){ 
				if(msg.charAt(i)=='$') {
				 msg =  msg.substring(0, i) + msg.substring(i+1,msg.length());
				}
			}
			System.out.print(msg);
		}
		list.clear();
	}

	@Override
	public void run() {
		while (true) {
			list.add(getMessage());
			print(list);
		}		
	}	
	@Override
	public void interrupt() {
		try {
			mcSocket.leaveGroup(mcIPAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mcSocket.close();
		super.interrupt();
	}
	class Compare implements Comparator<Message> {
		public Compare() {
		}
		@Override
		public int compare(Message m1, Message m2) {
			if (m1.id < m2.id) {
				return 1;
			} else {
				return -1;
			}
		}
	}
}
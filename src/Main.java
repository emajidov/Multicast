import java.util.Scanner;

public class Main {
	public static int port = 8888;
	public static String ip = "230.2.2.3";

	public static void main(String[] args) throws Exception {
		MulticastServer ms = new MulticastServer(port, ip);
		MulticastClient mc = new MulticastClient(port, ip);
		Scanner sc = new Scanner(System.in);
		Thread th = new Thread(ms);
		th.start();
		
		System.out.print("Enter message: ");
		mc.sendMessage(sc.nextLine());
		while (sc.hasNext()) {
			mc.sendMessage(sc.nextLine());
		    System.out.println();
		}
	}
}
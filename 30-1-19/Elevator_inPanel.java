
import java.util.Scanner;
import java.io.*;
import java.net.*;



public class Elevator_inPanel {
	
	   DatagramPacket sendPacket, receivePacket;
	   DatagramSocket sendReceiveSocket;
	   
	   public Elevator_inPanel(){
		   try {
		        // Construct a datagram socket and bind it to any available 
		        // port on the local host machine. This socket will be used to
		        // send and receive UDP Datagram packets.
		        sendReceiveSocket = new DatagramSocket();
		     } catch (SocketException se) {   // Can't create the socket.
		        se.printStackTrace();
		        System.exit(1);
		     }
	   }

	@Override
	public String toString() {
		return "Elevator_Panel [sendPacket=" + sendPacket + ", receivePacket=" + receivePacket + ", sendReceiveSocket="
				+ sendReceiveSocket + "]";
	}
	
	public static void print() {
		for( ; ;) {
			System.out.println("Please select a floor between 1-10");
			Scanner in = new Scanner(System.in);
			int reqestedFloorNumber = in.nextInt();
			if(!(reqestedFloorNumber < 10)) {
				System.out.println("Please enter a valid input");
			}
		}
	}
	
	public static void main(String[] args) {
		print();
	}

}

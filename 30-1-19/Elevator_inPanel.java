
import java.util.Calendar;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;


public class Elevator_inPanel {
	
	   static DatagramPacket sendPacket;
	   static DatagramSocket sendSocket;
	   
	   public Elevator_inPanel(){
		   try {
		        // Construct a datagram socket and bind it to any available 
		        // port on the local host machine. This socket will be used to
		        // send and receive UDP Datagram packets.
		        sendSocket = new DatagramSocket();
		     } catch (SocketException se) {   // Can't create the socket.
		        se.printStackTrace();
		        System.exit(1);
		     }
	   }

	@Override
	public String toString() {
		return "Elevator_Panel [sendPacket=" + sendPacket + ", sendSocket="
				+ sendSocket + "]";
	}
	
	public static String generateDate() { 
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return sdf.format(cal.getTime());
	}
	
	public static void sendDestinationRequest(int packetType,int floorNum, int UpDown,int ElevID, String SystemTime)
	{
	   // Prepare a DatagramPacket and send it via sendReceiveSocket
	   // to port 5000 on the destination host.

	 
	   // Java stores characters as 16-bit Unicode values, but 
	   // DatagramPack;ets store their messages as byte arrays.
	   // Convert the String into bytes according to the platform's 
	   // default character encoding, storing the result into a new 
	   // byte array.
	   int destOrArrival = packetType;
	   int floorNo = floorNum;
	   int UpOrDown = UpDown;
	   int ID = ElevID;
	   String Time = SystemTime;

	   byte a[] = new byte[] {(byte)1,(byte)0,(byte)destOrArrival,(byte)0,(byte)floorNo,(byte)0,(byte)UpOrDown,(byte)0,(byte)ID,(byte)0};
	   ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
	  
	  
	   try {
		    outputStream.write(a);
		    outputStream.write(Time.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	   byte msg[] = outputStream.toByteArray( );
	   
	// Construct a datagram packet that is to be sent to a specified port 
	   // on a specified host.
	   // The arguments are:
	   //  msg - the message contained in the packet (the byte array)
	   //  msg.length - the length of the byte array
	   //  InetAddress.getLocalHost() - the Internet address of the 
	   //     destination host.
	   //     In this example, we want the destination to be the same as
	   //     the source (i.e., we want to run the client and server on the
	   //     same computer). InetAddress.getLocalHost() returns the Internet
	   //     address of the local host.
	   //  5000 - the destination port number on the destination host.
	   try {
	      sendPacket = new DatagramPacket(msg, msg.length,
	                                      InetAddress.getLocalHost(), 23);
	   } catch (UnknownHostException e) {
	      e.printStackTrace();
	      System.exit(1);
	   }
	   System.out.println("Client: Sending packet:");
	   System.out.println("To host: " + sendPacket.getAddress());
	   System.out.println("Destination host port: " + sendPacket.getPort());
	   
	   try {
	       sendSocket.send(sendPacket);
	    } catch (IOException e) {
	       e.printStackTrace();
	       System.exit(1);
	    }

	    System.out.println("Client: Packet sent.\n");
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

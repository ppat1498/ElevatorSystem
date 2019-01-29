package milestone_1;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Floor {
	   DatagramPacket sendPacket, receivePacket;
	   static DatagramSocket sendSocket;
	   static DatagramSocket receiveSocket;


public Floor()
{
   try {
      // Construct a datagram socket and bind it to any available 
      // port on the local host machine. This socket will be used to
      // send UDP Datagram packets.
      sendSocket = new DatagramSocket();

      // Construct a datagram socket and bind it to port 5000 
      // on the local host machine. This socket will be used to
      // receive UDP Datagram packets.
      receiveSocket = new DatagramSocket(69);
      
      // to test socket timeout (2 seconds)
      //receiveSocket.setSoTimeout(2000);
   } catch (SocketException se) {
      se.printStackTrace();
      System.exit(1);
   } 
}
public void receiveAndEcho(int packetType,int floorNum, int UpDown,int ElevID, int SystemTime)
{
	 

   // Construct a DatagramPacket for receiving packets up 
   // to 100 bytes long (the length of the byte array).

   byte data[] = new byte[20];
   receivePacket = new DatagramPacket(data, data.length);
   System.out.println("Server: Waiting for Packet.\n");

   // Block until a datagram packet is received from receiveSocket.
   try {        
      System.out.println("Waiting..."); // so we know we're waiting
      receiveSocket.receive(receivePacket);
   } catch (IOException e) {
      System.out.print("IO Exception: likely:");
      System.out.println("Receive Socket Timed Out.\n" + e);
      e.printStackTrace();
      System.exit(1);
   }
   
// Process the received datagram.
   System.out.println("Server: Packet received:");
   System.out.println("From host: " + receivePacket.getAddress());
   System.out.println("Host port: " + receivePacket.getPort());
   System.out.println("received\n");
   
   int destOrArrival = packetType;
   int floorNo = floorNum;
   int UpOrDown = UpDown;
   int ID = ElevID;
   int Time = SystemTime;

   
   byte a[] = new byte[] {(byte)0,(byte)0,(byte)destOrArrival,(byte)0,(byte)floorNo,(byte)0,(byte)UpOrDown,(byte)0,(byte)ID,(byte)0,(byte)Time};
// Slow things down (wait 500 ms)
   try {
       Thread.sleep(500);
   } catch (InterruptedException e ) {
       e.printStackTrace();
       System.exit(1);
   }
   		   sendPacket = new DatagramPacket(a, a.length,receivePacket.getAddress(), 23);

	System.out.println( "Server: Sending packet:");
	System.out.println("To host: " + sendPacket.getAddress());
	System.out.println("Destination host port: " + sendPacket.getPort());
	
	 try {
         sendSocket.send(sendPacket);
      } catch (IOException e) {
         e.printStackTrace();
         System.exit(1);
      }

      System.out.println("Server: packet sent");

      // We're finished, so close the sockets.
   }
public static void main( String args[] )
{
   Floor c = new Floor();
   int i = 1;
   while(i <= 11) {
   c.receiveAndEcho(i, i, i, i, i);
   i++;
   }
   sendSocket.close();
   receiveSocket.close();
}
}

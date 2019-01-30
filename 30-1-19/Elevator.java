package milestone_1;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.math.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Elevator {

	 DatagramPacket sendPacket, receivePacket;
	 static DatagramSocket sendReceiveSocket;
	 int currentFloor;
	 int nextFloor;
	 int currentDirection;
	 int nextDirection;
	 Boolean pendingRequest;
	 
	
	 public Elevator()
	   {
	      try {
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send and receive UDP Datagram packets.
	         sendReceiveSocket = new DatagramSocket(23);
	      } catch (SocketException se) {   // Can't create the socket.
	         se.printStackTrace();
	         System.exit(1);
	      }
	   }


public void sendDestinationRequest(int packetType,int floorNum, int UpDown,int ElevID, String SystemTime)
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
       sendReceiveSocket.send(sendPacket);
    } catch (IOException e) {
       e.printStackTrace();
       System.exit(1);
    }

    System.out.println("Client: Packet sent.\n");
}

public int[] sendArrivalRequest(int packetType,int ElevID, int SystemTime) {
	// Prepare a DatagramPacket and send it via sendReceiveSocket
	   // to port 5000 on the destination host.

	 
	   // Java stores characters as 16-bit Unicode values, but 
	   // DatagramPack;ets store their messages as byte arrays.
	   // Convert the String into bytes according to the platform's 
	   // default character encoding, storing the result into a new 
	   // byte array.
	   int destOrArrival = packetType;
	   int ID = ElevID;
	   int Time = SystemTime;

	   byte a[] = new byte[] {(byte)1,(byte)0,(byte)destOrArrival,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)ID,(byte)0,(byte)Time};
	   ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
	  
	  
	   try {
		    outputStream.write(a);
			
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
	       sendReceiveSocket.send(sendPacket);
	    } catch (IOException e) {
	       e.printStackTrace();
	       System.exit(1);
	    }

	    System.out.println("Client: Packet sent.\n");
	    
	    byte data[] = new byte[19];
	      receivePacket = new DatagramPacket(data, data.length);

	      try {
	         // Block until a datagram is received via sendReceiveSocket.  
	         sendReceiveSocket.receive(receivePacket);
	      } catch(IOException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }

	      // Process the received datagram.
	      System.out.println("Client: Packet received:");
	      System.out.println("From host: " + receivePacket.getAddress());
	      System.out.println("Host port: " + receivePacket.getPort());
	      
	     int ar[] = new int[2];
	     ar[0] = data[4];
	     ar[1] = data[6];
	     return ar;
	    		
	 
	
}
public String generateDate() { 
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	return sdf.format(cal.getTime());
}


public void runElevator() {
	
	int counts = 0;
	for(;;) { 
		int floorDifference = Math.abs(this.currentFloor - this.nextFloor);
		if(this.pendingRequest) {
			this.sendDestinationRequest(1, floorNum, this.currentDirection, 1, SystemTime);
		}
		else if(counts%(10+floorDifference) == 0){
			this.sendArrivalRequest(1, 1, SystemTime);
			
		}
		else {
			Thread.sleep(1000);
			counts++;
		}
	}
}
}


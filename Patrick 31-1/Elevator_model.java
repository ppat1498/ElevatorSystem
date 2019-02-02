
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Elevator_model {		//this class creates a new elevator
	   DatagramPacket sendPacket, receivePacket;
	   static DatagramSocket sendSocket;
	   static DatagramSocket receiveSocket;
	   static int currentFloor;
	   static int nextFloor;

public Elevator_model(int floor)
{
	setCurrentFloor(floor);
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
public void receiveAndReply()			//this receives the message and sends a reply after motor has run
{
	 

   // Construct a DatagramPacket for receiving packets up 
   // to 100 bytes long (the length of the byte array).

   byte data[] = new byte[30];
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
   
   int destOrArrival = data[2];
   setNextFloor(data[4]);
   //int UpOrDown = data[6];
   //int ID = data[8];
   byte[] arr = Arrays.copyOfRange(data, 10, 18);
   String received = new String(arr,0,arr.length);
   String str = "Idle";
   if(destOrArrival == 0) {
	   System.out.println( "invalid destination packet");
   }
   
   else if(destOrArrival == 1){
		   if(data[6] == 1) {
			   str = "going up to " + getNextFloor();
		   }
		   else if(data[6] == 0){
			   str = "going down to "+ getNextFloor();
		   }
		   System.out.println( "@ "+ received + " The Elevator is "+ str);
		   runMotor(getCurrentFloor(),getNextFloor());
		   setCurrentFloor(getNextFloor());
		   System.out.println( "@ "+ generateDate() + " The Elevator is now on floor " + getCurrentFloor());
		   // Slow things down (wait 10 ms)
		   
		   try {
		       Thread.sleep(10);
		   } catch (InterruptedException e ) {
		       e.printStackTrace();
		       System.exit(1);
		   }
		    sendPacket = new DatagramPacket(data, data.length,receivePacket.getAddress(), 23);
		
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
}

public void runMotor(int start,int end) {		//this method is used to run the motor to the destination floor with the appropriate wait times
	int time = 4000;
	if(start == end) {
		System.out.println("Opening doors...");
		try {
		       Thread.sleep(time);
		   } catch (InterruptedException e ) {
		       e.printStackTrace();
		       System.exit(1);
		   }
	}
	
	
	else {
		System.out.println("closing doors...");
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("motor running...");
		time = 1000 * (Math.abs(start - end));
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Opening doors...");
		time = 4000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		} 
	}
}
public static int getCurrentFloor() {
	return currentFloor;
}

public static void setCurrentFloor(int floor) {
	currentFloor = floor;
}

public static int getNextFloor() {
	return nextFloor;
}

public static void setNextFloor(int floor) {
	nextFloor = floor;
}

public static String generateDate() { 
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	return sdf.format(cal.getTime());
}

public static void main( String args[] )
{
   Elevator_model c = new Elevator_model(1);
   int i = 1;
   while(i <= 100) {
	   c.receiveAndReply();
	   i++;
   }
   sendSocket.close();
   receiveSocket.close();
}
}

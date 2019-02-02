package milestone_1;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Scheduler_Queue {

   DatagramPacket sendPacket, receivePacket;
   static DatagramSocket sendSocket;
   static DatagramSocket receiveSocket;
   PriorityQueue<Integer> elevatorQ;
   static int currentFloor;
   
   public Scheduler_Queue(int start)
   {
	   elevatorQ = new PriorityQueue<Integer>();
	   setCurrentFloor(start);
      try {
         // Construct a datagram socket and bind it to any available 
         // port on the local host machine. This socket will be used to
         // send UDP Datagram packets.
         sendSocket = new DatagramSocket();

         // Construct a datagram socket and bind it to port 5000 
         // on the local host machine. This socket will be used to
         // receive UDP Datagram packets.
         receiveSocket = new DatagramSocket(23);
         
         // to test socket timeout (2 seconds)
         //receiveSocket.setSoTimeout(2000);
      } catch (SocketException se) {
         se.printStackTrace();
         System.exit(1);
      } 
   }

   public void receive()
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
      int len = receivePacket.getLength();
      System.out.println("Length: " + len);
      System.out.print("Containing: " );
      System.out.println(Arrays.toString(data) + "\n");
      // Form a String from the byte array.
      //String received = new String(data,0,len);   
      //System.out.println(received + "\n");
      
      int destOrArrival = data[2];
      int floorNo = data[4];
      int UpOrDown = data[6];
      //int ID = data[8];
      //byte[] arr = Arrays.copyOfRange(data, 10, 17);
      //String received = new String(arr,0,arr.length);
      
      if (destOrArrival == 0) {
    	  ScheduleRequest(floorNo,UpOrDown);
      }
      
      else if(destOrArrival == 1) {
    	  dispatchRequest();
      }
      
   }

   private void dispatchRequest() {
	   if (!(elevatorQ.isEmpty())) {
		int destination = elevatorQ.poll();
		int direction = 2;
		if(getCurrentFloor() > destination) {direction = 0;}
		if(getCurrentFloor() < destination) {direction = 1;}
		setCurrentFloor(destination);
		byte a[] = new byte[] { (byte) 2, (byte) 0, (byte) 1, (byte) 0, (byte) destination, (byte) 0, (byte)direction,
				(byte) 0, (byte) 1, (byte) 0 };
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String time = generateDate();
		try {
			outputStream.write(a);
			outputStream.write(time.getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte msg[] = outputStream.toByteArray();
		sendPacket = new DatagramPacket(msg, msg.length, receivePacket.getAddress(), 69);
		System.out.println("Server: Sending packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
		System.out.print("Containing: ");
		System.out.println(Arrays.toString(msg) + "\n");
		//System.out.println(new String(sendPacket.getData(),0,len));
		// or (as we should be sending back the same thing)
		// System.out.println(received); 
		// Send the datagram packet to the client via the send socket. 
		try {
			sendSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server: packet sent");
	}
		
		// We're finished, so close the sockets.
}

private void ScheduleRequest(int floor, int direction) {
	if(elevatorQ.isEmpty()) {
		elevatorQ.add(floor);
		dispatchRequest();
	}
	
}
public static String generateDate() { 
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
	return sdf.format(cal.getTime());
}

public static int getCurrentFloor() {
	return currentFloor;
}

public static void setCurrentFloor(int floor) {
	currentFloor = floor;
}

public static void main( String args[] )
   {
      Scheduler_Queue c = new Scheduler_Queue(0);
      int i = 1;
      while(i <= 11) {
      c.receive();
      i++;
      }
      sendSocket.close();
      receiveSocket.close();
      System.out.println("Program finished");
   }
}
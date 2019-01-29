//scheduler for the elevator system.
//uses code from simple echo server for sysc 3303
//L4G2 milestone 1 27/1/2019

import java.io.*;
import java.net.*;

public class Scheduler {
	
	private ElevatorQueue eQueue1;
	public enum Direction{ //everything that the elevator should do
		UP, DOWN, IDLE, INACTIVE;
	}
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendSocket, receiveSocket;
	
	public Scheduler()
	   {
		  eQueue1 = new ElevatorQueue();
	      try {
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send UDP Datagram packets.
	         sendSocket = new DatagramSocket();

	         // Construct a datagram socket and bind it to port 5000 
	         // on the local host machine. This socket will be used to
	         // receive UDP Datagram packets.
	         receiveSocket = new DatagramSocket(5000);
	         
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

	      byte data[] = new byte[100];
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
	      if(data[6] == (byte)1) {
	      	eQueue1.setDirection(ElevatorQueue.Direction.UP);
	      }
	      else if(data[6] == (byte)0) {
	    	eQueue1.setDirection(ElevatorQueue.Direction.DOWN); 
	      }
	      else if(data[6] == (byte)2) {
		    eQueue1.setDirection(ElevatorQueue.Direction.IDLE); 
	      }
	       else {
	    	eQueue1.setDirection(ElevatorQueue.Direction.INACTIVE);
	       }
	      }
	      
	      // TODO schedule packet
	      
	      
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

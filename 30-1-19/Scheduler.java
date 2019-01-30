//scheduler for the elevator system.
//uses code from simple echo server for sysc 3303
//L4G2 milestone 1 27/1/2019

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

import java.net.*;

public class Scheduler {
	
	private ElevatorQueue eQueue1;
	private ElevatorQueue.Direction CurrentDirection = ElevatorQueue.getDirection();
	private static Direction direction = Direction.IDLE;
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendSocket, receiveSocket;
	public static final int MAX_NUM_OF_FLOORS = 10;
	private int floor = 1;
	
	public Scheduler()
	   {
		  eQueue1 = new ElevatorQueue();
		  this.CurrentDirection = ElevatorQueue.getDirection();
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
	      
	public enum Direction{ //everything that the elevator should do
		UP, DOWN, IDLE, INACTIVE;
	}
	
	
	
		
	private PriorityQueue<Integer> requests = new PriorityQueue<Integer>(MAX_NUM_OF_FLOORS, new Comparator<Integer>() {
		public int compare(Integer a, Integer b) {
			if(direction == direction.DOWN) {
				if(a<b) {
					return 1;
				}
				if(a>b) { 
					return -1;
				}
			} else if(direction == direction.UP){
				if(a<b) {
					return -1;
				}
				if(a>b) { 
					return 1;
				}
			}
			return 0;
		}
	});
	
	public void removerequests(int floor) {
		if(requests.contains(floor)) {
			requests.remove();
		}
	}
	
	public void addrequests(int floor) {
		if(!requests.contains(floor)) {
			if(requests.isEmpty()) {
				if(this.floor < floor) {
					setDirection(direction.UP);
				}
			}
			requests.add(floor);
		}
	}
	
	public void getPassengers() {
	}
	
	//GETTERS
	
	public boolean isIdle() {
		return direction == direction.IDLE;
	}
	
	public int getFloor() {
		return floor;
	}
	
	public PriorityQueue<Integer> getrequestss(){
		return requests;
	}
	
	public static Direction getDirection() {
		return direction;
	}
	
	//SETTERS
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public String toString() {
		return "Direction: " + direction + "\nFloor: " + floor + "\nrequestsed Floors: " + requests;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

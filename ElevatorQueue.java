import java.util.Comparator;
import java.util.PriorityQueue;


public class ElevatorQueue {
	
	public enum Direction{ //everything that the elevator should do
		UP, DOWN, IDLE, INACTIVE;
	}
	
	private Direction direction = Direction.IDLE;
	private int floor = 1;
	public static final int MAX_NUM_OF_FLOORS = 10;
	
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
	
	public Direction getDirection() {
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
		

	}


}
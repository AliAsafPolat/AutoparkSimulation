
public class ParkRecord {
	private Time enterTime;
	private Time exitTime;
	private Vehicle vehicle;
	
	public ParkRecord (Time x,Time y,Vehicle z){
		this.enterTime=x;
		this.exitTime=y;
		this.vehicle=z;
	}
	public ParkRecord(Time x,Vehicle z) {
		this.enterTime=x;
		this.vehicle=z;
	}
	
	public int getParkingDuration() {
		return	enterTime.getDifference(exitTime);
	}
	
	public Time getEnterTime() {
		return enterTime;
	}
	
	public void setEnterTime(Time enterTime) {
		this.enterTime = enterTime;
	}
	
	public Time getExitTime() {
		return exitTime;
	}
	
	public void setExitTime(Time exitTime) {
		this.exitTime = exitTime;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


}


public class Time {
	private int hour;
	private int minute;
	
	public Time(int h,int m) {
		this.hour=h;
		this.minute=m;
	}
	
	
	public int getDifference(Time t) {
		int x=t.getHour()-this.hour;
		return x;
	
	}
	
	public boolean isAfterThan(Time x) {
		if(this.getDifference(x)<0)
			return true;
		return false;
	}
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public int getMinute() {
		return minute;
	}
		
	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getTimeString() {
		return String.valueOf(hour)+":"+String.valueOf(minute);
	}
}

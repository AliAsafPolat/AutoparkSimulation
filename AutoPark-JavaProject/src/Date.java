import java.util.Calendar;

public class Date {
	private int day;
	private int month;
	private int year;
	
	public Date(int x,int y,int z) {
		this.day=x;
		this.month=y;
		this.year=z;
	}	
	
	public boolean isAfterThan(Date d) {
		if(d.getYear()>this.year)
			return true;
		if(d.getYear()==this.year&&d.getMonth()>this.month)
			return true;
		if(d.getYear()==this.year&&d.getMonth()==this.month&&d.getDay()>this.day)
			return true;
		return false;
	}
	
	public boolean isBeforethan(Date d) {
		if(!this.isAfterThan(d)&&!this.isEqualWith(d))
			return true;
		
		return false;
		
	}
	public boolean isEqualWith(Date d) {
		if(d==null || d.getClass()!=this.getClass())
			return false;
		
		if(d.getYear()==this.year&&d.getMonth()==this.month&&d.getDay()==this.day)
			return true;
		return false;
	}
	
	public static Date getToday() {
		Calendar time = Calendar.getInstance();
		int gun=time.get(Calendar.DAY_OF_MONTH);
		int ay=time.get(Calendar.MONTH)+1;
		int yil=time.get(Calendar.YEAR);
		
		return new Date(gun,ay,yil);
	}
	
	public String toString() {
		return day+" "+month+" "+year;
	}
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}

	public String getStringVal() {
		return String.valueOf(day)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
	}
	
}

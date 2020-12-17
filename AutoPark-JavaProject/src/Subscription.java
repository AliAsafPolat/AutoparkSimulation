
public class Subscription {
	
	private Date begin;
	private Date end;
	private SubscribedVehicle vehicle;
	
	public Subscription(Date begin,Date end,String plate) {
		this.begin=begin;
		this.end=end;
		vehicle=new SubscribedVehicle(plate);
			vehicle.setSubscription(this);
	}
	
	public Subscription(Date begin,Date end) {
		this.begin=begin;
		this.end=end;
	}
	
	public boolean isValid() {
		Date x=Date.getToday();
		if(x.isBeforethan(end))
			return false;
		return true;
	}
	
	public Date getBegin() {
		return begin;
	}
	
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public SubscribedVehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(SubscribedVehicle vehicle) {
		this.vehicle = vehicle;
		if(this.isValid())							//Eðer bu subscription geçerliyse aynýsýný arabaya da ver.
			vehicle.setSubscription(this);   
	}
	
}

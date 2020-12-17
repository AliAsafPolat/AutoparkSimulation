
public class RegularVehicle implements Vehicle {
	private String plate;
	
	public RegularVehicle(String p) {
		this.plate=p;	
	}

	@Override
	public String getPlate() {
		return this.plate;
	}

	@Override
	public Subscription getSubscription() {		
		return null;
	}

	@Override
	public boolean isSpecial() {
		return false;
	}

}

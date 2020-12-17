import java.util.ArrayList;

public class SubscribedVehicle implements Vehicle {
	private String plate;
	private Subscription subscription;
		
	public SubscribedVehicle(String plate,Subscription s) {
		this.plate=plate;
		this.subscription=s;
	}
	
	public SubscribedVehicle(String p) {
		this.plate=p;
	}

	@Override
	public String getPlate() {
		return this.plate;
	}

	@Override
	public Subscription getSubscription() {
		return this.subscription;
	}

	@Override
	public boolean isSpecial() {
		return false;
	}


	public void setPlate(String plate) {
		this.plate = plate;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	public String toString() {
		return this.plate;
	}
	
}


public class OfficialVehicle implements Vehicle {
	private String plate;

	public OfficialVehicle(String s) {
		this.plate=s;
	}

	@Override
	public String getPlate() {
		return plate;
	}

	@Override
	public Subscription getSubscription() {
		return null;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

}

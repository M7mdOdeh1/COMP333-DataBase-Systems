package application;

public class DeliveryCompany {
	private int deliveryID;
	private String deliveryName;
	private String location;
	private int noOfVehicles;

	public DeliveryCompany(int deliveryID, String deliveryName, String location, int noOfVehicles) {
		this.deliveryID = deliveryID;
		this.deliveryName = deliveryName;
		this.location = location;
		this.noOfVehicles = noOfVehicles;
	}

	public int getDeliveryID() {
		return deliveryID;
	}

	public void setDeliveryID(int deliveryID) {
		this.deliveryID = deliveryID;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNoOfVehicles() {
		return String.valueOf(noOfVehicles);
	}

	public void setNoOfVehicles(int noOfVehicles) {
		this.noOfVehicles = noOfVehicles;
	}
}

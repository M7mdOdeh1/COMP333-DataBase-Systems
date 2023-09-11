package application;

public class Warehouse {
	private int warehouseID;
	private String warehouseName;
	private String warehouseLocation;

	public Warehouse(int warehouseID, String warehouseName, String warehouseLocation) {
		this.warehouseID = warehouseID;
		this.warehouseName = warehouseName;
		this.warehouseLocation = warehouseLocation;
	}

	public int getWarehouseID() {
		return warehouseID;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public String getWarehouseLocation() {
		return warehouseLocation;
	}

	public void setWarehouseID(int warehouseID) {
		this.warehouseID = warehouseID;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}
}

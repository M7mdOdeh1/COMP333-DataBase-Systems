package application;

public class Item {
	private int itemID;
	private String itemName;
	private String brand;
	private String color;
	private String category;
	private double size;
	private double price;
	private int quantity;
	private int warehouseID;

	public Item(int itemID, String itemName, String brand, String color, String category, double size, double price,
			int quantity, int warehouseID) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.brand = brand;
		this.color = color;
		this.category = category;
		this.size = size;
		this.price = price;
		this.quantity = quantity;
		this.warehouseID = warehouseID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSize() {
		return String.valueOf(size);
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getPrice() {
		return String.valueOf(price);
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getQuantity() {
		return String.valueOf(quantity);
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getWarehouseID() {
		return String.valueOf(warehouseID);
	}

	public void setWarehouseID(int warehouseID) {
		this.warehouseID = warehouseID;
	}

	@Override
	public String toString() {
		return "itemName: " + itemName + "\nbrand: " + brand + "\ncolor: " + color
				+ "\ncategory: " + category + "\nsize: " + size + "\nprice: " + price;
	}
	
	
}

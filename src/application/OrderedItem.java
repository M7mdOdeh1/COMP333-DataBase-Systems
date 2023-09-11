package application;

public class OrderedItem {
	private int orderID;
	private int itemID;
	private int quantity;
	private double itemPrice;
	private double finalPrice;

	public OrderedItem(int orderID, int itemID, int quantity, double itemPrice, double finalPrice) {
		super();
		this.orderID = orderID;
		this.itemID = itemID;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		this.finalPrice = finalPrice;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
		return "OrderedItem [orderID=" + orderID + ", itemID=" + itemID + ", quantity=" + quantity + ", itemPrice="
				+ itemPrice + ", finalPrice=" + finalPrice + "]";
	}
	
	

	
}

package application;

public class Cart {
	private int userNo;
	private int noOfItems;
	private double totalPrice;

	public Cart(int userNo, int noOfItems, double totalPrice) {
		this.userNo = userNo;
		this.noOfItems = noOfItems;
		this.totalPrice = totalPrice;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}

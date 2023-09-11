package application;

import java.util.Date;

public class Orders {
	private int orderID;
	private Date confirmationDate;
	private double totalPrice;
	private int userNo;
	private int deliveryID;
    
	public Orders(int orderID, Date confirmationDate, double totalPrice, int userNo, int deliveryID) {
		super();
		this.orderID = orderID;
		this.confirmationDate = confirmationDate;
		this.totalPrice = totalPrice;
		this.userNo = userNo;
		this.deliveryID = deliveryID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public String getTotalPrice() {
		return String.valueOf(totalPrice);
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUserNo() {
		return  String.valueOf(userNo);
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getDeliveryID() {
		return String.valueOf(deliveryID);
	}

	public void setDeliveryID(int deliveryID) {
		this.deliveryID = deliveryID;
	}

	@Override
	public String toString() {
		return "Orders [orderID=" + orderID + ", confirmationDate=" + confirmationDate + ", totalPrice=" + totalPrice
				+ ", userNo=" + userNo + ", deliveryID=" + deliveryID + "]";
	}
    
	
	
    
}

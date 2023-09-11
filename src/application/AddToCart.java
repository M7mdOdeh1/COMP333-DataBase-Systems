package application;

public class AddToCart {
    private int userNo;
    private int itemID;
    private int quantity;
    private double itemPrice;
    private double finalPrice;
    
    
    public AddToCart(int userNo, int itemID, int quantity, double itemPrice,double finalPrice) {
        this.userNo = userNo;
        this.itemID = itemID;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.finalPrice=finalPrice;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
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
		return "itemID: " + itemID + "\nquantity: " + quantity + "\nitemPrice: "
				+ itemPrice + "\nfinalPrice: " + finalPrice;
	}
    
    
}

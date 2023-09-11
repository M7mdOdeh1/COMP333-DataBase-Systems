package application;

public class Phone {
	private String userPhone;
	private int userNo;

	public Phone(String userPhone, int userNo) {
		this.userPhone = userPhone;
		this.userNo = userNo;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
}
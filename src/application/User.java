package application;

public class User {
	
	private int userNo;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String permission;
    private String color;


	public User(int userNo,String name, String email, String password, String address, String phone, String permission) {
        this.userNo=userNo;
    	this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.permission= permission;
    }
    
    
    
    
    public int getUserNo() {
        return userNo;
    }
    public void setUserNo(int userNo) {
    	this.userNo=userNo;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    
    
    
    public String getColor() {
		return color;
	}




	public void setColor(String color) {
		this.color = color;
	}




	@Override
   	public String toString() {
   		return "User [userNo=" + userNo + ", name=" + name + ", email=" + email + ", password=" + password
   				+ ", address=" + address + ", phone=" + phone + ", permission=" + permission + "]";
   	}

}
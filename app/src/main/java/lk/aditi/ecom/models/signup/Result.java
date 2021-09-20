package lk.aditi.ecom.models.signup;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("user_name")
	private String userName;

	@SerializedName("lon")
	private String lon;

	@SerializedName("expired_at")
	private String expiredAt;

	@SerializedName("type")
	private String type;

	@SerializedName("ios_register_id")
	private Object iosRegisterId;

	@SerializedName("password")
	private String password;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("lat")
	private String lat;

	@SerializedName("image")
	private String image;

	@SerializedName("business_name")
	private String businessName;

	@SerializedName("address")
	private String address;

	@SerializedName("last_login")
	private String lastLogin;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("otp")
	private String otp;

	@SerializedName("register_id")
	private String registerId;

	@SerializedName("token")
	private String token;

	@SerializedName("social_id")
	private String socialId;

	@SerializedName("check_otp")
	private String checkOtp;

	@SerializedName("dob")
	private String dob;

	@SerializedName("delivery_info")
	private String deliveryInfo;

	@SerializedName("location")
	private String location;

	@SerializedName("status")
	private String status;

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setLon(String lon){
		this.lon = lon;
	}

	public String getLon(){
		return lon;
	}

	public void setExpiredAt(String expiredAt){
		this.expiredAt = expiredAt;
	}

	public String getExpiredAt(){
		return expiredAt;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setIosRegisterId(Object iosRegisterId){
		this.iosRegisterId = iosRegisterId;
	}

	public Object getIosRegisterId(){
		return iosRegisterId;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setBusinessName(String businessName){
		this.businessName = businessName;
	}

	public String getBusinessName(){
		return businessName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLastLogin(String lastLogin){
		this.lastLogin = lastLogin;
	}

	public String getLastLogin(){
		return lastLogin;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setOtp(String otp){
		this.otp = otp;
	}

	public String getOtp(){
		return otp;
	}

	public void setRegisterId(String registerId){
		this.registerId = registerId;
	}

	public String getRegisterId(){
		return registerId;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setSocialId(String socialId){
		this.socialId = socialId;
	}

	public String getSocialId(){
		return socialId;
	}

	public void setCheckOtp(String checkOtp){
		this.checkOtp = checkOtp;
	}

	public String getCheckOtp(){
		return checkOtp;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setDeliveryInfo(String deliveryInfo){
		this.deliveryInfo = deliveryInfo;
	}

	public String getDeliveryInfo(){
		return deliveryInfo;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"user_name = '" + userName + '\'' + 
			",lon = '" + lon + '\'' + 
			",expired_at = '" + expiredAt + '\'' + 
			",type = '" + type + '\'' + 
			",ios_register_id = '" + iosRegisterId + '\'' + 
			",password = '" + password + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",lat = '" + lat + '\'' + 
			",image = '" + image + '\'' + 
			",business_name = '" + businessName + '\'' + 
			",address = '" + address + '\'' + 
			",last_login = '" + lastLogin + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",otp = '" + otp + '\'' + 
			",register_id = '" + registerId + '\'' + 
			",token = '" + token + '\'' + 
			",social_id = '" + socialId + '\'' + 
			",check_otp = '" + checkOtp + '\'' + 
			",dob = '" + dob + '\'' + 
			",delivery_info = '" + deliveryInfo + '\'' + 
			",location = '" + location + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
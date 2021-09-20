package lk.aditi.ecom.models.address;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("country")
	private String country;

	@SerializedName("address_type")
	private String addressType;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("city")
	private String city;

	@SerializedName("date-time")
	private String dateTime;

	@SerializedName("street_address2")
	private String streetAddress2;

	@SerializedName("id")
	private String id;

	@SerializedName("street_address1")
	private String streetAddress1;

	@SerializedName("state")
	private String state;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setAddressType(String addressType){
		this.addressType = addressType;
	}

	public String getAddressType(){
		return addressType;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setStreetAddress2(String streetAddress2){
		this.streetAddress2 = streetAddress2;
	}

	public String getStreetAddress2(){
		return streetAddress2;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStreetAddress1(String streetAddress1){
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress1(){
		return streetAddress1;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"country = '" + country + '\'' + 
			",address_type = '" + addressType + '\'' + 
			",user_id = '" + userId + '\'' + 
			",city = '" + city + '\'' + 
			",date-time = '" + dateTime + '\'' + 
			",street_address2 = '" + streetAddress2 + '\'' + 
			",id = '" + id + '\'' + 
			",street_address1 = '" + streetAddress1 + '\'' + 
			",state = '" + state + '\'' + 
			"}";
		}
}
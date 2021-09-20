package lk.aditi.ecom.models.shopnearbyme;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("image")
	private String image;

	@SerializedName("address")
	private String address;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("distance")
	private String distance;

	@SerializedName("estimate_time")
	private int estimateTime;

	@SerializedName("shop_mobile")
	private String shopMobile;

	@SerializedName("lon")
	private String lon;

	@SerializedName("id")
	private String id;

	@SerializedName("shop_name")
	private String shopName;

	@SerializedName("lat")
	private String lat;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
	}

	public void setEstimateTime(int estimateTime){
		this.estimateTime = estimateTime;
	}

	public int getEstimateTime(){
		return estimateTime;
	}

	public void setShopMobile(String shopMobile){
		this.shopMobile = shopMobile;
	}

	public String getShopMobile(){
		return shopMobile;
	}

	public void setLon(String lon){
		this.lon = lon;
	}

	public String getLon(){
		return lon;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName(){
		return shopName;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"image = '" + image + '\'' + 
			",address = '" + address + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",distance = '" + distance + '\'' + 
			",estimate_time = '" + estimateTime + '\'' + 
			",shop_mobile = '" + shopMobile + '\'' + 
			",lon = '" + lon + '\'' + 
			",id = '" + id + '\'' + 
			",shop_name = '" + shopName + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}
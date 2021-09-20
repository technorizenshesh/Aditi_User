package lk.aditi.ecom.models.brand;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("number")
	private String number;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("brand_image")
	private String brandImage;

	@SerializedName("brand_name")
	private String brandName;

	@SerializedName("id")
	private String id;

	public void setNumber(String number){
		this.number = number;
	}

	public String getNumber(){
		return number;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setBrandImage(String brandImage){
		this.brandImage = brandImage;
	}

	public String getBrandImage(){
		return brandImage;
	}

	public void setBrandName(String brandName){
		this.brandName = brandName;
	}

	public String getBrandName(){
		return brandName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"number = '" + number + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",brand_image = '" + brandImage + '\'' + 
			",brand_name = '" + brandName + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
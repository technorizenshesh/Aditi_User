package lk.aditi.ecom.models.productdetail;

import com.google.gson.annotations.SerializedName;

public class ReviewItem{

	@SerializedName("shop_id")
	private String shopId;

	@SerializedName("image")
	private String image;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("rating")
	private String rating;

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private String id;

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return rating;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
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
			"ReviewItem{" + 
			"shop_id = '" + shopId + '\'' + 
			",image = '" + image + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",user_id = '" + userId + '\'' + 
			",user_name = '" + userName + '\'' + 
			",rating = '" + rating + '\'' + 
			",comment = '" + comment + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
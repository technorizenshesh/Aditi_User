package lk.aditi.ecom.models.addtocart;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("shop_id")
	private String shopId;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("item_id")
	private String itemId;

	@SerializedName("item_amount")
	private String itemAmount;

	@SerializedName("id")
	private String id;

	@SerializedName("status")
	private String status;

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
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

	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	public String getItemId(){
		return itemId;
	}

	public void setItemAmount(String itemAmount){
		this.itemAmount = itemAmount;
	}

	public String getItemAmount(){
		return itemAmount;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
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
			"shop_id = '" + shopId + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",user_id = '" + userId + '\'' + 
			",item_id = '" + itemId + '\'' + 
			",item_amount = '" + itemAmount + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
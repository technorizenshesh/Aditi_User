package lk.aditi.ecom.models.shopdetail;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("shop_id")
	private String shopId;

	@SerializedName("amount")
	private String amount;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("item_image")
	private String itemImage;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("brand_name")
	private String brandName;

	@SerializedName("id")
	private String id;

	@SerializedName("shop_name")
	private String shopName;

	@SerializedName("brand_id")
	private String brandId;

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setItemImage(String itemImage){
		this.itemImage = itemImage;
	}

	public String getItemImage(){
		return itemImage;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
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

	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName(){
		return shopName;
	}

	public void setBrandId(String brandId){
		this.brandId = brandId;
	}

	public String getBrandId(){
		return brandId;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"shop_id = '" + shopId + '\'' + 
			",amount = '" + amount + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",item_image = '" + itemImage + '\'' + 
			",item_name = '" + itemName + '\'' + 
			",brand_name = '" + brandName + '\'' + 
			",id = '" + id + '\'' + 
			",shop_name = '" + shopName + '\'' + 
			",brand_id = '" + brandId + '\'' + 
			"}";
		}
}
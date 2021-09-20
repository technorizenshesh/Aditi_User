package lk.aditi.ecom.models.cardlist;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("shop_id")
	private String shopId;

	@SerializedName("cart_id")
	private String cartId;

	@SerializedName("amount")
	private String amount;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("item_id")
	private String itemId;

	@SerializedName("item_image")
	private String itemImage;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("item_amount")
	private int itemAmount;

	@SerializedName("id")
	private String id;

	@SerializedName("brand_id")
	private String brandId;

	public void setShopId(String shopId){
		this.shopId = shopId;
	}

	public String getShopId(){
		return shopId;
	}

	public void setCartId(String cartId){
		this.cartId = cartId;
	}

	public String getCartId(){
		return cartId;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
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

	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	public String getItemId(){
		return itemId;
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

	public void setItemAmount(int itemAmount){
		this.itemAmount = itemAmount;
	}

	public int getItemAmount(){
		return itemAmount;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
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
			",cart_id = '" + cartId + '\'' + 
			",amount = '" + amount + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",item_id = '" + itemId + '\'' + 
			",item_image = '" + itemImage + '\'' + 
			",item_name = '" + itemName + '\'' + 
			",item_amount = '" + itemAmount + '\'' + 
			",id = '" + id + '\'' + 
			",brand_id = '" + brandId + '\'' + 
			"}";
		}
}
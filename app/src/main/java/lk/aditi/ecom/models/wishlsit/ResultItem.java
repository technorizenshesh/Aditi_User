package lk.aditi.ecom.models.wishlsit;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("amount")
	private String amount;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("color")
	private String color;

	@SerializedName("item_id")
	private String itemId;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("brand_id")
	private String brandId;

	@SerializedName("shop_id")
	private String shopId;

	@SerializedName("cart_id")
	private String cartId;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("size")
	private String size;

	@SerializedName("item_image")
	private String itemImage;

	@SerializedName("item_amount")
	private int itemAmount;

	@SerializedName("id")
	private String id;

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

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	public String getItemId(){
		return itemId;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setBrandId(String brandId){
		this.brandId = brandId;
	}

	public String getBrandId(){
		return brandId;
	}

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

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	public void setItemImage(String itemImage){
		this.itemImage = itemImage;
	}

	public String getItemImage(){
		return itemImage;
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

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"amount = '" + amount + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",color = '" + color + '\'' + 
			",item_id = '" + itemId + '\'' + 
			",item_name = '" + itemName + '\'' + 
			",brand_id = '" + brandId + '\'' + 
			",shop_id = '" + shopId + '\'' + 
			",cart_id = '" + cartId + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",size = '" + size + '\'' + 
			",item_image = '" + itemImage + '\'' + 
			",item_amount = '" + itemAmount + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
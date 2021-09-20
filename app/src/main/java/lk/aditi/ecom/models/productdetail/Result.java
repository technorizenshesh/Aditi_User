package lk.aditi.ecom.models.productdetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("amount")
	private String amount;

	@SerializedName("product")
	private Product product;

	@SerializedName("color")
	private List<String> color;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("brand_name")
	private String brandName;

	@SerializedName("brand_id")
	private String brandId;

	@SerializedName("shop_id")
	private String shopId;

	@SerializedName("number")
	private String number;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("size")
	private List<String> size;

	@SerializedName("brand_image")
	private String brandImage;

	@SerializedName("review")
	private List<ReviewItem> review;

	@SerializedName("item_image")
	private String itemImage;

	@SerializedName("id")
	private String id;

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setProduct(Product product){
		this.product = product;
	}

	public Product getProduct(){
		return product;
	}

	public void setColor(List<String> color){
		this.color = color;
	}

	public List<String> getColor(){
		return color;
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

	public void setSize(List<String> size){
		this.size = size;
	}

	public List<String> getSize(){
		return size;
	}

	public void setBrandImage(String brandImage){
		this.brandImage = brandImage;
	}

	public String getBrandImage(){
		return brandImage;
	}

	public void setReview(List<ReviewItem> review){
		this.review = review;
	}

	public List<ReviewItem> getReview(){
		return review;
	}

	public void setItemImage(String itemImage){
		this.itemImage = itemImage;
	}

	public String getItemImage(){
		return itemImage;
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
			"Result{" + 
			"amount = '" + amount + '\'' + 
			",product = '" + product + '\'' + 
			",color = '" + color + '\'' + 
			",item_name = '" + itemName + '\'' + 
			",brand_name = '" + brandName + '\'' + 
			",brand_id = '" + brandId + '\'' + 
			",shop_id = '" + shopId + '\'' + 
			",number = '" + number + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",size = '" + size + '\'' + 
			",brand_image = '" + brandImage + '\'' + 
			",review = '" + review + '\'' + 
			",item_image = '" + itemImage + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
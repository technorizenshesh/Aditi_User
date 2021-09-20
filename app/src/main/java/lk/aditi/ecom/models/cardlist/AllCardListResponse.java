package lk.aditi.ecom.models.cardlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCardListResponse{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("total_amount")
	private int totalAmount;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setTotalAmount(int totalAmount){
		this.totalAmount = totalAmount;
	}

	public int getTotalAmount(){
		return totalAmount;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
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
			"AllCardListResponse{" + 
			"result = '" + result + '\'' + 
			",total_amount = '" + totalAmount + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
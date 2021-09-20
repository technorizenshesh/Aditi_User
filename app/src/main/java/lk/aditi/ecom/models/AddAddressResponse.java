package lk.aditi.ecom.models;

import com.google.gson.annotations.SerializedName;

public class AddAddressResponse{

	@SerializedName("result")
	private Result result;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
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
			"AddAddressResponse{" + 
			"result = '" + result + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
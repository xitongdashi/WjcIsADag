package po.receivedata;

import java.io.Serializable;
import java.sql.Timestamp;

import po.FormPO;
import po.receivedata.StateEnum;

/**
 * 
 * @author wjc
 *2015/10/24
 */

public class ReceivePO extends FormPO implements Serializable{
	


	public ReceivePO(){
		
	}
	
	public ReceivePO(String orderID, String transitID, Timestamp data,
			String depature, String state) {
		super();
		this.orderID = orderID;
		this.transitID = transitID;
		this.data = data;
		this.depature = depature;
		this.setState(state);
	}
	
	private String orderID;
	private String transitID;
	private Timestamp data;
	private String depature;
	private StateEnum state;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getTransitID() {
		return transitID;
	}
	public void setTransitID(String transitID) {
		this.transitID = transitID;
	}
	public String getDepature() {
		return depature;
	}
	public void setDepature(String depature) {
		this.depature = depature;
	}
	public StateEnum getState() {
		return state;
	}
	public void setState(String state) {
		if(state.equalsIgnoreCase("Complete"))
			this.state = StateEnum.Complete;
		else if(state.equalsIgnoreCase("Damage"))
			this.state = StateEnum.Damage;
		else if(state.equalsIgnoreCase("Lose"))
			this.state = StateEnum.Lose;
	}
	
	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

}

package vo.transitvo;

import java.util.ArrayList;
import java.util.Calendar;

import po.FormEnum;
import po.transportdata.CenterOutPO;
import po.transportdata.TransportationEnum;
import userinfo.UserInfo;

public class CenterOutVO extends TransitVO  {
	
	public String	placeFrom;//出发地
	public String	shelfNum;//货柜号
	public TransportationEnum transitState;//转运类型
	public CenterOutVO(String formID,String createrID){
		super(FormEnum.CENTER_TRANSPORT,formID,createrID);
	}

	public CenterOutVO(String formID,String placeFrom,String TransportID,String shelfNum,
			Calendar LoadDate,String expense,String placeTo,String	peopleSee,ArrayList<String> IDs,TransportationEnum transitState,String createrID,String truckID){
		this(formID,createrID);
		this.placeFrom = placeFrom;
		this.TransportID = TransportID;
		this.shelfNum = shelfNum;
		this.LoadDate = LoadDate;
		this.placeTo = placeTo;
		this.peopleSee = peopleSee;
		this.transitState =transitState;
		this.expense = expense;
		this.IDs=IDs;
		this.numberOfIndex=truckID;
	}
	public CenterOutVO(CenterOutPO po){
		this(po.getFormID(),po.getPlaceFrom(), po.getTransportID(), po.getShelfNum(),po.getLoadDate(), po.getExpense(), po.getPlaceTo(), po.getPeopleSee(),null
				,po.getTransitState(),po.getCreatorID(),po.getNumberOfIndex());
		ArrayList<String> idClone=this.selfDeepClone(po.getIDs());
		this.IDs=idClone;

	}

	public CenterOutPO toPO(){
		ArrayList<String> idPO=this.selfDeepClone(IDs);

		CenterOutPO centerOutPO= new CenterOutPO(formID,placeFrom, shelfNum, 
				 transitState.name(), LoadDate, TransportID, placeTo, 
				peopleSee, expense, idPO,createrID,numberOfIndex);
		centerOutPO.setCache_OperatorID(UserInfo.getUserID());
		return centerOutPO;
	}

	/* (non-Javadoc)
	 * @see vo.FormVO#getMainInfo()
	 */
	@Override
	public String getMainInfo() {
		return placeFrom+" "+placeTo;
	}
}

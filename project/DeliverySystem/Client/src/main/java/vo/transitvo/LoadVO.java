package vo.transitvo;

import java.util.ArrayList;
import java.util.Calendar;

import po.FormEnum;
import po.transportdata.LoadPO;
import userinfo.UserInfo;

public class LoadVO extends TransitVO {

	public String peopletransport;
	public String expense;

	public LoadVO(String formID,String createrID){
		super(FormEnum.LOAD_CAR,formID,createrID);
	}

	public LoadVO(String formID,String truckID,String peopletransport,String expense,
			Calendar LoadDate,String TransportID,String placeTo,String	peopleSee,ArrayList<String> IDs,String createrID){
		this(formID,createrID);

		this.peopletransport = peopletransport;
		this.expense = expense;
		this.LoadDate = LoadDate;
		this.TransportID = TransportID;
		this.placeTo = placeTo;
		this.peopleSee = peopleSee;
		this.IDs=IDs;
		this.numberOfIndex=truckID;
	}

	public LoadVO(LoadPO po){
		this(po.getFormID(),po.getNumberOfIndex(), po.getPeopleTransport(), po.getExpense(),po.getLoadDate(), po.getTransportID(), po.getPlaceTo(), po.getPeopleSee()
				,null,po.getCreatorID());
		ArrayList<String> idClone=this.selfDeepClone(po.getIDs());
		this.IDs=idClone;
	}
	public LoadPO toPO(){
		ArrayList<String> idPO=this.selfDeepClone(IDs);

		LoadPO loadPO= new LoadPO(formID, peopletransport, LoadDate, TransportID, placeTo, peopleSee, expense, idPO,createrID,numberOfIndex);
		loadPO.setCache_OperatorID(UserInfo.getUserID());
		return loadPO;
	}

	/* (non-Javadoc)
	 * @see vo.FormVO#getMainInfo()
	 */
	@Override
	public String getMainInfo() {
		return placeTo;
	}

}

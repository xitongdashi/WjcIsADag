package bl.blService.manageblService;

import java.util.ArrayList;

import po.companydata.HallPO;
import message.OperationMessage;
import vo.managevo.institution.HallVO;

/** 
 * Client//blService.manageblService//ManageblHallService.java
 * @author CXWorks
 * @date 2015年10月25日 下午3:16:47
 * @version 1.0 
 */
public interface ManageblHallService {
	
	public ArrayList<HallVO> getHall();
	
	public OperationMessage addHall(HallVO hall);
	
	public OperationMessage modifyHall(HallVO hall);
	
	public OperationMessage deleteHall(HallVO hall);
	
	public HallVO searchHall(String hallID);
	
	public String newHallID(String centerID);
	
	public String nearCenterID(String cityID);
}

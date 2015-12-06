package bl.blService.transportblService;

import java.util.ArrayList;

import bl.blService.FormBLService;
import message.CheckFormMessage;
import message.OperationMessage;
import po.transportdata.LoadPO;
import vo.managevo.car.CarVO;
import vo.managevo.institution.InstitutionVO;
import vo.managevo.staff.DriverVO;
import vo.managevo.staff.StaffVO;
import vo.ordervo.OrderVO;
import vo.transitvo.CenterOutVO;
import vo.transitvo.LoadVO;

/**
 * 
 * @author mx
 *2015/10/25
 */
public interface TransportHallBLService extends FormBLService<LoadVO> {
	/**
	 * 
	 * 根据营业厅信息获取可用的司机列表
	 * @param hallID
	 * @return
	 */
	public ArrayList<DriverVO> getDrivers(String hallID);
	/**
	 * 获取当前可用车辆信息
	 * @param hallID
	 * @return
	 */
	public ArrayList<CarVO> getCars(String hallID);
	
	public ArrayList<InstitutionVO> getLocation(String hallID);
	
	public OrderVO getOrder(String orderID);
}

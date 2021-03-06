package bl.blImpl.transportbl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import message.CheckFormMessage;
import message.OperationMessage;
import po.FormEnum;
import po.companydata.CenterPO;
import po.companydata.HallPO;
import po.orderdata.OrderPO;
import po.transportdata.CenterOutPO;
import rmi.companydata.CompanyDataCenterService;
import rmi.companydata.CompanyDataHallService;
import rmi.examineService.ExamineSubmitService;
import rmi.orderdata.OrderDataService;
import rmi.transportdata.CenterOutDataService;
import tool.draft.DraftService;
import tool.vopo.VOPOFactory;
import userinfo.UserInfo;
import vo.ordervo.OrderVO;
import vo.transitvo.CenterOutVO;
import bl.NetReconnect.Reconnect;
import bl.blService.transportblService.TransportCenterBLService;
import bl.clientNetCache.CacheHelper;

public class TransportCenterBLImpl implements TransportCenterBLService {
	private DraftService draftService;
	private VOPOFactory vopoFactory;
	public TransportCenterBLImpl(VOPOFactory vopoFactory,DraftService draftService){
		this.draftService=draftService;
		this.vopoFactory=vopoFactory;
		
	}

		
		public CenterOutVO loadDraft() {
			CenterOutVO vo=(CenterOutVO)draftService.getDraft(FormEnum.CENTER_TRANSPORT);
			return vo;
		}
		public OperationMessage saveDraft(CenterOutVO form) {
			return draftService.saveDraft(form);
		}
		public ArrayList<CheckFormMessage> checkFormat(CenterOutVO form,
				boolean isFinal) {
			// TODO Auto-generated method stub
			ArrayList<CheckFormMessage> result=new ArrayList<CheckFormMessage>();
			CheckFormMessage stub=new CheckFormMessage();
			result.add(stub);
			return result;

		}
		public OperationMessage submit(CenterOutVO form) {
			CenterOutPO po=(CenterOutPO)vopoFactory.transVOtoPO(form);
			ExamineSubmitService examineSubmitService=CacheHelper.getExamineSubmitService();
			try {
				return examineSubmitService.submit(po);
			} catch (RemoteException e) {
				Reconnect.ReConnectFactory();
				return new OperationMessage(false, "net error");
			}
		}
		/* (non-Javadoc)
		 * @see bl.blService.FormBLService#newID()
		 */
		public String newID() {
			CenterOutDataService centerOutDataService=CacheHelper.getTransportDataService();
			String ID;
			try {
				ID = centerOutDataService.newID(UserInfo.getInstitutionID());
				return ID;
			} catch (RemoteException e) {
				Reconnect.ReConnectFactory();
				return null;
			}
			
		}
		/* (non-Javadoc)
		 * @see bl.blService.transportblService.TransportCenterBLService#getOrder(java.lang.String)
		 */
		public OrderVO getOrder(String orderID) {
			OrderDataService orderDataService=CacheHelper.getOrderDataService();
			try {
				OrderPO po=orderDataService.getFormPO(orderID);
				OrderVO vo=(OrderVO)vopoFactory.transPOtoVO(po);
				return vo;
			} catch (RemoteException e) {
				Reconnect.ReConnectFactory();
				return null;
			}
		}


		/* (non-Javadoc)
		 * @see bl.blService.transportblService.TransportCenterBLService#getLocation(java.lang.String)
		 */
		@Override
		public ArrayList<String> getLocation(String centerID) {
			System.out.println(centerID);
			ArrayList<String> ans=new ArrayList<String>();
			CompanyDataCenterService companyDataCenterService=CacheHelper.getCompanyDataCenterService();
			CompanyDataHallService companyDataHallService=CacheHelper.getCompanyDataHallService();
			try {
				ArrayList<CenterPO> centerPOs=companyDataCenterService.getCenter();
				ans.addAll(centerPOs.stream()
						.map(cen->cen.getCenterID())
						.filter(id->!id.equalsIgnoreCase(centerID))
						.collect(Collectors.toList()));
				//
				ArrayList<HallPO> hallPOs=companyDataHallService.getHall();
				for (HallPO hallPO : hallPOs) {
					if (hallPO.getNearCenterID().equalsIgnoreCase(centerID)) {
						ans.add(hallPO.getHallID());
					}
				}
				return ans;
			} catch (RemoteException e) {
				Reconnect.ReConnectFactory();
				return null;
			}
			
		}


		/* (non-Javadoc)
		 * @see bl.blService.transportblService.TransportCenterBLService#newTransID(java.lang.String)
		 */
		@Override
		public String newTransID(String unitID) {
			CenterOutDataService centerOutDataService=CacheHelper.getTransportDataService();
			try {
				String ans=centerOutDataService.newTransID(unitID);
				return ans;
			} catch (RemoteException e) {
				Reconnect.ReConnectFactory();
				return null;
			}
		}


		/* (non-Javadoc)
		 * @see bl.blService.FormBLService#getHistory(java.lang.String)
		 */
		@Override
		public List<CenterOutVO> getHistory(String creatorID) {
			CenterOutDataService centerOutDataService=CacheHelper.getTransportDataService();
			try {
				List<CenterOutPO> centerOutPOs=centerOutDataService.getHistory(creatorID);
				List<CenterOutVO> ans=(List<CenterOutVO>) vopoFactory.transPOtoVO(centerOutPOs);
				return ans;
			} catch (RemoteException e) {
				Reconnect.ReConnectFactory();
				return null;
			}
		}



}

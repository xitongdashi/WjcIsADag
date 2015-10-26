package blImpl.financebl;

import blService.financeblService.RevenueBLService;
import message.CheckFormMessage;
import message.OperationMessage;
import rmi.financedata.FinanceFormDataService;
import util.R;
import vo.financevo.RevenueVO;
import vo.ordervo.OrderVO;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sissel on 2015/10/26.
 */
public class RevenueBLImpl implements RevenueBLService {
    public OperationMessage loadOrder(String orderNumber) {
        return new OperationMessage();
    }

    public String getNewRevenueID(String date) {
        return "222333";
    }

    public OrderVO getOrderVO() {
        return new OrderVO();
    }

    public RevenueVO getRevenueVO(String revenueID) {
        return new RevenueVO();
    }

    public RevenueVO getRevenueVO(String date, String hallID) {
        return new RevenueVO();
    }

    public List<RevenueVO> getRevenueVOs(String startDate, String endDate) {
        return new LinkedList<RevenueVO>();
    }

    public RevenueVO loadDraft() {
        return new RevenueVO();
    }

    public OperationMessage saveDraft(RevenueVO form) {
        return new OperationMessage();
    }

    public ArrayList<CheckFormMessage> checkFormat(RevenueVO form, boolean isFinal) {
    	ArrayList<CheckFormMessage> result =new ArrayList<CheckFormMessage>();
		CheckFormMessage stub=new CheckFormMessage();
		result.add(stub);
		return result;
    }

    public OperationMessage submit(RevenueVO form) {
        return new OperationMessage();
    }

    public static void drive(FinanceFormDataService ffds) throws RemoteException {
        if(ffds.downloadAllRevenuePOs() != null)
            System.out.println("downloadAllRevenuePOs tested");
        if(ffds.getNewRevenueID("222333", "222") != null)
            System.out.println("getNewRevenueID tested");
        if(ffds.getRevenuePO("222333") != null)
            System.out.println("getRevenuePO tested");
        if(ffds.updateRevenuePOs("222333") != null)
            System.out.println("updateRevenuePOs tested");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        FinanceFormDataService ffds = (FinanceFormDataService) Naming.lookup
                ("rmi://" + R.string.LocalHost + "/" + R.string.FinanceDataService);
        drive(ffds);
    }
}
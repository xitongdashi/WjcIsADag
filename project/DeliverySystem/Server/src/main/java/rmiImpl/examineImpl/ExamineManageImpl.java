package rmiImpl.examineImpl;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;

import message.ChatMessage;
import message.OperationMessage;
import model.examine.ExamineQueue;
import po.FormEnum;
import po.FormPO;
import po.deliverdata.DeliverPO;
import po.financedata.PaymentPO;
import po.financedata.RevenuePO;
import po.orderdata.OrderPO;
import po.receivedata.ReceivePO;
import po.storedata.StoreInPO;
import po.storedata.StoreOutPO;
import po.systemdata.LogPO;
import po.transportdata.CenterOutPO;
import po.transportdata.LoadPO;
import rmi.chatRemindService.ChatNewService;
import rmi.examineService.ExamineManageService;
import rmi.systemdata.LogDataService;
import rmiImpl.chatRemindImpl.Reminder;
import rmiImpl.systemdata.LogDataImpl;

public class ExamineManageImpl extends UnicastRemoteObject implements
		ExamineManageService {
	private static final long serialVersionUID = 1L;
	/* 表单队列在此类中产生，将引用赋给ExamineSubmitImpl */
	private volatile ExamineQueue queue;
	/*负责新建消息*/
	private ChatNewService addMessage;

	private PassHelper pass_helper;

	/*负责添加纪录到系统日志*/
	private LogDataService log;

	/*总经理的ID*/
	private static String managerID = "01000001";

	public ExamineManageImpl() throws RemoteException, MalformedURLException {
		super();
		this.queue = new ExamineQueue();
		addMessage = new Reminder();
		pass_helper = new PassHelper();
		log = new LogDataImpl();
	}

	public ExamineQueue getQueue() {
		return queue;
	}

	public ArrayList<FormPO> getForms() {
		// TODO Auto-generated method stub
		return queue.removeForms();
	}

	public OperationMessage modifyForm(FormPO form) throws RemoteException {
		// TODO Auto-generated method stub
		FormEnum type = form.getFormType();
		OperationMessage result;
		switch (type) {
		case DELIVER:
			result = pass_helper.getDeliverDataService()
					.update((DeliverPO) form);
			break;
		case ORDER:
			result = pass_helper.getOrderDataService().update((OrderPO) form);
			break;
		case PAYMENT:
			result = pass_helper.getPaymentDataService()
					.update((PaymentPO) form);
			break;
		case RECEIVE:
			result = pass_helper.getReceiveDataService()
					.update((ReceivePO) form);
			break;
		case REVENUE:
			result = pass_helper.getRevenueDataService()
					.update((RevenuePO) form);
			break;
		case STORE_IN:
			StoreInPO sInPO = (StoreInPO) form;
			result = pass_helper.getStoreFormDataService().updateStoreInPO(sInPO);
			pass_helper.getStoreModelDataService().setLocation(form.getFormID().substring(2, 9),sInPO.getLocation());//改变数据库中这个位置的状态
			break;
		case STORE_OUT:
			StoreOutPO sOutPO = (StoreOutPO) form;
			result = pass_helper.getStoreFormDataService().updateStoreOutPO(sOutPO);
			pass_helper.getStoreModelDataService().setLocation(form.getFormID().substring(2, 9),sOutPO.getLocation());//改变数据库中这个位置的状态
			break;
		case TRANSPORT_CENTER:
			result = pass_helper.getTransportDataService().update(
					(CenterOutPO) form);
			break;
		case TRANSPORT_HALL:
			result = pass_helper.getLoadDataService().update((LoadPO) form);
			break;
		default:
			return new OperationMessage(false, "表单隐藏信息有问题");
		}
		ChatMessage mes = new ChatMessage(managerID,form.getCreaterID(),
				"表单被修改：" + form.getFormID());
		addMessage.add(form.getCreaterID(), mes);
		log.insert(new LogPO(managerID, Calendar.getInstance(), "修改表单:" + form.getFormID()));
		return result;
	}

	public OperationMessage passForm(ArrayList<FormPO> forms)
			throws RemoteException {
		// TODO Auto-generated method stub
		OperationMessage mes = new OperationMessage();
		for (FormPO tmp : forms) {
			OperationMessage result = new OperationMessage();
			FormEnum type = tmp.getFormType();
			switch (type) {
			case DELIVER:
				result = pass_helper.getDeliverDataService().insert(
						(DeliverPO) tmp);
				break;
			case ORDER:
				result = pass_helper.getOrderDataService().insert((OrderPO) tmp);
				break;
			case PAYMENT:
				result = pass_helper.getPaymentDataService().insert(
						(PaymentPO) tmp);
				break;
			case RECEIVE:
				result = pass_helper.getReceiveDataService().insert(
						(ReceivePO) tmp);
				break;
			case REVENUE:
				result = pass_helper.getRevenueDataService().insert(
						(RevenuePO) tmp);
				break;
			case STORE_IN:
				StoreInPO sInPO = (StoreInPO) tmp;
				sInPO.setMoney(pass_helper.getOrderDataService().getFormPO(sInPO.getOrderID()).getMoney());
				result = pass_helper.getStoreFormDataService().updateStoreInPO(sInPO);
				pass_helper.getStoreModelDataService().setLocation(tmp.getFormID().substring(2, 9),sInPO.getLocation());//改变数据库中这个位置的状态
				break;
			case STORE_OUT:
				StoreOutPO sOutPO = (StoreOutPO) tmp;
				OrderPO orderPO = pass_helper.getOrderDataService().getFormPO(sOutPO.getOrderID());
				sOutPO.setMoney(orderPO.getMoney());
				ArrayList<String> IDs = orderPO.getFormIDs();
				String inID = IDs.get(IDs.size()-1);
				sOutPO.setLocation(pass_helper.getStoreFormDataService().getStoreInPO(inID).getLocation());
				result = pass_helper.getStoreFormDataService().updateStoreOutPO(sOutPO);
				pass_helper.getStoreModelDataService().setLocation(tmp.getFormID().substring(2, 9),sOutPO.getLocation());//改变数据库中这个位置的状态
				break;
			case TRANSPORT_CENTER:
				result = pass_helper.getTransportDataService().insert(
						(CenterOutPO) tmp);
				break;
			case TRANSPORT_HALL:
				result = pass_helper.getLoadDataService().insert((LoadPO) tmp);
				break;
			default:
				result = new OperationMessage(false, "表单隐藏信息有问题");
			}
			if (!result.operationResult) {
				mes.operationResult = false;
				mes.addReason(tmp.getFormID());
			}else {
				ChatMessage chat = new ChatMessage(managerID,tmp.getCreaterID(),
						"表单通过：" + tmp.getFormID());
				addMessage.add(tmp.getCreaterID(), chat);
				log.insert(new LogPO(managerID, Calendar.getInstance(), "新建表单:" + tmp.getFormID()));
			}
		}
		return mes;
	}

	public OperationMessage deleteForm(ArrayList<FormPO> forms)
			throws RemoteException {
		// TODO Auto-generated method stub
		OperationMessage mes = new OperationMessage();
		for (FormPO tmp : forms) {
			OperationMessage result = new OperationMessage();
			FormEnum type = tmp.getFormType();
			switch (type) {
			case DELIVER:
				result = pass_helper.getDeliverDataService().delete(
						tmp.getFormID());
				break;
			case ORDER:
				result = pass_helper.getOrderDataService().delete(
						tmp.getFormID());
				break;
			case PAYMENT:
				result = pass_helper.getPaymentDataService().delete(
						tmp.getFormID());
				break;
			case RECEIVE:
				result = pass_helper.getReceiveDataService().delete(
						tmp.getFormID());
				break;
			case REVENUE:
				result = pass_helper.getRevenueDataService().delete(
						tmp.getFormID());
				break;
			case STORE_IN:
				result = pass_helper.getStoreFormDataService().deleteStoreInPO(
						tmp.getFormID());
				break;
			case STORE_OUT:
				result = pass_helper.getStoreFormDataService().deleteStoreOutPO(
						tmp.getFormID());
				break;
			case TRANSPORT_CENTER:
				result = pass_helper.getTransportDataService().delete(
						tmp.getFormID());
				break;
			case TRANSPORT_HALL:
				result = pass_helper.getLoadDataService()
						.delete(tmp.getFormID());
				break;
			default:
				result = new OperationMessage(false, "表单隐藏信息有问题");
			}
			if (!result.operationResult) {
				mes.operationResult = false;
				mes.addReason(tmp.getFormID());
			}else{
				ChatMessage chat = new ChatMessage(managerID,tmp.getCreaterID(),
						"表单被删除：" + tmp.getFormID());
				addMessage.add(tmp.getCreaterID(), chat);
				log.insert(new LogPO(managerID, Calendar.getInstance(), "删除表单:" + tmp.getFormID()));
			}
		}
		return mes;
	}

}

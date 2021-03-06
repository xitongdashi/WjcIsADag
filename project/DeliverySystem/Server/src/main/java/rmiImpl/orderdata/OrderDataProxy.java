package rmiImpl.orderdata;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import message.OperationMessage;
import po.orderdata.OrderPO;
import po.systemdata.SystemState;
import rmi.orderdata.OrderDataService;
import rmiImpl.CommonData;
import rmiImpl.initaldata.InitialDataProxy;

public class OrderDataProxy extends CommonData<OrderPO> implements OrderDataService {

	OrderDataService orderDataService = new OrderDataImpl();

	public OrderDataProxy() throws RemoteException {
		super();
	}

	@Override
	public OperationMessage insert(OrderPO po) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.insert(po);
		return null;
	}

	@Override
	public OrderPO getFormPO(String id) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL)) {
			return orderDataService.getFormPO(id);
		} else if (InitialDataProxy.getState().equals(SystemState.INITIALIZING)) {
			String[] split = id.split("-");
			if(split[0].equalsIgnoreCase(InitialDataProxy.getID()))
				return orderDataService.getFormPO(split[1]);
		}
		return null;
	}

	@Override
	public OperationMessage delete(String id) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.delete(id);
		return null;
	}

	@Override
	public OperationMessage update(OrderPO po) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.update(po);
		return null;
	}

	@Override
	public String newID(String unitID) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.newID(unitID);
		return null;
	}

	@Override
	public OperationMessage clear() throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.clear();
		return null;
	}

	@Override
	public ArrayList<OrderPO> getAll() throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.getAll();
		return null;
	}

	@Override
	public OperationMessage addFormID(String orderID, String formID) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.addFormID(orderID, formID);
		return null;
	}

	@Override
	public OperationMessage setFinished(String orderID) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.setFinished(orderID);
		return null;
	}

	@Override
	public List<OrderPO> getHistory(String creatorID) throws RemoteException {
		if (InitialDataProxy.getState().equals(SystemState.NORMAL))
			return orderDataService.getHistory(creatorID);
		return null;
	}

}

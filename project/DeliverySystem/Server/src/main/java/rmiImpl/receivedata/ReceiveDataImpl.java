package rmiImpl.receivedata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import message.OperationMessage;
import po.FormEnum;
import po.receivedata.ReceivePO;
import rmi.receivedata.ReceiveDataService;
import rmiImpl.CommonData;
import database.ConnecterHelper;
import database.MySql;
import database.enums.TableEnum;
import rmiImpl.orderdata.OrderDataImpl;

/**
 *
 * @author wjc 2015/10/24
 */

public class ReceiveDataImpl extends CommonData<ReceivePO> implements ReceiveDataService {

	private String Table_Name;
	private Connection conn = null;
	private PreparedStatement statement = null;
	private String today = "";// 格式eg.2015-11-22
	private int ID_MAX;

	public ReceiveDataImpl() throws RemoteException {
		super();

		Table_Name = "receive";
		conn = ConnecterHelper.getConn();

		// 为today和ID_MAX初始化
		this.newID(null);
		ID_MAX--;
	}

	public Connection getConn() {
		return conn;
	}

	public OperationMessage insert(ReceivePO po) throws RemoteException {
		OperationMessage result = new OperationMessage();
		String insert = MySql.insert(TableEnum.RECEIVE, new HashMap<String, String>() {
			{
				put("formID", po.getFormID());
				put("formState", po.getFormState().toString());
				put("orderID", po.getOrderID());
				put("transitID", po.getTransitID());
				put("date", po.getDateForSQL().toString());
				put("depature", po.getDepature());
				put("state", po.getState().toString());
				put("date_and_unit", po.getFormID().substring(2, 17));
				put("creatorID", po.getCreatorID());
			}
		});
		try {
			statement = conn.prepareStatement(insert);
			statement.executeUpdate();
		} catch (SQLException e) {
			if (this.getFormPO(po.getFormID()) != null) {
				po.setFormID(this.newID(po.getFormID().substring(9, 17)));
				this.insert(po);
			} else {
				result = new OperationMessage(false, "新建时出错：");
				System.err.println("新建时出错：");
				e.printStackTrace();
			}
		}

		return result;
	}

	public OperationMessage delete(String id) {
		OperationMessage result = new OperationMessage();
		String delete = MySql.delete(TableEnum.RECEIVE, new HashMap<String, String>() {
			{
				put("formID", id);
			}
		});
		try {
			statement = conn.prepareStatement(delete);
			statement.executeUpdate();
		} catch (SQLException e) {
			result = new OperationMessage(false, "删除时出错：");
			System.err.println("删除时出错：");
			e.printStackTrace();
		}
		return result;
	}

	public OperationMessage update(ReceivePO po) throws RemoteException {
		OperationMessage result = new OperationMessage();
		if (!this.delete(po.getFormID()).operationResult)
			return result = new OperationMessage(false, "数据库中没有对应表单");
		if (!this.insert(po).operationResult)
			return result = new OperationMessage(false, "更新失败");
		else
			return result;

	}

	public String newID(String unitID) {
		ResultSet rs = null;
		String temp = new Timestamp(System.currentTimeMillis()).toString().substring(0, 10);
		final String target = unitID + temp.substring(0, 4) + temp.substring(5, 7) + temp.substring(8);

		// 当前日期与缓存日期一致
		if (temp.equalsIgnoreCase(today)) {
			this.ID_MAX++;
			String added = String.format("%07d", ID_MAX);
			return "03" + target + added;
		}

		// 当前日期与缓存日期不一致
		today = temp;
		String selectAll = MySql.select(TableEnum.RECEIVE, new HashMap<String, String>() {
			{
				put("date_and_unit", target);
			}
		});
		try {
			statement = conn.prepareStatement(selectAll);
			rs = statement.executeQuery(selectAll);
			while (rs.next()) {
				ID_MAX = Math.max(ID_MAX, Integer.parseInt(rs.getString("formID").substring(17)));// 最后7位编号
			}
		} catch (SQLException e) {
			System.err.println("访问数据库时出错：");
			e.printStackTrace();
		}

		ID_MAX++;// 将该数字加一
		if (ID_MAX > 9999999)
			return null;
		String added = String.format("%07d", ID_MAX);

		return "03" + target + added;
	}

	public OperationMessage clear() {
		OperationMessage result = new OperationMessage();
		String clear = MySql.delete(TableEnum.RECEIVE, null);
		try {
			statement = conn.prepareStatement(clear);
			statement.executeUpdate();
		} catch (SQLException e) {
			result = new OperationMessage(false, "清空数据库时出错：");
			System.err.println("清空数据库时出错：");
			e.printStackTrace();
		}
		return result;
	}

	public ReceivePO getFormPO(String id) throws RemoteException {
		String select = MySql.select(TableEnum.RECEIVE, new HashMap<String, String>() {
			{
				put("formID", id);
			}
		});
		ResultSet rs = null;
		ReceivePO result = null;
		try {
			statement = conn.prepareStatement(select);
			rs = statement.executeQuery(select);
			rs.next();
			result = new ReceivePO(rs.getString("formID"), rs.getString("orderID"), rs.getString("transitID"),
					rs.getTimestamp("date"), rs.getString("depature"), rs.getString("state"),
					rs.getString("creatorID"));
			result.setFormState(rs.getString("formState"));
		} catch (SQLException e) {
			System.err.println("查找数据库时出错：");
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public ArrayList<ReceivePO> getAll() throws RemoteException {
		String selectAll = MySql.select(TableEnum.RECEIVE, null);
		ResultSet rs = null;
		ReceivePO temp = null;
		ArrayList<ReceivePO> result = new ArrayList<ReceivePO>();
		try {
			statement = conn.prepareStatement(selectAll);
			rs = statement.executeQuery(selectAll);
			while (rs.next()) {
				temp = new ReceivePO(rs.getString("formID"), rs.getString("orderID"), rs.getString("transitID"),
						rs.getTimestamp("date"), rs.getString("depature"), rs.getString("state"),
						rs.getString("creatorID"));
				temp.setFormState(rs.getString("formState"));
				result.add(temp);

			}
		} catch (SQLException e) {
			System.err.println("查找数据库时出错：");
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<ReceivePO> getHistory(String creatorID) throws RemoteException {
		String select = MySql.select(TableEnum.RECEIVE,new HashMap<String, String>() {
			{
				put("creatorID", creatorID);
			}
		});
		ResultSet rs = null;
		ReceivePO temp = null;
		ArrayList<ReceivePO> result = new ArrayList<ReceivePO>();
		try {
			statement = conn.prepareStatement(select);
			rs = statement.executeQuery(select);
			while (rs.next()) {
				temp = new ReceivePO(rs.getString("formID"), rs.getString("orderID"), rs.getString("transitID"),
						rs.getTimestamp("date"), rs.getString("depature"), rs.getString("state"),
						rs.getString("creatorID"));
				temp.setFormState(rs.getString("formState"));
				result.add(temp);

			}
		} catch (SQLException e) {
			System.err.println("查找数据库时出错：");
			e.printStackTrace();
		}

		return result;
	}

}

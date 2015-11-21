package rmiImpl.deliverdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import message.OperationMessage;
import po.FormEnum;
import po.deliverdata.DeliverPO;
import po.receivedata.ReceivePO;
import rmi.deliverdata.DeliverDataService;
import rmiImpl.CommonData;
import rmiImpl.ConnecterHelper;

/**
 * 
 * @author wjc 2015/10/24
 */

public class DeliverDataImpl extends CommonData<DeliverPO> implements
		DeliverDataService {

	private String Table_Name;
	private Connection conn = null;
	private PreparedStatement statement = null;

	public DeliverDataImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
		super();
		Table_Name = "deliver";
		conn = ConnecterHelper.connSQL(conn);
	}

	public Connection getConn() {
		return conn;
	}

	public OperationMessage insert(DeliverPO po) {
		// TODO Auto-generated method stub
		OperationMessage result = new OperationMessage();
		String insert = "insert into " + Table_Name
				+ "(formID,formState,orderID,postman,date) " + "values('"
				+ po.getFormID() + "','" + po.getFormState().toString() + "','"
				+ po.getOrderID() + "','" + po.getPostman() + "','"
				+ po.getDateForSQL().toString() + "')";

		try {
			statement = conn.prepareStatement(insert);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = new OperationMessage(false, "新建时出错：");
			System.err.println("新建时出错：");
			e.printStackTrace();
		}

		return result;
	}

	public OperationMessage delete(String id) {
		// TODO Auto-generated method stub
		OperationMessage result = new OperationMessage();
		String delete = "delete from " + Table_Name + " where formID= '" + id
				+ "'";
		try {
			statement = conn.prepareStatement(delete);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = new OperationMessage(false, "删除时出错：");
			System.err.println("删除时出错：");
			e.printStackTrace();
		}
		return result;
	}

	public OperationMessage update(DeliverPO po) {
		// TODO Auto-generated method stub
		OperationMessage result = new OperationMessage();
		if(!this.delete(po.getFormID()).operationResult)
			return result = new OperationMessage(false,"数据库中没有对应表单");
		if(!this.insert(po).operationResult)
			return result = new OperationMessage(false,"更新失败");
		else
			return result;
	}

	public String newID() {// 返回最大值
		// TODO Auto-generated method stub
		String selectAll = "select * from " + Table_Name;
		ResultSet rs = null;
		String ID_MAX = "";
		try {
			statement = conn.prepareStatement(selectAll);
			rs = statement.executeQuery(selectAll);
			while (rs.next()) {
				ID_MAX = rs.getString("formID");
			}
		} catch (SQLException e) {
			System.err.println("查找数据库时出错：");
			e.printStackTrace();
		}

		return ID_MAX;
	}

	public OperationMessage clear() {
		// TODO Auto-generated method stub
		OperationMessage result = new OperationMessage();
		String clear = "delete from " + Table_Name;
		try {
			statement = conn.prepareStatement(clear);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = new OperationMessage(false, "清空数据库时出错：");
			System.err.println("清空数据库时出错：");
			e.printStackTrace();
		}
		return result;
	}

	public DeliverPO getFormPO(String id) throws RemoteException {
		// TODO Auto-generated method stub
		String select = "select * from " + Table_Name + " where formID= '" + id
				+ "'";
		ResultSet rs = null;
		DeliverPO result = null;
		try {
			statement = conn.prepareStatement(select);
			rs = statement.executeQuery(select);
			rs.next();
			result = new DeliverPO(rs.getString("orderID"),
					rs.getTimestamp("date"), rs.getString("postman"));
			result.setFormType(FormEnum.DELIVER);
			result.setFormID(rs.getString("formID"));
			result.setFormState(rs.getString("formState"));
		} catch (SQLException e) {
			System.err.println("查找数据库时出错：");
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<DeliverPO> getAll() throws RemoteException {
		// TODO Auto-generated method stub
		String selectAll = "select * from " + Table_Name;
		ResultSet rs = null;
		DeliverPO temp = null;
		ArrayList<DeliverPO> result = new ArrayList<DeliverPO>();
		try {
			statement = conn.prepareStatement(selectAll);
			rs = statement.executeQuery(selectAll);
			while (rs.next()) {
				temp = new DeliverPO(rs.getString("orderID"),
						rs.getTimestamp("date"), rs.getString("postman"));
				temp.setFormType(FormEnum.DELIVER);
				temp.setFormID(rs.getString("formID"));
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
	public ArrayList<DeliverPO> available(String HallID) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<DeliverPO> result = new ArrayList<DeliverPO>();
		//需要规范好表单号才能写
		//遍历order表，查其中FromIDs中最后一张到达单是否为targetHallID
		return null;
	}
}

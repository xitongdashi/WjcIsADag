package rmi.receivedata;
import java.util.ArrayList;

import message.OperationMessage;
import po.receivedata.ReceivePO;
import po.receivedata.StateEnum;

/**
 * 
 * @author wjc
 *2015/10/24
 */

public interface ReceiveDataService {
	
	/**
	 * 插入新的到达信息
	 * @param po 新的到达信息
	 * @return 返回操作结果
	 */
	public OperationMessage insert(ReceivePO po);

	/**
	 * 按到达单号查找到达信息
	 * @param id 到达单编号
	 * @return 返回目标到达信息
	 */
	public ReceivePO find(String id);
	
	/**
	 * 删除到达信息
	 * @param id 需被删除的到达信息对应到达单号
	 * @return 返回操作结果
	 */
	public OperationMessage delete(String id);

	/**
	 * 更新到达信息
	 * @param id 需被更新的到达信息对应到达单号
	 * @return 返回操作结果
	 */
	public OperationMessage update(ReceivePO po);

	/**
	 * 清空到达信息
	 * @param id 需被清空的到达信息对应到达单号
	 * @return 返回操作结果
	 */
	public OperationMessage init(String id);
	
	/**
	 * 获得新的到达单编号
	 * @return 新的到达单编号
	 */
	public String newID();
	
	/**
	 * 清空到达数据
	 * @return 返回操作结果
	 */
	public OperationMessage clear();
	
	/**
	 * 展示全部到达信息
	 * @return 所有到达信息
	 */
	public ArrayList<ReceivePO> show();

}

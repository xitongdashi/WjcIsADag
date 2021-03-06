package bl.blService.FormatCheckService;

import java.util.Calendar;

import message.CheckFormMessage;
/**
 * 
 * @author wjc
 *2015/10/24
 */

public interface FormatCheckService{
	
	/**
	 * 检查订单号
	 * @param ID 订单号
	 * @return 返回检查结果
	 */
	public CheckFormMessage checkOrderID(String ID);
	
	/**
	 * 检查日期（系统时间之前）
	 * @param date 日期
	 * @return 返回检查结果
	 */
	public  CheckFormMessage checkPostDate(Calendar date);
	
	/*
	 * 检查日期（系统时间之hou）
	 * @param data 日期
	 * @return 返回检查结果
	 */
	public  CheckFormMessage checkPreDate(Calendar date);
	
	/**
	 * 查重
	 * @param from 出发地,to 到达地
	 * @return 返回检查结果
	 */
	public  CheckFormMessage checkLocation(String from, String to);
	
	/**
	 * 检查中转单号
	 * @param ID 中转单号
	 * @return 返回检查结果
	 */
	public CheckFormMessage checkTransitID(String ID);
	
	/**
	 * 检查中转中心编号
	 * @param ID 中转中心编号
	 * @return 返回检查结果
	 */
	public CheckFormMessage checkTransportHallID(String ID);
	
	/**
	 * 检查营业厅编号
	 * @param ID 营业厅编号
	 * @return 返回检查结果
	 */
	public CheckFormMessage checkTransportCenterID(String ID);
	
	/**
	 * 检查车辆代号
	 * @param ID 车辆代号
	 * @return 返回检查结果
	 */
	public CheckFormMessage checkTruckLoadID(String ID);
	
	/**
	 * 检查手机号码
	 * @param num 手机号码
	 * @return 返回检查结果
	 */
	public CheckFormMessage checkPhone(String num);
	
	/**
	 * 检查输入是否为空
	 * @param in 输入的字符串
	 * @return 返回检查结果
	 */
	public CheckFormMessage checkIsNull(String in);

	/**
	 * 检查接收单ID
	 * @param ID
	 * @return
	 */
	public CheckFormMessage checkReceiveID(String ID);
}

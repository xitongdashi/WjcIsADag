package bl.clientNetCache.dataProxy;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import bl.clientNetCache.save.CacheSaver;
import bl.clientNetCache.save.VersionSaver;
import bl.clientRMI.RMIHelper;
import message.OperationMessage;
import operation.Operation;
import po.configurationdata.City2DPO;
import po.configurationdata.PackPO;
import po.configurationdata.PricePO;
import po.configurationdata.ProportionPO;
import po.configurationdata.SalaryStrategyPO;
import rmi.configurationdata.ConfigurationDataService;
import userinfo.UserInfo;

/** 
 * Client//bl.clientNetCache.dataProxy//ConfigurationDataProxy.java
 * @author CXWorks
 * @date 2015年12月17日 下午11:36:39
 * @version 1.0 
 */
public class ConfigurationDataProxy extends DataProxy implements ConfigurationDataService {
	private static ConfigurationDataProxy proxy;
	//
	private static final String VERSION_NAME="configurationCacheVersion";
	private static final String CITY="cityCache";
	private static final String SALARY="salaryCache";
	private static final String PROPORTION="proportionCache";
	private static final String PACK="packCache";
	private static final String PRICE="priceCache";
	//
	private ConfigurationDataService configurationDataService;
	private CacheSaver saver;
	private long clientVersion;
	//
	private ArrayList<City2DPO> city;
	private ArrayList<SalaryStrategyPO> salary;
	private PricePO price;
	private ProportionPO proportion;
	private PackPO pack;
	private double warningLine;
	
	private ConfigurationDataProxy(){}
	//
	public static ConfigurationDataProxy getInstance() throws RemoteException{
		if (proxy!=null) {
			return proxy;
		}
		//
		proxy=new ConfigurationDataProxy();
		//version
		VersionSaver versionSaver=new VersionSaver();
		proxy.clientVersion=versionSaver.loadVersion(VERSION_NAME);
		proxy.configurationDataService=RMIHelper.getConfigurationDataService();
		long serverVersion=proxy.configurationDataService.getLatestVersionID();
		//cache data
		proxy.saver=new CacheSaver();
		proxy.city=(ArrayList<City2DPO>) proxy.saver.loadCache(CITY, true);
		proxy.salary=(ArrayList<SalaryStrategyPO>) proxy.saver.loadCache(SALARY, true);
		proxy.pack=(PackPO) proxy.saver.loadCache(PACK);
		proxy.price=(PricePO) proxy.saver.loadCache(PRICE);
		proxy.proportion=(ProportionPO) proxy.saver.loadCache(PROPORTION);
		proxy.warningLine=proxy.configurationDataService.getWarningline(UserInfo.getInstitutionID());
		
		//
		if (serverVersion>proxy.clientVersion) {
			List<Operation> operations=proxy.configurationDataService.getOperation(proxy.clientVersion);
			proxy.dealOperation(operations);
			proxy.clientVersion=serverVersion;
		}
		//
		return proxy;
	}

	/* (non-Javadoc)
	 * @see rmi.DataService#getConn()
	 */
	@Override
	public Connection getConn() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see rmi.cachedata.CacheDataService#getLatestVersionID()
	 */
	@Override
	public long getLatestVersionID() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see rmi.cachedata.CacheDataService#getOperation(long)
	 */
	@Override
	public List<Operation> getOperation(long localVersion)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#newCity2D(po.configurationdata.City2DPO)
	 */
	@Override
	public OperationMessage newCity2D(City2DPO po) throws RemoteException {
		city.add(po);
		this.clientVersion++;
		return configurationDataService.newCity2D(po);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#deleteCity2D(java.lang.String)
	 */
	@Override
	public OperationMessage deleteCity2D(String name) throws RemoteException {
		city.removeIf(ci->{return ci.getName().equalsIgnoreCase(name);});
		this.clientVersion++;
		return configurationDataService.deleteCity2D(name);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#modifyCity2D(po.configurationdata.City2DPO)
	 */
	@Override
	public OperationMessage modifyCity2D(City2DPO po) throws RemoteException {
		this.deleteCity2D(po.getName());
		this.city.add(po);
		this.clientVersion++;
		return configurationDataService.modifyCity2D(po);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#getCity2D(java.lang.String)
	 */
	@Override
	public City2DPO getCity2D(String name) throws RemoteException {
		return city.stream()
				.filter(ci->{return ci.getName().equalsIgnoreCase(name);})
				.findFirst().get();
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#getAllCity2D()
	 */
	@Override
	public ArrayList<City2DPO> getAllCity2D() throws RemoteException {
		return this.city;
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#clearCity2D()
	 */
	@Override
	public OperationMessage clearCity2D() throws RemoteException {
		this.city.clear();
		this.clientVersion++;
		return configurationDataService.clearCity2D();
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#getSalaryStrategy()
	 */
	@Override
	public ArrayList<SalaryStrategyPO> getSalaryStrategy()
			throws RemoteException {
		return this.salary;
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#newSalaryStrategy(java.util.List)
	 */
	@Override
	public OperationMessage newSalaryStrategy(List<SalaryStrategyPO> po)
			throws RemoteException {
		this.salary.addAll(po);
		this.clientVersion++;
		return configurationDataService.newSalaryStrategy(po);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#modifySalaryStrategy(po.configurationdata.SalaryStrategyPO)
	 */
	@Override
	public OperationMessage modifySalaryStrategy(SalaryStrategyPO salaryStrategy)
			throws RemoteException {
		salary.removeIf(sal->{return sal.getStaff().equals(salaryStrategy.getStaff());});
		salary.add(salaryStrategy);
		this.clientVersion++;
		return configurationDataService.modifySalaryStrategy(salaryStrategy);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#getPack()
	 */
	@Override
	public PackPO getPack() throws RemoteException {
		return this.pack;
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#newPack(po.configurationdata.PackPO)
	 */
	@Override
	public OperationMessage newPack(PackPO po) throws RemoteException {
		this.pack=po;
		this.clientVersion++;
		return configurationDataService.newPack(po);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#modifyPack(po.configurationdata.PackPO)
	 */
	@Override
	public OperationMessage modifyPack(PackPO pack) throws RemoteException {
		this.pack=pack;
		this.clientVersion++;
		return configurationDataService.modifyPack(pack);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#getPrice()
	 */
	@Override
	public PricePO getPrice() throws RemoteException {
		return this.price;
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#newPrice(po.configurationdata.PricePO)
	 */
	@Override
	public OperationMessage newPrice(PricePO po) throws RemoteException {
		this.price=po;
		this.clientVersion++;
		return configurationDataService.newPrice(po);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#modifyPrice(po.configurationdata.PricePO)
	 */
	@Override
	public OperationMessage modifyPrice(PricePO price) throws RemoteException {
		this.price=price;
		this.clientVersion++;
		return configurationDataService.modifyPrice(price);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#getProportion()
	 */
	@Override
	public ProportionPO getProportion() throws RemoteException {
		return this.proportion;
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#modifyProportion(po.configurationdata.ProportionPO)
	 */
	@Override
	public OperationMessage modifyProportion(ProportionPO proportion)
			throws RemoteException {
		this.proportion=proportion;
		this.clientVersion++;
		return configurationDataService.modifyProportion(proportion);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#newProportion(po.configurationdata.ProportionPO)
	 */
	@Override
	public OperationMessage newProportion(ProportionPO po)
			throws RemoteException {
		this.proportion=po;
		this.clientVersion++;
		return configurationDataService.newProportion(po);
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#getWarningline(java.lang.String)
	 */
	@Override
	public double getWarningline(String centerID) throws RemoteException {
		return this.warningLine;
	}

	/* (non-Javadoc)
	 * @see rmi.configurationdata.ConfigurationDataService#setWarningline(java.lang.String, double)
	 */
	@Override
	public OperationMessage setWarningline(String centerID, double value)
			throws RemoteException {
		this.warningLine=value;
		this.clientVersion++;
		return configurationDataService.setWarningline(centerID, value);
	}
	/* (non-Javadoc)
	 * @see bl.clientNetCache.dataProxy.DataProxy#dealOperation(java.util.List)
	 */
	@Override
	protected void dealOperation(List<Operation> operations) {
		for (Operation operation : operations) {
			
		}
		
	}

}
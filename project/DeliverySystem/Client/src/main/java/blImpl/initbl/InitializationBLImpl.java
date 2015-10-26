package blImpl.initbl;

import blService.financeblService.BankAccountBLService;
import blService.initblService.InitializationBLService;
import blService.manageblService.ManageblCarService;
import blService.manageblService.ManageblCenterService;
import blService.manageblService.ManageblHallService;
import blService.manageblService.ManageblStaffService;
import blService.storeblService.StoreModelBLService;
import message.CheckFormMessage;
import message.OperationMessage;
import model.store.StoreAreaCode;
import model.store.StoreModel;
import po.initialdata.InitialDataPO;
import vo.financevo.BankAccountVO;
import vo.initialdata.InitialDataVO;
import vo.managevo.car.CarVO;
import vo.managevo.institution.CenterVO;
import vo.managevo.institution.HallVO;
import vo.managevo.staff.StaffVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sissel on 2015/10/26.
 */
public class InitializationBLImpl implements InitializationBLService {
    // models
    InitialDataVO dataVO;

    // manageServices
    StoreModelBLService storeService;
    BankAccountBLService bankService;
    ManageblCarService carService;
    ManageblStaffService staffService;
    ManageblCenterService centerService;
    ManageblHallService hallService;

    public List<BankAccountVO> getAllAccounts() {
    	List<BankAccountVO> result =new ArrayList<BankAccountVO>();
    	BankAccountVO stub=new BankAccountVO();
		result.add(stub);
		return result;
    }

    public List<BankAccountVO> filterAccounts(List<BankAccountVO> list, String s) {
    	List<BankAccountVO> result =new ArrayList<BankAccountVO>();
    	BankAccountVO stub=new BankAccountVO();
		result.add(stub);
		return result;
    }

    public OperationMessage addAccount(BankAccountVO avo) {
        return new OperationMessage();
    }

    public OperationMessage deleteAccount(BankAccountVO avo) {
        return new OperationMessage();
    }

    public OperationMessage editAccount(BankAccountVO avo, String newName) {
        return new OperationMessage();
    }

    public List<StoreModel> getAllStoreModels() {
    	List<StoreModel> result =new ArrayList<StoreModel>();
    	StoreModel stub=new StoreModel();
		result.add(stub);
		return result;
    }

    public OperationMessage selectStoreModel(StoreModel model) {
        return new OperationMessage();
    }

    public OperationMessage reducePartition(StoreAreaCode area, int number) {
        return new OperationMessage();
    }

    public OperationMessage expandPartition(StoreAreaCode area, int number) {
        return new OperationMessage();
    }

    public OperationMessage deleteRow(StoreAreaCode area, int rowNum, boolean confirmed) {
        return new OperationMessage();
    }

    public OperationMessage addRow(StoreAreaCode area, int initCapacity) {
        return new OperationMessage();
    }

    public OperationMessage adjustRow(StoreAreaCode area, int rowNum, int newCapacity, boolean confirmed) {
        return new OperationMessage();
    }

    public List<CarVO> getAllCars() {
    	List<CarVO> result =new ArrayList<CarVO>();
    	CarVO stub=new CarVO();
		result.add(stub);
		return result;
    }

    public List<CarVO> filterCarsByHall(String hallID) {
    	List<CarVO> result =new ArrayList<CarVO>();
    	CarVO stub=new CarVO();
		result.add(stub);
		return result;
    }

    public OperationMessage addCar(CarVO car) {
        return new OperationMessage();
    }

    public OperationMessage modifyCar(CarVO car) {
        return new OperationMessage();
    }

    public OperationMessage deleteCar(CarVO car) {
        return new OperationMessage();
    }

    public List<StaffVO> getAllStaffs() {
    	List<StaffVO> result =new ArrayList<StaffVO>();
    	StaffVO stub=new StaffVO();
		result.add(stub);
		return result;
    }

    public List<StaffVO> filterStaffsByHall(String hallID) {
    	List<StaffVO> result =new ArrayList<StaffVO>();
    	StaffVO stub=new StaffVO();
		result.add(stub);
		return result;
    }

    public OperationMessage modifyStaff(StaffVO after) {
        return new OperationMessage();
    }

    public OperationMessage addStaff(StaffVO staff) {
        return new OperationMessage();
    }

    public OperationMessage deleteStaff(StaffVO staff) {
        return new OperationMessage();
    }

    public List<CenterVO> getAllCenters() {
    	List<CenterVO> result =new ArrayList<CenterVO>();
    	CenterVO stub=new CenterVO();
		result.add(stub);
		return result;
    }

    public List<CenterVO> filterCentersByNumber(String number) {
    	List<CenterVO> result =new ArrayList<CenterVO>();
    	CenterVO stub=new CenterVO();
		result.add(stub);
		return result;
    }

    public OperationMessage addCenter(CenterVO center) {
        return new OperationMessage();
    }

    public OperationMessage deleteCenter(CenterVO center) {
        return new OperationMessage();
    }

    public OperationMessage modifyCenter(CenterVO center) {
        return new OperationMessage();
    }

    public List<HallVO> getAllHalls() {
    	List<HallVO> result =new ArrayList<HallVO>();
    	HallVO stub=new HallVO();
		result.add(stub);
		return result;
    }

    public List<HallVO> filterHallsByNumber(String number) {
    	List<HallVO> result =new ArrayList<HallVO>();
    	HallVO stub=new HallVO();
		result.add(stub);
		return result;
    }

    public OperationMessage addHall(HallVO center) {
        return new OperationMessage();
    }

    public OperationMessage deleteHall(HallVO center) {
        return new OperationMessage();
    }

    public OperationMessage modifyHall(HallVO center) {
        return new OperationMessage();
    }

    public InitialDataVO getInitialDataVO(String version) {
        return new InitialDataVO();
    }

    public OperationMessage requestInitData() {
        return new OperationMessage();
    }

    public OperationMessage uploadInitialData() {
        return new OperationMessage();
    }

    public OperationMessage abortInitData() {
        return new OperationMessage();

    }
}

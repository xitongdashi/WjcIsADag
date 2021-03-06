package bl.blImpl.transportbl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import tool.draft.DraftController;
import tool.vopo.VOPOFactory;
import vo.transitvo.LoadVO;
import bl.blService.transportblService.TransportHallBLService;

/** 
 * @author Wjc
 * @date 2015年11月16日
 * @version 1.0 
 */
public class TestTransportHall {
	TransportHallBLService transportHallBLService;
	ArrayList<LoadVO> l;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		transportHallBLService = new TransportHallBLImpl(new VOPOFactory() ,new DraftController());
		l = new ArrayList<LoadVO>();
		LoadVO[] centerout= new LoadVO[3];
		centerout[0] = new LoadVO("11", "111111", "吴平", "20", Calendar.getInstance(), "111111", "北京", "人1",null,null);
		centerout[1] = new LoadVO("11", "111111", "孟平", "30", Calendar.getInstance(), "111111", "北京", "人2",null,null);
		centerout[2] = new LoadVO("11", "111111", "王平", "25", Calendar.getInstance(), "111111", "北京", "人3",null,null);
		for (int i = 0; i < centerout.length; i++) {
			l.add(centerout[i]);
		}
	}
	
	
	@Test
	public void testSubmit(){
		assertTrue(transportHallBLService.submit(l.get(0)).operationResult);
		assertTrue(transportHallBLService.submit(l.get(1)).operationResult);
		assertTrue(transportHallBLService.submit(l.get(2)).operationResult);
	}
	
	@Test
	public void testNewID(){
		assertNotNull(transportHallBLService.newID());
	}
	
	@Test
	public void testGetDrivers(){
		assertNotNull(transportHallBLService.getDrivers("111111"));
	}
	
	@Test
	public void testGetCars(){
		assertNotNull(transportHallBLService.getCars("111111"));
	}
	
	@Test
	public void testGetLocation(){
		assertNotNull(transportHallBLService.getLocation("111111"));
	}
	
	@Test
	public void testGetOrder(){
		assertNotNull(transportHallBLService.getOrder("111111"));
	}
}

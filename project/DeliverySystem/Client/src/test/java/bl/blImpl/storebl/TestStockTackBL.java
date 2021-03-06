package bl.blImpl.storebl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bl.blService.storeblService.StockTackBLService;

/** 
 * @author Wjc
 * @date 2015年11月16日
 * @version 1.0 
 */
public class TestStockTackBL {
	StockTackBLService stockTackBLService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		stockTackBLService = new StockTackBLImpl();
	}
	
	@Test
	public void testGetStockTack(){
		assertNotNull(stockTackBLService.getStockTack(null));
	}
	
	@Test
	public void testGetOrder(){
		assertNotNull(stockTackBLService.getOrder("111111"));
	}
	
	@Test
	public void testGetStoreInVO(){
		assertNotNull(stockTackBLService.getStoreInVO("111111"));
	}
	
	@Test
	public void testMakeExcel(){
		assertTrue(stockTackBLService.makeExcel("path").operationResult);
	}
}

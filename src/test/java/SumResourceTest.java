import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.mybank.transactionservice.beans.Transaction;
import com.mybank.transactionservice.dao.TransactionDAO;
import com.mybank.transactionservice.resource.SumResource;
import com.mybank.transactionservice.resource.TranactionResource;

import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;


public class SumResourceTest {
	
	private TransactionDAO transactionDAO;
    private SumResource sumResource;
    private TranactionResource transactionResource;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        sumResource = new SumResource();
        transactionResource = new TranactionResource();
        transactionDAO = mock(TransactionDAO.class);
    }

    @After
    public void tearDown() {

    }   
    
    @Test
    public void testGetTotalAmountBasedOnTransactionIdSuccess() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(5000);
        transaction.setType("cars");
        transactionResource.addTransaction(1, transaction);        

        transaction = new Transaction();
        transaction.setAmount(10000);
        transaction.setType("cars");
        transaction.setParentId(1);

        Response result = sumResource.getTotalAmount(1);     
        assertEquals(200, result.getStatus());
    }
    
    @Test
    public void testGetTotalAmountInvalidTransaction() throws Exception {
        long i = 321123;
        Response result = sumResource.getTotalAmount(i);
        assertEquals(404, result.getStatus());
    }

}

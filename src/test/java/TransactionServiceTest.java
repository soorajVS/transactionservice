import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.mybank.transactionservice.beans.Transaction;
import com.mybank.transactionservice.dao.TransactionDAO;
import com.mybank.transactionservice.resource.TranactionResource;

import static org.junit.Assert.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;


public class TransactionServiceTest {

    private TransactionDAO transactionServiceMock;

    private TranactionResource transactionResource;   

    @Before
    public void setUp() {
        transactionResource = new TranactionResource();
        transactionServiceMock = mock(TransactionDAO.class);
    }       


    @Test
    public void testAddNewTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(1000);
        transaction.setType("cars");

        Response result = transactionResource.addTransaction(10,transaction);

        assertEquals(200, result.getStatus());
    }
    
    @Test
    public void testAddTransactionInvalidId() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(0);
        transaction.setParentId(0);
        transaction.setType("cars");

        Response result = transactionResource.addTransaction(0, transaction);

        assertEquals(400, result.getStatus());
    }


    @Test
    public void testGetTransactionNotFound() throws Exception {        
        Response result = transactionResource.getTransactionForId(3);
        assertEquals(404, result.getStatus());
    }
  

    @Test
    public void testGetTransaction() throws Exception {        
        Response result = transactionResource.getTransactionForId(1);
        assertEquals(200, result.getStatus());
    }

}

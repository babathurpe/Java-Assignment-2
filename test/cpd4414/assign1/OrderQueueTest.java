/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.assign1;

import static cpd4414.assign1.OrderQueue.fulfillRequest;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueueTest {

    public OrderQueueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws CustomerException, PurchaseException {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);

        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }

    @Test
    public void testWhenNewOrderAndNoCustomerExistsThenThrowException() throws PurchaseException {
        
        boolean goodOrder = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("", "");
        try {
            orderQueue.add(order);
        }
        catch (CustomerException ex) {
            goodOrder = true;
        }
        assertTrue(goodOrder);
    }
    
    @Test
    public void testWhenNewOrderArrivesAndNoPurchaseExistsThenThrowException() throws CustomerException, PurchaseException {
        boolean goodPurchase = false;
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase(null, 0));
        
        try{
            orderQueue.add(order);
        } catch(PurchaseException ex){
            goodPurchase = true;
        }
        assertTrue(goodPurchase);
    }

    @Test
    public void testWhenNextOrderAndOrdersExistThenGetOrderWithNoTimeProcessed() throws CustomerException, PurchaseException {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0001", 450));
        order1.addPurchase(new Purchase("PROD0007", 250));
        Order order2 = new Order("CUST00002", "DCB Construction");
        order2.addPurchase(new Purchase("PROD0004", 150));
        order2.addPurchase(new Purchase("PROD0006", 850));
        orderQueue.add(order1);
        orderQueue.add(order2);
        order1.setTimeProcessed(new Date());
        order2.setTimeProcessed(null);
        assertEquals(order2.getListOfPurchases(), this);
    }

    @Test
    public void testWhenNextOrderRequestAndOrdersExistThenReturnNull() throws CustomerException, PurchaseException {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0001", 450));
        order1.addPurchase(new Purchase("PROD0007", 250));
        orderQueue.add(order1);
        Object result = orderQueue.nextOrder();
        Object expected = null;
        assertEquals(expected, result);
    }
    
    @Test
    public void testWhenOrderProcessRequestAndOrderHasTimeReceivedThenSetTimeProcessedToNow() throws CustomerException, PurchaseException {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0001", 450));
        order1.addPurchase(new Purchase("PROD0007", 250));
        orderQueue.add(order1);
        order1.setTimeReceived(new Date());
        //order1.setTimeProcessed(new Date());
        
        long expResult = new Date().getTime();
        long result = order1.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void testWhenOrderProcessRequestComesAndOrderHasNoTimeReceivedThenThrowException() throws CustomerException, PurchaseException {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0001", 450));
        order1.addPurchase(new Purchase("PROD0007", 250));
        boolean timeReceived = false;
        try{
            OrderQueue.processOrder(order1);
        } catch(TimeReceivedException ex){
            timeReceived = true;
        }
        assertTrue(timeReceived);
    }
    
    @Test
    public void testWhenOrderProcessCompleteAndOrderIsInStockThenSetTimeProcessedToNow() throws CustomerException, PurchaseException, TimeReceivedException {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0001", 450));
        order1.addPurchase(new Purchase("PROD0007", 250));
        orderQueue.add(order1);
        
        OrderQueue.processOrder(order1);
        order1.setTimeProcessed(new Date());
        order1.setTimeReceived(new Date());
        
        long expResult = new Date().getTime();
        long result = order1.getTimeFulfilled().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
    @Test
    public void testWhenOrderFulfillRequestAndOrderDoesNotHaveTimeProcessedThenThrowException() throws CustomerException, PurchaseException, TimeReceivedException {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0001", 450));
        order1.addPurchase(new Purchase("PROD0007", 250));
        orderQueue.add(order1);
        boolean timeProcessed = false;
        try{
            OrderQueue.processOrder(order1);
        } catch(TimeReceivedException ex){
            timeProcessed = true;
        }
        assertTrue(timeProcessed);
    }
    
    @Test
    public void testWhenOrderFulfillRequestAndOrderDoesNotHaveTimeReceivedThenThrowException() throws CustomerException, PurchaseException, TimeReceivedException, TimeProcessedException {
        OrderQueue orderQueue = new OrderQueue();
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0001", 450));
        order1.addPurchase(new Purchase("PROD0007", 250));
        orderQueue.add(order1);
        boolean timeReceived = false;
        try{
            orderQueue.processOrder(order1);
        } catch(TimeReceivedException ex){
            timeReceived = true;
        }
        assertTrue(timeReceived);
    }
}

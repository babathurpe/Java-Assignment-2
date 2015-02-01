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

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueue {

    Queue<Order> orderQueue = new ArrayDeque<>();
    Order order1 = new Order("CUST00001", "ABC Construction");
    //new Purchase("PROD0007", 250);
    

    public void add(Order order) throws CustomerException, PurchaseException {
        if (order.getListOfPurchases() != null) {
            orderQueue.add(order);
            if (order.getCustomerId().isEmpty() || order.getCustomerName().isEmpty()) {
                throw new CustomerException();
            }
            order.setTimeReceived(new Date());
        } else {
            throw new PurchaseException();
        }

        order.setTimeProcessed(new Date());
    }

    public Order nextOrder() {
        //orderQueue.poll();
        if (orderQueue.peek() == null) {
            return null;
        } else {
            return orderQueue.peek();
        }
    }
    
    public static void processOrder(Order order) throws TimeReceivedException{
        //order.setTimeReceived(new Date());
        if(order.getTimeReceived() == null){
            throw new TimeReceivedException();
        }
        order.setTimeFulfilled(new Date());
    }
    
    public static void fulfillRequest(Order order) throws TimeProcessedException{
        if(order.getTimeProcessed() == null){
            throw new TimeProcessedException();
        }
        //order.setTimeFulfilled(new Date());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpd4414.assign1;

/**
 *
 * @author Babathurpe
 */
public class TimeReceivedException extends Exception {

    public TimeReceivedException() {
        super("Order does not have a time received.");
    }
    
    
}

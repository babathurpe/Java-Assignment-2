/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cpd4414.assign1;

/**
 *
 * @author c0642703
 */
public class PurchaseException extends Exception {

    public PurchaseException() {
        super("No purchase in this order!");
    }
}

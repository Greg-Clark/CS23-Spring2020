/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eccbankatm;

/**
 *
 * @author Greg Clark
 */
public class Deposit extends Transaction
{
    private double cashAmount; // cash amount for each deposit
    private double checkAmount; // check amount for each deposit

    public Deposit(int tCount, int tId, double tAmt, double cashAmount,
        double checkAmount) {
        super(tCount, tId, tAmt);
        this.cashAmount = cashAmount;
        this.checkAmount = checkAmount;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public double getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(double checkAmount) {
        this.checkAmount = checkAmount;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eccbank;

/**
 *
 * @author sport
 */
public class Check extends Transaction
{
    private int checkNumber; // check number for each check transaction

    public Check(int tCount, int tId, double tAmt, int checkNumber) {
        super(tCount, tId, tAmt);
        this.checkNumber = checkNumber;
    }

    public int getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(int checkNumber) {
        this.checkNumber = checkNumber;
    }
}

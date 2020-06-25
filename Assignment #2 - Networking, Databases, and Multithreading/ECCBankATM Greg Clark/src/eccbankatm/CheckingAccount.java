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
import java.util.ArrayList;

public class CheckingAccount extends Account {
    private ArrayList<Transaction> transList;  // keeps a list of Transaction objects for the account
    private int transCount;   // the count of Transaction objects and used as the ID for each transaction
    private double totalServiceCharge;
    private boolean below500;

    public CheckingAccount(String name, double initialBalance)
    {
        super(name, initialBalance);
        totalServiceCharge = 0;
        transList = new ArrayList<Transaction>();
        transCount = 0;
    }

    public double getBalance()
    {
       return balance;
    }

    public void setBalance(double transAmt, int tCode)
    {
        if (tCode == 1)
            balance -= transAmt;
        else //if (tCode == 2)
            balance += transAmt;
    }

    public double getServiceCharge()
    {
        return totalServiceCharge;
    }

    public void setServiceCharge(double currentServiceCharge)
    {
        totalServiceCharge += currentServiceCharge;
    }

    public void addTrans(Transaction newTrans)   // adds a transaction object to the transList
    {
        transList.add(newTrans);
        transCount++;
    }

    public int getTransCount()  //returns the current value of transCount
    {
        return transCount;
    }

    public Transaction getTrans(int i) // returns the i-th Transaction object in the list
    {
        return transList.get(i);
    }

    public boolean getBelow500() {
        return below500;
    }

    public void setBelow500(boolean below500) {
        this.below500 = below500;
    }
}

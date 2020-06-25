package com.example.cs23assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {
    private Vector<CheckingAccount> caVector = new Vector<CheckingAccount>();
    private int accountNumber = 0;
    private CheckingAccount ca = null;
    private Transaction t = null;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.accountTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.nameEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.balanceTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.balanceEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.okButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.cancelButton).setVisibility(View.INVISIBLE);

        findViewById(R.id.transCashDepositTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCheckTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCodeTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCheckTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCheckDepositAmtTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCheckAmtTextView).setVisibility(View.INVISIBLE);

        findViewById(R.id.transCashDepositEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCheckEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCheckDepositAmtEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCheckAmtEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCodeEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.okButtonTrans).setVisibility(View.INVISIBLE);
        findViewById(R.id.cancelButtonTrans).setVisibility(View.INVISIBLE);
        findViewById(R.id.okButtonTrans1).setVisibility(View.INVISIBLE);
        findViewById(R.id.okButtonTrans2).setVisibility(View.INVISIBLE);

        findViewById(R.id.accountSearchTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.accountSearchEditText).setVisibility(View.INVISIBLE);
        findViewById(R.id.okSearchButton).setVisibility(View.INVISIBLE);

        String[] arraySpinner = new String[] { "Accounts", "Add An Account", "List All Transactions", "List All Checks",
                "List All Deposits", "List All Service Charges", "Find An Account", "List All Accounts"};
        Spinner dropdownAcct = findViewById(R.id.spinnerAccounts);
        ArrayAdapter<String> adapterAcct = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapterAcct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownAcct.setAdapter(adapterAcct);

        String[] transArraySpinner = new String[] { "Transactions", "Enter Transaction"};

        Spinner dropdownTrans = findViewById(R.id.spinnerTransactions);
        ArrayAdapter<String> adapterTrans = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, transArraySpinner);
        adapterTrans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownTrans.setAdapter(adapterTrans);

        dropdownAcct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if(s == "Add An Account")
                    addAccount();
                else if(s == "List All Transactions")
                    listAllTransactions();
                else if(s == "List All Checks")
                    listAllChecks();
                else if(s == "List All Deposits")
                    listAllDeposits();
                else if(s == "List All Service Charges")
                    listAllServiceCharges();
                else if(s == "Find An Account")
                    findAccount();
                else if(s == "List All Accounts")
                    listAllAccounts();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dropdownTrans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if(s == "Enter Transaction")
                {
                    enterTransaction();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addAccount()
    {
        findViewById(R.id.titleTextView).setVisibility(View.INVISIBLE);
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setVisibility(View.INVISIBLE);
        account.setSelection(0);
        trans.setVisibility(View.INVISIBLE);
        trans.setSelection(0);



        findViewById(R.id.accountTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.nameEditText).setVisibility(View.VISIBLE);
        findViewById(R.id.balanceTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.balanceEditText).setVisibility(View.VISIBLE);
        findViewById(R.id.okButton).setVisibility(View.VISIBLE);
        findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);


        Button cancel = findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes back to main activity
                EditText editText1 = findViewById(R.id.nameEditText);
                editText1.getText().clear();
                EditText editText2 = findViewById(R.id.balanceEditText);
                editText2.getText().clear();

                findViewById(R.id.accountTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.nameEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.balanceTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.balanceEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.okButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.cancelButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.accountSearchTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.accountSearchEditText).setVisibility(View.INVISIBLE);


                findViewById(R.id.titleTextView).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerAccounts).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerTransactions).setVisibility(View.VISIBLE);
            }
        });

        Button ok = findViewById(R.id.okButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.nameEditText);
                String name = editText1.getText().toString();

                if(name.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Name", Toast.LENGTH_LONG);
                    toast.show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    return;
                }

                EditText editText2 = findViewById(R.id.balanceEditText);
                String balanceString = editText2.getText().toString();

                if(balanceString.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Balance", Toast.LENGTH_LONG);
                    toast.show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    return;
                }

                double balance = Double.parseDouble(balanceString);

                Context context = getApplicationContext();
                CharSequence text = "New account added for " + name;
                int duration = Toast.LENGTH_SHORT;

                if(ca != null)
                {
                    caVector.setElementAt(ca, accountNumber);
                }
                ca = new CheckingAccount (name, balance);
                ca.setBelow500(false);

                caVector.addElement(ca);
                accountNumber = caVector.size() - 1;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                toast.setGravity(Gravity.TOP, 0, 300);

                //goes back to main activity
                editText1.getText().clear();
                editText2.getText().clear();

                findViewById(R.id.accountTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.nameEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.balanceTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.balanceEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.okButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.cancelButton).setVisibility(View.INVISIBLE);

                findViewById(R.id.titleTextView).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerAccounts).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerTransactions).setVisibility(View.VISIBLE);
            }
        });
    }

    private void listAllTransactions()
    {
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setSelection(0);
        trans.setSelection(0);

        if (ca == null) {
            nullAccount();
            return;
        }
        String message = "List All Transactions\nName: " + ca.getName()
                + "\nBalance: " + nf.format(ca.getBalance())
                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                + "\n\nID     Type      Amount\n";
        for (int i = 0; i < ca.getTransCount(); i++) {
            message += String.format("%-7d", i);
            switch (ca.getTrans(i).getTransId()) {
                case 0:
                    message += "Svc. Chg. ";
                    break;
                case 1:
                    message += "Check     ";
                    break;
                case 2:
                    message += "Deposit   ";
                    break;
            }
            message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
        toast.setGravity(Gravity.CENTER, 0, 0);

    }

    private void listAllChecks()
    {
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setSelection(0);
        trans.setSelection(0);

        if (ca == null) {
            nullAccount();
            return;
        }

        String message = "List All Checks\nName: " + ca.getName()
                + "\nBalance: " + nf.format(ca.getBalance())
                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                + "\n\nID     Check     Amount\n";
        for (int i = 0; i < ca.getTransCount(); i++) {
            if (ca.getTrans(i).getTransId() == 1) {
                Check c = (Check) ca.getTrans(i);
                message += String.format("%-7d", i);
                message += String.format("%-10d", c.getCheckNumber());
                message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
            }
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    private void listAllDeposits()
    {
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setSelection(0);
        trans.setSelection(0);

        if (ca == null) {
            nullAccount();
            return;
        }

        String message = "List All Deposits\nName: " + ca.getName()
                + "\nBalance: " + nf.format(ca.getBalance())
                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                + "\n\nID     Cash        Check        Amount\n";
        for (int i = 0; i < ca.getTransCount(); i++) {
            if (ca.getTrans(i).getTransId() == 2) {
                Deposit d = (Deposit) ca.getTrans(i);
                message += String.format("%-7d", i);
                message += String.format("$%-10.2f ", d.getCashAmount());
                message += String.format("$%-10.2f  ", d.getCheckAmount());
                message += String.format("$%-10.2f\n", ca.getTrans(i).getTransAmount());
            }
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    private void listAllServiceCharges()
    {
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setSelection(0);
        trans.setSelection(0);

        if (ca == null) {
            nullAccount();
            return;
        }
        String message = "List All Service Charges\nName: " + ca.getName()
                + "\nBalance: " + nf.format(ca.getBalance())
                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                + "\n\nID     Amount\n";
        for (int i = 0; i < ca.getTransCount(); i++) {
            if (ca.getTrans(i).getTransId() == 0) {
                message += String.format("%-7d", i);
                message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
            }
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    private void findAccount()
    {
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setSelection(0);
        trans.setSelection(0);

        if (ca == null) {
            nullAccount();
            return;
        }

        findViewById(R.id.titleTextView).setVisibility(View.INVISIBLE);
        account.setVisibility(View.INVISIBLE);
        trans.setVisibility(View.INVISIBLE);

        findViewById(R.id.accountSearchTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.accountSearchEditText).setVisibility(View.VISIBLE);
        findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);
        findViewById(R.id.okSearchButton).setVisibility(View.VISIBLE);

        Button ok = findViewById(R.id.okSearchButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchName = findViewById(R.id.accountSearchEditText);
                String name = searchName.getText().toString();

                searchName.getText().clear();

                boolean found = false;
                for (int index = 0; index < caVector.size(); index++)
                {
                    CheckingAccount c = caVector.elementAt(index);

                    // check on the name of the account
                    if (name.equals(c.getName())) {
                        name = "Found account for " + name;

                        ca = caVector.elementAt(index);
                        accountNumber = index;
                        found = true;
                    }
                }

                if(!found)
                    name = "Account not found for " + name;

                Toast toast = Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG);
                toast.show();
                toast.setGravity(Gravity.CENTER, 0, 0);

                findViewById(R.id.titleTextView).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerAccounts).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerTransactions).setVisibility(View.VISIBLE);

                findViewById(R.id.accountSearchTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.accountSearchEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.cancelButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.okSearchButton).setVisibility(View.INVISIBLE);
            }
        });
    }

    private void listAllAccounts()
    {
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setSelection(0);
        trans.setSelection(0);

        if (ca == null) {
            nullAccount();
            return;
        }
        String message = "List of All Accounts:\n\n";
        for (int index = 0; index < caVector.size(); index++)
        {
            message += "Name: " + caVector.elementAt(index).getName()
                    + "\nBalance: " + nf.format(caVector.elementAt(index).getBalance())
                    + "\nTotal Service Charge: " + nf.format(caVector.elementAt(index).getServiceCharge())
                    + "\n\n";
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    private void enterTransaction()
    {
        Spinner account = findViewById(R.id.spinnerAccounts);
        Spinner trans = findViewById(R.id.spinnerTransactions);
        account.setSelection(0);
        trans.setSelection(0);

        if (ca == null) {
            nullAccount();
            return;
        }
        findViewById(R.id.titleTextView).setVisibility(View.INVISIBLE);
        account.setVisibility(View.INVISIBLE);
        trans.setVisibility(View.INVISIBLE);

        findViewById(R.id.transCodeTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.transCodeEditText).setVisibility(View.VISIBLE);
        findViewById(R.id.okButtonTrans).setVisibility(View.VISIBLE);
        findViewById(R.id.cancelButtonTrans).setVisibility(View.VISIBLE);

        Button cancel = findViewById(R.id.cancelButtonTrans);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.transCodeEditText);
                editText1.getText().clear();

                findViewById(R.id.titleTextView).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerAccounts).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerTransactions).setVisibility(View.VISIBLE);

                findViewById(R.id.transCodeTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCodeEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.okButtonTrans).setVisibility(View.INVISIBLE);
                findViewById(R.id.cancelButtonTrans).setVisibility(View.INVISIBLE);
                findViewById(R.id.okButtonTrans1).setVisibility(View.INVISIBLE);
                findViewById(R.id.okButtonTrans2).setVisibility(View.INVISIBLE);

                findViewById(R.id.transCheckTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckAmtTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckAmtEditText).setVisibility(View.INVISIBLE);

                findViewById(R.id.transCashDepositTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCashDepositEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckDepositAmtTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckDepositAmtEditText).setVisibility(View.INVISIBLE);
            }
        });

        Button ok = findViewById(R.id.okButtonTrans);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.transCodeEditText);
                String transCodeString = editText1.getText().toString();

                double transCode = -1;
                if(!transCodeString.equals(""))
                {
                    transCode = Double.parseDouble(transCodeString);
                }


                editText1.getText().clear();
                if(transCode == 0)
                {
                    findViewById(R.id.titleTextView).setVisibility(View.VISIBLE);
                    findViewById(R.id.spinnerAccounts).setVisibility(View.VISIBLE);
                    findViewById(R.id.spinnerTransactions).setVisibility(View.VISIBLE);

                    findViewById(R.id.transCodeTextView).setVisibility(View.INVISIBLE);
                    findViewById(R.id.transCodeEditText).setVisibility(View.INVISIBLE);
                    findViewById(R.id.okButtonTrans).setVisibility(View.INVISIBLE);
                    findViewById(R.id.cancelButtonTrans).setVisibility(View.INVISIBLE);
                }
                else if(transCode == 1)
                {
                    transCode1();
                }
                else if(transCode == 2)
                {
                    transCode2();
                }
                else
                {
                    String text = "Invalid Transaction Code";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                }
            }
        });
    }



    private void transCode1()
    {
        findViewById(R.id.okButtonTrans).setVisibility(View.INVISIBLE);

        findViewById(R.id.transCodeTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCodeEditText).setVisibility(View.INVISIBLE);

        findViewById(R.id.okButtonTrans1).setVisibility(View.VISIBLE);
        findViewById(R.id.transCheckTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.transCheckEditText).setVisibility(View.VISIBLE);
        findViewById(R.id.transCheckAmtTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.transCheckAmtEditText).setVisibility(View.VISIBLE);

        Button ok = findViewById(R.id.okButtonTrans1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText checkNumEditText = findViewById(R.id.transCheckEditText);
                String checkNumString = checkNumEditText.getText().toString();
                int checkNum = -1;
                if(!checkNumString.equals(""))
                    checkNum = Integer.parseInt(checkNumString);



                EditText checkAmt = findViewById(R.id.transCheckAmtEditText);
                String checkAmtString = checkAmt.getText().toString();
                double amt = -1;
                if(!checkAmtString.equals(""))
                    amt = Double.parseDouble(checkAmtString);

                checkNumEditText.getText().clear();
                checkAmt.getText().clear();

                if(amt < 0 || checkNum < 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT);
                    toast.show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    return;
                }
                ca.setBalance(amt, 1);
                t = new Check(ca.getTransCount(), 1, amt, checkNum);
                ca.addTrans(t);
                ca.setServiceCharge(0.15);
                t = new Transaction(ca.getTransCount(), 0, 0.15);
                ca.addTrans(t);
                String message = ca.getName() + "'s Account\nTransaction: Check #"
                        + checkNum + " in Amount of " + nf.format(amt)
                        + "\nCurrent Balance: " + nf.format (ca.getBalance())
                        + "\nService Charge: Check --- charge $0.15";
                if (ca.getBalance() < 500 && !(ca.getBelow500())) {
                    ca.setServiceCharge(5.00);
                    ca.setBelow500(true);
                    message += "\nServiceCharge: Below $500 --- charge $5.00";
                    t = new Transaction(ca.getTransCount(), 0, 5.00);
                    ca.addTrans(t);
                }
                if (ca.getBalance() < 50) {
                    message += "\nWarning: Balance below $50";
                }
                if (ca.getBalance() < 0) {
                    ca.setServiceCharge(10.00);
                    message += "\nServiceCharge: Below $0 --- charge $10.00";
                    t = new Transaction(ca.getTransCount(), 0, 10.00);
                    ca.addTrans(t);
                }

                message += "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());

                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.show();
                toast.setGravity(Gravity.CENTER, 0, 0);

                findViewById(R.id.okButtonTrans1).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckAmtTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckAmtEditText).setVisibility(View.INVISIBLE);

                findViewById(R.id.titleTextView).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerAccounts).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerTransactions).setVisibility(View.VISIBLE);
            }
        });
    }

    private void transCode2()
    {
        findViewById(R.id.okButtonTrans).setVisibility(View.INVISIBLE);

        findViewById(R.id.transCodeTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.transCodeEditText).setVisibility(View.INVISIBLE);

        findViewById(R.id.transCashDepositTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.transCashDepositEditText).setVisibility(View.VISIBLE);
        findViewById(R.id.transCheckDepositAmtTextView).setVisibility(View.VISIBLE);
        findViewById(R.id.transCheckDepositAmtEditText).setVisibility(View.VISIBLE);
        findViewById(R.id.okButtonTrans2).setVisibility(View.VISIBLE);

        Button ok = findViewById(R.id.okButtonTrans2);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cashEditText = findViewById(R.id.transCashDepositEditText);
                String cashString = cashEditText.getText().toString();
                double cashAmount = 0;
                if(!cashString.equals(""))
                {
                    cashAmount = Double.parseDouble(cashString);
                }

                EditText checkEditText = findViewById(R.id.transCheckDepositAmtEditText);
                String checkString = checkEditText.getText().toString();

                cashEditText.getText().clear();
                checkEditText.getText().clear();

                double checkAmount = 0;
                if(!checkString.equals(""))
                    checkAmount = Double.parseDouble(checkString);

                double amount = cashAmount + checkAmount;

                if(checkAmount < 0 || cashAmount < 0 || amount <= 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid input", Toast.LENGTH_SHORT);
                    toast.show();
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    return;
                }


                ca.setBalance(amount, 2);
                t = new Deposit(ca.getTransCount(), 2, amount, cashAmount, checkAmount);
                ca.addTrans(t);
                ca.setServiceCharge(0.1);
                t = new Transaction(ca.getTransCount(), 0, 0.1);
                ca.addTrans(t);


                String message = ca.getName()
                        + "'s Account\nTransaction: Deposit in Amount of " + nf.format(amount)
                        + "\nCurrent Balance: " + nf.format (ca.getBalance())
                        + "\nService Charge: Deposit --- charge $0.10"
                        + "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());

                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toast.show();
                toast.setGravity(Gravity.CENTER, 0, 0);

                findViewById(R.id.transCashDepositTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCashDepositEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckDepositAmtTextView).setVisibility(View.INVISIBLE);
                findViewById(R.id.transCheckDepositAmtEditText).setVisibility(View.INVISIBLE);
                findViewById(R.id.okButtonTrans2).setVisibility(View.INVISIBLE);
                findViewById(R.id.cancelButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.cancelButtonTrans).setVisibility(View.INVISIBLE);

                findViewById(R.id.titleTextView).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerAccounts).setVisibility(View.VISIBLE);
                findViewById(R.id.spinnerTransactions).setVisibility(View.VISIBLE);
            }
        });
    }

    private void nullAccount()
    {
        String text = "Account not selected\nYou must select an account first";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        toast.setGravity(Gravity.TOP, 0, 300);
    }
}

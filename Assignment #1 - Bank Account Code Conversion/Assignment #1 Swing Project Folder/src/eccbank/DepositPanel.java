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
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DepositPanel {
    double cashAmount;
    double checkAmount;

    public DepositPanel() {
        JTextField cashField = new JTextField(5);
        JTextField checkField = new JTextField(5);

        JPanel depositPanel = new JPanel();
        depositPanel.add(new JLabel("Cash"));
        depositPanel.add(cashField);
        depositPanel.add(new JLabel("Checks"));
        depositPanel.add(checkField);
        depositPanel.setLayout(new GridLayout(4,1));

        int result = JOptionPane.showConfirmDialog(null, depositPanel, 
            "Deposit Window", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (cashField.getText().equals("")) {
                cashAmount = 0.0;
            }
            else {
                cashAmount = Double.parseDouble(cashField.getText());
            }
            if (checkField.getText().equals("")) {
                checkAmount = 0.0;
            }
            else {
                checkAmount = Double.parseDouble(checkField.getText());
            }
        }
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public double getCheckAmount() {
        return checkAmount;
    }
}

// Greg Clark
// CS 23, Section #0131
// Assignment #0, Problem #1
// Prints out the salaries of varying athletes

package com.GregClark;


import java.text.DecimalFormat;

public class MainTwo {

    private static DecimalFormat df = new DecimalFormat("#.##");

    public static void printSalary(Athlete a)
    {
        System.out.println("Yearly Salary: " + df.format(a.getSalary())
        + "\nSalary per game: " + df.format(a.salaryPerGame()));
    }

    public static void main(String[] args) {
        Athlete soccer = new SoccerPlayer("Greg", 1000000);
        printSalary(soccer);
    }
}

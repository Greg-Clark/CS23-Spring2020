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
        Athlete baseball = new BaseballPlayer("Greg", 1000000);
        Athlete basketball = new BasketballPlayer("Greg", 1000000);
        Athlete football = new FootballPlayer("Greg", 1000000);

        System.out.println("Soccer Player:");
        printSalary(soccer);
        System.out.println("\nBaseball Player:");
        printSalary(baseball);
        System.out.println("\nBasketball Player:");
        printSalary(basketball);
        System.out.print("\nFootball Player:");
        printSalary(football);
    }
}

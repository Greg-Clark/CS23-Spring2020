// Greg Clark
// CS 23, Section #0131
// Assignment #0, Problem #1
// Prints out the salaries of varying athletes

package com.GregClark;


public class Athlete {
    protected String name;
    protected double salary;

    protected int numGames;

    public Athlete()
    {
        name = "";
        salary = 0.0;
    }

    public Athlete(String n, double sal)
    {
        name = n;
        salary = sal;
    }

    public double salaryPerGame()
    {
        return salary/numGames;
    }

    public double getSalary()
    {
        return salary;
    }
}

// Greg Clark
// CS 23, Section #0131
// Assignment #0, Problem #1
// Prints out the salaries of varying athletes

package com.GregClark;

public class BaseballPlayer extends Athlete{
    public BaseballPlayer()
    {
        super();
        numGames = 162;
    }
    public BaseballPlayer(String n, double sal)
    {
        super(n, sal);
        numGames = 162;
    }
}

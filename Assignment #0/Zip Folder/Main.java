// Greg Clark
// CS 23, Section #0131
// Assignment #0, Problem #1
// Creates a rectangle with a point inside of it

package com.company;

import java.util.Scanner;

public class Main {

    //takes rectangle input from user and prints out results
    public static void inputData()
    {
        double tempX, tempY, tempW, tempH;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter x coordinate of the rectangle: ");
        tempX = s.nextDouble();
        System.out.print("Enter y coordinate of the rectangle: ");
        tempY = s.nextDouble();
        System.out.print("Enter width of the rectangle: ");
        tempW = s.nextDouble();
        System.out.print("Enter height of the rectangle: ");
        tempH = s.nextDouble();

        Rectangle rect = new Rectangle(tempX, tempY, tempW, tempH);
        rect.displayResults();
    }


    public static void main(String[] args) {
            Rectangle r1 = new Rectangle();
            Rectangle r2 = new Rectangle(3, 4, 4, 40);
            Rectangle r3 = new Rectangle(4, 5, 3, 10);
            Rectangle r4 = new Rectangle(-1.2, 3.6, 3.5, 35.9);
            Rectangle r5 = new Rectangle(2, 10, -5, -3);

            char repeat = 'Y';
            Scanner scan = new Scanner(System.in);


            while(repeat == 'y' || repeat == 'Y')
            {
                inputData();
                System.out.println("Would you like to enter another triangle? Y/N ");
                repeat = scan.next().charAt(0);
            }
//            r1.displayResults();
//            r2.displayResults();
//            r3.displayResults();
//            r4.displayResults();
//            r5.displayResults();


//            System.out.println(r2.contains(r3));
//            System.out.println(r2.contains(r4));
//            System.out.println(r2.overlaps(r4));
//            System.out.println(r4.contains(r5));

//        System.out.println(r2.contains(3,10));
//        System.out.println(r2.contains(r5));
//        System.out.println(r2.overlaps(r3));
//        System.out.println(r4.contains(0,0));


    }
}

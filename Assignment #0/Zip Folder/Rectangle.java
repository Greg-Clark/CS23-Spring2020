// Greg Clark
// CS 23, Section #0131
// Assignment #0, Problem #1
// Creates a rectangle with a point inside of it

package com.company;

import org.w3c.dom.css.Rect;

public class Rectangle {
    private double x, y, width, height;
    //getters for private data members
    public double getX() {return x;}
    public double getY() {return y;}
    public double getWidth() {return width;}
    public double getHeight() {return height;}

    //default constructor for rectangle
    public Rectangle() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }
    //constructor for rectangle
    public Rectangle(double tempX, double tempY, double w, double h)
    {
        x = tempX;
        y = tempY;
        width = w;
        height = h;
        if(w < 0)
            width = 0;
        if(h < 0)
            height = 0;
    }
    //gets area of rectangle
    public double getArea()
    {
        return width * height;
    }
    //gets perimeter of rectangle
    public double getPerimeter()
    {
        return 2*height + 2*width;
    }

    //checks to see if point is inside rectangle
    public boolean contains(double tempX, double tempY)
    {
        if(tempX > (x + width/2) || tempX < (x - width/2))
            return false;
        if(tempY > (y + height/2) || tempY < (y - height/2))
            return false;
        return true;
    }
    //checks to see if given rectangle is inside this rectangle
    public boolean contains(Rectangle r)
    {
        if((r.getX() + (r.getWidth()/2)) > (x + width/2) || (r.getX() - (r.getWidth()/2)) < (x - width/2))
            return false;
        if((r.getY() + (r.getHeight()/2)) > (y + height/2) || (r.getY() - (r.getHeight()/2)) < (y - height/2))
            return false;
        return true;
    }
    //checks to see if a given rectangle is inside this rectangle
    public boolean overlaps(Rectangle r)
    {
        boolean cornerInside = false;
        boolean cornerOutside = false;
        //checks top left corner
        if(contains(r.getX()-r.getWidth()/2, r.getY() + r.getHeight()/2))
            cornerInside = true;
        else
            cornerOutside = true;
        //checks bottom left corner
        if(contains(r.getX()-r.getWidth()/2, r.getY() - r.getHeight()/2))
            cornerInside = true;
        else
            cornerOutside = true;
        //checks top right corner
        if(contains(r.getX()+r.getWidth()/2, r.getY() + r.getHeight()/2))
            cornerInside = true;
        else
            cornerOutside = true;
        //checks bottom right corner
        if(contains(r.getX()+r.getWidth()/2, r.getY() - r.getHeight()/2))
            cornerInside = true;
        else
            cornerOutside = true;
        return cornerInside && cornerOutside;
    }

    public void displayResults()
    {
        System.out.print("The perimeter of the rectangle is: ");
        System.out.printf("%.2f", getPerimeter());
        System.out.print("\nThe area of the rectangle is: ");
        System.out.printf("%.2f", getArea());
        System.out.println("\n");

    }
}

package hw0;

import java.util.Scanner;

/**
 * Adder asks the user for two ints and computes their sum.
 * 
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
public class Adder {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter first number: ");
        int x = console.nextInt();
        System.out.print("Enter second number: ");
        int y = console.nextInt();
        int sum = computeSum(x, y);
        System.out.println(x + " + " + y + " = " + sum);
    }

    /**
     * 
     * @param x First number to sum.
     * @param y Second number to sum.
     * @return sum of x and y.
     */
    public static int computeSum(int x, int y) {
        return x - y;
    }
}

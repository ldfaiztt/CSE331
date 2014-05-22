package hw0.optional;

public class Primes {

    private static void findPrimes(int nValues) {
        boolean isPrime = true;

        for (int i = 2; i <= nValues; i++) {
            isPrime = true;

            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                System.out.println(i);
            }
        }
    }


    private static void findPrimesFaster(int nValues) {
        // PLACE YOUR IMPLEMENTATION HERE
        System.out.println("The method findPrimesFaster has not been implemented");
    }


    private static void findPrimesEvenFaster(int nValues) {
        // PLACE YOUR IMPLEMENTATION HERE
        System.out.println("The method findPrimesEvenFaster has not been implemented");
    }



    public static void main(String[] args) {

        // Find and print all primes between 0 and 50.

        System.out.println("Running method findPrimes:");
        findPrimes(50);

        System.out.println("\nRunning method findPrimesFaster:");
        findPrimesFaster(50);

        System.out.println("\nRunning method findPrimesEvenFaster:");
        findPrimesEvenFaster(50);



        // Time how long it takes to find primes.

        long startTime = 0;
        long endTime = 0;


        System.out.print("\n\nTiming method findPrimes:");
        startTime = System.currentTimeMillis();
        findPrimes(20000);
        endTime = System.currentTimeMillis();
        System.out.println("  " + (endTime-startTime) + " milliseconds");

        System.out.print("\nTiming method findPrimesFaster:");
        startTime = System.currentTimeMillis();
        findPrimesFaster(20000);
        endTime = System.currentTimeMillis();
        System.out.println("  " + (endTime-startTime) + " milliseconds");

        System.out.print("\nTiming method findPrimesEvenFaster:");
        startTime = System.currentTimeMillis();
        findPrimesEvenFaster(20000);
        endTime = System.currentTimeMillis();
        System.out.println("  " + (endTime-startTime) + " milliseconds");


        System.out.print("\n\nTiming method findPrimes:");
        startTime = System.currentTimeMillis();
        findPrimes(50000);
        endTime = System.currentTimeMillis();
        System.out.println("  " + (endTime-startTime) + " milliseconds");

        System.out.print("\nTiming method findPrimesFaster:");
        startTime = System.currentTimeMillis();
        findPrimesFaster(50000);
        endTime = System.currentTimeMillis();
        System.out.println("  " + (endTime-startTime) + " milliseconds");

        System.out.print("\nTiming method findPrimesEvenFaster:");
        startTime = System.currentTimeMillis();
        findPrimesEvenFaster(50000);
        endTime = System.currentTimeMillis();
        System.out.println("  " + (endTime-startTime) + " milliseconds");
    }

}

package hw0.optional;

public class FinancialCalc {

    public static void main(String[] args) {
        double principal = 1000.00;    // $1000 initial investment
        double interestRate = 0.035;   // 3.5% interest rate
        int numOfYears = 7;            // investment length is 7 years

        double value = 0.0;
        value = principal * Math.pow((1 + interestRate), numOfYears);

        System.out.println("Investing $" + principal +
                " at an interest rate of " + (interestRate*100) + "%" +
                " for " + numOfYears + " years" +
                " will have a final worth of $" + value);
    }

}

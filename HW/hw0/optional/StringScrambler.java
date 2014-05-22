package hw0.optional;

public class StringScrambler {

    public String reverseWordOrder(String input) {

        // PLACE YOUR IMPLEMENTATION HERE

        // this line added so skeleton class compiles; remove it.
        return "";
    }


    public static void main(String args[]) {
        StringScrambler scrambler = new StringScrambler();
        String input;
        String output;

        input = "To be or not to be, that is the question.";
        output = scrambler.reverseWordOrder(input);
        System.out.println(output);

        input = "Stressed spelled backwards is Desserts";
        output = scrambler.reverseWordOrder(input);
        System.out.println("\n\n" + output);
    }

}

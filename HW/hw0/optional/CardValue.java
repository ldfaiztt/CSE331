package hw0.optional;


/**
 * Represents one of 13 values (2-10, Jack, Queen, King, Ace) that appear
 * on a playing card. This class is immutable.
 */
public enum CardValue  {

    //
    // CONSTANTS
    //

    // The 13 acceptable card values.

    /**
     * The card value representing 2.
     */
    TWO("two", "2"),

    /**
     * The card value representing 3.
     */
    THREE("three", "3"),

    /**
     * The card value representing 4.
     */
    FOUR("four", "4"),

    /**
     * The card value representing 5.
     */
    FIVE ("five", "5"),

    /**
     * The card value representing 6.
     */
    SIX ("six", "6"),

    /**
     * The card value representing 7.
     */
    SEVEN ("seven", "7"),

    /**
     * The card value representing 8.
     */
    EIGHT ("eight", "8"),

    /**
     * The card value representing 9.
     */
    NINE("nine", "9"),

    /**
     * The card value representing 10.
     */
    TEN ("ten", "10"),

    /**
     * The card value representing a Jack.
     */
    JACK ("Jack", "J"),

    /**
     * The card value representing a Queen.
     */
    QUEEN("Queen", "Q"),

    /**
     * The card value representing a King.
     */
    KING ("King", "K"),

    /**
     * The card value representing an Ace.
     */
    ACE ("Ace", "A");

    //
    // MEMBER VARIABLES
    //

    /**
     * The name of this card value.
     */
    private String name;

    /**
     * The symbol representing this card value that appears on a playing card.  For
     * the values 2-10, this symbol is simply the digits themselves; for JACK, QUEEN,
     * KING, and ACE values, the symbols are "J", "Q", "K", and "A" respectively.
     */
    private String letterOnCard;

    //
    // METHODS
    //

    //-------------------------------------------
    /**
     * Creates a new card value.
     *
     * @param valueName  the name of this value
     * @param letter     the symbol representing this card value that appears
     *                   on a playing card
     *
     *
     *
     * @modifies this
     * @effects  creates a new <code>CardValue</code> object
     */
    CardValue(String valueName, String letter) {
        name = valueName;
        letterOnCard = letter;
    }

    //-------------------------------------------
    /**
     * Returns the name of this value.
     * <p>
     * The value names can be either "2", "3", ..., "10", "Jack", "Queen", "King", or "Ace".
     *
     *
     * @effects  returns the name of this value
     */
    public String getName() {
        return name;
    }

    //-------------------------------------------
    /**
     * Returns the symbol representing this card value.
     * <p>
     * For the values 2-10, this symbol is simply the digits themselves; for JACK, QUEEN,
     * KING, and ACE values, the symbols are "J", "Q", "K", and "A" respectively.
     *
     *
     * @effects  returns the symbol representing this card value
     */
    public String getLetterOnCard() {
        return letterOnCard;
    }


    //-------------------------------------------
    /**
     * Returns a description of this value.
     *
     *
     * @effects  returns a description of this value
     */
    public String toString() {
        return getName();
    }

}

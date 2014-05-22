package hw0.optional;

/**
 * Card is a class representing single playing card consisting of a value and a
 * suit (e.g. [Ace,Spades],[10,Clubs]). Cards are immutable; once a Card has
 * been created with a given value and suit, that value and suit cannot be
 * changed.
 */
public class Card implements Comparable<Card> {

    // AF(c) = c represents a playing card with value of c.value and a
    // suit of c.suit (e.g. Ace of Spades, 10 of Clubs)

    // RI(c) = c.value != null && c.suit != null

    //
    // MEMBER VARIABLES
    //

    // the member variables are declared to be final because this class
    // is immutable.

    /**
     * The value of this card.
     */
    private final CardValue value;

    /**
     * The suit of this card.
     */
    private final CardSuit suit;

    //
    // METHODS
    //

    // -------------------------------------------
    /**
     * Creates a new playing card.
     *
     * @param aValue
     *            the value of this card
     * @param aSuit
     *            the suit of this card
     *
     * @modifies this
     * @effects creates a new <code>Card</code> object
     */
    public Card(CardValue aValue, CardSuit aSuit) {
        this.value = aValue;
        this.suit = aSuit;
    }

    // -------------------------------------------
    /**
     * @effects returns the <code>CardSuit</code> associated with this card
     */
    public CardSuit getSuit() {
        return this.suit;
    }

    // -------------------------------------------
    /**
     * @effects returns the <code>CardValue</code> associated with this card
     */
    public CardValue getValue() {
        return this.value;
    }

    // -------------------------------------------
    /**
     * Compares this card with the specified card for order. The purpose of
     * being able to compare cards is to be able to sort a hand of cards.
     * <p>
     * Cards are ranked primarily by number, secondarily by suit. That means
     * that this card is ranked lower than another card if one of these
     * conditions is met:
     * <ul>
     * <li>This card's face value is less than the other card's face value; or
     * <li>Both cards' face values are equal, but this card's suit is ranked
     * lower than the other card's suit.
     * </ul>
     *
     * EXAMPLE: [Ace,Clubs] is ranked higher than [10,Spades] because face value
     * is higher, but is ranked lower than [Ace,Spades] because its suit is
     * ranked lower.
     *
     * @param c
     *            the Card to be compared
     * @exception ClassCastException
     *                if the specified object's type is not Card
     * @exception NullPointerException
     *                if the specified object is null
     *
     * @effects
     * <ul>
     * <li>If <code>o</code> is not an instance of Card, throws a
     * <code>ClassCastException</code></li>
     * <li>If <code>o</code> is null, throws a
     * <code>NullPointerException</code></li>
     * <li>Returns a negative integer, zero, or a positive integer if this
     * <code>Card</code> is less than, equal to, or greater than the specified
     * <code>Card</code>, respectively</li>
     * </ul>
     */
    public int compareTo(Card c) {
        if (c == null) {
            throw new NullPointerException();
        }
        // cast the Object o to a Card now that we've check to make sure it
        // is one!

        if (this.value.equals(c.value)) {
            return this.suit.compareTo(c.suit);
        } else {
            return this.value.compareTo(c.value);
        }
    }

    // -------------------------------------------
    /**
     * Returns true if this card is equal to the other card. Two cards are equal
     * if both their values and suits are identical.
     *
     * @param otherCardObject
     *            the other card
     *
     * @effects returns true if both cards are equal; in all other cases,
     *          returns false
     */
    public boolean equals(/*@Nullable*/ Object otherCardObject) {
        if (!(otherCardObject instanceof Card)) {
            return false;
        }
        return hashCode() == ((Card) otherCardObject).hashCode();
    }

    // -------------------------------------------
    /**
     * Returns a hashcode for this object. This hashcode is the same for all
     * Cards equal to this one (as indicated by the equals method). Note that it
     * is good practice to override the hashCode method when redefining the
     * equals method.
     *
     * @effects returns a hashcode for this object; invoking this methods on two
     *          equal Cards results in the same hashcode
     */
    public int hashCode() {
        int suitMultiplier = suit.ordinal();
        int valueInt = value.ordinal() + 1;
        return ((suitMultiplier * 13) + valueInt);
    }

    // -------------------------------------------
    /**
     * @effects returns a description of this card
     */
    public String toString() {
        return (value.toString() + " of " + suit.toString());
    }

}

package hw3;

/**
 * <b>RatTerm</b> is an immutable representation of a term in a single-variable
 * polynomial expression. The term has the form C*x^E where C is a rational
 * number and E is an integer.
 * <p>
 *
 * A RatTerm, t, can be notated by the pair (C . E), where C is the coefficient
 * of t, and E is the exponent of t.
 * <p>
 *
 * The zero RatTerm, (0 . 0), is the only RatTerm that may have a zero
 * coefficient. For example, (0 . 7) is an invalid RatTerm and an attempt to
 * construct such a RatTerm (through the constructor or arithmetic operations on
 * existing RatTerms) will return the semantically equivalent RatTerm (0 . 0).
 * For example, (1 . 7) + (-1 . 7) = (0 . 0).
 * <p>
 *
 * (0 . 0), (1 . 0), (1 . 1), (1 . 3), (3/4 . 17), (7/2 . -1), and (NaN . 74)
 * are all valid RatTerms, corresponding to the polynomial terms "0", "1", "x",
 * "x^3", "3/4*x^17", "7/2*x^-1" and "NaN*x^74", respectively.
 */
// See RatNum's documentation for a definition of "immutable".
public final class RatTerm {

    /** Coefficient of this term. */
    private final RatNum coeff;

    /** Exponent of this term. */
    private final int expt;

    // Abstraction Function:
    // For a given RatTerm t, "coefficient of t" is synonymous with
    // t.coeff, and, likewise, "exponent of t" is synonymous with t.expt.
    // All RatTerms with a zero coefficient are represented by the
    // zero RatTerm, z, which has zero for its coefficient AND exponent.
    //
    // Representation Invariant:
    // coeff != null
    // coeff.equals(RatNum.ZERO) ==> expt == 0

    /** A constant holding a Not-a-Number (NaN) value of type RatTerm */
    public static final RatTerm NaN = new RatTerm(RatNum.NaN, 0);

    /** A constant holding a zero value of type RatTerm */
    public static final RatTerm ZERO = new RatTerm(RatNum.ZERO, 0);

    /** A constant holding a one value of type RatTerm */
    private static final RatNum ONE = new RatNum(1);

    /**
     * @param c the coefficient of the RatTerm to be constructed.
     * @param e the exponent of the RatTerm to be constructed.
     * @requires c != null
     * @effects Constructs a new RatTerm t, with t.coeff = c, and if
     *          c.equals(RatNum.ZERO), then t.expt = 0, otherwise t.expt = e
     */
    public RatTerm(RatNum c, int e) {
    	// special case for zero coefficient
        if (c.equals(RatNum.ZERO)) {
        	coeff = c;
            expt = 0;
        } else {
        	coeff = c;
        	expt = e;
        }
        checkRep(); // check if representation invariant holds
    }

    /**
     * Gets the coefficient of this RatTerm.
     *
     * @return the coefficient of this RatTerm.
     */
    public RatNum getCoeff() {
    	return coeff;
    }

    /**
     * Gets the exponent of this RatTerm.
     *
     * @return the exponent of this RatTerm.
     */
    public int getExpt() {
        return expt;
    }

    /**
     * Returns true if this RatTerm is not-a-number.
     *
     * @return true if and only if this has NaN as a coefficient.
     */
    public boolean isNaN() {
        return coeff.isNaN();
    }

    /**
     * Returns true if this RatTerm is equal to 0.
     *
     * @return true if and only if this has zero as a coefficient.
     */
    public boolean isZero() {
        return coeff.equals(RatNum.ZERO);
    }

    /**
     * Returns the value of this RatTerm, evaluated at d.
     *
     * @param d The value at which to evaluate this term.
     * @return the value of this polynomial when evaluated at 'd'. For example,
     *         "3*x^2" evaluated at 2 is 12. if (this.isNaN() == true), return
     *         Double.NaN
     */
    public double eval(double d) {
    	if (isNaN())
    		return Double.NaN;
        
    	return coeff.doubleValue() * Math.pow(d, expt);
    }

    /**
     * Negation operation.
     *
     * @return a RatTerm equals to (-this). If this is NaN, then returns NaN.
     */
    public RatTerm negate() {
        return new RatTerm(coeff.negate(), expt);
    }

    /**
     * Addition operation.
     *
     * @param p The other value to be added.
     * @requires arg != null
     * @return a RatTerm equals to (this + arg). If either argument is NaN, then
     *         returns NaN.
     * @throws IllegalArgumentException
     *             if (this.expt != arg.expt) and neither argument is zero or
     *             NaN.
     */
    public RatTerm add(RatTerm p) {
    	if (this.isNaN() || p.isNaN())
    		return new RatTerm(RatNum.NaN, this.expt);
    	
    	else if (this.isZero())
    		// 0 + a*x^b = a*x^b
    		return new RatTerm(p.coeff, p.expt);
    	
    	else if (p.isZero())
    		// a*x^b + 0 = a*x^b
    		return new RatTerm(this.coeff, this.expt);
    	
    	else if (this.expt != p.expt)
    		throw new IllegalArgumentException("Argument p must have the " +
    				"same exponent as this term.");
    	
    	// (a*x^c) + (b*x^c) = (a+b)*x^c
    	return new RatTerm(this.coeff.add(p.coeff), this.expt);
    }

    /**
     * Subtraction operation.
     *
     * @param p The value to be subtracted.
     * @requires arg != null
     * @return a RatTerm equals to (this - arg). If either argument is NaN, then
     *         returns NaN.
     * @throws IllegalArgumentException
     *             if (this.expt != arg.expt) and neither argument is zero or
     *             NaN.
     */
    public RatTerm sub(RatTerm p) {
    	if (this.isNaN() || p.isNaN())
    		return new RatTerm(RatNum.NaN, this.expt);
    	
    	else if (this.isZero())
    		// 0 - a*x^b = -a*x^b
    		return new RatTerm(p.coeff.negate(), p.expt);
    	
    	else if (p.isZero())
    		// a*x^b - 0 = a*x^b
    		return new RatTerm(this.coeff, this.expt);
    	
    	else if (this.expt != p.expt)
    		throw new IllegalArgumentException("Argument p must have the " +
    				"same exponent as this term.");
    	
    	// (a*x^c) - (b*x^c) = (a-b)*x^c
    	return new RatTerm(this.coeff.sub(p.coeff), this.expt);
    }

    /**
     * Multiplication operation.
     *
     * @param p The other value to be multiplied.
     * @requires arg != null
     * @return a RatTerm equals to (this * arg). If either argument is NaN, then
     *         returns NaN.
     */
    public RatTerm mul(RatTerm p) {
    	if (this.isNaN() || p.isNaN())
    		return new RatTerm(RatNum.NaN, this.expt);
    	
    	// (a*x^b) * (c*x^d) = (a*c)*x^(b+d)
        return new RatTerm(this.coeff.mul(p.coeff), this.expt + p.expt);
    }

    /**
     * Division operation.
     *
     * @param p The divisor.
     * @requires arg != null
     * @return a RatTerm equals to (this / arg). If arg is zero, or if either
     *         argument is NaN, then returns NaN.
     */
    public RatTerm div(RatTerm p) {
    	if (p.isZero() || (this.isNaN() || p.isNaN()))
    		return new RatTerm(RatNum.NaN, this.expt);
    	
    	// (a*x^b) / (c*x^d) = (a/c)*x^(b-d)
        return new RatTerm(this.coeff.div(p.coeff), this.expt - p.expt);
    }

    /**
     * Return the derivative of this RatTerm.
     *
     * @return a RatTerm that, q, such that q = dy/dx, where this == y. In other
     *         words, q is the derivative of this. If this.isNaN(), then return
     *         some q such that q.isNaN()
     *         <p>
     *         Given a term, a*x^b, the derivative of the term is: (a*b)*x^(b-1)
     *         for b > 0 and 0 for b == 0 (Do not worry about the case when b <
     *         0. The caller of this function, RatPoly, contains a rep.
     *         invariant stating that b is never less than 0.)
     */
    public RatTerm differentiate() {
        if (isNaN())
        	return new RatTerm(RatNum.NaN, 0);
        
        else if (expt == 0)
        	// y = 0, dy/dx = 0
        	return new RatTerm(RatNum.ZERO, 0);
        
        return new RatTerm(coeff.mul(new RatNum(expt)), expt - 1);
    }

    /**
     * Returns the antiderivative of this RatTerm.
     *
     * @return a RatTerm, q, such that dq/dx = this where the constant of
     *         intergration is assumed to be 0. In other words, q is the
     *         antiderivative of this. If this.isNaN(), then return some q such
     *         that q.isNaN()
     *         <p>
     *         Given a term, a*x^b, (where b >= 0) the antiderivative of the
     *         term is: a/(b+1)*x^(b+1) (Do not worry about the case when b < 0.
     *         The caller of this function, RatPoly, contains a rep. invariant
     *         stating that b is never less than 0.)
     */
    public RatTerm antiDifferentiate() {
        if (isNaN())
        	return new RatTerm(RatNum.NaN, 0);
        
        return new RatTerm(coeff.div(new RatNum(expt + 1)), expt + 1);
    }

    /**
     * Returns a string representation of this RatTerm.
     *
     * @return A String representation of the expression represented by this.
     *         <p>
     *         There is no whitespace in the returned string.
     *         <p>
     *         If the term is itself zero, the returned string will just be "0".
     *         <p>
     *         If this.isNaN(), then the returned string will be just "NaN"
     *         <p>
     *
     * The string for a non-zero, non-NaN RatTerm is in the form "C*x^E" where C
     * is a valid string representation of a RatNum (see {@link hw3.RatNum}'s
     * toString method) and E is an integer. UNLESS: (1) the exponent E is zero,
     * in which case T takes the form "C" (2) the exponent E is one, in which
     * case T takes the form "C*x" (3) the coefficient C is one, in which case T
     * takes the form "x^E" or "x" (if E is one) or "1" (if E is zero).
     * <p>
     * Valid example outputs include "3/2*x^2", "-1/2", "0", and "NaN".
     */
    @Override
    public String toString() {
        if (this.isNaN()) {
            return "NaN";
        }
        StringBuilder output = new StringBuilder();
        RatNum c = coeff;
        int e = expt;
        if (c.isNegative()) {
            output.append("-");
            c = c.negate();
        }
        if (c.equals(ONE) && e == 1) {
            output.append("x");
        } else if (e == 0) {
            output.append(c.toString());
        } else if (c.equals(ONE)) {
            output.append("x^" + e);
        } else if (e == 1) {
            output.append(c.toString() + "*x");
        } else {
            output.append(c.toString() + "*x^" + e);
        }
        return output.toString();
    }

    /**
     * Builds a new RatTerm, given a descriptive String.
     *
     * @param ratStr A string of the format described in the @requires clause.
     * @requires 'termStr' is an instance of a string with no spaces that
     *           expresses a RatTerm in the form defined in the toString()
     *           method.
     *           <p>
     *
     * Valid inputs include "0", "x", and "-5/3*x^3", and "NaN".
     *
     * @return a RatTerm t such that t.toString() = termStr
     */
    public static RatTerm valueOf(String termStr) {
        if (termStr.equals("NaN")) {
            return NaN;
        }

        // Term is: "R" or "R*x" or "R*x^N" or "x^N" or "x",
        // where R is a rational num and N is an integer.

        // First we parse the coefficient
        int multIndex = termStr.indexOf("*");
        RatNum coeff = null;
        if (multIndex == -1) {
            // "R" or "x^N" or "x"
            int xIndex = termStr.indexOf("x");
            if (xIndex == -1) {
                // "R"
                coeff = RatNum.valueOf(termStr);
            } else {
                int negIndex = termStr.indexOf("-");
                // "x^N" or "x" ==> coeff = 1
                if (negIndex == -1) {
                    coeff = new RatNum(1);
                }
                // "-x^N" or "-x" ==> coeff = -1
                else if (negIndex == 0) {
                    coeff = new RatNum(-1);
                } else {
                    throw new RuntimeException(
                            "Minus sign, '-', not allowed in the middle of input string: "
                                    + termStr);
                }
            }
        } else {
            // "R*x" or "R*x^N"
            coeff = RatNum.valueOf(termStr.substring(0, multIndex));
        }

        // Second we parse the exponent
        int powIndex = termStr.indexOf("^");
        int expt;
        if (powIndex == -1) {
            // "R" or "R*x" or "x"
            int xIndex = termStr.indexOf("x");
            if (xIndex == -1) {
                // "R"
                expt = 0;
            } else {
                // "R*x" or "x"
                expt = 1;
            }
        } else {
            // "R*x^N" or "x^N"
            expt = Integer.parseInt(termStr.substring(powIndex + 1));
        }
        return new RatTerm(coeff, expt);
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also.
     */
    @Override
    public int hashCode() {
        if (this.isNaN()) {
            return 0;
        }
        return coeff.hashCode() * 7 + expt * 43;
    }

    /**
     * Standard equality operation.
     *
     * @param obj The object to be compared for equality.
     * @return true iff 'obj' is an instance of a RatTerm and 'this' and 'obj'
     *         represent the same RatTerm. Note that all NaN RatTerms are equal.
     */
    @Override
    public boolean equals(/*@Nullable*/ Object obj) {
        if (obj instanceof RatTerm) {
            RatTerm rt = (RatTerm) obj;
            if (this.isNaN() && rt.isNaN()) {
                return true;
            } else {
                return this.expt == rt.expt && this.coeff.equals(rt.coeff);
            }
        } else {
            return false;
        }
    }

    /**
     * Checks that the representation invariant holds (if any).
     * Throws an exception if the rep invariant is violated.
     */
    private void checkRep() throws RuntimeException {
       // assert coeff != null: "coeff == null";
       // assert !(coeff.equals(RatNum.ZERO) && expt != 0): "coeff is zero while expt == " + expt;
        
    	if (coeff == null) {
            throw new RuntimeException("coeff == null");
        }
        if (coeff.equals(RatNum.ZERO) && expt != 0) {
            throw new RuntimeException("coeff is zero while expt == " + expt);
        }
    }
}

/**
 * IntTreeMap maps integers (keys) to objects (values).
 * 
 * IntTreeMap can be represented as a set of <key, value> pairs:
 *
 *   @specfield kvpairs : Set< Pair<int, Object> >
 *     e.g. kvpairs = { <i_1, o_1>, <i2, o_2>, ..., <i_n, o_n> }
 *
 *   @derivedfield keys : Set<int>   
 *     keys = {kv.key | kv in kvpairs}
 *
 * Abstract Invariant:
 *
 *   for every k in keys: there is only one pair in kvpairs such that
 *   kvpair.key == k.
 */
public class IntTreeMap {
    private IntTreeMap left, right;
    private int key;
    private Object val;
    
    // ABSTRACTION FUNCTION:
    //   AF(r) = {} if r is null
    //         = { <this.key, this.val> } : AF(left) : AF(right) o.w.
    
    // REPRESENTATION INVARIANT:
    //   key > max(left.keys) if left != null
    //   key < min(right.keys) if right != null
    //   RI(left)
    //   RI(right)
    //   In other words, all keys in the left subtree are < this.key and all
    //   keys in the right subtree are > this.key
}

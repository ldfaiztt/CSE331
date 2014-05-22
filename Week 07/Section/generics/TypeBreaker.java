import java.util.ArrayList;
import java.util.List;

/**
 * Let's break Java's type system.
 */
public class TypeBreaker {
    
    public static void main(String[] args) {
        breakArrays();
    }

    public static void breakArrays() {
        // create an integer array
        Integer[] intarr = new Integer[2];
        intarr[0] = 2;
        System.out.println(intarr[0]);
        
        // arrays are covariant, so we can assign intarr to objarr
        Object[] objarr = intarr;
        System.out.println(objarr[0]);
        
        // this code type checks, but it fails at runtime.
        Object o = new Object();
        objarr[0] = o;
    }
    
    public static void genericType() {
        // create a List of Integers
        List<Integer> intlist = new ArrayList<Integer>();
        intlist.add(2);
        System.out.println(intlist);
        
        // this code doesn't type check because generics are invariant
        // List<Object> objlist = intlist;
    }
}

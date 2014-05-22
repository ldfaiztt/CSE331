import java.util.*;

// Krysta Yousoufian, CSE 331, Autumn 2011
//
// Demonstration of how to use generic methods
public class GenericsTest {

	public static void main(String[] args) {
		// Set up variables
		Set<Integer> intSet1 = new HashSet<Integer>();
		intSet1.add(0);
		intSet1.add(1);
		intSet1.add(2);
		
		Set<Integer> intSet2 = new HashSet<Integer>();
		intSet2.add(10);
		intSet2.add(11);
		intSet2.add(12);
		
		Set<Double> doubleSet = new HashSet<Double>();
		doubleSet.add(3.14);
		doubleSet.add(2.718);
		doubleSet.add(1.618);
		
		Set<Number> numberSet = new HashSet<Number>();
		numberSet.add(123);
		numberSet.add(123.456);
		
		// Try out our generic methods

		// Union accepts two sets of Integers...
		SetUtils.union(intSet1, intSet2);
		// But not sets of Integers and Numbers (this line won't compile))
		//SetUtils.union(intSet1, numberSet);
		// unionBetter() uses bounded wildcards to accept sets of Integers and Numbers
		SetUtils.unionBetter(intSet1, numberSet);
		
		// Usually with generic methods, the compiler can figure out the type. But
		// sometimes it gets confused and you have to explicitly give it the type.
		
		// When doing the union of sets of the same type, it compiles.
		Set<Integer> intUnion = SetUtils.unionBetter(intSet1, intSet2);
		
		// When doing the union of sets of different types with no return type, it compiles.
		SetUtils.unionBetter(intSet1, doubleSet);
		
		// When you add a return type, Java gets confused! (this line won't compile)
		//Set<Number> numUnion1 = SetUtils.unionBetter(intSet1, doubleSet);
		// Explicitly tell the method that the generic type is Number, and it happily compiles.
		// In section, we were missing the <Number> parameter, which confused Java
		Set<Number> numUnion2 = SetUtils.<Number>unionBetter(intSet1, doubleSet);
		
		// With wildcards, you can count the intersection between two sets of Integers...
		SetUtils.intersectionCount(intSet1, intSet2);
		
		// ...or even between sets of Integers and Doubles (why you'd want to, I don't know)
		SetUtils.intersectionCount(intSet1, doubleSet);
		
		// heck, you can even compare Integers and Strings. Again, I don't know why
		// you'd want to, but declaring the method with wildcards says the method
		// doesn't give a hoot what each Set stores. 
		SetUtils.intersectionCount(intSet1, new HashSet<String>());
		
		// You can add a set of Integers to another set of Integers...
		SetUtils.addAll(intSet1, intSet2);
		
		// ...but you can't add a set of Integers to a set of Numbers, even
		// though this seems reasonable. (This line throws an error)
		//SetUtils.addAll(intSet1, numberSet);
		// addAllBetter() uses bounded wildcards to allow you to add Integers to Numbers.
		SetUtils.addAllBetter(intSet1, numberSet);  // that's better!
	}

}

import java.util.*;

// Krysta Yousoufian, CSE 331, Autumn 2011
//
// Various methods to demonstrate use of generics
// Many of these examples come from Effective Java by Joshua Bloch, ch. 5
public class SetUtils {
	// Basic example of generic methods
	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> result = new HashSet<E>();
		result.addAll(s1);
		result.addAll(s2);
		return result;
	}

	// Taken directly from Effective Java, pg. 113 (except renamed)
	// Basic example of wildcards
	// Notice how we don't need a <?> or <E> before the return type
	// in the method declaration
	public static int intersectionCount(Set<?> s1, Set<?> s2) {
		int result = 0;
		for (Object o : s1) {
			if (s2.contains(o))
				result++;
		}
		return result;
	}

	// Adds everything in source to dest
	// Example where wildcards don't work - can't add an item (except null) to a
	// Set<?> This might seem weird, but it's just the rule.
	// A second example of where not to use wildcards is union() - you need to
	// know ¹what the type is to construct a new set of that type
	public static <E> void addAll(Set<E> source, Set<E> dest) {
		for (E item : source) {
			dest.add(item);
		}
	}

	// Example of using bounded wildcards with extends for more flexible methods
	public static <E> Set<E> unionBetter(Set<? extends E> s1,
			Set<? extends E> s2) {
		Set<E> result = new HashSet<E>();
		result.addAll(s1);
		result.addAll(s2);
		return result;
	}

	// Example of using bounded wildcards with super for more flexible methods
	public static <E> void addAllBetter(Set<E> source, Set<? super E> dest) {
		for (E item : source) {
			dest.add(item);
		}
	}
}

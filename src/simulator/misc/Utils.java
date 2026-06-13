package simulator.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
	public static <T> List<T> arrayToList(T[] array) {
		return new ArrayList<>(Arrays.asList(array));
	}
}

package utils;

import java.util.*;

public class Helpers {

	/**
	 * Validate empty String
	 *
	 * @return boolean
	 */
	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Validate zero
	 *
	 * @return boolean
	 */
	public static boolean isNonZero(Integer integer) {
		if (Optional.ofNullable(integer).orElse(0).intValue() != 0)
			return true;
		else
			return false;
	}

	/**
	 * Comparing Two Data
	 *
	 * @return Map
	 */
    public static Map<String, Object> compare(Map<String, Object> source, Map<String, Object> dest) {
        Map<String, Object> compareResult = new HashMap<>();
        source.forEach((k, v) ->
        {
            if (v != null && dest.get(k) != null) {
                if (v.toString().trim().equals(dest.get(k).toString().trim()))
                    compareResult.put(k, "same");
                else compareResult.put(k, "different");
            } else if (v != null || dest.get(k) != null)
                compareResult.put(k, "different");
            else compareResult.put(k, "same");
        });

        return compareResult;
    }
}

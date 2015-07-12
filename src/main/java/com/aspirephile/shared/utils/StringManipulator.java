package com.aspirephile.shared.utils;

import com.aspirephile.shared.debug.NullPointerAsserter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringManipulator {

	private NullPointerAsserter asserter;

	private static final String emailExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

	public StringManipulator(NullPointerAsserter asserter) {
		this.asserter = asserter;
	}

	private String getFormattedStringArrayAssumingNonNull(String[] stringArray) {
		String result = "{";
		for (int i = 0; i < stringArray.length; i += 2) {
			result += (' ' + stringArray[i] + ':' + stringArray[i + 1] + ',');
		}
		result = result.substring(0, result.length() - 1);
		result += '}';
		return result;
	}

	public String getFormatedStringArray(String[] stringArray) {
		if (asserter.assertPointer((Object[]) stringArray)) {
			return getFormattedStringArrayAssumingNonNull(stringArray);
		}
		return "null";
	}

	public String getFormatedStringArrayQuietly(String[] stringArray) {
		if (asserter.assertPointerQuietly((Object[]) stringArray)) {
			return getFormattedStringArrayAssumingNonNull(stringArray);
		}
		return "null";
	}

	public static boolean isEmailValid(CharSequence email) {
		boolean isValid = false;

		Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
}

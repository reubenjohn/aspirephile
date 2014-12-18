package com.aspirephile.shared.utils;

import com.aspirephile.shared.debug.NullPointerAsserter;

public class StringManipulator {
	
	private NullPointerAsserter asserter ;

	public StringManipulator(NullPointerAsserter asserter) {
		this.asserter =asserter;
	}

	public String getFormatedStringArray(String[] stringArray) {
		if (asserter.assertPointer((Object[]) stringArray)) {
			String result = "{";
			for (int i = 0; i < stringArray.length; i += 2) {
				result += (' ' + stringArray[i] + ':' + stringArray[i + 1] + ',');
			}
			result = result.substring(0, result.length() - 1);
			result += '}';
			return result;
		}
		return "null";
	}

}

package com.google.common.testing.testng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;

/**
 * Contains assertion conditions, not directly supported by TestNG.
 * 
 * @author Kartik Kumar
 */
public class TestNGAsserts {
	
	/** Private Constructor. */
	private TestNGAsserts() {
		throw new AssertionError("Not to be instantiated.");
	}
	
	/** Asserts that {@code actual} is not equal to {@code expected} based on
	 * both {@code ==} and {@code Object#equals}.
	 */
	public static void assertNotEqual(Object actual, Object expected,
			String message) {
		if (equals(actual, expected)) {
			failEqual(message, expected);
		}
	}
	
	public static void assertNotEqual(Object actual, Object expected) {
		assertNotEqual(actual, expected, null);
	}
	
	/**
	 * Verifies that {@code expectedRegex} exactly matches {@code actual} and
	 * returns a {@code message} if it doesn't. The MatchResult is returned in
	 * case the test needs access to any captured groups. Note that you can also
	 * use this for a literal string, by wrapping your expected string in
	 * {@link Pattern#quote}.
	 */
	public static MatchResult assertMatchesRegex(String message,
			String expectedRegex, String actual) {
		if (actual == null) {
			failNotMatches(message, expectedRegex, null);
		}
		Matcher matcher = getMatcher(expectedRegex, actual);
		if (!matcher.matches()) {
			failNotMatches(message, expectedRegex, actual);
		}
		return matcher;
	}
	
	public static MatchResult assertMatchesRegex(String expectedRegex,
			String actual) {
		return assertMatchesRegex(null, expectedRegex, actual);
	}
	
	/**
	 * Asserts that {@code expectedRegex} matches a substring of {@code actual}
	 * and returns {@code message} if it doesn't. The Matcher is returned in
	 * case the test needs access to any captured groups.  Note that you can
	 * also use this for a literal string, by wrapping your expected string in
   * {@link Pattern#quote}.
	 */
	public static MatchResult assertContainsRegex(String message,
			String expectedRegex, String actual) {
		if (actual == null) {
			failNotContainsRegex(message, expectedRegex, null);
		}
		Matcher matcher = getMatcher(expectedRegex, actual);
		if (!matcher.find()) {
			failNotContainsRegex(message, expectedRegex, actual);
		}
		return matcher;
	}
	
	public static MatchResult assertContainsRegex(String expectedRegex,
			String actual) {
		return assertContainsRegex(null, expectedRegex, actual);
	}
	
	/**
	 * Asserts that {@code expectedRegex} does not match substring of
	 * {@code actual} and returns {@code message} if it does. Note that you
	 * can also use this for a literal string, by wrapping your expected string
	 * in {@link Pattern#quote}.
	 * 
	 */
	public static void assertNotContainsRegex(String message,
			String expectedRegex, String actual) {
		Matcher matcher = getMatcher(expectedRegex, actual);
		if (matcher.find()) {
			failContainsRegex(message, expectedRegex, actual);
		}
	}
	
	public static void assertNotContainsRegex(String expectedRegex,
			String actual) {
		assertNotContainsRegex(null, expectedRegex, actual);
	}
	
	public static void assertNotMatchesRegex(String message,
			String expectedRegex, String actual) {
		Matcher matcher = getMatcher(expectedRegex, actual);
		if (matcher.matches()) {
			failMatches(message, expectedRegex, actual);
		}
	}
	
	public static void assertNotMatchesRegex(String expectedRegex, 
			String actual) {
		assertNotMatchesRegex(null, expectedRegex, actual);
	}
	
	/** 
	 * A cousin of TestNG's assertEqualsNoOrder, this method asserts that the
	 * contents are in a specified order.
	 * 
	 * @see org.testng.Assert.assertEqualsNoOrder(Object[], Object[], String)
	 */
	public static void assertEqualsOrder(String message,
			Iterable<?> elements, Object... objects) {
		Assert.assertEquals(Arrays.asList(objects),
				newArrayList(elements), message);
	}
	
	/** Overloaded method leverage a message. */
	public static void  assertEqualsOrder(Iterable<?> elements, 
			Object... objects) {
		assertEqualsOrder(null, elements, objects);
	}
	
	/**
	 * Copied from Google collections to avoid (for now) depending on it (until
	 * we really need it).
	 */
	private static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
		if (elements instanceof Collection) {
			return new ArrayList<E>((Collection<? extends E>) elements);
		} else {
			ArrayList<E> element1 = new ArrayList<E>();
			Iterator<? extends E> iterator = elements.iterator();
			while (iterator.hasNext()) {
				element1.add(iterator.next());
			}
			return element1;
		}
	}
	
	private static Matcher getMatcher(String expectedRegex, String actual) {
		Pattern pattern = Pattern.compile(expectedRegex);
		return pattern.matcher(actual);
	}
	
	private static void failEqual(String message, Object expected) {
		failWithMessage(message, "expected not to be <" + expected + ">");
	}
	
	private static void failWithMessage(String message, String ourMessage) {
		Assert.fail(message != null ? message + ' ' + ourMessage : ourMessage);
	}
	
	private static boolean equals(Object actual, Object expected) {
		return actual == expected || (actual != null && actual.equals(expected));
	}
	
	private static void failNotMatches(String message, String expected,
			String actual) {
		String actualDesc = actual == null ? "null" : ('<' + actual + '>');
		failWithMessage(message,
				"expected to match regex <" + expected + "> but was:" +
				actualDesc);
	}
	
	private static void failNotContainsRegex(String message, String expected,
			String actual) {
		String actualDesc = actual == null ? "null" : '<' + actual + '>';
		failWithMessage(message, "expected to contain regex <" + expected +
				"> but was:" + actualDesc);
	}
	
	private static void failContainsRegex(String message, String expected,
			String actual) {
		String actualDesc = actual == null ? "null" : '<' + actual + '>';
		failWithMessage(message, "expected not to contain regex <" + expected +
				"> but contains " + actualDesc);
	}
	
	private static void failMatches(String message, String expected,
			String actual) {
		String actualDesc = actual == null ? "null" : '<' + actual + '>';
		failWithMessage(message, 
				"expected not to match regex <" + expected + "> but was " +
				actualDesc);
	}

}

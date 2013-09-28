package com.google.common.testing.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;

/**
 * Unit Test for {@link TestNGAsserts}.
 *
 * @author Kartik Kumar
 */
public class TestNGAssertsTest {

  @Test
  public void testNotEqualSuccess() throws Exception {
    TestNGAsserts.assertNotEqual(1, 2);
  }

  @Test
  public void testNotEqualNullSuccess1() throws Exception {
    TestNGAsserts.assertNotEqual(null, 1);
  }

  @Test
  public void testNotEqualNullSuccess2() throws Exception {
    TestNGAsserts.assertNotEqual(2, null);
  }

  @Test(
      expectedExceptions = AssertionError.class,
      expectedExceptionsMessageRegExp = "^expected not to be <1>$")
  public void testNotEqualFailure() throws Exception {
      TestNGAsserts.assertNotEqual(1, 1);
  }

  @Test(
      expectedExceptions = AssertionError.class,
      expectedExceptionsMessageRegExp = "expected not to be <null>")
  public void testNotEqualNullFailure() throws Exception {
    TestNGAsserts.assertNotEqual(null, null);
  }

  @Test(
      expectedExceptions = AssertionError.class,
      expectedExceptionsMessageRegExp = "foo expected not to be <1>")
  public void testMessageReturnedWithNotEqualFailure() throws Exception {
    TestNGAsserts.assertNotEqual(1, 1, "foo");
  }

  @Test
  public void testMatchesRegexSuccess() throws Exception {
    MatchResult result = TestNGAsserts.assertMatchesRegex("a(.)", "ab");
    Assert.assertEquals("b", result.group(1));
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testMatchesRegexFailure() throws Exception {
    TestNGAsserts.assertMatchesRegex("ac", "a");
  }

  @Test(
      expectedExceptions=AssertionError.class)
  public void testMatchesRegexFailureWithMessage() throws Exception {
    TestNGAsserts.assertMatchesRegex("foo", "ac", "a");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testMatchesRegexNullFailure() throws Exception {
    TestNGAsserts.assertMatchesRegex("a(.)", null);
  }

  @Test
  public void testNotMatchesRegexSuccess() throws Exception {
    TestNGAsserts.assertNotMatchesRegex("ab", "a");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testNotMatchesRegexFailure() throws Exception {
    TestNGAsserts.assertNotMatchesRegex("a(.)", "ab");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testNotMatchesRegexFailureWithMessage() throws Exception {
    TestNGAsserts.assertNotMatchesRegex("foo", "a(.)", "ab");
  }

  @Test
  public void testContainsRegexSuccess() throws Exception {
    TestNGAsserts.assertContainsRegex("a(.)", "ab");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testContainsRegexFailure() throws Exception {
    TestNGAsserts.assertContainsRegex("ab", "a");
  }

  @Test(
      expectedExceptions = AssertionError.class,
      expectedExceptionsMessageRegExp =
          "foo expected to contain regex <ab> but was:<a>")
  public void testContainsRegexFailureWithMessage() throws Exception {
    TestNGAsserts.assertContainsRegex("foo", "ab", "a");
  }

  @Test(
      expectedExceptions = AssertionError.class,
      expectedExceptionsMessageRegExp =
          "expected to contain regex <ab> but was:null")
  public void testContainsNullRegexFailure() throws Exception {
    TestNGAsserts.assertContainsRegex("ab", null);
  }

  @Test
  public void testNotContainsRegexSuccess() throws Exception {
    TestNGAsserts.assertNotContainsRegex("ab", "a");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testNotContainsRegexFailure() throws Exception {
    TestNGAsserts.assertNotContainsRegex("a(.)", "ab");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testNotContainsRegexFailureWithMessage() throws Exception {
    TestNGAsserts.assertNotContainsRegex("foo", "a(.)", "ab");
  }

  @Test
  public void testAssertEqualsOrder() throws Exception {
    List<Integer> integers = Arrays.asList(1, 2, 3);
    TestNGAsserts.assertEqualsOrder(integers, 1 ,2 ,3);
  }

  @Test(
      expectedExceptions = AssertionError.class)
      //expectedExceptionsMessageRegExp = "Lists differ at element [1]: 2 !=
      // 3" +
      //   "expected:<2> but was:<3>")
  public void testAssertEqualsOrderFailure() throws Exception {
    List<Integer> integers = Arrays.asList(1, 2, 3);
    TestNGAsserts.assertEqualsOrder(integers, 1, 3, 2);
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testAssertEqualsOrderFailureWithMessage() throws Exception {
    List<Integer> integers = Arrays.asList(1, 2, 3);
    TestNGAsserts.assertEqualsOrder("foo", integers, 1, 3, 2);
  }

  @Test
  public void testAssertEqualsOrderEmptyCollection() throws Exception {
    List<String> list = Collections.emptyList();
    TestNGAsserts.assertEqualsOrder(list);
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testAssertEqualsOrderEmptyCollectionFailure() throws Exception {
    List<String> list = Collections.emptyList();
    TestNGAsserts.assertEqualsOrder(list, "abc");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testAssertEqualsOrderEmptyCollectionFailureWithMessage() throws Exception {
    List<String> list = Collections.emptyList();
    TestNGAsserts.assertEqualsOrder("foo", list, "abc");
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testAssertsEqualsOrderEmptyFailureEmptyExpected()
      throws Exception {
    List<String> list = Collections.singletonList("abc");
    TestNGAsserts.assertEqualsOrder("null", list);
  }

  @Test(
      expectedExceptions = AssertionError.class)
  public void testAssertsEqualsOrderEmptyFailureEmptyExpectedWithMessage()
      throws Exception {
    List<String> list = Collections.singletonList("abc");
    TestNGAsserts.assertEqualsOrder("foo", list);
  }
}
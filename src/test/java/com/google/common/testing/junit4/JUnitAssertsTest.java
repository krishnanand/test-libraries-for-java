/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.testing.junit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;

/**
 * Unit test for {@link JUnitAsserts}.
 *
 * @author kevinb
 */
@RunWith(JUnit4.class)
public class JUnitAssertsTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private void expectAssertionError(String message) {
    this.expectedException.expect(AssertionError.class);
    this.expectedException.expectMessage(message);
  }

  @Test
  public void testNotEqualSuccess() {
    JUnitAsserts.assertNotEqual(1, 2);
  }

  @Test
  public void testNotEqualNullSuccess1() {
    JUnitAsserts.assertNotEqual(null, 2);
  }

  @Test
  public void testNotEqualNullSuccess2() {
    JUnitAsserts.assertNotEqual(1, null);
  }

  @Test
  public void testNotEqualFailure() {
    expectAssertionError("expected not to be:<1>");
    JUnitAsserts.assertNotEqual(1, 1);
  }

  @Test
  public void testNotEqualNullFailure() {
    expectAssertionError("expected not to be:<null>");
    JUnitAsserts.assertNotEqual(null, null);
  }

  @Test
  public void testMessageReturnedByNotEqualFailure() {
    expectAssertionError("foo expected not to be:<1>");
    JUnitAsserts.assertNotEqual("foo", 1, 1);
  }

  @Test
  public void testMatchesRegexSuccess() {
    MatchResult result = JUnitAsserts.assertMatchesRegex("a(.)", "ab");
    Assert.assertEquals("b", result.group(1));
  }

  @Test
  public void testMatchesRegexFailure() {
    expectAssertionError("thing expected to match regex:<a(.)> but was:<ace>");
    JUnitAsserts.assertMatchesRegex("thing", "a(.)", "ace");
  }

  @Test
  public void testMatchesRegexFailureNull() {
    expectAssertionError(
        "thing expected to match regex:<a(.)> but was:null");
    JUnitAsserts.assertMatchesRegex("thing", "a(.)", null);
  }

  @Test
  public void testContainsRegexSuccess() {
    MatchResult result = JUnitAsserts.assertContainsRegex("a(.)", "ace");
    Assert.assertEquals("c", result.group(1));
  }

  @Test
  public void testContainsRegexFailure() {
    expectAssertionError("thing expected to contain regex:<a(.)> but was:<ha>");
    JUnitAsserts.assertContainsRegex("thing", "a(.)", "ha");
  }

  @Test
  public void testContainsRegexFailureNull() {
    expectAssertionError("thing expected to contain regex:<a(.)> but was:null");
    JUnitAsserts.assertContainsRegex("thing", "a(.)", null);
  }

  @Test
  public void testNotMatchesRegexSuccess() {
    JUnitAsserts.assertNotMatchesRegex("a(.)", "ace");
  }

  @Test
  public void testNotMatchesRegexFailure() {
    expectAssertionError(
        "thing expected not to match regex:<a(.)> but was:<ab>");
    JUnitAsserts.assertNotMatchesRegex("thing", "a(.)", "ab");
  }

  @Test
  public void testNotContainsRegexSuccess() {
    JUnitAsserts.assertNotContainsRegex("a(.)", "ha");
  }

  @Test
  public void testNotContainsRegexFailure() {
    expectAssertionError(
        "thing expected not to contain regex:<a(.)> but was:<ace>");
    JUnitAsserts.assertNotContainsRegex("thing", "a(.)", "ace");
  }

  @Test
  public void testContentsInOrderSuccess() {
    List<Integer> actual = Arrays.asList(1, 2, 3);
    JUnitAsserts.assertContentsInOrder(actual, 1, 2, 3);
  }

  @Test
  public void testContentsInOrderEmptySuccess() {
    List<Integer> actual = Collections.emptyList();
      JUnitAsserts.assertContentsInOrder(actual);
  }

  @Test
  public void testContentsInOrderEmptyFailureEmptyActual() {
    expectAssertionError("foo expected:<[1]> but was:<[]>");
    List<Integer> actual = Collections.emptyList();
    JUnitAsserts.assertContentsInOrder("foo", actual, 1);
  }

  @Test
  public void testContentsInOrderEmptyFailureEmptyExpected() {
    expectAssertionError("foo expected:<[]> but was:<[1]>");
    List<Integer> actual = Collections.singletonList(1);
    JUnitAsserts.assertContentsInOrder("foo", actual);
  }

  @Test
  public void testContentsInOrderFailureDueToOrder() {
    List<Integer> actual = Arrays.asList(1, 2);
    expectAssertionError("foo expected:<[2, 1]> but was:<[1, 2]>");
    JUnitAsserts.assertContentsInOrder("foo", actual, 2, 1);
  }
}

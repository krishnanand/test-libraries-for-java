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

package com.google.common.testing.junit3;

import static com.google.common.testing.junit3.JUnitAsserts.assertContentsInOrder;
import static com.google.common.testing.junit3.JUnitAsserts.assertNotEqual;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for {@link JUnitAsserts}.
 *
 * @author kevinb
 */
public class JUnitAssertsTest extends TestCase {

  public void testNotEqualSuccess() {
    assertNotEqual(1, 2);
  }

  public void testNotEqualNullSuccess1() {
    assertNotEqual(null, 2);
  }

  public void testNotEqualNullSuccess2() {
    assertNotEqual(1, null);
  }

  public void testNotEqualFailure() {
    try {
      assertNotEqual(1, 1);
      fail("no exception thrown");
    } catch (AssertionFailedError e) {
      assertEquals("expected not to be:<1>", e.getMessage());
    }
  }

  public void testNotEqualNullFailure() {
    try {
      assertNotEqual(null, null);
      fail("no exception thrown");
    } catch (AssertionFailedError e) {
      assertEquals("failure message generated by MoreAsserts",
          "expected not to be:<null>", e.getMessage());
    }
  }

  public void testMessageReturnedByNotEqualFailure() {
    try {
      assertNotEqual("foo", 1, 1);
      fail("no exception thrown");
    } catch (AssertionFailedError e) {
      assertEquals("failure message generated by MoreAsserts",
          "foo expected not to be:<1>", e.getMessage());
    }
  }

  public void testContentsInOrderSuccess() {
    List<Integer> actual = Arrays.asList(1, 2, 3);
    assertContentsInOrder(actual, 1, 2, 3);
  }

  public void testContentsInOrderEmptySuccess() {
    List<Integer> actual = Collections.emptyList();
    assertContentsInOrder(actual);
  }

  public void testContentsInOrderEmptyFailureEmptyActual() {
    List<Integer> actual = Collections.emptyList();
    try {
      assertContentsInOrder("foo", actual, 1);
      fail("no exception thrown");
    } catch (AssertionFailedError e) {
      assertEquals("failure message generated by MoreAsserts",
          "foo", e.getMessage());
    }
  }

  public void testContentsInOrderEmptyFailureEmptyExpected() {
    List<Integer> actual = Collections.singletonList(1);
    try {
      assertContentsInOrder("foo", actual);
      fail("no exception thrown");
    } catch (AssertionFailedError e) {
      assertEquals("failure message generated by MoreAsserts",
          "foo", e.getMessage());
    }
  }

  public void testContentsInOrderFailureDueToOrder() {
    List<Integer> actual = Arrays.asList(1, 2);
    try {
      assertContentsInOrder("foo", actual, 2, 1);
      fail("no exception thrown");
    } catch (AssertionFailedError e) {
      assertEquals("failure message generated by MoreAsserts",
          "foo expected:<2> but was:<1>", e.getMessage());
    }
  }
}
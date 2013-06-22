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

import com.google.common.testing.TearDown;
import com.google.common.testing.TearDownStack;
import com.google.common.testing.TestLogHandler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for {@link TearDownTestCase}.
 *
 * @author kevinb
 */
@RunWith(JUnit4.class)
public class TearDownTestCaseTest {
  
  private TearDownTestCase test;
  private List<String> messages;
  private TestLogHandler handler;

  @Before
  public void setUp() throws Exception {
    test = new TearDownTestCase() {};
    messages = new ArrayList<String>();
    handler = new TestLogHandler();
    TearDownStack.logger.addHandler(handler);
    TearDownStack.logger.setUseParentHandlers(false);
  }

  @Test
  public void testAdHocTearDownObject() throws Exception {
    final SomeObject obj = new SomeObject("a");
    test.addTearDown(new TearDown() {
      public void tearDown() {
        messages.add(obj.desc);
      }
    });

    test.tearDownRule.stack.runTearDown();
    JUnitAsserts.assertContentsInOrder(messages, "a");
  }

  @Test
  public void testReusableTearDownObject() throws Exception {
    SomeObject obj = new SomeObject("b");
    test.addTearDown(new SomeObjectTearDown(obj));

    test.tearDownRule.stack.runTearDown();
    JUnitAsserts.assertContentsInOrder(messages, "b");
  }

  @Test
  public void testSelfCleaningObject() throws Exception {
    TidyObject obj = new TidyObject("c");
    test.addTearDown(obj);

    test.tearDownRule.stack.runTearDown();
    JUnitAsserts.assertContentsInOrder(messages, "c");
  }

  @Test
  public void testReverseOrder() throws Exception {
    test.addTearDown(new TidyObject("x"));
    test.addTearDown(new TidyObject("y"));
    test.addTearDown(new TidyObject("z"));

    test.tearDownRule.stack.runTearDown();
    JUnitAsserts.assertContentsInOrder(messages, "z", "y", "x");
  }

  @Test
  public void testTearDownFailure() throws Exception {
    test.addTearDown(new TidyObject("before"));
    test.addTearDown(new FailingTearDown());
    test.addTearDown(new TidyObject("after"));

    try {
      test.tearDownRule.stack.runTearDown();
      Assert.fail();
    } catch (RuntimeException expected) {
    }
    JUnitAsserts.assertContentsInOrder(messages, "after", "whoops", "before");
  }

  @Test
  public void testDontSkipOptionalTearDowns() throws Exception {
    test.addTearDown(new TidyObject("sometimes"));
    test.addTearDown(new TidyObject("always"));
    test.tearDownRule.stack.runTearDown();
    JUnitAsserts.assertContentsInOrder(messages, "always", "sometimes");
  }

  @Test
  public void testWithNoTestEnvironments() throws Throwable {
    test.tearDownRule.stack.runTearDown();
  }

  /** This is deeply ironic. */
  @After
  public void tearDown() throws Exception {
    TearDownStack.logger.removeHandler(handler);
    TearDownStack.logger.setUseParentHandlers(true);
  }

  private static class SomeObject {
    String desc;
    SomeObject(String desc) {
      this.desc = desc;
    }
  }

  private class TidyObject implements TearDown {
    String desc;
    TidyObject(String desc) {
      this.desc = desc;
    }
    public void tearDown() {
      messages.add(desc);
    }
  }

  private class SomeObjectTearDown implements TearDown {
    SomeObject toclean;
    SomeObjectTearDown(SomeObject toclean) {
      this.toclean = toclean;
    }
    public void tearDown() {
      messages.add(toclean.desc);
    }
  }

  private class FailingTearDown implements TearDown {
    public void tearDown() {
      messages.add("whoops");
      doSomethingThatFails();

      // never try to do two things in the same TearDown!
      messages.add("this will never appear");
    }
    void doSomethingThatFails() {
      throw new RuntimeException("Don't worry, this exception is expected.");
    }
  }
}

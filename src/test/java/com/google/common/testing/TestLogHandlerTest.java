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

package com.google.common.testing;

import com.google.common.testing.junit4.TearDownTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Unit test for {@link TestLogHandler}.
 *
 * @author kevinb
 */
@RunWith(JUnit4.class)
public class TestLogHandlerTest extends TearDownTestCase {

  private TestLogHandler handler;

   @Before
   public void setUp() throws Exception {
    handler = new TestLogHandler();

    // You could also apply it higher up the Logger hierarchy than this
    ExampleClassUnderTest.logger.addHandler(handler);

    ExampleClassUnderTest.logger.setUseParentHandlers(false); // optional

    addTearDown(new TearDown() {
      public void tearDown() throws Exception {
        ExampleClassUnderTest.logger.setUseParentHandlers(true);
        ExampleClassUnderTest.logger.removeHandler(handler);
      }
    });
  }

  @Test
  public void test() throws Exception {
    Assert.assertTrue(handler.getStoredLogRecords().isEmpty());
    ExampleClassUnderTest.foo();
    LogRecord record = handler.getStoredLogRecords().iterator().next();
    Assert.assertEquals(Level.INFO, record.getLevel());
    Assert.assertEquals("message", record.getMessage());
    Assert.assertSame(EXCEPTION, record.getThrown());
  }

  @Test
  public void testConcurrentModification() throws Exception {
    // Tests for the absence of a bug where logging while iterating over the
    // stored log records causes a ConcurrentModificationException
    Assert.assertTrue(handler.getStoredLogRecords().isEmpty());
    ExampleClassUnderTest.foo();
    ExampleClassUnderTest.foo();
    for (LogRecord record : handler.getStoredLogRecords()) {
      ExampleClassUnderTest.foo();
    }
  }

  static final Exception EXCEPTION = new Exception();

  static class ExampleClassUnderTest {
    static final Logger logger
        = Logger.getLogger(ExampleClassUnderTest.class.getName());

    static void foo() {
      logger.log(Level.INFO,  "message", EXCEPTION);
    }
  }
}

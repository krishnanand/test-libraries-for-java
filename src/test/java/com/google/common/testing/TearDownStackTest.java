/*
 * Copyright (C) 2010 Google Inc.
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
import org.junit.Test;

/**
 * @author Luiz-Otavio "Z" Zorzella
 */
public class TearDownStackTest extends TearDownTestCase {

  @Test
  public void testSingleTearDown() throws Exception {
    final TearDownStack stack = buildTearDownStack();
    
    final SimpleTearDown tearDown = new SimpleTearDown();
    stack.addTearDown(tearDown);
    
    Assert.assertEquals(false, tearDown.ran);
    
    stack.runTearDown();

    Assert.assertEquals("tearDown should have run", true, tearDown.ran);
  }

  @Test
  public void testMultipleTearDownsHappenInOrder() throws Exception {
    final TearDownStack stack = buildTearDownStack();
    
    final SimpleTearDown tearDownOne = new SimpleTearDown();
    stack.addTearDown(tearDownOne);

    final Callback callback = new Callback() {
      public void run() {
        Assert.assertEquals("tearDownTwo should have been run before " +
                "tearDownOne",
                false, tearDownOne.ran);
      }
    };
    
    final SimpleTearDown tearDownTwo = new SimpleTearDown(callback);
    stack.addTearDown(tearDownTwo);
    
    Assert.assertEquals(false, tearDownOne.ran);
    Assert.assertEquals(false, tearDownTwo.ran);
    
    stack.runTearDown();

    Assert.assertEquals("tearDownOne should have run", true, tearDownOne.ran);
    Assert.assertEquals("tearDownTwo should have run", true, tearDownTwo.ran);
  }

  @Test
  public void testThrowingTearDown() throws Exception {
    final TearDownStack stack = buildTearDownStack();
    
    final ThrowingTearDown tearDownOne = new ThrowingTearDown("one");
    stack.addTearDown(tearDownOne);

    final ThrowingTearDown tearDownTwo = new ThrowingTearDown("two");
    stack.addTearDown(tearDownTwo);

    Assert.assertEquals(false, tearDownOne.ran);
    Assert.assertEquals(false, tearDownTwo.ran);
    
    try {
      stack.runTearDown();
      Assert.fail("runTearDown should have thrown an exception");
    } catch (ClusterException expected) {
      Assert.assertEquals("two", expected.getCause().getMessage());
    } catch (RuntimeException e) {
      throw new RuntimeException(
        "A ClusterException should have been thrown, rather than a " + e.getClass().getName(), e);
    }

    Assert.assertEquals(true, tearDownOne.ran);
    Assert.assertEquals(true, tearDownTwo.ran);
  }

  /**
   * Builds a {@link TearDownStack} that makes sure it's clear by the end of
   * this test.
   */
  private TearDownStack buildTearDownStack() {
    final TearDownStack result = new TearDownStack();
    addTearDown(new TearDown() {
      
      public void tearDown() throws Exception {
        Assert.assertEquals(
                "The test should have cleared the stack (say, by virtue of running runTearDown)",
                0, result.stack.size());
      }
    });
    return result;
  }

  private static final class ThrowingTearDown implements TearDown {

    private final String id;
    boolean ran = false;

    ThrowingTearDown(String id) {
      this.id = id;
    }
    
    public void tearDown() throws Exception {
      ran = true;
      throw new RuntimeException(id);
    }
  }

  private static final class SimpleTearDown implements TearDown {

    boolean ran = false;
    Callback callback = null;
    
    public SimpleTearDown() {}

    public SimpleTearDown(Callback callback) {
      this.callback = callback;
    }
    
    public void tearDown() throws Exception {
      if (callback != null) {
        callback.run();
      }
      ran = true;
    }
  }
  
  private interface Callback {
    void run();
  }
}

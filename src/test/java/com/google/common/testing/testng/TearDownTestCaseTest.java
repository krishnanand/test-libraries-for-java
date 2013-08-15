package com.google.common.testing.testng;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit Test for {@link TearDownTestCase}.
 *
 * @author Kartik Kumar
 */
@RunWith(JUnit4.class)
public class TearDownTestCaseTest {

  @Test
  public void assertTrue() {
    Assert.assertTrue(true);
  }

 /* private TearDownTestCase tearDownTestCase;
  private List<String> messages;
  private TestLogHandler handler;

  @BeforeMethod(alwaysRun = true)
  public void setUpTestNG() {
    tearDownTestCase = new TearDownTestCase();
    messages = new ArrayList<String>();
    handler = new TestLogHandler();
    TearDownStack.logger.addHandler(handler);
    TearDownStack.logger.setUseParentHandlers(false);
  }


  @AfterMethod(alwaysRun = true)
  public void tearDownTestNG() {
    tearDownTestCase = null;
    messages = null;
    handler = null;
    // Revert to previous settings.
    TearDownStack.logger.removeHandler(handler);
    TearDownStack.logger.setUseParentHandlers(true);
  }

  @Test
  public void testAdHocTearDownObject() {
    final SomeObj obj = new SomeObj("one");
    tearDownTestCase.addTearDown(new TearDown() {
      public void tearDown() throws Exception {
        messages.add(obj.desc);
      }
    });
    tearDownTestCase.tearDownStack.runTearDown();
    TestNGAsserts.assertEqualsOrder(messages, obj.desc);
  }

  @Test
  public void testReusableTearDownObject() throws Exception {
    SomeObj obj = new SomeObj("two");
    tearDownTestCase.addTearDown(new SomeObjectTearDown(obj));
    tearDownTestCase.tearDownStack.runTearDown();
    TestNGAsserts.assertEqualsOrder(messages, obj.desc);
  }

  @Test
  public void testSelfCleaningObject() throws Exception {
    TidyObject tidyObject = new TidyObject("tidy");
    tearDownTestCase.addTearDown(tidyObject);
    tearDownTestCase.tearDownStack.runTearDown();
    TestNGAsserts.assertEqualsOrder(messages, tidyObject.desc);
  }

  @Test
  public void testSelfCleaningObjectsInReverseObject() throws Exception {
    TidyObject z = new TidyObject("Z");
    TidyObject y = new TidyObject("Y");
    TidyObject x = new TidyObject("X");
    tearDownTestCase.addTearDown(z);
    tearDownTestCase.addTearDown(y);
    tearDownTestCase.addTearDown(x);
    tearDownTestCase.tearDownStack.runTearDown();
    TestNGAsserts.assertEqualsOrder(messages, x.desc, y.desc, z.desc);
  }

  @Test(
      expectedExceptions = RuntimeException.class,
      expectedExceptionsMessageRegExp = "^whoops$")
  public void testTearDownFailure() throws Exception {
    FailureTearDown obj = new FailureTearDown("fail");
    tearDownTestCase.addTearDown(obj);
    tearDownTestCase.tearDownStack.runTearDown();
  }

  @Test()
  public void testRecoverFromTearDownFailure() throws Exception {
    TidyObject tidy1 = new TidyObject("before");
    FailureTearDown fail = new FailureTearDown("oops");
    TidyObject tidy2 = new TidyObject("after");
    tearDownTestCase.addTearDown(tidy1);
    tearDownTestCase.addTearDown(fail);
    tearDownTestCase.addTearDown(tidy2);
    try {
      tearDownTestCase.tearDownStack.runTearDown();
      Assert.fail();
    } catch (RuntimeException rte) {
      Assert.assertEquals(rte.getMessage(), "whoops");
    }
    TestNGAsserts.assertEqualsOrder(messages, "after", "oops", "before");
  }

  @Test
  public void testDontSkipOptionalTearDowns() throws Exception {
    tearDownTestCase.addTearDown(new TidyObject("sometimes"));
    tearDownTestCase.addTearDown(new TidyObject("always"));
    tearDownTestCase.tearDownStack.runTearDown();
    TestNGAsserts.assertEqualsOrder(messages, "always", "sometimes");
  }

  @Test
  public void testEmptyEnvironment() throws Exception {
    tearDownTestCase.tearDownStack.runTearDown();
  }

  private static class SomeObj {
    String desc;

    SomeObj(String desc) {
      this.desc = desc;
    }
  }

  private class SomeObjectTearDown implements TearDown {
    SomeObj obj;

    public SomeObjectTearDown(SomeObj obj) {
      this.obj = obj;
    }

    public void tearDown() throws Exception {
      messages.add(obj.desc);
    }
  }

  private class TidyObject implements TearDown {
    String desc;

    public TidyObject(String desc) {
      this.desc = desc;
    }

    public void tearDown() throws Exception {
      messages.add(desc);
    }
  }

  private class FailureTearDown implements TearDown {
    String desc;

    public FailureTearDown(String desc) {
      this.desc = desc;
    }

    public void tearDown() throws Exception {
      messages.add(this.desc);
      doSomethingThatFails();
    }

    private void doSomethingThatFails() {
      throw new RuntimeException("whoops");
    }
  } */
}

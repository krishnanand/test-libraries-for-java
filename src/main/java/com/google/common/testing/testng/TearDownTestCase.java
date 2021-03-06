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

package com.google.common.testing.testng;

import com.google.common.testing.TearDown;
import com.google.common.testing.TearDownAccepter;
import com.google.common.testing.TearDownStack;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

/**
 * A base class for test cases that want to register tear down operations
 * programmatically rather than depending on the TestNG framework to do it for
 * them.
 * 
 * <p>Consider a test case where a method under test performs a CRUD operation.
 * 
 * <pre class = "code">
 * 
 * @org.testng.annotations.Test()
 * public void testReadStream() throws Exception {
 *   InputStream is = new FileInputStream("file.txt");
 *   .... Read InputStream.
 *   is.close();
 * }
 * </pre>
 * 
 * <p>The drawback of this test is that if the read operation input steam throws
 * an exception, then the input stream can not be closed. We can guarantee that
 * the input stream will be closed by moving the clean up method annotated as 
 * {@AfterMethod} and changing the code to:
 * 
 * <pre class = "code">
 * 
 *   private InputStream is;
 *   
 *   @org.testng.annotations.Test()
 *   public void testReadStream() throws Exception {
 *     is = new FileInputStream("file.txt");
 *   }
 *   
 *   @org.testng.annotations.AfterMethod(alwaysRun = true)
 *   public void tearDown() throws Exception {
 *     if (is != null) {
 *       is.close();
 *     }
 *   }
 * </pre>
 * 
 * <p>This approach has serious drawbacks. 
 * <ul>
 *   <li>The scope of "is" has increased. "is" is now a field attribute rather
 *   than a local variable attribute.
 *   <li>This is invoked for all tests (those annotated with @Test) even if they
 *   have nothing to do with "is".
 * <ul>
 * 
 * <p>We can work around this by leveraging TestNG dependencies feature. We can
 * group test methods and then have the clean up method as a test method
 * dependent upon the parent method. The code now will be changed to:
 * 
 * <pre class = "code">
 * 
 *   private InputStream is;
 *   
 *   @org.testng.annotations.Test(groups = "inputStream")
 *   public void testRead() throws Exception {
 *     is = new FileInputStream("file.txt");
 *     ... Read Stream...
 *   }
 *   
 *   @org.testng.annotations.Test(dependsOnGroups = "inputStream",
 *       alwaysRun = "true")
 *    public void cleanupInputStream() {
 *        if (is != null) {
 *            is.close();
 *        }
 *    }
 * </pre>
 * 
 * <p>This approach is still flawed because: 
 * <ul>
 *   <li>This still does not alleviate our basic flaw in design. The scope
 *   of our input stream has not been localized. But this introduces another
 *   problem which is:</li>
 *   <li>Separation of concerns: We have now made a configuration method a test
 *   method.
 * </ul>
 * 
 * <p>If you are writing a piece of test infrastructure rather than a test case,
 * you want to ensure that the piece of code you write is actually cleaned up,
 * all you need to do is to pass an instance of {@link TearDown} to an active
 * instance of TearDownTestCase as :
 * 
 * public class MyTest extends TearDownTestCase {
 * 
 *   @org.testng.annotations.Test
 *   public void read() throws Exception {
 *      final InputStream is = new FileInputStream("file.txt");
 *      addTearDown(new TearDown() {
 *          public void tearDown() {
 *               is.close();
 *          }
 *      });
 *   }
 * }
 * 
 * @author Kartik Kumar
 */
public abstract class TearDownTestCase implements TearDownAccepter, IHookable {

  final TearDownStack tearDownStack = new TearDownStack();

  @Override
 	public void addTearDown(TearDown tearDown) {
		this. tearDownStack.addTearDown(tearDown);
	}

  @Override
  public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
    iHookCallBack.runTestMethod(iTestResult);
    this.tearDownStack.runTearDown();
  }
}

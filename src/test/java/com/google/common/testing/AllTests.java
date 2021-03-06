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

import com.google.common.testing.junit4.JUnitAssertsTest;
import com.google.common.testing.junit4.TearDownTestCaseTest;
import com.google.common.testing.testng.TestNGAssertsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
    {JUnitAssertsTest.class, TearDownTestCaseTest.class,
     TearDownStackTest.class, TestLogHandlerTest.class,
        com.google.common.testing.testng.TearDownTestCase.class,
        TestNGAssertsTest.class})
public class AllTests {}

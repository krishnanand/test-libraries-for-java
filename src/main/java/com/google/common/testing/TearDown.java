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

/**
 * An object that can perform a {@link #tearDown} operation.
 *
 * @author Kevin Bourrillion
 */
public interface TearDown {

  /**
   * Performs a <b>single</b> tear-down operation. See
   * {@link com.google.common.testing.junit3.TearDownTestCase} and
   * {@link com.google.common.testing.junit4.TearDownTestCase} for example.
   *
   * <p>If you want to not fail a test when a {@link TearDown} throws an 
   * exception, you should implement a {@link SloppyTearDown} instead.
   *
   * <p> Note that, for backwards compatibility, JUnit 3's 
   * {@link com.google.common.testing.junit3.TearDownTestCase} currently does
   * not fail a test when an exception is thrown from one of its 
   * {@link TearDown}s, but this is subject to change. Also, Junit 4's 
   * {@link com.google.common.testing.junit4.TearDownTestCase} will. 
   * 
   * @throws Exception for any reason. {@code TearDownTestCase} ensures that
   *     any exception thrown will not interfere with other TearDown
   *     operations.
   */
  void tearDown() throws Exception;
}

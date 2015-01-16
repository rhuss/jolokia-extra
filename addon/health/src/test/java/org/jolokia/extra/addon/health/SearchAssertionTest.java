/*
 * Copyright 2009-2013 Roland Huss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jolokia.extra.addon.health;

import org.jolokia.extra.addon.health.assertion.ResultCollector;
import org.jolokia.extra.addon.health.assertion.SearchAssertion;
import org.testng.annotations.Test;

/**
 */
public class SearchAssertionTest {
    @Test
    public void testCheckWorks() throws Exception {
        SearchAssertion check = new SearchAssertion();
        check.setId("my.check");
        check.setDescription("Check that we have enough mbeans for java.lang");
        check.setMbeanName("java.lang:type=*");
        check.setComparison(">");
        check.setCount(1);

        Asserts.assertCheckValid(check);
    }

    @Test
    public void testCheckFails() throws Exception {
        SearchAssertion check = new SearchAssertion();
        check.setId("my.check");
        check.setDescription("Check that we have enough cheese beans");
        check.setMbeanName("foo.cheese:type=*");
        check.setComparison(">");
        check.setCount(1);

        ResultCollector collector = Asserts.assertCheckFails(check);
        System.out.println("Results: " + collector.getFailures());
    }

}

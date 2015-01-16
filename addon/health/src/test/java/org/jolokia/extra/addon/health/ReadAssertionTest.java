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

import org.jolokia.extra.addon.health.assertion.ReadAssertion;
import org.jolokia.extra.addon.health.assertion.ResultCollector;
import org.testng.annotations.Test;

import static org.jolokia.extra.addon.health.Asserts.*;

/**
 */
public class ReadAssertionTest {
    @Test
    public void testCheckWorks() throws Exception {
        ReadAssertion check = new ReadAssertion();
        check.setId("my.check");
        check.setDescription("Check that the cpu load is valid");
        check.setMbeanName("java.lang:type=OperatingSystem");
        check.setAttribute("ProcessCpuLoad");
        check.setComparison(">");
        check.setValue(-0.1);

        assertCheckValid(check);
    }

    @Test
    public void testCheckFails() throws Exception {
        ReadAssertion check = new ReadAssertion();
        check.setId("my.check");
        check.setDescription("Check that the cpu load is valid");
        check.setMbeanName("java.lang:type=OperatingSystem");
        check.setAttribute("ProcessCpuLoad");
        check.setComparison("<");
        check.setValue(-1000);

        ResultCollector collector = assertCheckFails(check);
        System.out.println("Results: " + collector.getFailures());
    }

}

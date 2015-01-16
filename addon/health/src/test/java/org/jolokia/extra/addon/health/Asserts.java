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

import java.io.IOException;

import javax.management.MalformedObjectNameException;

import org.jolokia.backend.MBeanServerExecutorLocal;
import org.jolokia.backend.executor.MBeanServerExecutor;
import org.jolokia.extra.addon.health.assertion.Assertion;
import org.jolokia.extra.addon.health.assertion.ResultCollector;

import static org.testng.AssertJUnit.*;

/**
 */
public class Asserts {
    public static void assertCheckValid(Assertion check) throws IOException, MalformedObjectNameException {
        ResultCollector collector = new ResultCollector();
        MBeanServerExecutor executor = createExecutor();
        check.perform(executor, collector);
        assertTrue("Should be valid but got: " + collector.getFailures(), collector.isValid());
    }

    public static ResultCollector assertCheckFails(Assertion check) throws IOException, MalformedObjectNameException {
        ResultCollector collector = new ResultCollector();
        MBeanServerExecutor executor = createExecutor();
        check.perform(executor, collector);
        assertFalse("Should have failures but got: " + collector.getFailures(), collector.isValid());
        return collector;
    }

    public static MBeanServerExecutor createExecutor() {
        return new MBeanServerExecutorLocal();
    }
}

package org.jolokia.extra.addon.health;/*
 * 
 * Copyright 2014 Roland Huss
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

import java.io.IOException;

import javax.management.MalformedObjectNameException;

import org.jolokia.backend.plugin.MBeanPluginContext;
import org.jolokia.extra.addon.health.assertion.*;

/**
 * @author roland
 * @since 16/01/15
 */
public class SampleHealthCheck implements SampleHealthCheckMBean {
    private final MBeanPluginContext context;

    public SampleHealthCheck(MBeanPluginContext ctx) {
        this.context = ctx;
    }

    // Return <code>null</code> if everything is ok, a failures string if not.
    // Should be JSON Object in real
    public String cpuLoadCheck(int threshold) throws IOException, MalformedObjectNameException {
        ReadAssertion check = new ReadAssertion();
        check.setId("my.check");
        check.setDescription("Check that the cpu load is valid");
        check.setMbeanName("java.lang:type=OperatingSystem");
        check.setAttribute("ProcessCpuLoad");
        check.setComparison("<");
        check.setValue(threshold);

        return execCheck(check);
    }

    // Another sample check
    public String mbeanCountCheck() throws IOException, MalformedObjectNameException {
        SearchAssertion check = new SearchAssertion();
        check.setId("my.check");
        check.setDescription("Check that we have enough mbeans for java.lang");
        check.setMbeanName("java.lang:type=*");
        check.setComparison(">");
        check.setCount(1);

        return execCheck(check);
    }

    // ====================================

    private String execCheck(Assertion check) throws IOException, MalformedObjectNameException {
        ResultCollector collector = new ResultCollector();
        check.perform(context, collector);
        return collector.getFailures().toString();
    }


}

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
package org.jolokia.extra.addon.health.assertion;

import java.io.IOException;
import java.util.Set;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.jolokia.backend.executor.MBeanServerExecutor;

/**
 * Represents an assertion on the number of mbeans that match an expression
 */
public class SearchAssertion extends Assertion {
    private String mbeanName;
    private int count;

    @Override
    public String toString() {
        return "SearchAssertion{" +
                "id=" + getId() +
                ", description=" + getDescription() +
                ", mbeanName='" + mbeanName + '\'' +
                ", comparison='" + getComparison() + "'" +
                ", count=" + count +
                '}';
    }

    public String getMbeanName() {
        return mbeanName;
    }

    public void setMbeanName(String mbeanName) {
        this.mbeanName = mbeanName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void perform(MBeanServerExecutor serverExecutor, ResultCollector collector) throws IOException, MalformedObjectNameException {
        ObjectName objectName = new ObjectName(mbeanName);
        Set<ObjectName> objectNames = serverExecutor.queryNames(objectName);
        int size = 0;
        if (objectNames != null) {
            size = objectNames.size();
        }
        super.evaluate(collector, size, count);
    }
}

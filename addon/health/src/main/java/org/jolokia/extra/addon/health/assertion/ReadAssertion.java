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

import javax.management.*;

import org.jolokia.backend.executor.MBeanServerExecutor;

/**
 * Represents an assertion based on the value of an attribute of an mbean
 */
public class ReadAssertion extends Assertion {
    private String mbeanName;
    private String attribute;
    private Object value;

    @Override
    public String toString() {
        return "ReadAssertion{" +
                "id=" + getId() +
                ", description=" + getDescription() +
                ", mbeanName='" + mbeanName + '\'' +
                ", attribute='" + attribute + '\'' +
                ", comparison='" + getComparison() + "'" +
                ", value=" + value +
                '}';
    }

    public ReadAssertion() {
    }

    public ReadAssertion(ReadAssertion assertion, String mbeanName) {
        this.mbeanName = mbeanName;
        this.attribute = assertion.attribute;
        this.value = assertion.value;
        setId(assertion.getId());
        setDescription(assertion.getDescription());
        setComparison(assertion.getComparison());
    }


    public String getMbeanName() {
        return mbeanName;
    }

    public void setMbeanName(String mbeanName) {
        this.mbeanName = mbeanName;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public void perform(MBeanServerExecutor serverExecutor, final ResultCollector collector) throws IOException, MalformedObjectNameException {
        ObjectName objectName = new ObjectName(mbeanName);

        try {
            serverExecutor.each(objectName, new MBeanServerExecutor.MBeanEachCallback() {
                public void callback(MBeanServerConnection pConn, ObjectName pName) throws ReflectionException, InstanceNotFoundException, IOException, MBeanException {
                    // lets copy the assertion for each mbean as this could be using a wildcard
                    ReadAssertion assertion = new ReadAssertion(ReadAssertion.this, pName.toString());
                    try {
                        Object current = pConn.getAttribute(pName, attribute);
                        assertion.evaluateObject(collector, current, value);
                    } catch (AttributeNotFoundException e) {
                        throw new IOException(e);
                    }
                }
            });
        } catch (ReflectionException e) {
            throw new IOException(e);
        } catch (MBeanException e) {
            throw new IOException(e);
        }
    }
}

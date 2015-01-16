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

import javax.management.MalformedObjectNameException;

import org.jolokia.backend.executor.MBeanServerExecutor;

/**
 * Represents a base class for any kind of assertion
 */
public abstract class Assertion {
    protected String id;
    protected String description;
    protected String comparison;

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected void evaluate(ResultCollector collector, int actual, int value) {
        Comparison comparisonObject = getComparisonObject(collector);
        if (comparisonObject != null && !comparisonObject.evaluate(actual, value)) {
            collector.failed(this, actual, comparisonObject, value);
        }
    }

    protected void evaluate(ResultCollector collector, long actual, long value) {
        Comparison comparisonObject = getComparisonObject(collector);
        if (comparisonObject != null && !comparisonObject.evaluate(actual, value)) {
            collector.failed(this, actual, comparisonObject, value);
        }
    }

    protected void evaluate(ResultCollector collector, float actual, float value) {
        Comparison comparisonObject = getComparisonObject(collector);
        if (comparisonObject != null && !comparisonObject.evaluate(actual, value)) {
            collector.failed(this, actual, comparisonObject, value);
        }
    }

    protected void evaluate(ResultCollector collector, double actual, double value) {
        Comparison comparisonObject = getComparisonObject(collector);
        if (comparisonObject != null && !comparisonObject.evaluate(actual, value)) {
            collector.failed(this, actual, comparisonObject, value);
        }
    }

    protected void evaluate(ResultCollector collector, Comparable actual, Comparable value) {
        Comparison comparisonObject = getComparisonObject(collector);
        if (comparisonObject != null && !comparisonObject.evaluate(actual, value)) {
            collector.failed(this, actual, comparisonObject, value);
        }
    }

    /**
     * Evaluate a pair of objects and delegate to the typesafe API
     */
    protected void evaluateObject(ResultCollector collector, Object actual, Object value) {
        Object o = actual;
        if (o == null) {
            o = value;
        }
        if (o instanceof Number) {
            if (o instanceof Double) {
                evaluate(collector, asDouble(actual), asDouble(value));
            } else if (o instanceof Float) {
                evaluate(collector, asFloat(actual), asFloat(value));
            } else if (o instanceof Long) {
                evaluate(collector, asLong(actual), asLong(value));
            } else {
                evaluate(collector, asInt(actual), asInt(value));
            }
        } else {
            evaluate(collector, asComparable(actual), asComparable(value));
        }
    }

    protected double asDouble(Object value) {
        if (value instanceof Number) {
            Number n = (Number) value;
            return n.doubleValue();
        }
        return 0;
    }

    protected float asFloat(Object value) {
        if (value instanceof Number) {
            Number n = (Number) value;
            return n.floatValue();
        }
        return 0;
    }

    protected long asLong(Object value) {
        if (value instanceof Number) {
            Number n = (Number) value;
            return n.longValue();
        }
        return 0;
    }

    protected int asInt(Object value) {
        if (value instanceof Number) {
            Number n = (Number) value;
            return n.intValue();
        }
        return 0;
    }

    protected Comparable asComparable(Object value) {
        if (value instanceof Comparable) {
            return (Comparable) value;
        }
        return null;
    }


    protected Comparison getComparisonObject(ResultCollector collector) {
        Comparison comparisonObject = Comparison.fromText(comparison);
        if (comparisonObject == null) {
            collector.missingComparison(this);
        }
        return comparisonObject;
    }

    public abstract void perform(MBeanServerExecutor serverExecutor, ResultCollector collector) throws IOException, MalformedObjectNameException;
}

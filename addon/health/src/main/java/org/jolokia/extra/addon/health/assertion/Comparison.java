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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a comparision operation on a counter or value
 */
public enum Comparison {

    LESS_THAN("<") {
        @Override
        protected boolean evaluateDifference(int result) {
            return result < 0;
        }
    },
    LESS_THAN_OR_EQUAL("<=") {
        @Override
        protected boolean evaluateDifference(int result) {
            return result <= 0;
        }
    },
    EQUAL("==") {
        @Override
        protected boolean evaluateDifference(int result) {
            return result == 0;
        }
    },
    NOT_EQUAL("!=") {
        @Override
        protected boolean evaluateDifference(int result) {
            return result != 0;
        }
    },
    GREATER_THAN(">") {
        @Override
        protected boolean evaluateDifference(int result) {
            return result > 0;
        }
    },
    GREATER_THAN_EQUAL(">") {
        @Override
        protected boolean evaluateDifference(int result) {
            return result >= 0;
        }
    };
    private static Map<String,Comparison> map = new HashMap<String, Comparison>();

    static {
        for (Comparison comparison : Comparison.values()) {
            map.put(comparison.getText(), comparison);
        }
    }

    /**
     * Returns the {@link Comparison} object for the given expression text like ">" or "=="
     */
    public static Comparison fromText(String text) {
        return map.get(text);
    }

    private final String text;

    Comparison(String text) {
        this.text = text;
    }


    public boolean evaluate(int v1, int v2) {
        int diff = v1 - v2;
        return evaluateDifference(diff);
    }

    public boolean evaluate(long v1, long v2) {
        int diff = v1 > v2 ? 1 : v1 == v2 ? 0 : -1;
        return evaluateDifference(diff);
    }

    public boolean evaluate(float v1, float v2) {
        int diff = v1 > v2 ? 1 : v1 == v2 ? 0 : -1;
        return evaluateDifference(diff);
    }

    public boolean evaluate(double v1, double v2) {
        int diff = v1 > v2 ? 1 : v1 == v2 ? 0 : -1;
        return evaluateDifference(diff);
    }

    public boolean evaluate(Comparable v1, Comparable v2) {
        int diff;
        if (v1 == v2) {
            diff = 0;
        } else {
            if (v1 != null && v2 == null) {
                diff = 1;
            } else if (v1 == null) {
                diff = -1;
            } else {
                diff = v1.compareTo(v2);
            }
        }
        return evaluateDifference(diff);
    }

    protected abstract boolean evaluateDifference(int result);

    public String getText() {
        return text;
    }
}

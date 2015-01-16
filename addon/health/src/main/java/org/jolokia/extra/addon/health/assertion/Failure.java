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

/**
 * Represents a failure check result
 */
public class Failure {
    private final Assertion assertion;
    private final Object actualValue;

    public Failure(Assertion assertion, Object actualValue) {
        this.assertion = assertion;
        this.actualValue = actualValue;
    }

    @Override
    public String toString() {
        return "Failure{" +
                "assertion=" + assertion +
                ", actualValue=" + actualValue +
                '}';
    }

    public Assertion getAssertion() {
        return assertion;
    }

    public Object getActualValue() {
        return actualValue;
    }
}

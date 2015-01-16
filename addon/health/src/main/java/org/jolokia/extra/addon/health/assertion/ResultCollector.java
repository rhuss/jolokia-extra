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

import java.util.ArrayList;
import java.util.List;

/**
 * A collector of results after performing a number of health checks
 */
public class ResultCollector {
    private List<Failure> failures = new ArrayList<Failure>();

    public void failed(Assertion check, Object actual, Comparison comparisonObject, Object value) {
        failures.add(new Failure(check, actual));
    }

    public void missingComparison(Assertion assertion) {
        // log warning?
    }

    /**
     * Returns the failure results
     */
    public List<Failure> getFailures() {
        return failures;
    }

    /**
     * Returns true if there were no failures
     */
    public boolean isValid() {
        return failures.isEmpty();
    }
}

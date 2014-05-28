/*
 *  Copyright 2012 Marcin Plonka
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


/**
 * @author mplonka
 */
package org.jolokia.extra.addon.jsr77.simplifier.statistic;

import javax.management.j2ee.statistics.BoundedRangeStatistic;

public class BoundedRangeStatisticSimplifier
		extends BoundaryStatisticSimplifier<BoundedRangeStatistic> {
    @SuppressWarnings("unchecked")
    public BoundedRangeStatisticSimplifier() {
        super(BoundedRangeStatistic.class);

		addExtractor("current", new CurrentExtractor());
		addExtractor("highWaterMark", new HighWaterMarkExtractor());
		addExtractor("lowWaterMark", new LowWaterMarkExtractor());
	}

    private static class CurrentExtractor implements AttributeExtractor<BoundedRangeStatistic> {
        public Long extract(BoundedRangeStatistic o) {
            return o.getCurrent();
        }
    }

    private static class HighWaterMarkExtractor implements AttributeExtractor<BoundedRangeStatistic> {
        public Long extract(BoundedRangeStatistic o) {
            return o.getHighWaterMark();
        }
    }

    private static class LowWaterMarkExtractor implements AttributeExtractor<BoundedRangeStatistic> {
        public Long extract(BoundedRangeStatistic o) {
            return o.getLowWaterMark();
        }
    }
}

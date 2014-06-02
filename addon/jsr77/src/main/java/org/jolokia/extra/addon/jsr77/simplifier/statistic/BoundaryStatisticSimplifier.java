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

import javax.management.j2ee.statistics.BoundaryStatistic;

public class BoundaryStatisticSimplifier<T extends BoundaryStatistic> extends StatisticSimplifier<T> {
    @SuppressWarnings("unchecked")
    public BoundaryStatisticSimplifier() {
        this((Class<T>) BoundaryStatistic.class);
    }

	protected BoundaryStatisticSimplifier(Class<T> type) {
		super(type);
		addExtractor("lowerBound", new AttributeExtractor<T>() {
            public Long extract(T o) {
                return o.getLowerBound();
            }
        });
		addExtractor("upperBound", new AttributeExtractor<T>() {
            public Long extract(T o) {
                return o.getUpperBound();
            }
        });
	}

}

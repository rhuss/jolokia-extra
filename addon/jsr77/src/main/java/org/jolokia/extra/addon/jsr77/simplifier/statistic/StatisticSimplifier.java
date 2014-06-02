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

import javax.management.j2ee.statistics.Statistic;

import org.jolokia.converter.json.simplifier.SimplifierExtractor;

public class StatisticSimplifier<T extends Statistic> extends SimplifierExtractor<T> {
    @SuppressWarnings("unchecked")
    public StatisticSimplifier() {
        this((Class<T>) Statistic.class);
    }

	@SuppressWarnings("unchecked")
	protected StatisticSimplifier(Class<T> type) {
        super(type);
        addExtractors(new Object[][]{
                {"lastSampleTime", new AttributeExtractor<T>() {
                    public Long extract(T o) {
                        return o.getLastSampleTime();
                    }
                }},
                {"name", new AttributeExtractor<T>() {
                    public String extract(T o) {
                        return o.getName();
                    }
                }},
                {"startTime", new AttributeExtractor<T>() {
                    public Long extract(T o) {
                        return o.getStartTime();
                    }
                }},
                {"unit", new AttributeExtractor<T>() {
                    public String extract(T o) {
                        return o.getUnit();
                    }
                }}
        });
    }

}

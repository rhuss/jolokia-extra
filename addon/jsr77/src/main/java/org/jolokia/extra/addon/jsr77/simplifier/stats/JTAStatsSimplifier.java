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
package org.jolokia.extra.addon.jsr77.simplifier.stats;

import javax.management.j2ee.statistics.CountStatistic;
import javax.management.j2ee.statistics.JTAStats;

public class JTAStatsSimplifier extends StatsSimplifier<JTAStats> {

    public JTAStatsSimplifier() {
        this(JTAStats.class);
    }

	protected JTAStatsSimplifier(Class<JTAStats> type) {
		super(type);
		addExtractor("activeCount", new ActiveCountExtractor());
		addExtractor("committedCount", new CommittedCountExtractor());
		addExtractor("rolledbackCount", new RolledbackCountExtractor());
	}

    // ================================================================================================

    private static class ActiveCountExtractor implements AttributeExtractor<JTAStats> {
        public CountStatistic extract(JTAStats o) {
            return o.getActiveCount();
        }
    }

    private static class CommittedCountExtractor implements AttributeExtractor<JTAStats> {
        public CountStatistic extract(JTAStats o) {
            return o.getCommittedCount();
        }
    }

    private static class RolledbackCountExtractor implements AttributeExtractor<JTAStats> {
        public CountStatistic extract(JTAStats o) {
            return o.getRolledbackCount();
        }
    }
}

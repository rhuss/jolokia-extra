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

import javax.management.j2ee.statistics.*;

public class JDBCConnectionPoolStatsSimplifier
		extends JDBCConnectionStatsSimplifier<JDBCConnectionPoolStats> {

    public JDBCConnectionPoolStatsSimplifier() {
        super(JDBCConnectionPoolStats.class);
        addExtractors(new Object[][]{
                {"closeCount", new CloseCountExtractor()},
                {"createCount", new CreateCountExtractor()},
                {"poolSize", new PoolSizeExtractor()},
                {"freePoolSize", new FreePoolSizeExtractor()},
                {"waitingThreadCount", new WaitingThreadCountExtractor()}
        });
    }

    private static class CloseCountExtractor implements AttributeExtractor<JDBCConnectionPoolStats> {
        public CountStatistic extract(JDBCConnectionPoolStats o) {
            return o.getCloseCount();
        }
    }

    private static class CreateCountExtractor implements AttributeExtractor<JDBCConnectionPoolStats> {
        public CountStatistic extract(JDBCConnectionPoolStats o) {
            return o.getCreateCount();
        }
    }

    private static class PoolSizeExtractor implements AttributeExtractor<JDBCConnectionPoolStats> {
        public BoundedRangeStatistic extract(JDBCConnectionPoolStats o) {
            return o.getPoolSize();
        }
    }

    private static class FreePoolSizeExtractor implements AttributeExtractor<JDBCConnectionPoolStats> {
        public BoundedRangeStatistic extract(JDBCConnectionPoolStats o) {
            return o.getFreePoolSize();
        }
    }

    private static class WaitingThreadCountExtractor implements AttributeExtractor<JDBCConnectionPoolStats> {
        public RangeStatistic extract(JDBCConnectionPoolStats o) {
            return o.getWaitingThreadCount();
        }
    }
}

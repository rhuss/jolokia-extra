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

import javax.management.j2ee.statistics.JCAConnectionStats;
import javax.management.j2ee.statistics.TimeStatistic;

public class JCAConnectionStatsSimplifier<T extends JCAConnectionStats> extends StatsSimplifier<T> {
    @SuppressWarnings("unchecked")
    public JCAConnectionStatsSimplifier() {
        this((Class<T>) JCAConnectionStats.class);
    }

	protected JCAConnectionStatsSimplifier(Class<T> type) {
        super(type);
        addExtractors(new Object[][]{
                {"connectionFactory", new ConnectionFactoryExtractor<T>()},
                {"managedConnectionFactory", new ManagedConnectionFactoryExtractor<T>()},
                {"useTime", new UseTimeExtractor<T>()},
                {"waitTime", new WaitTimeExtractor<T>()}
        });
    }

    private class ConnectionFactoryExtractor<T extends JCAConnectionStats> implements AttributeExtractor<T> {
        public String extract(T o) {
            return o.getConnectionFactory();
        }
    }

    private class ManagedConnectionFactoryExtractor<T extends JCAConnectionStats> implements AttributeExtractor<T> {
        public String extract(T o) {
            return o.getManagedConnectionFactory();
        }
    }

    private class UseTimeExtractor<T extends JCAConnectionStats> implements AttributeExtractor<T> {
        public TimeStatistic extract(T o) {
            return o.getUseTime();
        }
    }

    private class WaitTimeExtractor<T extends JCAConnectionStats> implements AttributeExtractor<T> {
        public TimeStatistic extract(T o) {
            return o.getWaitTime();
        }
    }
}

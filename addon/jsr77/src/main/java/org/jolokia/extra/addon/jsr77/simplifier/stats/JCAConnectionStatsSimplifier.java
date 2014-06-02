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

public class JCAConnectionStatsSimplifier extends StatsSimplifier<JCAConnectionStats> {

    public JCAConnectionStatsSimplifier() {
        super(JCAConnectionStats.class);
        addExtractors(new Object[][]{
                {"connectionFactory", new ConnectionFactoryExtractor()},
                {"managedConnectionFactory", new ManagedConnectionFactoryExtractor()},
        });
    }

    private static class ConnectionFactoryExtractor implements AttributeExtractor<JCAConnectionStats> {
        public String extract(JCAConnectionStats o) {
            return o.getConnectionFactory();
        }
    }

    private static class ManagedConnectionFactoryExtractor implements AttributeExtractor<JCAConnectionStats> {
        public String extract(JCAConnectionStats o) {
            return o.getManagedConnectionFactory();
        }
    }
}

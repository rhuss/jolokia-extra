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

import java.util.Map;

import javax.management.j2ee.statistics.JDBCConnectionStats;
import javax.management.j2ee.statistics.JDBCStats;

import org.jolokia.extra.addon.jsr77.simplifier.util.ArrayMapConverter;

public class JDBCStatsSimplifier extends StatsSimplifier<JDBCStats> {

    public JDBCStatsSimplifier() {
		super(JDBCStats.class);

        final ArrayMapConverter<JDBCConnectionStats> arrayConverter = createConverter();

        // Since JDBCConnectionPoolStats inherits from JDBCConnectionStats but JDBCConnectionPoolStats
        // doesnt add any extra values (remember, we are creating the vlues from getStatistics())
        // we can reuse it here
        addExtractor("connectionPools", new AttributeExtractor<JDBCStats>() {
            public Map<String,JDBCConnectionStats> extract(JDBCStats o) {
                return arrayConverter.convert(o.getConnectionPools(),"name");
            }
        });
		addExtractor("connections", new AttributeExtractor<JDBCStats>() {
            public Map<String,JDBCConnectionStats> extract(JDBCStats o) {
                return arrayConverter.convert(o.getConnections(),"name");
            }
        });
	}

    // =========================

    private ArrayMapConverter<JDBCConnectionStats> createConverter() {

        ArrayMapConverter.KeyExtractor<JDBCConnectionStats> keyExtractor =
                new ArrayMapConverter.KeyExtractor<JDBCConnectionStats>() {
                    public String extractKey(JDBCConnectionStats value) {
                        return value.getJdbcDataSource();
                    }
                };
        return new ArrayMapConverter<JDBCConnectionStats>(keyExtractor);
    }
}

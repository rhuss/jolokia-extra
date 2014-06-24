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

import javax.management.j2ee.statistics.*;

import org.jolokia.extra.addon.jsr77.simplifier.util.ArrayMapConverter;

public class JCAStatsSimplifier extends StatsSimplifier<JCAStats> {

    public JCAStatsSimplifier() {
        this(JCAStats.class);
    }

	protected JCAStatsSimplifier(Class<JCAStats> type) {
		super(type);
        final ArrayMapConverter<JCAConnectionStats> converter = createConverter();
		addExtractor("connectionPools", new AttributeExtractor<JCAStats>() {
            public Map<String,JCAConnectionStats> extract(JCAStats o) {
                return converter.convert(o.getConnectionPools(), "name");
            }
        });
		addExtractor("connections", new AttributeExtractor<JCAStats>() {
            public Map<String,JCAConnectionStats> extract(JCAStats o) {
                return converter.convert(o.getConnections(), "name");
            }
        });
	}

    // =========================

    private ArrayMapConverter<JCAConnectionStats> createConverter() {

        ArrayMapConverter.KeyExtractor<JCAConnectionStats> keyExtractor =
                new ArrayMapConverter.KeyExtractor<JCAConnectionStats>() {
                    public String extractKey(JCAConnectionStats value) {
                        String name = value.getManagedConnectionFactory();
                        if (name == null) {
                            value.getConnectionFactory();
                        }
                        if (name == null) {
                            throw new IllegalStateException("No connection factory available as required for JSR-77");
                        }
                        return name;
                    }
                };
        
        return new ArrayMapConverter<JCAConnectionStats>(keyExtractor);
    }

}

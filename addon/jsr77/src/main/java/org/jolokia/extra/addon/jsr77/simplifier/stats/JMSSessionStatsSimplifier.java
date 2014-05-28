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

public class JMSSessionStatsSimplifier extends StatsSimplifier<JMSSessionStats> {

    public JMSSessionStatsSimplifier() {
        this(JMSSessionStats.class);
    }

	protected JMSSessionStatsSimplifier(Class<JMSSessionStats> type) {
		super(type);
		addExtractors(new Object[][]{
                {"consumers", new ConsumersExtractor()},
                {"durableSubscriptionCount", new DurableSubscriptionCountExtractor()},
                {"expiredMessageCount", new ExpiredMessageCountExtractor()},
                {"messageCount", new MessageCountExtractor()},
                {"messageWaitTime", new MessageWaitTimeExtractor()},
                {"pendingMessageCount", new PendingMessageCountExtractor()},
                {"producers", new ProducersExtractor()}
        });
	}

    private static class ConsumersExtractor implements AttributeExtractor<JMSSessionStats> {
        public JMSConsumerStats[] extract(JMSSessionStats o) {
            return o.getConsumers();
        }
    }

    private static class DurableSubscriptionCountExtractor implements AttributeExtractor<JMSSessionStats> {
        public CountStatistic extract(JMSSessionStats o) {
            return o.getDurableSubscriptionCount();
        }
    }

    private static class ExpiredMessageCountExtractor implements AttributeExtractor<JMSSessionStats> {
        public CountStatistic extract(JMSSessionStats o) {
            return o.getExpiredMessageCount();
        }
    }

    private static class MessageCountExtractor implements AttributeExtractor<JMSSessionStats> {
        public CountStatistic extract(JMSSessionStats o) {
            return o.getMessageCount();
        }
    }

    private static class MessageWaitTimeExtractor implements AttributeExtractor<JMSSessionStats> {
        public TimeStatistic extract(JMSSessionStats o) {
            return o.getMessageWaitTime();
        }
    }

    private static class PendingMessageCountExtractor implements AttributeExtractor<JMSSessionStats> {
        public CountStatistic extract(JMSSessionStats o) {
            return o.getPendingMessageCount();
        }
    }

    private static class ProducersExtractor implements AttributeExtractor<JMSSessionStats> {
        public JMSProducerStats[] extract(JMSSessionStats o) {
            return o.getProducers();
        }
    }
}

/**
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.streaming.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.config.SiddhiConfiguration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.stratio.streaming.extensions.DistinctWindowExtension;

@Configuration
// TODO refactor
public class StreamingSiddhiConfiguration {

    public static final String QUERY_PLAN_IDENTIFIER = "StratioStreamingCEP-Cluster";

    @Autowired
    private ConfigurationContext configurationContext;

    @Bean(destroyMethod = "shutdown")
    public SiddhiManager siddhiManager() {
        SiddhiConfiguration conf = new SiddhiConfiguration();
        conf.setInstanceIdentifier("StratioStreamingCEP-Instance-" + UUID.randomUUID().toString());
        conf.setQueryPlanIdentifier(QUERY_PLAN_IDENTIFIER);
        conf.setDistributedProcessing(false);

        @SuppressWarnings("rawtypes")
        List<Class> extensions = new ArrayList<>();
        extensions.add(DistinctWindowExtension.class);
        conf.setSiddhiExtensions(extensions);

        // Create Siddhi Manager
        SiddhiManager siddhiManager = new SiddhiManager(conf);

        Config config = new Config();
        config.setInstanceName("stratio-streaming-hazelcast-instance");
        NetworkConfig network = config.getNetworkConfig();
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);

        // TODO enable failover
        // if (configurationContext.isFailOverEnabled()) {
        //
        // siddhiManager.setPersistStore(new
        // Casandra2PersistenceStore(configurationContext.getCassandraHostsQuorum(),
        // "", ""));
        //
        // StreamPersistence.restoreLastRevision(siddhiManager);
        // }

        return siddhiManager;
    }

}

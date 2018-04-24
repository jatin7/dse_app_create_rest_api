package com.academy.datastax.conf;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.dse.DseCluster.Builder;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.auth.DsePlainTextAuthProvider;
import com.datastax.driver.mapping.MappingManager;

/**
 * Connectivity to DSE (cassandra, graph, search, analytics).
 *
 * @author DataStax evangelist team.
 */
@Configuration
public class DseConfiguration {

	/** Internal logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DseConfiguration.class);
    
    @Value("${dse.cassandra.username}")
    public Optional < String > dseUsername;
   
    @Value("${dse.cassandra.password}")
    public Optional < String > dsePassword;
    
    @Value("${dse.cassandra.host:localhost}")
    private String cassandraHost;
    
    @Value("${dse.cassandra.hosts:9042}")
    private int cassandraPort;
    
    @Value("${dse.cassandra.keyspace: demo}")
    private String cassandraKeyspace;
   
    @Bean
    public DseSession dseSession() {
         LOGGER.info("Initializing connection to DSE Cluster...");
         Builder clusterConfig = new Builder();
         clusterConfig.addContactPoint(cassandraHost);
         clusterConfig.withPort(cassandraPort);
         // Authentication (if provided)
         if (dseUsername.isPresent() && dsePassword.isPresent()  && dseUsername.get().length() > 0) {
            clusterConfig.withAuthProvider(
                    new DsePlainTextAuthProvider(dseUsername.get(), dsePassword.get()));
         }
         return clusterConfig.build().connect(cassandraKeyspace);
    }
    
    @Bean
    public MappingManager mappingMapper(DseSession session) {
        return new MappingManager(session);
    }
}

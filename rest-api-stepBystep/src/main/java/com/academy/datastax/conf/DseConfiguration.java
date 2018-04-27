package com.academy.datastax.conf;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.academy.datastax.utils.DseUtils;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseCluster.Builder;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.auth.DsePlainTextAuthProvider;

/**
 * Connectivity to DSE.
 * 
 * Information here
 * https://docs.datastax.com/en/developer/java-driver-dse/1.6/
 * 
 * @author Datastax Academy.
 */
@Configuration
public class DseConfiguration {
	
    @Bean
    public DseCluster dseCluster (
        @Value("${dse.cassandra.host:localhost}") String cassandraHost,
        @Value("${dse.cassandra.clusterName:Test Cluster}") String cassandraClusterName,
        @Value("${dse.cassandra.hosts:9042}") int cassandraPort,
        @Value("${dse.cassandra.username}") Optional < String > dseUsername,
        @Value("${dse.cassandra.password}") Optional < String > dsePassword) {
        Builder clusterConfig = new Builder();
        clusterConfig.addContactPoint(cassandraHost);
        clusterConfig.withPort(cassandraPort);
        clusterConfig.withClusterName(cassandraClusterName);
        if (dseUsername.isPresent() && dsePassword.isPresent()  && dseUsername.get().length() > 0) {
           clusterConfig.withAuthProvider(new DsePlainTextAuthProvider(dseUsername.get(), dsePassword.get()));
        }
        return clusterConfig.build();
    }
    
    @Bean
    public DseSession dseSession(
            DseCluster dseCluster, 
            @Value("${dse.cassandra.keyspace: demo}") String cassandraKeyspace) {
        final DseSession session = dseCluster.connect();
        DseUtils.createKeySpaceSimpleStrategy(session, cassandraKeyspace, 3);
        return session;
    }
    
}

package com.academy.datastax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academy.datastax.conf.DseConfiguration;
import com.academy.datastax.utils.DseUtils;
import com.datastax.driver.dse.DseSession;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= { DseConfiguration.class })
@TestPropertySource(locations = { "classpath:application-test.properties"})
public class TestConnection {
    
    @Autowired
    private DseSession dseSession;
    
    @Value("${dse.cassandra.keyspace}")
    private String cassandraKeyspace;
    
    @Test
    public void testEnvironment() throws FileNotFoundException {
        assertNotNull(cassandraKeyspace);
        assertNotNull(dseSession);
        assertEquals(cassandraKeyspace, dseSession.getLoggedKeyspace());
        
        DseUtils.executeCQLFile(dseSession, "/cql/create-schema.cql");
        DseUtils.executeCQLFile(dseSession, "/cql/import-data.cql");
        
        System.out.println("--------------------------------------------");
        System.out.println("Congratulations you are ready to START !!  ");
        System.out.println("--------------------------------------------");
    }
  
}

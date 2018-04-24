package com.academy.datastax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileNotFoundException;

import org.junit.Ignore;
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
@Ignore
public class TestConnection {
    
    @Autowired
    private DseSession dseSession;
    
    @Value("${dse.cassandra.keyspace}")
    private String cassandraKeyspace;
    
    @Test
    public void getConnectedKeySpace() {
        assertNotNull(cassandraKeyspace);
        assertNotNull(dseSession);
        assertEquals(cassandraKeyspace, dseSession.getLoggedKeyspace());
    }
    
    @Test
    public void testCreateSchema() throws FileNotFoundException {
        DseUtils.executeCQLFile(dseSession, "/cql/create-schema.cql");
    }
    
    @Test
    public void testInsertWithCQL() {
        String text = "Hello World.";
        dseSession.execute(""
                + "INSERT INTO comments_by_user (commentid, userid, videoid, comment) " 
                + "VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d, b17e0fa3-62f7-47f6-a47a-552c925d4d79, " 
                + "        12b5b195-46d7-492a-a7ec-1909688901da,'" + text + "');");
        
        assertEquals(text, 
        dseSession.execute(""
                + "SELECT * FROM comments_by_user "
                + "WHERE commentid = 1aae5f50-445e-11e8-8977-abaff7c8fa1d "
                + "AND userid = b17e0fa3-62f7-47f6-a47a-552c925d4d79;")
                  .all().stream().findFirst().get().getString("comment"));
    }
    
    @Test
    public void testDropSchema() throws FileNotFoundException {
        DseUtils.executeCQLFile(dseSession, "/cql/drop-schema.cql");
    }
   

}

package com.academy.datastax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academy.datastax.conf.DseConfiguration;
import com.academy.datastax.dao.CommentDseCrudRepository01_PrepareStatement;
import com.datastax.driver.dse.DseSession;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes= { DseConfiguration.class, CommentDseCrudRepository01_PrepareStatement.class })
@TestPropertySource(
        locations = { "classpath:application-test.properties"})
public class TestCrudDaoStep01 {
    
    @Autowired
    private DseSession dseSession;
    
    @Autowired
    private CommentDseCrudRepository01_PrepareStatement dao;
    
    @Test
    public void insert() {
        String commentId = "1aae5f50-445e-11e8-8977-abaff7c8fa1d";
        String userid = "b17e0fa3-62f7-47f6-a47a-552c925d4d79";
        String videoid = "12b5b195-46d7-492a-a7ec-1909688901da";
        String comment = "popo";
        
        dao.insert(commentId, userid, videoid, comment);
        
        assertEquals(comment, 
        dseSession.execute(""
                + "SELECT * FROM comments_by_user "
                + "WHERE commentid = 1aae5f50-445e-11e8-8977-abaff7c8fa1d "
                + "AND userid = b17e0fa3-62f7-47f6-a47a-552c925d4d79;")
                  .all().stream().findFirst().get().getString("comment"));
    }

}

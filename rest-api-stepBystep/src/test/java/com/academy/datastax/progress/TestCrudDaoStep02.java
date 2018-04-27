package com.academy.datastax.progress;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academy.datastax.conf.DseConfiguration;
import com.academy.datastax.dao.CommentDseCrudRepository02_BatchStatement;
import com.datastax.driver.dse.DseSession;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes= { DseConfiguration.class, CommentDseCrudRepository02_BatchStatement.class })
@TestPropertySource(
        locations = { "classpath:application-test.properties"})
public class TestCrudDaoStep02 {
    
    @Autowired
    private DseSession dseSession;
    
    @Autowired
    private CommentDseCrudRepository02_BatchStatement dao;
    
    @Test
    public void insert() {
        String commentId = "1aae5f50-445e-11e8-8977-abaff7c8fa1d";
        String userid    = "b17e0fa3-62f7-47f6-a47a-552c925d4d79";
        String videoid   = "12b5b195-46d7-492a-a7ec-1909688901da";
        String comment   = "popo";
        
        dao.insert(commentId, userid, videoid, comment);
        
        assertEquals(comment, 
                dseSession.execute(""
                    + "SELECT * FROM comments_by_user "
                    + "WHERE commentid = " + commentId
                    + "AND userid = "      + userid + ";")
                  .all().stream().findFirst().get().getString("comment"));
        
        assertEquals(comment, 
                dseSession.execute(""
                    + "SELECT * FROM comments_by_video "
                    + "WHERE commentid = " + commentId
                    + "AND videoid = "      + videoid + ";")
                  .all().stream().findFirst().get().getString("comment"));
    }

}

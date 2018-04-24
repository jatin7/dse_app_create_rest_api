package com.academy.datastax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academy.datastax.conf.DseConfiguration;
import com.academy.datastax.dao.CommentDseCrudRepository02_BatchStatement;
import com.academy.datastax.dao.CommentDseCrudRepository03_CRUD;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.dse.DseSession;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes= { DseConfiguration.class, CommentDseCrudRepository03_CRUD.class })
@TestPropertySource(
        locations = { "classpath:application-test.properties"})
public class TestCrudDaoStep03 {
    
    @Autowired
    private DseSession dseSession;
    
    @Autowired
    private CommentDseCrudRepository03_CRUD dao;
    
    /**
     * Query count all with the limit clause.
     */
    protected long countAll(String tableName) {
        return dseSession.execute(QueryBuilder.select().countAll().from(tableName)
                         .limit(100).toString())
                         .iterator().next().getLong(0);
    }
    
    
    @Test
    public void insert() {
        UUID commentId = UUID.fromString("1aae5f50-445e-11e8-8977-abaff7c8fa1d");
        UUID userid    = UUID.fromString("b17e0fa3-62f7-47f6-a47a-552c925d4d79");
        UUID videoid   = UUID.fromString("12b5b195-46d7-492a-a7ec-1909688901da");
        String comment   = "popo";
        dao.upsert(commentId, userid, videoid, comment);
        
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
    
    
    @Test
    public void delete() {
        UUID commentId = UUID.fromString("1aae5f50-445e-11e8-8977-abaff7c8fa1d");
        UUID userid    = UUID.fromString("b17e0fa3-62f7-47f6-a47a-552c925d4d79");
        UUID videoid   = UUID.fromString("12b5b195-46d7-492a-a7ec-1909688901da");
        assertEquals(1, countAll("comments_by_video"));
        assertEquals(1, countAll("comments_by_user"));
        dao.delete(commentId, userid, videoid);
        assertEquals(0, countAll("comments_by_video"));
        assertEquals(0, countAll("comments_by_user"));
    }

}

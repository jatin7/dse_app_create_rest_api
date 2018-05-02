package com.academy.datastax.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.datastax.driver.core.querybuilder.QueryBuilder.update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.dse.DseSession;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDao02_QueryBuilder {
    
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
    
    /** Single place to holder tablename and colum name. */
    private static final String TABLENAME = "comments_by_user";
    private static final String COMMENTID = "commentid";
    private static final String USERID    = "userid";
    private static final String VIDEOID   = "videoid";
    private static final String COMMENT   = "comment";
    
    /** Default Constructor. */
    @Autowired
    public CommentDao02_QueryBuilder(DseSession dseSession) {
        this.dseSession     = dseSession;
    }
    
    // --- comments_by_user----
    
    /*
     * INSERT
     * 
     * INSERT INTO comments_by_user (commentid, userid, videoid, comment)
     * VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d,
     *         b17e0fa3-62f7-47f6-a47a-552c925d4d79,
     *         12b5b195-46d7-492a-a7ec-1909688901da,
     *         'Video A / Comment A');
     */
    public void insert(String commentid, String userid, String videoid, String comment) {
       dseSession.execute(insertInto(TABLENAME)
                 .value(COMMENTID, UUID.fromString(commentid))
                 .value(USERID,    UUID.fromString(userid))
                 .value(VIDEOID,   UUID.fromString(videoid))
                 .value(COMMENT,   comment)
                 .toString());
     }
    
    /* 
     * UPDATE
     * 
     * UPDATE comments_by_user 
     * SET comment = 'Hello'
     * where commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e
     * and userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;
     */
    public void updateComment(String commentid, String userId, String comment) {
         dseSession.execute(update(TABLENAME)
                     .with(set(COMMENT, comment))
                     .where(eq(COMMENTID, UUID.fromString(commentid)))
                     .and(eq(USERID,      UUID.fromString(userId)))
                     .toString());
     }
    
    /*
     * DELETE
     * 
     * DELETE FROM comments_by_user 
     * where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79
     * and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e;
     */
    public void deleteComment(String userid, String commentid) {
        dseSession.execute(delete().from(TABLENAME)
                .where(eq(COMMENTID, UUID.fromString(commentid)))
                .and(eq(USERID,      UUID.fromString(userid)))
                .toString());
    }
    
    /*
     * READ (one)
     * 
     * SELECT comment 
     * FROM comments_by_user
     * where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79 
     * and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e 
     */
    public Optional < String > find(String useruuid, String commentid) {
        ResultSet rs = dseSession.execute(
                select(COMMENT)
                .from(TABLENAME)
                .where(eq(COMMENTID, UUID.fromString(commentid)))
                .and(eq(USERID, UUID.fromString(useruuid)))
                .toString());
        
        Optional < String > result = Optional.empty();
        Iterator< Row > rows = rs.iterator();
        if (rows.hasNext()) {
            return Optional.ofNullable(rows.next().getString(COMMENT));
        }
        return result;
    }
    
    /*
     * READ (user)
     * SELECT comment 
     * FROM comments_by_user
     * where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;
     */
    public List < String > findByUserId(String useruuid) {
        ResultSet rs = dseSession.execute(select(COMMENT)
                .from(TABLENAME)
                .where(eq(USERID, UUID.fromString(useruuid)))
                .toString());
        
        List < String > result = new ArrayList<>();
        for (Row row : rs) {
            result.add(row.getString(COMMENT));
        }
        return result;
    }

}

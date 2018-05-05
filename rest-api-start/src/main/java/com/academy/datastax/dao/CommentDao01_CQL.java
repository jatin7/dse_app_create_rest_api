package com.academy.datastax.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
public class CommentDao01_CQL {
    
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
   
    /** Default Constructor. */
    @Autowired
    public CommentDao01_CQL(DseSession dseSession) {
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
        StringBuilder query = new StringBuilder()
                 .append("INSERT INTO comments_by_user (commentid, userid, videoid, comment) ")
                 .append("VALUES(")
                 .append(commentid + ",").append(userid + ",")
                 .append(videoid + ",").append("'" + comment + "');");
         dseSession.execute(query.toString());
     }
    
    /* 
     * UPDATE
     * 
     * UPDATE comments_by_user 
     * SET comment = 'Hello'
     * where commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e
     * and userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;
     */
    public void update(String commentid, String userId, String comment) {
        StringBuilder query = new StringBuilder()
                 .append("UPDATE comments_by_user ")
                 .append("SET comment=")
                 .append("'" + comment + "' ")
                 .append("WHERE commentid=")
                 .append(commentid)
                 .append("AND userid=")
                 .append(userId);
         dseSession.execute(query.toString());
     }
    
    /*
     * DELETE
     * 
     * DELETE FROM comments_by_user 
     * where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79
     * and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e;
     */
    public void delete(String userid, String commentid) {
        dseSession.execute(new StringBuilder()
                .append("DELETE FROM comments_by_user ")
                .append("WHERE commentid=")
                .append(commentid)
                .append("AND userid=")
                .append(userid).toString());
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
        ResultSet rs = dseSession.execute(new StringBuilder()
                .append("SELECT comment FROM comments_by_user ")
                .append("WHERE commentid=")
                .append(commentid)
                .append("AND userid=")
                .append(useruuid).toString());
        Optional < String > result = Optional.empty();
        Iterator< Row > rows = rs.iterator();
        if (rows.hasNext()) {
            return Optional.ofNullable(rows.next().getString("comment"));
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
        ResultSet rs = dseSession.execute(new StringBuilder()
                .append("SELECT comment FROM comments_by_user where userid=")
                .append(useruuid).toString());
        List < String > result = new ArrayList<>();
        for (Row row : rs) {
            result.add(row.getString("comment"));
        }
        return result;
    }

}

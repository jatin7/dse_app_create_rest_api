package com.academy.datastax.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.dse.DseSession;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDseCrudRepository {
    
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
   
    /** Default Constructor. */
    @Autowired
    public CommentDseCrudRepository(DseSession dseSession) {
        this.dseSession     = dseSession;
    }
    
    public void insert(String commentid, String userid, String videoid, String comment) {
        
        StringBuilder query = new StringBuilder()
                .append("INSERT INTO comments_by_user (commentid, userid, videoid, comment) ")
                .append("VALUES(")
                .append(commentid + ",").append(userid + ",")
                .append(videoid + ",").append("'" + comment + "');");
        
        dseSession.execute(query.toString());
    }

}

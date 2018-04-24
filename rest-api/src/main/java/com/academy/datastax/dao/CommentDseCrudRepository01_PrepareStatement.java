package com.academy.datastax.dao;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.dse.DseSession;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDseCrudRepository01_PrepareStatement {
    
    /** Hold Connectivity to DSE. */
    @Autowired
    protected DseSession dseSession;
    
    /** Always use {@link PreparedStatement} when possible. */
    private PreparedStatement insertIntoUserComment;

    /** Default Constructor. */
    public CommentDseCrudRepository01_PrepareStatement() {}
            
    /** Default Constructor. */
    public CommentDseCrudRepository01_PrepareStatement(DseSession dseSession) {
        this.dseSession     = dseSession;
        prepare();
    }
    
    @PostConstruct
    public void prepare() {
        insertIntoUserComment = dseSession.prepare(
                QueryBuilder.insertInto("comments_by_user")
                        .value("commentid", QueryBuilder.bindMarker())
                        .value("userid", QueryBuilder.bindMarker())
                        .value("videoid", QueryBuilder.bindMarker())
                        .value("comment", QueryBuilder.bindMarker()));
    }
    
    public void insert(String commentid, String userid, String videoid, String comment) {
        final BoundStatement insertComment = insertIntoUserComment.bind()
                .setUUID("commentid", UUID.fromString(commentid))
                .setUUID("userid",  UUID.fromString(userid))
                .setUUID("videoid",  UUID.fromString(videoid))
                .setString("comment",  comment);        
        dseSession.execute(insertComment);
    }
    
    

}

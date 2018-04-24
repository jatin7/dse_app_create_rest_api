package com.academy.datastax.dao;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
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
public class CommentDseCrudRepository02_BatchStatement {
    
    /** Hold Connectivity to DSE. */
    @Autowired
    protected DseSession dseSession;
    
    /** Always use {@link PreparedStatement} when possible. */
    private PreparedStatement insertIntoUserComment;
    private PreparedStatement insertIntoVideoComment;

    /** Default Constructor. */
    public CommentDseCrudRepository02_BatchStatement() {}
            
    /** Default Constructor. */
    public CommentDseCrudRepository02_BatchStatement(DseSession dseSession) {
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
        
        insertIntoVideoComment = dseSession.prepare(
                QueryBuilder.insertInto("comments_by_video")
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
        //dseSession.execute(insertComment);
        final BoundStatement insertVideo = insertIntoVideoComment.bind()
                .setUUID("commentid", UUID.fromString(commentid))
                .setUUID("userid",  UUID.fromString(userid))
                .setUUID("videoid",  UUID.fromString(videoid))
                .setString("comment",  comment);
        //dseSession.execute(insertVideo);
        
        BatchStatement batchStatement = new BatchStatement(BatchStatement.Type.UNLOGGED);
        batchStatement.add(insertComment); // Insert Query generate from annotated bean CommentByVideo
        batchStatement.add(insertVideo);   // Insert Query generate from annotated bean CommentByUser
        batchStatement.setDefaultTimestamp(System.currentTimeMillis());
        //batchStatement.setConsistencyLevel(ConsistencyLevel.QUORUM);
        dseSession.execute(batchStatement);
    }
    
    

}

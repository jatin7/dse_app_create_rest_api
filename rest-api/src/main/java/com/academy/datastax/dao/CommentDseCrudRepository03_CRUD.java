package com.academy.datastax.dao;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.academy.datastax.model.Comment;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.dse.DseSession;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDseCrudRepository03_CRUD {
    
    /** Hold Connectivity to DSE. */
    @Autowired
    protected DseSession dseSession;
    
    /** Always use {@link PreparedStatement} when possible. */
    private PreparedStatement insertIntoUserComment;
    private PreparedStatement insertIntoVideoComment;
    private PreparedStatement deleteFromVideoComment;
    private PreparedStatement deleteFromUserComment;
    public PreparedStatement selectVideoComment;
    private PreparedStatement selectUserComment;

    /** Default Constructor. */
    public CommentDseCrudRepository03_CRUD() {}
            
    /** Default Constructor. */
    public CommentDseCrudRepository03_CRUD(DseSession dseSession) {
        this.dseSession     = dseSession;
        prepare();
    }
    
    @PostConstruct
    public void prepare() {
        
        // CREATE, UPDATE
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
        
        // Delete
        deleteFromVideoComment = dseSession.prepare(
                QueryBuilder.delete()
                    .from("comments_by_video")
                    .where(QueryBuilder.eq("commentid", QueryBuilder.bindMarker()))
                    .and(QueryBuilder.eq("videoid", QueryBuilder.bindMarker())));
        deleteFromUserComment = dseSession.prepare(
                QueryBuilder.delete()
                    .from("comments_by_user")
                    .where(QueryBuilder.eq("commentid", QueryBuilder.bindMarker()))
                    .and(QueryBuilder.eq("userid", QueryBuilder.bindMarker())));
         
        // SELECT
        selectVideoComment = dseSession.prepare(
                QueryBuilder.select()
                        .column("userid").column("commentid")
                        .column("videoid").column("comment")
                        .fcall("toTimestamp", QueryBuilder.column("commentid")).as("comment_timestamp")
                    .from("comments_by_video")
                    .where(QueryBuilder.eq("videoid", QueryBuilder.bindMarker())));
        selectUserComment = dseSession.prepare(
                QueryBuilder.select()
                        .column("userid").column("commentid")
                        .column("videoid").column("comment")
                        .fcall("toTimestamp", QueryBuilder.column("commentid")).as("comment_timestamp")
                    .from("comments_by_user")
                    .where(QueryBuilder.eq("userid", QueryBuilder.bindMarker())));
    }
    
    /**
     * if you provide all fields, update is like INSERT.
     * Still following syntax is valid.
     *
     * UPDATE demo.comments_by_user SET comment = :comment "
     * WHERE userid = :userid AND commentid= :commentid;" 
     */
    // CREATE + UPDATE
    public void upsert(UUID commentid, UUID userid, UUID videoid, String comment) {
        final BoundStatement insertComment = insertIntoUserComment.bind()
                .setUUID("commentid", commentid).setUUID("userid",  userid)
                .setUUID("videoid",  videoid).setString("comment",  comment);
        final BoundStatement insertVideo = insertIntoVideoComment.bind()
                .setUUID("commentid", commentid).setUUID("userid",  userid)
                .setUUID("videoid",  videoid).setString("comment",  comment);
        dseSession.execute(new BatchStatement().add(insertComment).add(insertVideo));
    }
    
    // DELETE
    public void delete(UUID commentid, UUID userid, UUID videoid) {
        final BoundStatement deleteVideo = deleteFromVideoComment.bind()
                .setUUID("commentid", commentid).setUUID("videoid",  videoid);
        final BoundStatement deleteUser = deleteFromUserComment.bind()
                .setUUID("commentid", commentid).setUUID("userid",  userid);
        dseSession.execute(new BatchStatement().add(deleteVideo).add(deleteUser));
    }
    
    
    public List < Comment > listCommentsForUser(UUID userid) {
        BoundStatement selectUserComments = selectUserComment.bind().setUUID("userid", userid);
        ResultSet rs = dseSession.execute(selectUserComments);
        //if (rs.)
        return null;
    }
    
    public List < Comment > listCommentsForVideos(UUID commentid, UUID videoid) {
        return null;
    }
    

}

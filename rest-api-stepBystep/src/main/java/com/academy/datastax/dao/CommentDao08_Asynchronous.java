package com.academy.datastax.dao;

import static com.academy.datastax.model.Comment.COLUMN_COMMENT;
import static com.academy.datastax.model.Comment.COLUMN_COMMENTID;
import static com.academy.datastax.model.Comment.COLUMN_USERID;
import static com.academy.datastax.model.Comment.COLUMN_VIDEOID;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.academy.datastax.model.Comment;
import com.academy.datastax.model.CommentByUser;
import com.academy.datastax.model.CommentByVideo;
import com.academy.datastax.utils.DseUtils;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Statement;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDao08_Asynchronous {
    
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
    
    /** Hold Driver Mapper to implement ORM with Cassandra. */
    protected MappingManager mappingManager;
    
    /** Mapper to ease queries. */
    protected Mapper < CommentByUser >  mapperCommentByUser;
    protected Mapper < CommentByVideo > mapperCommentByVideo;
    
    /** Default Constructor. */
    @Autowired
    public CommentDao08_Asynchronous(DseSession dseSession, MappingManager mappingManager) {
        this.dseSession     = dseSession;
        this.mappingManager = mappingManager;
        prepare();
    }
    
    /** Initialize the PrepareStatement. */
    public void prepare() {
        mapperCommentByUser  = mappingManager.mapper(CommentByUser.class);
        mapperCommentByVideo = mappingManager.mapper(CommentByVideo.class);
    }
    
    /*
     * INSERT SYNC
     */
    public void insertComment(Comment c) {
        BatchStatement batch = new BatchStatement();
        batch.add(mapperCommentByVideo.saveQuery(new CommentByVideo(c)));
        batch.add(mapperCommentByUser.saveQuery(new CommentByUser(c)));
        dseSession.execute(batch);
     }
    
    /*
     * INSERT ASYNC
     */
    public CompletableFuture<Void> insertCommentAsync(Comment c) {
        
        // Create the completable future
        CompletableFuture<Void>   cfv        = new CompletableFuture<>();
        FutureCallback<ResultSet> myCallback = new FutureCallback<ResultSet>() {
            public void onFailure(Throwable ex) { cfv.completeExceptionally(ex); }
            public void onSuccess(ResultSet rs) { cfv.complete(null); } 
        };
        
        // Create batch and execute as a callback
        BatchStatement batch = new BatchStatement();
        batch.add(mapperCommentByVideo.saveQuery(new CommentByVideo(c)));
        batch.add(mapperCommentByUser.saveQuery(new CommentByUser(c)));
        Futures.addCallback(dseSession.executeAsync(batch), myCallback);
        return cfv;
    }
    
    /*
     * DELETE
     */
    public void deleteComment(Comment c) {
        dseSession.execute(new BatchStatement()
                .add(mapperCommentByUser.deleteQuery(new CommentByUser(c)))
                .add(mapperCommentByVideo.deleteQuery(new CommentByVideo(c))));
    }
    
    /*
     * DELETE ASYNC
     */
    public CompletableFuture<Void> deleteCommentAsync(Comment c) {
        
        // Create the completable future
        CompletableFuture<Void>   cfv        = new CompletableFuture<>();
        FutureCallback<ResultSet> myCallback = new FutureCallback<ResultSet>() {
            public void onFailure(Throwable ex) { cfv.completeExceptionally(ex); }
            public void onSuccess(ResultSet rs) { cfv.complete(null); } 
        };
        
        // Create batch and execute as a callback
        BatchStatement batch = new BatchStatement();
        batch.add(mapperCommentByVideo.deleteQuery(new CommentByVideo(c)));
        batch.add(mapperCommentByUser.deleteQuery(new CommentByUser(c)));
        Futures.addCallback(dseSession.executeAsync(batch), myCallback);
        return cfv; 
    }
    
    /*
     * READ (one)
     */
    public Optional < CommentByUser > findByCommentAndUserIds(UUID userid, UUID commentid) {
        Statement             q   = mapperCommentByUser.getQuery(userid, commentid);
        ResultSet             rs  = dseSession.execute(q);
        Result<CommentByUser> res = mapperCommentByUser.map(rs);
        return getFirstCommentIfExist(res);
    }
    
    /*
     * READ (one) ASYNC
     */
    public CompletableFuture< Optional < CommentByUser > >  findByCommentAndUserIdsAsync(UUID userid, UUID commentid) {
        Statement query = mapperCommentByUser.getQuery(userid, commentid);
        ResultSetFuture                          resFuture = dseSession.executeAsync(query);
        ListenableFuture<Result<CommentByUser>>  future    = mapperCommentByUser.mapAsync(resFuture);
        CompletableFuture<Result<CommentByUser>> comFuture = DseUtils.buildCompletableFuture(future);
        return comFuture.thenApplyAsync(this::getFirstCommentIfExist);    
    }
    
    /*
     * READ (all)
     */
    public List < CommentByUser > findCommentsByUserId(UUID userid) {
        Statement query = mapperCommentByUser.getQuery(userid);
        return mapperCommentByUser.map(dseSession.execute(query)).all();
    }
    
    /*
     * READ (all) ASYNC
     */
    public CompletableFuture< List < CommentByUser > > findCommentsByUserIdAsync(UUID userid) {
        Statement query = mapperCommentByUser.getQuery(userid);
        ResultSetFuture                          resFuture = dseSession.executeAsync(query);
        ListenableFuture<Result<CommentByUser>>  future    = mapperCommentByUser.mapAsync(resFuture);
        CompletableFuture<Result<CommentByUser>> comFuture = DseUtils.buildCompletableFuture(future);
        return comFuture.thenApplyAsync(Result::all);
    }
    
    /*
     * READ (one)
     */
    public Optional < CommentByVideo > findByCommentAndVideoIds(UUID videoid, UUID commentid) {
        Statement query = mapperCommentByVideo.getQuery(videoid, commentid);
        Result<CommentByVideo> res = mapperCommentByVideo.map(dseSession.execute(query));
        return res.iterator().hasNext() ? Optional.ofNullable(res.one()) : Optional.empty();
    }
    
    /*
     * READ (all) 
     */
    public List < CommentByVideo > findCommentsByVideoId(UUID videoid) {
        Statement query = mapperCommentByVideo.getQuery(videoid);
        return mapperCommentByVideo.map(dseSession.execute(query)).all();
    }
    
    /*
     * READ (all) ASYNC
     */
    public CompletableFuture< List < CommentByVideo > > findCommentsByVideoIdAsync(UUID videoid) {
        return DseUtils.buildCompletableFuture(mapperCommentByVideo.mapAsync(
                                dseSession.executeAsync(mapperCommentByVideo.getQuery(videoid))))
                       .thenApplyAsync(Result::all);
    }
    
    /*
     * UPDATE
     */
    public void update(Comment c) {
        // for user you need userid commentid
        // for video you need videoid, commentid
        // you need everything, simpler is to call insert = upsert !
       insertComment(c);
    }
    
    /*
     * UPDATE
     */
    public void updateWithAccessor(Comment c) {
        mappingManager.createAccessor(CommentAccessor.class)
                      .update(c.getCommentid(), c.getVideoid(), c.getUserid(), c.getComment());
    }
    
    /**
     * When you need a 'custom' query you may use an {@link Accessor} and provide explicity Query.
     */
    @Accessor
    public interface CommentAccessor {
        
        @Query("BEGIN BATCH\n" + 
               "UPDATE comments_by_user SET comment = :comment "      + 
               "WHERE userid = :userid AND commentid= :commentid;\n" + 
               "UPDATE comments_by_video SET comment = :comment "      + 
               "WHERE videoid = :videoid AND commentid= :commentid;\n" +
               "APPLY BATCH;")
        void update(@Param(COLUMN_COMMENTID) UUID commentId, @Param(COLUMN_VIDEOID) UUID videoId, 
                    @Param(COLUMN_USERID)    UUID userId,    @Param(COLUMN_COMMENT) String comment);
    }
    
    /**
     * Syntaxique Sugar 
     * @param res
     * @return
     */
    private Optional < CommentByUser > getFirstCommentIfExist(Result<CommentByUser> res) {
        return res.iterator().hasNext() ? Optional.ofNullable(res.one()) : Optional.empty();
    }

}

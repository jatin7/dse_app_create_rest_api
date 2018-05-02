package com.academy.datastax.dao;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.academy.datastax.model.Comment;
import com.academy.datastax.model.CommentByUser;
import com.academy.datastax.model.CommentByVideo;
import com.academy.datastax.model.ResultPage;
import com.academy.datastax.utils.DseUtils;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDao09_Final {
    
    /** Internal logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DseUtils.class);
    
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
    
    /** Hold Driver Mapper to implement ORM with Cassandra. */
    protected MappingManager mappingManager;
    
    /** Mapper to ease queries. */
    protected Mapper < CommentByUser >  mapperCommentByUser;
    protected Mapper < CommentByVideo > mapperCommentByVideo;
    
    /** Default Constructor. */
    @Autowired
    public CommentDao09_Final(DseSession dseSession, MappingManager mappingManager) {
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
     * PRODUCTION READY INSERT
     */
    public CompletableFuture<Void> insertComment(final Comment c) {
        
        // NullPointers are ALWAYS YOUR Fault (no excuses)
        Assert.notNull(c, "Comment object is required");
        Assert.notNull(c.getUserid(), "userid is required to create a comment");
        Assert.notNull(c.getVideoid(), "videoid is required to create a comment");
        Assert.notNull(c.getCommentid(), "commentid is required to create a comment");
        
        // Create the completable future
        CompletableFuture<Void>   cfv        = new CompletableFuture<>();
        FutureCallback<ResultSet> myCallback = new FutureCallback<ResultSet>() {
            public void onFailure(Throwable ex) { cfv.completeExceptionally(ex); }
            public void onSuccess(ResultSet rs) { cfv.complete(null); } 
        };
        
        // Creating statements
        Statement q1 = mapperCommentByVideo.saveQuery(new CommentByVideo(c));
        Statement q2 = mapperCommentByUser.saveQuery(new CommentByUser(c));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("executing: {}", ((BoundStatement) q1).preparedStatement().getQueryString());
            LOGGER.debug("executing: {}", ((BoundStatement) q2).preparedStatement().getQueryString());
        }
        
        // Create batch and execute as a callback
        Futures.addCallback(dseSession.executeAsync(new BatchStatement().add(q1).add(q2)), myCallback);
        return cfv;
    }
    
    /*
     * PRODUCTION READY DELETE
     */
    public CompletableFuture<Void> deleteComment(final Comment c) {
        
        // NullPointers are ALWAYS YOUR Fault (no excuses)
        Assert.notNull(c, "Comment object is required");
        Assert.notNull(c.getUserid(), "userid is required to delete a comment");
        Assert.notNull(c.getVideoid(), "videoid is required to delete a comment");
        Assert.notNull(c.getCommentid(), "commentid is required to delete a comment");
        
        // Create the completable future
        CompletableFuture<Void>   cfv        = new CompletableFuture<>();
        FutureCallback<ResultSet> myCallback = new FutureCallback<ResultSet>() {
            public void onFailure(Throwable ex) { cfv.completeExceptionally(ex); }
            public void onSuccess(ResultSet rs) { cfv.complete(null); } 
        };
        
        // Creating statements
        Statement q1 = mapperCommentByVideo.deleteQuery(new CommentByVideo(c));
        Statement q2 = mapperCommentByUser.deleteQuery(new CommentByUser(c));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("executing: {}", ((BoundStatement) q1).preparedStatement().getQueryString());
            LOGGER.debug("executing: {}", ((BoundStatement) q2).preparedStatement().getQueryString());
        }
        
        // Create batch and execute as a callback
        Futures.addCallback(dseSession.executeAsync(new BatchStatement().add(q1).add(q2)), myCallback);
        return cfv;
    }
    
    /*
     * PRODUCTION READY UPDATE
     */
    public CompletableFuture<Void> updateComment(final Comment c) {
        return insertComment(c);
    }
    
    /*
     * PRODUCTION READY READ (video)
     */
    public ResultPage < CommentByVideo > readVideoComments(UUID videoid, Optional<String> pagingState,  Optional<Integer> pageSize) {
        Assert.notNull(videoid, "videoid is required to create a comment");
        // Build Query
        Statement query = mapperCommentByVideo.getQuery(videoid);
        pageSize.ifPresent(query::setFetchSize);
        pagingState.ifPresent(ps -> query.setPagingState(PagingState.fromString(ps)));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("executing: {}", ((BoundStatement) query).preparedStatement().getQueryString());
        }
        // Execute Query
        Result< CommentByVideo > result = mapperCommentByVideo.map(dseSession.execute(query));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Retrieving: {} comment(s)", result.getAvailableWithoutFetching());
        }
        
        // Create result page
        ResultPage<CommentByVideo> resultPage = new ResultPage<>();
        if (pageSize.isPresent()) {
            int currentlyRead = 0;
            Iterator<CommentByVideo> videosIter = result.iterator();
            while (!result.isFullyFetched() && currentlyRead < pageSize.get()) {
                resultPage.getResults().add(videosIter.next());
                currentlyRead++;
            }
            resultPage.setNextPage(result.getExecutionInfo().getPagingState());
            resultPage.setPageSize(pageSize.get());
        } else {
            resultPage.setresults(result.all());
        }
        return resultPage;
    }
    
    /*
     * PRODUCTION READY READ (user)
     */
    public ResultPage < CommentByUser > readUserComments(UUID userid, Optional<String> pagingState, Optional<Integer> pageSize) {
        Assert.notNull(userid, "userid is required to create a comment");
        // Build Query
        Statement query = mapperCommentByUser.getQuery(userid);
        pageSize.ifPresent(query::setFetchSize);
        pagingState.ifPresent(ps -> query.setPagingState(PagingState.fromString(ps)));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("executing: {}", ((BoundStatement) query).preparedStatement().getQueryString());
        }
        // Execute Query
        Result< CommentByUser > result = mapperCommentByUser.map(dseSession.execute(query));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Retrieving: {} comment(s)", result.getAvailableWithoutFetching());
        }
        // Create result page
        ResultPage<CommentByUser> resultPage = new ResultPage<>();
        if (pageSize.isPresent()) {
            int currentlyRead = 0;
            Iterator<CommentByUser> videosIter = result.iterator();
            while (!result.isFullyFetched() && currentlyRead < pageSize.get()) {
                resultPage.getResults().add(videosIter.next());
                currentlyRead++;
            }
            resultPage.setNextPage(result.getExecutionInfo().getPagingState());
            resultPage.setPageSize(pageSize.get());
        } else {
            resultPage.setresults(result.all());
        }
        return resultPage;
    }
    
}

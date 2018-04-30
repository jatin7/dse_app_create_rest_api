package com.academy.datastax.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.academy.datastax.model.CommentByUser;
import com.academy.datastax.model.CommentByVideo;
import com.academy.datastax.model.ResultPage;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.Statement;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDao07_Paging {
    
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
    
    /** Hold Driver Mapper to implement ORM with Cassandra. */
    protected MappingManager mappingManager;
    
    /** Mapper to ease queries. */
    protected Mapper < CommentByUser >  mapperCommentByUser;
    protected Mapper < CommentByVideo > mapperCommentByVideo;
    
    /** Default Constructor. */
    @Autowired
    public CommentDao07_Paging(DseSession dseSession, MappingManager mappingManager) {
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
     * READ (all)
     */
    public List < CommentByUser > findCommentsByUserId(UUID userid) {
        Statement query = mapperCommentByUser.getQuery(userid);
        return mapperCommentByUser.map(dseSession.execute(query)).all();
    }
    
    /*
     * READ (all), Paging State.
     * https://docs.datastax.com/en/developer/java-driver/3.5/manual/paging
     */
    public ResultPage< CommentByUser > findCommentsByUserIdPageable(UUID userid, Optional<String> pagingState, int pageSize) {
        
        // Build Query
        Statement query = mapperCommentByUser.getQuery(userid);
        query.setFetchSize(pageSize);
        pagingState.ifPresent(ps -> query.setPagingState(PagingState.fromString(ps)));
        Result< CommentByUser > result = mapperCommentByUser.map(dseSession.execute(query));
        
        // Create result page
        ResultPage<CommentByUser> resultPage = new ResultPage<>();
        resultPage.setPageSize(pageSize);
        resultPage.setNextPage(Optional.ofNullable(result.getExecutionInfo().getPagingState()).map(PagingState::toString));
        resultPage.setresults(result.all());
        return resultPage;
    }
    
    /*
     * READ (all)
     */
    public List < CommentByVideo > findCommentsByVideoId(UUID videoid) {
        Statement query = mapperCommentByVideo.getQuery(videoid);
        return mapperCommentByVideo.map(dseSession.execute(query)).all();
    }
    
    /*
     * READ (all), Paging State.
     * https://docs.datastax.com/en/developer/java-driver/3.5/manual/paging
     */
    public ResultPage< CommentByVideo > findCommentsByVideoIdPageable(UUID videoid, Optional<String> pagingState, int pageSize) {
        
        // Build Query
        Statement query = mapperCommentByVideo.getQuery(videoid);
        query.setFetchSize(pageSize);
        pagingState.ifPresent(ps -> query.setPagingState(PagingState.fromString(ps)));
        Result< CommentByVideo > result = mapperCommentByVideo.map(dseSession.execute(query));
        
        // Create result page
        ResultPage<CommentByVideo> resultPage = new ResultPage<>();
        resultPage.setPageSize(pageSize);
        resultPage.setNextPage(Optional.ofNullable(result.getExecutionInfo().getPagingState()).map(PagingState::toString));
        resultPage.setresults(result.all());
        return resultPage;
    }
    
}

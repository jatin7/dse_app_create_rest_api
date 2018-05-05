package com.academy.datastax.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.academy.datastax.model.Comment;
import com.academy.datastax.model.CommentByUser;
import com.academy.datastax.model.CommentByVideo;
import com.academy.datastax.model.ResultPage;
import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
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
    private   PreparedStatement         readAllCommentUserStatement;
    private   PreparedStatement         readAllCommentVideoStatement;
    
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
        
        // Query on Partition key only (not the whole primary key)
        String tableCommentByUser = mapperCommentByUser.getTableMetadata().getName();
        readAllCommentUserStatement = dseSession.prepare(select().all()
                .from(tableCommentByUser).where(eq(Comment.COLUMN_USERID,  QueryBuilder.bindMarker())));

        // Query on Partition key only (not the whole primary key)
        String tableCommentByVideo = mapperCommentByVideo.getTableMetadata().getName();
        readAllCommentVideoStatement = dseSession.prepare(select().all()
                .from(tableCommentByVideo).where(eq(Comment.COLUMN_VIDEOID,  QueryBuilder.bindMarker())));
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
    public ResultPage< CommentByUser > findCommentsByUserIdPageable(UUID userid, Optional<String> pagingState, Optional<Integer> pageSize) {
        
        // Build Query
        Statement query = readAllCommentUserStatement.bind().setUUID(Comment.COLUMN_USERID, userid);
        pageSize.ifPresent(query::setFetchSize);
        pagingState.ifPresent(ps -> query.setPagingState(PagingState.fromString(ps)));
        
        // Execute Query
        Result< CommentByUser > result = mapperCommentByUser.map(dseSession.execute(query));
       
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
    
    /*
     * READ (all), Paging State.
     * https://docs.datastax.com/en/developer/java-driver/3.5/manual/paging
     */
    public ResultPage< CommentByVideo > findCommentsByVideoIdPageable(UUID videoid, Optional<String> pagingState,  Optional<Integer> pageSize) {
        
        // Build Query
        Statement query = readAllCommentVideoStatement.bind().setUUID(Comment.COLUMN_VIDEOID, videoid);
        pageSize.ifPresent(query::setFetchSize);
        pagingState.ifPresent(ps -> query.setPagingState(PagingState.fromString(ps)));

        // Execute Query
        Result< CommentByVideo > result = mapperCommentByVideo.map(dseSession.execute(query));
        
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
    
}

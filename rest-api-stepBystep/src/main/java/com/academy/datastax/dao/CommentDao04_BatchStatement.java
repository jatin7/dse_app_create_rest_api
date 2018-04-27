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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.dse.DseSession;

/**
 * Demo for CRUD.
 *
 * @author DataStax Evangelist Team
 */
@Repository
public class CommentDao04_BatchStatement {
    
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
    
    /** Single place to holder tablename and colum name. */
    private static final String TABLENAME_COMMENTS_BY_USER  = "comments_by_user";
    private static final String TABLENAME_COMMENTS_BY_VIDEO = "comments_by_video";
    
    private static final String COMMENTID = "commentid";
    private static final String USERID    = "userid";
    private static final String VIDEOID   = "videoid";
    private static final String COMMENT   = "comment";
    
    /** Always use {@link PreparedStatement} when possible. */
    private PreparedStatement createPrepareStatementU;
    private PreparedStatement readOnePrepareStatementU;
    private PreparedStatement readAllPrepareStatementU;
    private PreparedStatement updatePrepareStatementU;
    private PreparedStatement deletePrepareStatementU;
    
    /** Always use {@link PreparedStatement} when possible. */
    private PreparedStatement createPrepareStatementV;
    private PreparedStatement readOnePrepareStatementV;
    private PreparedStatement readAllPrepareStatementV;
    private PreparedStatement updatePrepareStatementV;
    private PreparedStatement deletePrepareStatementV;
    
    /** Default Constructor. */
    @Autowired
    public CommentDao04_BatchStatement(DseSession dseSession) {
        this.dseSession     = dseSession;
        prepare();
    }
    
    /** Initialize the PrepareStatement. */
    @PostConstruct
    public void prepare() {
        // -- CommentByUser
        createPrepareStatementU = dseSession.prepare(insertInto(TABLENAME_COMMENTS_BY_USER)
                        .value(COMMENTID, QueryBuilder.bindMarker())
                        .value(USERID, QueryBuilder.bindMarker())
                        .value(VIDEOID, QueryBuilder.bindMarker())
                        .value(COMMENT, QueryBuilder.bindMarker()));
        readOnePrepareStatementU = dseSession.prepare(select(COMMENT)
                        .from(TABLENAME_COMMENTS_BY_USER)
                        .where(eq(COMMENTID, QueryBuilder.bindMarker()))
                        .and(eq(USERID,      QueryBuilder.bindMarker())));
        readAllPrepareStatementU = dseSession.prepare(select(COMMENT)
                        .from(TABLENAME_COMMENTS_BY_USER)
                        .where(eq(USERID,  QueryBuilder.bindMarker())));
        updatePrepareStatementU = dseSession.prepare(update(TABLENAME_COMMENTS_BY_USER)
                        .with(set(COMMENT,    QueryBuilder.bindMarker()))
                        .where(eq(COMMENTID,  QueryBuilder.bindMarker()))
                        .and(eq(USERID,       QueryBuilder.bindMarker())));
        deletePrepareStatementU = dseSession.prepare(delete()
                        .from(TABLENAME_COMMENTS_BY_USER)
                        .where(eq(COMMENTID, QueryBuilder.bindMarker()))
                        .and(eq(USERID,      QueryBuilder.bindMarker())));
        
        // -- CommentByVideo
        createPrepareStatementV = dseSession.prepare(insertInto(TABLENAME_COMMENTS_BY_VIDEO)
                        .value(COMMENTID, QueryBuilder.bindMarker())
                        .value(USERID, QueryBuilder.bindMarker())
                        .value(VIDEOID, QueryBuilder.bindMarker())
                        .value(COMMENT, QueryBuilder.bindMarker()));
        readOnePrepareStatementV = dseSession.prepare(select(COMMENT)
                        .from(TABLENAME_COMMENTS_BY_VIDEO)
                        .where(eq(COMMENTID, QueryBuilder.bindMarker()))
                        .and(eq(VIDEOID,      QueryBuilder.bindMarker())));
        readAllPrepareStatementV = dseSession.prepare(select(COMMENT)
                        .from(TABLENAME_COMMENTS_BY_VIDEO)
                        .where(eq(VIDEOID,  QueryBuilder.bindMarker())));
        updatePrepareStatementV = dseSession.prepare(update(TABLENAME_COMMENTS_BY_VIDEO)
                        .with(set(COMMENT,    QueryBuilder.bindMarker()))
                        .where(eq(COMMENTID,  QueryBuilder.bindMarker()))
                        .and(eq(VIDEOID,       QueryBuilder.bindMarker())));
        deletePrepareStatementV = dseSession.prepare(delete()
                        .from(TABLENAME_COMMENTS_BY_VIDEO)
                        .where(eq(COMMENTID, QueryBuilder.bindMarker()))
                        .and(eq(VIDEOID,      QueryBuilder.bindMarker())));
    }
    
    /*
     * INSERT
     * 
     * INSERT INTO comments_by_user (commentid, userid, videoid, comment)
     * VALUES (1aae5f50-445e-11e8-8977-abaff7c8fa1d,
     *         b17e0fa3-62f7-47f6-a47a-552c925d4d79,
     *         12b5b195-46d7-492a-a7ec-1909688901da,
     *         'Video A / Comment A');
     */
    public void insert_(String commentid, String userid, String videoid, String comment) {
        dseSession.execute(createPrepareStatementU.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(USERID,     UUID.fromString(userid))
                .setUUID(VIDEOID,    UUID.fromString(videoid))
                .setString(COMMENT,  comment));
        dseSession.execute(createPrepareStatementV.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(USERID,     UUID.fromString(userid))
                .setUUID(VIDEOID,    UUID.fromString(videoid))
                .setString(COMMENT,  comment));
     }
    
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
        BatchStatement batch = new BatchStatement();
        batch.add(createPrepareStatementU.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(USERID,     UUID.fromString(userid))
                .setUUID(VIDEOID,    UUID.fromString(videoid))
                .setString(COMMENT,  comment));
        batch.add(createPrepareStatementV.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(USERID,     UUID.fromString(userid))
                .setUUID(VIDEOID,    UUID.fromString(videoid))
                .setString(COMMENT,  comment));
        dseSession.execute(batch);
     }
    
    /* 
     * UPDATE
     * 
     * UPDATE comments_by_user 
     * SET comment = 'Hello'
     * where commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e
     * and userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;
     */
    public void updateComment(String commentid, String userId, String videoid, String comment) {
        BatchStatement batch = new BatchStatement();
        batch.add(updatePrepareStatementU.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(USERID,     UUID.fromString(userId))
                .setString(COMMENT,  comment));
        batch.add(updatePrepareStatementV.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(VIDEOID,     UUID.fromString(videoid))
                .setString(COMMENT,  comment));
        dseSession.execute(batch);
     }
    
    /*
     * DELETE
     * 
     * DELETE FROM comments_by_user 
     * where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79
     * and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e;
     */
    public void deleteComment(String userid, String commentid, String videoid) {
        BatchStatement batch = new BatchStatement();
        batch.add(deletePrepareStatementU.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(USERID,     UUID.fromString(userid)));
        batch.add(deletePrepareStatementV.bind()
                .setUUID(COMMENTID,  UUID.fromString(commentid))
                .setUUID(VIDEOID,    UUID.fromString(videoid)));
        dseSession.execute(batch);
    }
    
    /*
     * READ (one)
     * 
     * SELECT comment 
     * FROM comments_by_user
     * where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79 
     * and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e 
     */
    public Optional < String > findWithUser(String userid, String commentid) {
        return getFirstComment(
                dseSession.execute(readOnePrepareStatementU.bind()
                    .setUUID(COMMENTID,  UUID.fromString(commentid))
                    .setUUID(USERID,     UUID.fromString(userid))));
    }
    
    /*
     * READ (one)
     * 
     * SELECT comment 
     * FROM comments_by_video
     * where videoid=b17e0fa3-62f7-47f6-a47a-552c925d4d79 
     * and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e 
     */
    public Optional < String > findWithVideo(String videoid, String commentid) {
        return getFirstComment(
                dseSession.execute(readOnePrepareStatementV.bind()
                    .setUUID(COMMENTID,  UUID.fromString(commentid))
                    .setUUID(VIDEOID,     UUID.fromString(videoid))));
    }
    
    /**
     * Utility to extract data from resultset.
     *
     * @param rs
     *      result set
     * @return
     */
    private Optional < String > getFirstComment(ResultSet rs) {
        Optional < String > result = Optional.empty();
        Iterator< Row > rows = rs.iterator();
        if (rows.hasNext()) {
            return Optional.ofNullable(rows.next().getString(COMMENT));
        }
        return result;
    }
    
    /*
     * READ   (user)
     * SELECT comment 
     * FROM   comments_by_user
     * WHERE  userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;
     */
    public List < String > findCommentByUser(String userid) {
        return getCommentList(
                dseSession.execute(readAllPrepareStatementU.bind()
                    .setUUID(USERID,    UUID.fromString(userid))));
    }
    
    /*
     * READ   (video)
     * SELECT comment 
     * FROM   comments_by_video
     * WHERE  videoid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;
     */
    public List < String > findCommentByVideo(String videoid) {
        return getCommentList(
                dseSession.execute(readAllPrepareStatementV.bind()
                    .setUUID(VIDEOID, UUID.fromString(videoid))));
    }
    
    /**
     * Utility to extract data from resultset.
     *
     * @param rs
     *      result set
     * @return
     */
    private List <String > getCommentList(ResultSet rs) {
        List < String > result = new ArrayList<>();
        for (Row row : rs) {
            result.add(row.getString(COMMENT));
        }
        return result;
    }

}

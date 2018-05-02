package com.academy.datastax.dao;

import static com.datastax.driver.core.querybuilder.QueryBuilder.delete;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.insertInto;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;
import static com.datastax.driver.core.querybuilder.QueryBuilder.update;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.academy.datastax.model.Comment_;
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
public class CommentDao05_Bean {
    
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
    public CommentDao05_Bean(DseSession dseSession) {
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
        readOnePrepareStatementU = dseSession.prepare(select().all()
                        .from(TABLENAME_COMMENTS_BY_USER)
                        .where(eq(COMMENTID, QueryBuilder.bindMarker()))
                        .and(eq(USERID,      QueryBuilder.bindMarker())));
        readAllPrepareStatementU = dseSession.prepare(select().all()
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
        readOnePrepareStatementV = dseSession.prepare(select().all()
                        .from(TABLENAME_COMMENTS_BY_VIDEO)
                        .where(eq(COMMENTID, QueryBuilder.bindMarker()))
                        .and(eq(VIDEOID,      QueryBuilder.bindMarker())));
        readAllPrepareStatementV = dseSession.prepare(select().all()
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
    public void insertComment(Comment_ c) {
        BatchStatement batch = new BatchStatement();
        batch.add(createPrepareStatementU.bind()
                .setUUID(COMMENTID,  c.getCommentid())
                .setUUID(USERID,     c.getUserid())
                .setUUID(VIDEOID,    c.getVideoid())
                .setString(COMMENT,  c.getComment()));
        batch.add(createPrepareStatementV.bind()
                .setUUID(COMMENTID,  c.getCommentid())
                .setUUID(USERID,     c.getUserid())
                .setUUID(VIDEOID,    c.getVideoid())
                .setString(COMMENT,  c.getComment()));
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
    public void updateComment(Comment_ c) {
        BatchStatement batch = new BatchStatement();
        batch.add(updatePrepareStatementU.bind()
                .setUUID(COMMENTID,  c.getCommentid())
                .setUUID(USERID,     c.getUserid())
                .setString(COMMENT,  c.getComment()));
        batch.add(updatePrepareStatementV.bind()
                .setUUID(COMMENTID,  c.getCommentid())
                .setUUID(VIDEOID,    c.getVideoid())
                .setString(COMMENT,  c.getComment()));
        dseSession.execute(batch);
     }
    
    /*
     * DELETE
     * 
     * DELETE FROM comments_by_user 
     * where userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79
     * and commentid=1abe5f50-445e-11e8-8977-abdff7c8fa1e;
     */
    public void deleteComment(Comment_ c) {
        BatchStatement batch = new BatchStatement();
        batch.add(deletePrepareStatementU.bind()
                .setUUID(COMMENTID, c.getCommentid())
                .setUUID(USERID,    c.getUserid()));
        batch.add(deletePrepareStatementV.bind()
                .setUUID(COMMENTID,  c.getCommentid())
                .setUUID(VIDEOID,    c.getVideoid()));
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
    public Optional < Comment_ > findByCommentAndUserIds(String userid, String commentid) {
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
    public Optional < Comment_ > findByCommentAndVideoIds(String videoid, String commentid) {
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
    private Optional < Comment_ > getFirstComment(ResultSet rs) {
        Iterator< Row > rows = rs.iterator();
        return rows.hasNext() ? 
                Optional.ofNullable(mapRow(rows.next())) : 
                Optional.empty();
    }
    
    private Comment_ mapRow(Row currentRow) {
        Comment_ c = new Comment_();
        c.setCommentid(currentRow.getUUID(COMMENTID));
        c.setUserid(currentRow.getUUID(USERID));
        c.setVideoid(currentRow.getUUID(VIDEOID));
        c.setComment(currentRow.getString(COMMENT));
        return c;
    }
    
    /*
     * READ   (user)
     * SELECT comment 
     * FROM   comments_by_user
     * WHERE  userid=b17e0fa3-62f7-47f6-a47a-552c925d4d79;
     */
    public List < Comment_ > findCommentsByUserId(String userid) {
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
    public List < Comment_ > findCommentsByVideoId(String videoid) {
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
    private List <Comment_ > getCommentList(ResultSet rs) {
        return rs.all().stream().map(this::mapRow).collect(Collectors.toList());
    }

}

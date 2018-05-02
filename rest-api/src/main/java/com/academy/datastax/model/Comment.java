package com.academy.datastax.model;

import java.io.Serializable;
import java.util.UUID;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;

/**
 * Bean standing for comment on video.
 *
 * @author DataStax evangelist team.
 */
public class Comment implements Serializable {
    
    /** Serial. */
    private static final long serialVersionUID = 7675521710612951368L;
    
    /** Column names in the DB. */
    public static final String COLUMN_USERID    = "userid";
    public static final String COLUMN_VIDEOID   = "videoid";
    public static final String COLUMN_COMMENTID = "commentid";
    public static final String COLUMN_COMMENT   = "comment";

    @Column
    protected UUID userid;
    
    @Column
    protected UUID videoid;

    @ClusteringColumn
    protected UUID commentid;

    @Column
    protected String comment;
    
    /**
     * Default constructor.
     */
    public Comment() {
        this.commentid = UUIDs.timeBased();
    }
    
    /**
     * Default constructor.
     */
    public Comment(String comment) {
        this.comment = comment;
    }
    
    /**
     * Setter for attribute 'userid'.
     * @param userid
     * 		new value for 'userid '
     */
    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    /**
     * Setter for attribute 'videoid'.
     * @param videoid
     * 		new value for 'videoid '
     */
    public void setVideoid(UUID videoid) {
        this.videoid = videoid;
    }

    /**
     * Getter for attribute 'commentid'.
     *
     * @return
     *       current value of 'commentid'
     */
    public UUID getCommentid() {
        return commentid;
    }

    /**
     * Setter for attribute 'commentid'.
     * @param commentid
     * 		new value for 'commentid '
     */
    public void setCommentid(UUID commentid) {
        this.commentid = commentid;
    }

    /**
     * Getter for attribute 'comment'.
     *
     * @return
     *       current value of 'comment'
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter for attribute 'comment'.
     * @param comment
     * 		new value for 'comment '
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    /**
     * Getter for attribute 'userid'.
     *
     * @return
     *       current value of 'userid'
     */
    public UUID getUserid() {
        return userid;
    }
    
    /**
     * Getter for attribute 'videoid'.
     *
     * @return
     *       current value of 'videoid'
     */
    public UUID getVideoid() {
        return videoid;
    }
    

}

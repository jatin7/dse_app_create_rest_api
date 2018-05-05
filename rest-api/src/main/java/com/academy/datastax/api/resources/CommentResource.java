package com.academy.datastax.api.resources;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.datastax.dao.CommentDseDao;
import com.academy.datastax.model.Comment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST Resource implementing UPSERT (create/update) AND DELETE.
 *
 * @author DataStax Evangelist Team
 */
@RestController
@RequestMapping("/api/v1/videos/{videouid}/comments/{commentuuid}")
@Api(value = "/api/v1/videos/{videouid}/comments/{commentuuid}",  
     description = "CRUD operations working on comments")
public class CommentResource {
    
    /** Holds operations agains dse. */
    private CommentDseDao commentDao;
    
    @Autowired
    public CommentResource(CommentDseDao dao) {
        this.commentDao = dao;
    }
    
    /**
     * -------------------------------------------- 
     *                   UPSERT
     * --------------------------------------------
     */
    @RequestMapping(method = PUT, 
        consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(
        value = "Create or update a comment based on a given UUID", 
        response = ResponseEntity.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Comment has been created"),
        @ApiResponse(code = 400, message = "Invalid parameters provided"),
        @ApiResponse(code = 500, message = "Internal Error")})
    public ResponseEntity<Boolean> upsert(
                        @PathVariable(value = "commentuuid") String commentUuid,
                        @PathVariable(value = "videouid")    String videoid,
                        @RequestBody Comment comment) {
        
        // Parameters validation
        Assert.hasLength(commentUuid, "Comment id is required");
        Assert.hasLength(videoid, "Video id is required");
        Assert.notNull(comment, "Comments object cannot be null");
        Assert.hasLength(comment.getComment(), "Comments object cannot be null");
        Assert.notNull(comment.getUserid(), "Userid attribute cannot be null");
        
        // Consistency with the provided bean
        comment.setCommentid(UUID.fromString(commentUuid));
        comment.setVideoid(UUID.fromString(videoid));
        
        // Invoke Async
        commentDao.insertComment(comment);
        
        // Do not wait and return CREATED
        return new ResponseEntity<Boolean>(HttpStatus.CREATED);
    }
    
    /**
     * -------------------------------------------- 
     *                   DELETE
     * --------------------------------------------
     */
    @RequestMapping(method = DELETE,
        consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Delete a comment", 
            response = ResponseEntity.class)
    @ApiResponses({
            @ApiResponse(code = 204, message = "No content, comment is deleted"),
            @ApiResponse(code = 400, message = "Invalid parameters provided"),
            @ApiResponse(code = 404, message = "Comment not found"),
            @ApiResponse(code = 500, message = "Internal Error")})
    public ResponseEntity<Boolean> deleteComment(
                    @PathVariable(value = "commentuuid") String commentUuid, 
                    @RequestBody Comment comment) {
        // Parameters validation
        Assert.hasLength(commentUuid, "Comment id is required");
        Assert.notNull(comment, "Comments object cannot be null");
        Assert.hasLength(comment.getComment(), "Comments object cannot be null");
        Assert.notNull(comment.getUserid(), "Userid attribute cannot be null");
        comment.setCommentid(UUID.fromString(commentUuid));
        
        commentDao.deleteComment(comment);
        return new ResponseEntity<Boolean>(NO_CONTENT);
    }
}

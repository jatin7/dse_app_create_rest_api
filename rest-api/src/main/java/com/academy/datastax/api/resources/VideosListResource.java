package com.academy.datastax.api.resources;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.datastax.model.Comment;
import com.academy.datastax.model.DseSchema;
import com.academy.datastax.model.Video;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.dse.DseSession;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/videos")
public class VideosListResource {

    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
    
    /** Default Constructor. */
    @Autowired
    public VideosListResource(DseSession dseSession) {
        this.dseSession = dseSession;
    }
    
    @RequestMapping(value = "", method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve video list", response = Video.class)
    @ApiResponses(@ApiResponse(code = 200, message = "Retrieve comments for a dedicated video"))
    public List<Video> getVideos() {
        // PLEASE USE A REAL DAO, THIS IS JUST SAMPLE TO GET INFO, YOU SHOUDL CREATE A DEDICATED TABLE
        return dseSession.execute(QueryBuilder.select(Comment.COLUMN_VIDEOID).distinct().from(DseSchema.TABLENAME_COMMENTS_BY_VIDEO))
                         .all().stream().map(row -> new Video("Sample title", row.getUUID(Comment.COLUMN_VIDEOID)))
                         .collect(Collectors.toList());
    }

}

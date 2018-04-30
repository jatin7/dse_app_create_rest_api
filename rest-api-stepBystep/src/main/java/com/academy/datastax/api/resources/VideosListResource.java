package com.academy.datastax.api.resources;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academy.datastax.model.ResultPage;
import com.academy.datastax.model.Video;
import com.academy.datastax.model.VideoPreview;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/videos")
public class VideosListResource {
   
    @RequestMapping(value = "", method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve latest videos (home page)", response = Video.class)
    @ApiResponses(@ApiResponse(code = 200, message = "Retrieve comments for a dedicated video"))
    public ResultPage<VideoPreview> getLatestVideo(
            @ApiParam(name="pageSize", value="Requested page size, default is 10", required=false ) 
            @RequestParam("pageSize") Optional<Integer> ppageSize,
            @ApiParam(name="pageState", value="Use to retrieve next pages", required=false ) 
            @RequestParam("pageState") Optional<String> ppageState) {
        
        
        return null;
    }
    

}

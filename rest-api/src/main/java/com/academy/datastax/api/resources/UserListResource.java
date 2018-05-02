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
import com.academy.datastax.model.User;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.dse.DseSession;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/users")
public class UserListResource {
   
    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
    
    
    /** Default Constructor. */
    @Autowired
    public UserListResource(DseSession dseSession) {
        this.dseSession = dseSession;
    }
    
    @RequestMapping(value = "", method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve user list", response = List.class)
    @ApiResponses(@ApiResponse(code = 200, message = "Retrieve user preview list"))
    public List <User> getUsersMock() {
        // PLEASE USE A REAL DAO, THIS IS JUST SAMPLE TO GET INFO, YOU SHOUDL CREATE A DEDICATED TABLE
        return dseSession.execute(QueryBuilder.select(Comment.COLUMN_USERID).distinct().from(DseSchema.TABLENAME_COMMENTS_BY_USER))
                         .all().stream().map(row -> new User("username", "lastbame", row.getUUID(Comment.COLUMN_USERID)))
                         .collect(Collectors.toList());
    }
    

}

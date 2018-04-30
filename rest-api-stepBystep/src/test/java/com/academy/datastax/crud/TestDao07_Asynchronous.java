package com.academy.datastax.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.academy.datastax.conf.DseConfiguration;
import com.academy.datastax.dao.CommentDao08_Asynchronous;
import com.academy.datastax.model.Comment;
import com.datastax.driver.dse.DseSession;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes= { DseConfiguration.class, CommentDao08_Asynchronous.class })
@TestPropertySource(
        locations = { "classpath:application-test.properties"})
public class TestDao07_Asynchronous {
    
    @Autowired
    private DseSession dseSession;
    
    @Autowired
    private CommentDao08_Asynchronous dao;
    
    private Comment buildComment() {
        Comment c= new Comment();
        c.setCommentid(UUID.fromString("1aae5f50-445e-11e8-8977-abaff7c8fa1d"));
        c.setUserid(UUID.fromString("b17e0fa3-62f7-47f6-a47a-552c925d4d79"));
        c.setVideoid(UUID.fromString("12b5b195-46d7-492a-a7ec-1909688901da"));
        c.setComment("Hello World !");
        return c;
    }
    
    
    @Test
    public void insert() {
        // Given
        dseSession.execute("truncate comments_by_user");
        Comment c = buildComment();
        // When
        dao.insertComment(c);
        // Then
        assertEquals(c.getComment(), 
        dseSession.execute(""
                + "SELECT * FROM comments_by_user "
                + "WHERE commentid = 1aae5f50-445e-11e8-8977-abaff7c8fa1d "
                + "AND userid = b17e0fa3-62f7-47f6-a47a-552c925d4d79;")
                  .all().stream().findFirst().get().getString("comment"));
    }
    
    /*
    @Test
    public void readOne() {
        Optional<String> com = dao.find(userid, commentId);
        assertTrue(com.isPresent());
        assertEquals(comment, com.get());
        Optional<String> com2 = dao.find(UUID.randomUUID().toString(), commentId);
        assertFalse(com2.isPresent());
    }
    
    @Test
    public void readForUser() {
        List<String> listComments = dao.findByUserId(userid);
        assertFalse(listComments.isEmpty());
        assertEquals(1, listComments.size());
        assertEquals(comment, listComments.get(0));
    }
    
    @Test
    public void update1() {
        // All fields provided, create is also update
        dao.insert(commentId, userid, videoid, "comment2");
        List<String> listComments = dao.findByUserId(userid);
        assertFalse(listComments.isEmpty());
        assertEquals(1, listComments.size());
        assertEquals("comment2", listComments.get(0));
    }
    
    @Test
    public void update2() {
        // All fields provided, create is also update
        dao.updateComment(commentId, userid, "new comment");
        List<String> listComments = dao.findByUserId(userid);
        assertFalse(listComments.isEmpty());
        assertEquals(1, listComments.size());
        assertEquals("new comment", listComments.get(0));
    }

    @Test
    public void delete() {
        dao.deleteComment(userid, commentId);
        List<String> listComments = dao.findByUserId(userid);
        assertTrue(listComments.isEmpty());
    } */
                
}

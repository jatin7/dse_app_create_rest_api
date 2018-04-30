package com.academy.datastax.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * POJO holding definition of video.
 *
 * @author DataStax Evangelist Team
 */
public class Video {

    private UUID videoid;
   
    private String title;
    
    private String url;
    
    private String description;
    
    private Map< String, String > relatedResources = new HashMap<>();

    public Video(UUID videoid, String title, String url, String description) {
        super();
        this.videoid = videoid;
        this.title = title;
        this.url = url;
        this.description = description;
        relatedResources.put("comments", "http://localhost:8080/api/v1/videos/" + videoid.toString() + "/comments");
    }
    
    /**
     * Getter accessor for attribute 'title'.
     *
     * @return
     *       current value of 'title'
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter accessor for attribute 'title'.
     * @param title
     * 		new value for 'title '
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter accessor for attribute 'url'.
     *
     * @return
     *       current value of 'url'
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter accessor for attribute 'url'.
     * @param url
     * 		new value for 'url '
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter accessor for attribute 'description'.
     *
     * @return
     *       current value of 'description'
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter accessor for attribute 'description'.
     * @param description
     * 		new value for 'description '
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter accessor for attribute 'videoid'.
     *
     * @return
     *       current value of 'videoid'
     */
    public UUID getVideoid() {
        return videoid;
    }

    /**
     * Setter accessor for attribute 'videoid'.
     * @param videoid
     * 		new value for 'videoid '
     */
    public void setVideoid(UUID videoid) {
        this.videoid = videoid;
    }
    
    
}

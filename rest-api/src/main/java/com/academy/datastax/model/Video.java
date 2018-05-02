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

    private String title;
    
    private Map< String, String > relatedResources = new HashMap<>();

    public Video(String title, UUID videoid) {
        this.title = title;
        relatedResources.put("comments", "http://localhost:8080/api/v1/videos/" + videoid.toString() + "/comments");
        relatedResources.put("videodetails", "http://localhost:8080/api/v1/videos/" + videoid.toString());
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
     * Getter accessor for attribute 'relatedResources'.
     *
     * @return
     *       current value of 'relatedResources'
     */
    public Map<String, String> getRelatedResources() {
        return relatedResources;
    }

    /**
     * Setter accessor for attribute 'relatedResources'.
     * @param relatedResources
     * 		new value for 'relatedResources '
     */
    public void setRelatedResources(Map<String, String> relatedResources) {
        this.relatedResources = relatedResources;
    }
    
}

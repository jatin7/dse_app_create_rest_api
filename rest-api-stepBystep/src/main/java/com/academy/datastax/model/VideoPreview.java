package com.academy.datastax.model;

import java.util.UUID;

public class VideoPreview {

    private UUID videoid;
    
    private String title;
    
    private String url;

    private String details;

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
     * Getter accessor for attribute 'details'.
     *
     * @return
     *       current value of 'details'
     */
    public String getDetails() {
        return details;
    }

    /**
     * Setter accessor for attribute 'details'.
     * @param details
     * 		new value for 'details '
     */
    public void setDetails(String details) {
        this.details = details;
    }
    
    
}

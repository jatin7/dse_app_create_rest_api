package com.academy.datastax.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * POJO holding definition of user.
 *
 * @author DataStax Evangelist Team
 */
public class User implements Serializable {
    
    /** Serial. */
    private static final long serialVersionUID = 7697403882552330580L;

    private String userName;
    
    private String lastName;
    
    private UUID useruuid;
    
    private Map< String, String > relatedResources = new HashMap<>();

    public User() {
    }
    
    public User(UUID useruuid) {
        this("N/A", "N/A", useruuid);
    }
    
    public User(String userName, String lastName, UUID useruuid) {
        super();
        this.userName = userName;
        this.lastName = lastName;
        this.useruuid = useruuid;
        relatedResources.put("comments", "http://localhost:8080/api/v1/users/" + useruuid.toString() + "/comments");
    }

    /**
     * Getter accessor for attribute 'userName'.
     *
     * @return
     *       current value of 'userName'
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter accessor for attribute 'userName'.
     * @param userName
     * 		new value for 'userName '
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter accessor for attribute 'lastName'.
     *
     * @return
     *       current value of 'lastName'
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter accessor for attribute 'lastName'.
     * @param lastName
     * 		new value for 'lastName '
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter accessor for attribute 'useruuid'.
     *
     * @return
     *       current value of 'useruuid'
     */
    public UUID getUseruuid() {
        return useruuid;
    }

    /**
     * Setter accessor for attribute 'useruuid'.
     * @param useruuid
     * 		new value for 'useruuid '
     */
    public void setUseruuid(UUID useruuid) {
        this.useruuid = useruuid;
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

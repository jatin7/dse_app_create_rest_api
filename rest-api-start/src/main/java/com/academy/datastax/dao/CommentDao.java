package com.academy.datastax.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.dse.DseSession;

@Repository
public class MyDao {

    /** Hold Connectivity to DSE. */
    protected DseSession dseSession;
   
    /** Default Constructor. */
    @Autowired
    public MyDao(DseSession dseSession) {
        this.dseSession     = dseSession;
    }
    
    public void create(Object... someParams) {
        // INSERT INTO...
    }
    
    public void update(Object... someParams) {
        // UPDATE SET FROM
    }
    
    public void delete(Object... primaryKeyParams) {
        // DELETE ROM...
    }
    
    public Optional<Object> find(Object... primaryKeyParams) {
        // SELECT
        return null;
    }
    
    public List<Object> findByCriteriaX(Object... someParams) {
        // SELECT
        return null;
    }
    

}

package com.academy.datastax.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;

/**
 * Ease usage of the paginState.
 *
 * @author DataStax evangelist team.
 */
public class ResultPage < ENTITY > {

	/** Results map as entities. */
	private List < ENTITY > listOfResults = new ArrayList<>();

	/** Custom management of paging state. */
	private Optional<String> nextPage = Optional.empty();
	
	/** Requested page size. */
	private int pageSize;

	/**
	 * Default Constructor.
	 */
	public ResultPage() {}
	
	/**
	 * Constructor with mapper.
	 *
	 * @param rs
	 * 		result set
	 * @param mapper
	 * 		mapper
	 */
	public ResultPage(ResultSet rs, Mapper<ENTITY> mapper) {
		Result<ENTITY> results = mapper.map(rs);
		if (null != results) {
			listOfResults.addAll(results.all());
			nextPage = Optional.ofNullable(
					results.getExecutionInfo().getPagingState()).map(PagingState::toString);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (null != listOfResults) {
			sb.append("Results:");
			sb.append(listOfResults.toString());
		}
		if (nextPage.isPresent()) {
			sb.append("\n + pagingState is present : ");
			sb.append(nextPage.get());
		}
		return sb.toString();
	}
	
	/**
	 * Getter for attribute 'listOfResults'.
	 *
	 * @return current value of 'comments'
	 */
	public List<ENTITY> getResults() {
		return listOfResults;
	}

	/**
	 * Setter for attribute 'listOfResults'.
	 * 
	 * @param comments
	 *            new value for 'comments '
	 */
	public void setresults(List<ENTITY> comments) {
		this.listOfResults = comments;
	}

    /**
     * Getter accessor for attribute 'pageSize'.
     *
     * @return
     *       current value of 'pageSize'
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter accessor for attribute 'pageSize'.
     * @param pageSize
     * 		new value for 'pageSize '
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter accessor for attribute 'nextPage'.
     *
     * @return
     *       current value of 'nextPage'
     */
    public Optional<String> getNextPage() {
        return nextPage;
    }

    /**
     * Setter accessor for attribute 'nextPage'.
     * @param nextPage
     * 		new value for 'nextPage '
     */
    public void setNextPage(Optional<String> nextPage) {
        this.nextPage = nextPage;
    }

}

package org.kaapi.app.entities;

import java.io.Serializable;

public class Pagination implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int currentPage;
	private int perPage;
	private int totalCount;
	private int totalPages;
	
	public Pagination(){
		this(1,15,0,0);
	}	
	
	public Pagination(int currentPage, int perPage, int totalCount, int totalPages){
		this.currentPage = currentPage;
		this.perPage = perPage;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}

	public int totalPages(){
		return (int) Math.ceil((double)this.totalCount/perPage);
		
	}
	
	public int nextPage(){
		return this.currentPage+1;
	}
	
	public int previousPage(){
		return this.currentPage-1;
	}
	
	public boolean hasNextPage(){
		return this.nextPage() <=this.totalPages()? true :false;
	}
	
	public boolean hasPreviousPage(){
		return this.previousPage()>=1 ? true : false;
	}	
	
	public int offset(){
		return (this.currentPage-1)* perPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		if(perPage > 100 ){
			perPage = 10;
		}
		this.perPage = perPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}

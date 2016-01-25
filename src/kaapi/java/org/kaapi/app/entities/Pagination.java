package org.kaapi.app.entities;

import java.io.Serializable;

public class Pagination implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int page;
	private int item;
	private int totalCount;
	private int totalPages;
	
	public Pagination(){
		this(1,15,0,0);
	}	
	
	public Pagination(int page, int item, int totalCount, int totalPages){
		this.page = page;
		this.item = item;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}
	public int getPage() {
		return page;
	}

	public int totalPages(){
		return (int) Math.ceil((double)this.totalCount/item);
		
	}
	
	public int nextPage(){
		return this.page+1;
	}
	
	public int previousPage(){
		return this.page-1;
	}
	
	public boolean hasNextPage(){
		return this.nextPage() <=this.totalPages()? true :false;
	}
	
	public boolean hasPreviousPage(){
		return this.previousPage()>=1 ? true : false;
	}	
	
	public int offset(){
		return (this.page-1)* item;
	}
	
	public void setPage(int currentPage) {
		this.page = currentPage;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
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

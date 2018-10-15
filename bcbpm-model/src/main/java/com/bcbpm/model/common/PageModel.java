package com.bcbpm.model.common;

import java.io.Serializable;

/**
 * <p>Title: PageModel</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月15日 下午2:54:37
 * @version :
 * @description: 分页公共基础对象
 */
public class PageModel implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer currentPage;//前端传递过来的当前页
    private Integer pageSize;//每页展示条数
    private Integer totalPageNum;//总页数

    private Integer page;//mysql查询时limit的起始当前页

    private Object data;//返回列表数据集合

    public Integer getCurrentPage(){
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage){
        this.currentPage = currentPage;
        int tempPage = (currentPage == 0) ? 0 : (currentPage - 1) * pageSize;
        this.page = tempPage;
    }

    public Integer getTotalPageNum(){
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum){
        this.totalPageNum = totalPageNum;
    }

    public Integer getPage(){
        return page;
    }

    public void setPage(Integer page){
        this.page = page;
    }

    public Integer getPageSize(){
        return pageSize;
    }

    public void setPageSize(Integer pageSize){
        this.pageSize = pageSize;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }
}

package pers.zitianqiong.common;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数
 * @param <T>查询条件实体类
 */
@Getter
@Setter
public class PageParam<T> {

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer pageNum;
    
    /**
     * 查询条件
     */
    private T query;
    
    private Date start;
    
    private Date end;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private Date[] queryDate;
    
    private LocalDateTime[] queryTime;
}

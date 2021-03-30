package org.konanok.demo.mybatis.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Post实体类
 */
@Data
public class Post {
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private String tags;

    /**
     * 状态，0-草稿状态，1-已提交，2-已发布
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createDateTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateDateTime;
}

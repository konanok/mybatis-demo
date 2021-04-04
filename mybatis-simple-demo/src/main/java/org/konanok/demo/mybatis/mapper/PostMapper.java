package org.konanok.demo.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.konanok.demo.mybatis.entity.Post;

import java.util.List;

/**
 * Post映射器
 */
public interface PostMapper {

    /**
     * 插入一条post记录
     *
     * @param post
     */
    @Insert("INSERT INTO post (title, tags, status) VALUES (#{title}, #{tags}, #{status})")
    void add(Post post);

    /**
     * 查询所有post
     *
     * @return
     */
    @Select("SELECT * FROM post")
    @Results(value = {
            @Result(column = "create_date_time", property = "createDateTime"),
            @Result(column = "update_date_time", property = "updateDateTime")
    })
    List<Post> list();
}

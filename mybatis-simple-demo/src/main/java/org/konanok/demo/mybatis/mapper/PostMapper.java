package org.konanok.demo.mybatis.mapper;

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
    void add(Post post);

    /**
     * 查询所有post
     *
     * @return
     */
    List<Post> list();
}

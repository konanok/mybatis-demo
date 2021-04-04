package org.konanok.demo.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.konanok.demo.mybatis.entity.Post;
import org.konanok.demo.mybatis.mapper.PostMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * MyBatis简单的demo
 */
public class MyBatisSimpleDemoApplication {

    /**
     * 通过XML构建SqlSessionFactory
     *
     * @return SqlSessionFactory实例
     */
    private static SqlSessionFactory getSqlSessionFactoryByXML() throws IOException {
        return new SqlSessionFactoryBuilder().build(
                Resources.getResourceAsStream("mybatis-config.xml")
        );
    }

    /**
     * 通过Configuration构建SqlSessionFactory
     *
     * @return SqlSessionFactory实例
     */
    private static SqlSessionFactory getSqlSessionFactoryByConfiguration() {
        Configuration configuration = new Configuration();

        JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:demo");
        dataSource.setUser("root");
        dataSource.setPassword("root12345");

        Environment environment = new Environment(
                "development",
                transactionFactory,
                dataSource
        );

        configuration.setEnvironment(environment);
        configuration.addMapper(PostMapper.class);

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    /**
     * 获取初始化数据库的sql
     *
     * @return sql
     * @throws IOException
     */
    private static String getInitializeSql() throws IOException {
        BufferedReader reader = new BufferedReader(
                Resources.getResourceAsReader("h2db/init.sql")
        );

        StringBuilder sqlBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sqlBuilder.append(line);
        }
        return sqlBuilder.toString();
    }


    public static void main(String[] args) throws IOException, SQLException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactoryByConfiguration();

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

            String sql = getInitializeSql();
            Connection connection = sqlSession.getConnection();
            connection.prepareStatement(sql).execute();

            Post post1 = new Post();
            post1.setTitle("Test1");
            post1.setTags("mybatis,h2");
            post1.setStatus(0);

            Post post2 = new Post();
            post2.setTitle("Test2");
            post2.setTags("h2,mybatis");
            post2.setStatus(1);

            PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
            postMapper.add(post1);
            postMapper.add(post2);

            List<Post> postList = postMapper.list();
            postList.forEach(System.out::println);
        }
    }
}

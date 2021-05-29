package com.znlh.framework.domain.event.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Slf4j
 class AbstractStore {
    protected DataSource dataSource;
    protected String tablePrefix;

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public static void closeSilent(AutoCloseable closeable){
        if (Objects.nonNull(closeable)){
            try {
                closeable.close();
            }catch (Exception e){
                log.info(e.getMessage(), e);
            }
        }
    }

    protected <T> T selectOne(String sql,ResultSetToObject<T> obj, Object... args){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            if (log.isDebugEnabled()){
                log.debug("Preparing SQL statement:{}", sql);
            }
            stmt = connection.prepareStatement(sql);
            if (log.isDebugEnabled()){
                log.debug("setting params to PreparedStatement:{}", Arrays.toString(args));
            }
            for (int i = 0; i < args.length; i++){
                stmt.setObject(i+1, args[i]);
            }
            resultSet = stmt.executeQuery();
            if (resultSet.next()){
                return obj.toObject(resultSet);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeSilent(resultSet);
            closeSilent(stmt);
            closeSilent(connection);
        }
        return null;
    }

    protected <T> List<T> selectList(String sql, ResultSetToObject<T> obj, Object... args){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            if (log.isDebugEnabled()){
                log.debug("Preparing SQL statement:{}", sql);
            }
            stmt = connection.prepareStatement(sql);
            if (log.isDebugEnabled()){
                log.debug("setting params to PreparedStatement:{}", Arrays.toString(args));
            }
            for (int i = 0; i < args.length; i++){
                stmt.setObject(i+1, args[i]);
            }
            resultSet = stmt.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(obj.toObject(resultSet)) ;
            }
            return result;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeSilent(resultSet);
            closeSilent(stmt);
            closeSilent(connection);
        }
    }

    protected <T> int executeUpdate(String sql,ObjectToStatement<T> objectToStatement,T o){
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dataSource.getConnection();
            if (log.isDebugEnabled()){
                log.debug("Preparing SQL: {}", sql);
            }

            stmt = connection.prepareStatement(sql);
            if (log.isDebugEnabled()){
                log.debug("setting params to PrepareStatement: {}", o.toString());
            }

            objectToStatement.toStatement(o,stmt);
            int count = stmt.executeUpdate();
            if (!connection.getAutoCommit()){
                connection.commit();
            }
            return count;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeSilent(stmt);
            closeSilent(connection);
        }
    }


    protected int executeUpdate(String sql, Object...args){
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dataSource.getConnection();
            if (log.isDebugEnabled()){
                log.debug("Preparing SQL: {}", sql);
            }

            stmt = connection.prepareStatement(sql);
            if (log.isDebugEnabled()){
                log.debug("setting params to PrepareStatement: {}", Arrays.toString(args));
            }

            for (int i = 0; i < args.length; i++){
                stmt.setObject(i+1, args[i]);
            }
            int count = stmt.executeUpdate();
            if (!connection.getAutoCommit()){
                connection.commit();
            }
            return count;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeSilent(stmt);
            closeSilent(connection);
        }
    }

    protected  interface ResultSetToObject<T>{
        T toObject(ResultSet resultSet) throws SQLException;
    }

    protected interface ObjectToStatement<T>{
        void toStatement(T o, PreparedStatement stmt) throws SQLException;
    }



}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.taier.datasource.plugin.dm;

import com.dtstack.taier.datasource.api.utils.DBUtil;
import com.dtstack.taier.datasource.plugin.rdbms.AbsRdbmsClient;
import com.dtstack.taier.datasource.plugin.rdbms.ConnFactory;
import com.dtstack.taier.datasource.api.downloader.IDownloader;
import com.dtstack.taier.datasource.api.dto.ColumnMetaDTO;
import com.dtstack.taier.datasource.api.dto.SqlQueryDTO;
import com.dtstack.taier.datasource.api.dto.source.DmSourceDTO;
import com.dtstack.taier.datasource.api.dto.source.ISourceDTO;
import com.dtstack.taier.datasource.api.dto.source.RdbmsSourceDTO;
import com.dtstack.taier.datasource.api.exception.SourceException;
import com.dtstack.taier.datasource.api.source.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @company: www.dtstack.com
 * @Author ：Nanqi
 * @Date ：Created in 11:19 2020/4/17
 * @Description：达梦客户端
 */
@Slf4j
public class DmClient extends AbsRdbmsClient {

    private static final String DM_ALL_DATABASES = "SELECT DISTINCT OWNER FROM SYS.DBA_TABLES WHERE TABLESPACE_NAME = 'MAIN'";

    private static final String CREATE_TABLE_SQL = "select dbms_metadata.get_ddl(OBJECT_TYPE => 'TABLE',\n" +
            "NAME=>upper('%s'),SCHNAME => '%s')";

    // DMDB 获取指定schema下的表
    private static final String SHOW_TABLE_BY_SCHEMA_SQL = " SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = '%s' %s ";

    // DMDB 获取指定schema下的视图
    private static final String SHOW_VIEW_BY_SCHEMA_SQL = " UNION SELECT VIEW_NAME FROM ALL_VIEWS WHERE OWNER = '%s' %s ";

    // DMDB 获取所有的schema下所有表 ： 新sql
    private static final String SHOW_ALL_TABLE_SQL = "SELECT '\"'||OWNER||'\"'||'.'||'\"'||TABLE_NAME||'\"' AS TABLE_NAME FROM ALL_TABLES WHERE OWNER != 'SYS' %s ";

    // DMDB 获取所有的schema下所有视图 ： 新sql
    private static final String SHOW_ALL_VIEW_SQL = " UNION SELECT '\"'||OWNER||'\"'||'.'||'\"'||VIEW_NAME||'\"' AS TABLE_NAME FROM ALL_VIEWS WHERE OWNER != 'SYS' %s ";

    // 表查询基础sql
    private static final String TABLE_BASE_SQL = "SELECT TABLE_NAME FROM (%s) WHERE 1 = 1 %s ";

    // 表名正则匹配模糊查询，忽略大小写
    private static final String TABLE_SEARCH_SQL = " AND REGEXP_LIKE (TABLE_NAME, '%s', 'i') ";

    // 视图正则匹配模糊查询，忽略大小写
    private static final String VIEW_SEARCH_SQL = " AND REGEXP_LIKE (VIEW_NAME, '%s', 'i') ";

    // 限制条数语句
    private static final String LIMIT_SQL = " AND ROWNUM <= %s ";

    // 获取当前版本号
    private static final String SHOW_VERSION = "select BANNER from v$version";

    @Override
    protected ConnFactory getConnFactory() {
        return new DmConnFactory();
    }

    @Override
    protected DataSourceType getSourceType() {
        return DataSourceType.DMDB;
    }

    @Override
    public List<String> getTableList(ISourceDTO sourceDTO, SqlQueryDTO queryDTO) {
        return getTableListBySchema(sourceDTO, queryDTO);
    }

    @Override
    public List<ColumnMetaDTO> getColumnMetaData(ISourceDTO sourceDTO, SqlQueryDTO queryDTO) {
        Connection connection = getCon(sourceDTO, queryDTO);
        RdbmsSourceDTO rdbmsSourceDTO = (RdbmsSourceDTO) sourceDTO;
        Statement statement = null;
        ResultSet rs = null;

        // get primary key
        List<String> primaryKeyList = getPrimaryKeys(connection, queryDTO);
        List<String> nullableKeys = getNullableKeys(connection, queryDTO);

        List<ColumnMetaDTO> columns = new ArrayList<>();
        try {
            statement = connection.createStatement();
            statement.setMaxRows(1);
            String queryColumnSql = getQueryColumnSql(queryDTO, rdbmsSourceDTO);
            rs = statement.executeQuery(queryColumnSql);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            for (int i = 0, len = rsMetaData.getColumnCount(); i < len; i++) {
                ColumnMetaDTO columnMetaDTO = new ColumnMetaDTO();
                columnMetaDTO.setKey(rsMetaData.getColumnName(i + 1));
                columnMetaDTO.setType(doDealType(rsMetaData, i));
                columnMetaDTO.setPart(false);
                columnMetaDTO.setPrimaryKey(primaryKeyList.contains(columnMetaDTO.getKey()));
                columnMetaDTO.setNullable(nullableKeys.contains(columnMetaDTO.getKey()));
                // 获取字段精度，包括number类型
                if (columnMetaDTO.getType().equalsIgnoreCase("decimal")
                        || columnMetaDTO.getType().equalsIgnoreCase("float")
                        || columnMetaDTO.getType().equalsIgnoreCase("double")
                        || columnMetaDTO.getType().equalsIgnoreCase("numeric")
                        || columnMetaDTO.getType().equalsIgnoreCase("number")) {
                    columnMetaDTO.setScale(rsMetaData.getScale(i + 1));
                    columnMetaDTO.setPrecision(rsMetaData.getPrecision(i + 1));
                }

                // 获取TIMESTAMP字段的精度，因为flink需要timestamp(p)类型，所以将scale设置为precision
                if (columnMetaDTO.getType().toUpperCase().startsWith("TIMESTAMP")) {
                    columnMetaDTO.setType("TIMESTAMP");
                    columnMetaDTO.setPrecision(rs.getMetaData().getScale(i + 1));
                }

                columns.add(columnMetaDTO);
            }
        } catch (SQLException e) {
            throw new SourceException(String.format("Failed to get the meta information of the fields of the table: %s. Please contact the DBA to check the database and table information: %s",
                    queryDTO.getTableName(), e.getMessage()), e);
        } finally {
            com.dtstack.taier.datasource.plugin.common.utils.DBUtil.closeDBResources(rs, statement, connection);
        }

        return columns;
    }

    @Override
    public IDownloader getDownloader(ISourceDTO source, SqlQueryDTO queryDTO) throws Exception {
        DmSourceDTO dmSourceDTO = (DmSourceDTO) source;
        DmDownloader dmDownloader = new DmDownloader(getCon(dmSourceDTO), queryDTO.getSql(), dmSourceDTO.getSchema());
        dmDownloader.configure();
        return dmDownloader;
    }

    @Override
    public String getCreateTableSql(ISourceDTO source, SqlQueryDTO queryDTO) {
        DmSourceDTO dmSourceDTO = (DmSourceDTO) source;
        queryDTO.setSql(String.format(CREATE_TABLE_SQL, queryDTO.getTableName(), dmSourceDTO.getSchema()));
        return super.getCreateTableSql(source, queryDTO);
    }

    @Override
    public List<ColumnMetaDTO> getPartitionColumn(ISourceDTO source, SqlQueryDTO queryDTO) {
        throw new SourceException("Not Support");
    }

    @Override
    public String getShowDbSql() {
        return DM_ALL_DATABASES;
    }

    @Override
    protected String getVersionSql() {
        return SHOW_VERSION;
    }

    /**
     * 获取达梦数据库下的所有schema
     *
     * @param source   数据源信息
     * @param queryDTO 查询信息
     * @return
     */
    @Override
    public List<String> getAllDatabases(ISourceDTO source, SqlQueryDTO queryDTO) {
        List<String> result = new ArrayList<>();
        Connection connection = getCon(source);
        ResultSet rs = null;
        try {
            rs = connection.getMetaData().getSchemas();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (Exception e) {
            throw new SourceException("get all database from dmdb fail.");
        } finally {
            DBUtil.closeDBResources(rs, null, connection);
        }
        return result;
    }

    /**
     * 查询指定schema下的表，如果没有填schema，默认使用当前schema：支持条数限制、正则匹配
     *
     * @param sourceDTO 数据源信息
     * @param queryDTO  查询条件
     * @return 对应的sql语句
     */
    @Override
    protected String getTableBySchemaSql(ISourceDTO sourceDTO, SqlQueryDTO queryDTO) {
        // 构造表名模糊查询和条数限制sql
        String tableConstr = buildSearchSql(TABLE_SEARCH_SQL, queryDTO.getTableNamePattern(), queryDTO.getLimit());
        // 构造视图模糊查询和条数限制sql
        String viewConstr = buildSearchSql(VIEW_SEARCH_SQL, queryDTO.getTableNamePattern(), queryDTO.getLimit());
        RdbmsSourceDTO rdbmsSourceDTO = (RdbmsSourceDTO) sourceDTO;
        String schema = StringUtils.isNotBlank(queryDTO.getSchema()) ? queryDTO.getSchema() : rdbmsSourceDTO.getSchema();
        // schema若为空，则查询所有schema下的表
        String searchSql;
        if (StringUtils.isBlank(schema)) {
            log.info("schema is null，get all table！");
            searchSql = queryDTO.getView() ? String.format(SHOW_ALL_TABLE_SQL + SHOW_ALL_VIEW_SQL, tableConstr, viewConstr) : String.format(SHOW_ALL_TABLE_SQL, tableConstr);
        } else {
            searchSql = queryDTO.getView() ? String.format(SHOW_TABLE_BY_SCHEMA_SQL + SHOW_VIEW_BY_SCHEMA_SQL, schema, tableConstr, schema, viewConstr) : String.format(SHOW_TABLE_BY_SCHEMA_SQL, schema, tableConstr);
        }
        log.info("current used schema：{}", schema);

        return String.format(TABLE_BASE_SQL, searchSql, tableConstr);
    }

    /**
     * 构造模糊查询、条数限制sql
     *
     * @param tableSearchSql   查询 sql
     * @param tableNamePattern 模糊查询
     * @param limit            限制条数
     * @return sql 结果
     */
    private String buildSearchSql(String tableSearchSql, String tableNamePattern, Integer limit) {
        StringBuilder constr = new StringBuilder();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tableNamePattern)) {
            constr.append(String.format(tableSearchSql, tableNamePattern));
        }
        if (Objects.nonNull(limit)) {
            constr.append(String.format(LIMIT_SQL, limit));
        }
        return constr.toString();
    }

    protected String transferSchemaAndTableName(ISourceDTO sourceDTO, SqlQueryDTO sqlQueryDTO) {
        RdbmsSourceDTO rdbmsSourceDTO = (RdbmsSourceDTO) sourceDTO;
        String schema = StringUtils.isNotBlank(sqlQueryDTO.getSchema()) ? sqlQueryDTO.getSchema() : rdbmsSourceDTO.getSchema();
        return transferSchemaAndTableName(schema, sqlQueryDTO.getTableName());
    }

    protected String transferSchemaAndTableName(String schema, String tableName) {
        if (!tableName.startsWith("\"") && !tableName.endsWith("\"")) {
            tableName = String.format("\"%s\"", tableName);
            // 如果tableName包含Schema操作，无其他方法，只能去判断长度
        } else if (indexCount(tableName, "\"") >= 4) {
            return tableName;
        }
        if (StringUtils.isBlank(schema)) {
            return tableName;
        }
        if (!schema.startsWith("\"") && !schema.endsWith("\"")) {
            schema = String.format("\"%s\"", schema);
        }
        return String.format("%s.%s", schema, tableName);
    }

    private static int indexCount(String text, String countText) {
        // 根据指定的字符构建正则
        Pattern pattern = Pattern.compile(countText);
        // 构建字符串和正则的匹配
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        // 循环依次往下匹配
        // 如果匹配,则数量+1
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}

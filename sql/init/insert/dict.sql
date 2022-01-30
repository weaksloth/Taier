INSERT INTO `rdos_dict`(`type`,`dict_name`,`dict_value`,`dict_name_zh`,`dict_name_en`,`dict_sort`,`gmt_create`,`gmt_modified`,`is_deleted`)
VALUES
  ('1', 'MySQL', '1', 'MySQL', 'MySQL', '1', now(), now(), '0'),
  ('1', 'Oracle', '2', 'Oracle', 'Oracle', '2', now(), now(), '0'),
  ('1', 'SQLServer', '3', 'SQLServer', 'SQLServer', '3', now(), now(), '0'),
  ('1', 'PostgreSQL', '4', 'PostgreSQL', 'PostgreSQL', '4', now(), now(), '0'),
  ('1', 'RDBMS', '5', 'RDBMS', '其他关系型数据库', '5', now(), now(), '1'),
  ('1', 'HDFS', '6', 'HDFS', 'HDFS', '6', now(), now(), '0'),
  ('1', 'Hive2', '7', 'HIVE2', 'HIVE2', '7', now(), now(), '0'),
  ('1', 'Hive3', '50', 'HIVE3', 'HIVE3', '50', now(), now(), '0'),
  ('1', 'HBase', '8', 'HBASE', 'HBASE', '8', now(), now(), '0'),
  ('1', 'FTP', '9', 'FTP', 'FTP', '9', now(), now(), '0'),
  ('1', 'MaxCompute', '10', 'MaxCompute', 'MaxCompute', '10', now(), now(), '0'),
  ('1', 'ElasticSearch', '11', 'ElasticSearch', 'ElasticSearch', '11', now(), now(), '0'),
  ('1', 'Redis', '12', 'Redis', 'Redis', '12', now(), now(), '0'),
  ('1', 'MongoDB', '13', 'MongoDB', 'MongoDB', '13', now(), now(), '0'),
  ('1', 'DB2', '19', 'DB2', 'DB2', '19', now(), now(), '0'),
  ('1', 'CarbonData', '20', 'CarbonData', 'CarbonData', '20', now(), now(), '0'),
  ('1', 'LibrA', '21', 'LibrA', 'LibrA', 0, now(), now(), 0),
  ('1', 'GBase 8a', '22', 'GBase 8a', 'GBase 8a', '22', now(), now(), '0'),
  ('1', 'Kylin', '23', 'Kylin', 'Kylin', '23', now(), now(), '1'),
  ('1', 'Kudu', '24', 'Kudu', 'Kudu', '24', now(), now(), '0'),
  ('1', 'ClickHouse', '25', 'ClickHouse', 'ClickHouse', '25', now(), now(), '0'),
  ('1', 'Hive1', '27', 'Hive1', 'Hive1', '27', now(), now(), '0'),
  ('1', 'Polardb for MySQL', '28', 'Polardb for MySQL', 'Polardb for MySQL', '28', now(), now(), '0'),
  ('1', 'Impala', '29', 'Impala', 'Impala', '29', now(), now(), '0'),
  ('1', 'Inceptor', '52', 'Inceptor', 'Inceptor', '90', now(), now(), '0'),
  ('1', 'Phoenix', '30', 'Phoenix', 'Phoenix', '30', now(), now(), '1'),
  ('1', 'TiDB', '31', 'TiDB', 'TiDB', '31', now(), now(), '0'),
  ('1', 'DMDB', '35', '达梦DB', 'DMDB', '35', now(), now(), '0'),
  ('1', 'Greenplum', '36', 'Greenplum', 'Greenplum', '36', now(), now(), '0'),
  ('1', 'Phoenix5', '38', 'Phoenix5', 'Phoenix5', '38', now(), now(), '0'),
  ('1', 'Kingbase8', '40', 'Kingbase8', 'Kingbase8', '40', now(), now(), '0'),
  ('1', 'SparkThrift2.x', '45', 'SparkThrift2.x', 'SparkThrift2.x', '45', now(), now(), '0'),
  ('1', 'AWS S3', '51', 'AWS S3', 'AWS S3', '63', now(), now(), '0'),
  ('1', 'InfluxDB', '55', 'InfluxDB', 'InfluxDB', '95', now(), now(), '0'),
  ('1', 'AnalyticDB PostgreSQL', '54', 'AnalyticDB PostgreSQL', 'AnalyticDB PostgreSQL', '54', now(), now(), '0'),
  ('2', 'tinyint', '1', 'tinyint', 'tinyint', '1', now(), now(), '0'),
  ('2', 'smallint', '2', 'smallint', 'smallint', '2', now(), now(), '0'),
  ('2', 'int', '3', 'int', 'int', '3', now(), now(), '0'),
  ('2', 'bigint', '4', 'bigint', 'bigint', '4', now(), now(), '0'),
  ('2', 'boolean', '5', 'boolean', 'boolean', '5', now(), now(), '0'),
  ('2', 'float', '6', 'float', 'float', '6', now(), now(), '0'),
  ('2', 'double', '7', 'double', 'double', '7', now(), now(), '0'),
  ('2', 'string', '8', 'string', 'string', '8', now(), now(), '0'),
  ('2', 'binary', '9', 'binary', 'binary', '9', now(), now(), '0'),
  ('2', 'timestamp', '10', 'timestamp', 'timestamp', '10', now(), now(), '0'),
  ('2', 'decimal', '11', 'decimal', 'decimal', '11', now(), now(), '0'),
  ('2', 'date', '12', 'date', 'date', '12', now(), now(), '0'),
  ('2', 'varchar', '13', 'varchar', 'varchar', '13', now(), now(), '0'),
  ('2', 'char', '14', 'char', 'char', '14', now(), now(), '0'),
  ('3', 'SQL', '0', 'SQL', 'SQL', '1', now(), now(), '0'),
  ('3', 'Python 2.x', '1', 'Python 2.x', 'Python 2.x', '2', now(), now(), '0'),
  ('3', 'Python 3.x', '2', 'Python 3.x', 'Python 3.x', '3', now(), now(), '0'),
  ('3', 'Shell', '3', 'Shell', 'Shell', '4', now(), now(), '0'),
  ('4', 'TaskManager', '1', '任务管理', 'TaskManager', '1', now(), now(), '0'),
  ('4', 'ScriptManager', '2', '脚本管理', 'ScriptManager', '2', now(), now(), '0'),
  ('4', 'ResourceManager', '3', '资源管理', 'ResourceManager', '3', now(), now(), '0'),
  ('4', 'FunctionManager', '4', '函数管理', 'FunctionManager', '4', now(), now(), '0'),
  ('5', 'TaskManager', '1', '任务管理', 'TaskManager', '1', now(), now(), '0'),
  ('5', 'ResourceManager', '3', '资源管理', 'ResourceManager', '2', now(), now(), '0'),
  ('5', 'FunctionManager', '4', '函数管理', 'FunctionManager', '3', now(), now(), '0'),
  ('6', 'TaskDevelop', '1', '任务开发', 'TaskDevelop', '1', now(), now(), '0'),
  ('6', 'ScriptManager', '2', '脚本管理', 'ScriptManager', '2', now(), now(), '0'),
  ('6', 'ResourceManager', '3', '资源管理', 'ResourceManager', '3', now(), now(), '0'),
  ('7', 'TaskDevelop', '1', '任务开发', 'TaskDevelop', '1', now(), now(), '0'),
  ('7', 'ResourceManager', '3', '资源管理', 'ResourceManager', '3', now(), now(), '0'),
  ('7', 'CustomFunction', '4', '自定义函数', 'CustomFunction', '3', now(), now(), '0'),
  ('8', 'project_edit', '1', 'project_edit', 'project_edit', '1', now(), now(), '0'),
  ('4', 'TableQuery', '5', '表查询', 'TableQuery', '5', now(), now(), '0'),
  ('6', 'TableQuery', '5', '表查询', 'TableQuery', '5', now(), now(), '0'),
  ('9', 'STRING', '1', 'STRING', 'STRING', '1', now(), now(), '0'),
  ('9', 'TINYINT', '2', 'TINYINT', 'TINYINT', '2', now(), now(), '0'),
  ('9', 'SMALLINT', '3', 'SMALLINT', 'SMALLINT', '3', now(), now(), '0'),
  ('9', 'INT', '4', 'INT', 'INT', '4', now(), now(), '0'),
  ('9', 'BIGINT', '5', 'BIGINT', 'BIGINT', '5', now(), now(), '0'),
  ('9', 'BOOLEAN', '6', 'BOOLEAN', 'BOOLEAN', '6', now(), now(), '0'),
  ('9', 'FLOAT', '7', 'FLOAT', 'FLOAT', '7', now(), now(), '0'),
  ('9', 'DOUBLE', '8', 'DOUBLE', 'DOUBLE', '8', now(), now(), '0'),
  ('9', 'BINARY', '9', 'BINARY', 'BINARY', '9', now(), now(), '0'),
  ('9', 'TIMESTAMP', '10', 'TIMESTAMP', 'TIMESTAMP', '10', now(), now(), '0'),
  ('9', 'DECIMAL', '11', 'DECIMAL', 'DECIMAL', '11', now(), now(), '0'),
  ('9', 'DATE', '12', 'DATE', 'DATE', '12', now(), now(), '0'),
  ('9', 'VARCHAR', '13', 'VARCHAR', 'VARCHAR', '13', now(), now(), '0'),
  ('9', 'CHAR', '14', 'CHAR', 'CHAR', '14', now(), now(), '0'),
  ('10', 'SPARK_SQL', '0', 'SparkSQL', 'SparkSQL', '1', now(), now(), '0'),
  ('10', 'SPARK', '1', 'Spark', 'Spark', '2', now(), now(), '0'),
  ('10', 'SPARK_PYTHON', '3', 'PySpark', 'PySpark', '3', now(), now(), '0'),
  ('10', 'HADOOP_MR', '9', 'HadoopMR', 'HadoopMR', '4', now(), now(), '0'),
  ('10', 'SYNC', '2', '数据同步', 'SYNC', '5', now(), now(), '0'),
  ('10', 'WORK_FLOW', '10', '工作流', 'WORKFLOW', '6', now(), now(), '0'),
  ('10', 'PYTHON', '6', 'Python', 'Python', '9', now(), now(), '0'),
  ('10', 'SHELL', '7', 'Shell', 'Shell', '10', now(), now(), '0'),
  ('10', 'VIRTUAL', '-1', '虚节点', 'Virtual', '11', now(), now(), '0'),
  ('10','HIVE_SQL','17','HiveSQL','HiveSQL', '17', now(), now(), '0'),
  ('11', 'SPARK_SQL', '0', 'SparkSQL', 'SparkSQL', '1', now(), now(), '0'),
  ('11', 'SPARK', '1', 'Spark', 'Spark', '2', now(), now(), '0'),
  ('11', 'SYNC', '2', '数据同步', 'SYNC', '3', now(), now(), '0'),
  ('11', 'WORK_FLOW', '10', '工作流', 'WORKFLOW', '4', now(), now(), '0'),
  ('11', 'VIRTUAL', '-1', '虚节点', 'Virtual', '5', now(), now(), '0'),
  ('12', 'VIRTUAL', '-1', '虚节点', 'Virtual', '2', now(), now(), '0'),
  ('12', 'WORK_FLOW', '10', '工作流', 'WORKFLOW', '3', now(), now(), '0'),
  ('6', 'SparkSQLFunction', '4', 'SparkSQL', 'SparkSQLFunction', '4', now(), now(), '0'),
  ('13', 'SystemFunction', '6', '系统函数', 'SystemFunction', '2', now(), now(), '0'),
  ('13', 'CustomFunction', '6', '自定义函数', 'CustomFunction', '4', now(), now(), '0'),
  ('13', 'GreenPlumCustomFunction', '6', '自定义函数', 'GreenPlumCustomFunction', '8', now(), now(), '0')

on duplicate key update gmt_create=values(gmt_create);
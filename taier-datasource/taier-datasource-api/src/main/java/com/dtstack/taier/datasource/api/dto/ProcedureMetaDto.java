package com.dtstack.taier.datasource.api.dto;

import java.util.List;

public class ProcedureMetaDto {

    /**
     * procedure 名称
     */
    private String procedureName;

    /**
     * procedure 参数信息
     */
    private List<ParameterMetaDto> parameterMetaDtoList;

    public ProcedureMetaDto() {
    }

    public ProcedureMetaDto(String procedureName, List<ParameterMetaDto> parameterMetaDtoList) {
        this.procedureName = procedureName;
        this.parameterMetaDtoList = parameterMetaDtoList;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public List<ParameterMetaDto> getParameterMetaDtoList() {
        return parameterMetaDtoList;
    }

    public void setParameterMetaDtoList(List<ParameterMetaDto> parameterMetaDtoList) {
        this.parameterMetaDtoList = parameterMetaDtoList;
    }

    @Override
    public String toString() {
        return "ProcedureMetaDto{" +
                "procedureName='" + procedureName + '\'' +
                ", parameterMetaDtoList=" + parameterMetaDtoList +
                '}';
    }

    public static class ParameterMetaDto {

        private String columnName;

        /**
         * means input or output
         */
        private String columnType;

        private String dataType;

        private int precision;

        private int scale;

        public ParameterMetaDto() {

        }

        public ParameterMetaDto(String columnName, String columnType, String dataType, int precision, int scale) {
            this.columnName = columnName;
            this.columnType = columnType;
            this.dataType = dataType;
            this.precision = precision;
            this.scale = scale;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnType() {
            return columnType;
        }

        public void setColumnType(String columnType) {
            this.columnType = columnType;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public int getPrecision() {
            return precision;
        }

        public void setPrecision(int precision) {
            this.precision = precision;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        @Override
        public String toString() {
            return "ParameterMetaDto{" +
                    "columnName='" + columnName + '\'' +
                    ", columnType='" + columnType + '\'' +
                    ", dataType='" + dataType + '\'' +
                    ", precision=" + precision +
                    ", scale=" + scale +
                    '}';
        }
    }

}

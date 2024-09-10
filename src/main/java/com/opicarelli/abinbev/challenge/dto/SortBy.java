package com.opicarelli.abinbev.challenge.dto;

public class SortBy {

    private String columnName;
    private String order;

    public SortBy(String columnName, String order) {
        this.columnName = columnName;
        this.order = order;
    }

    public SortBy(SortByBuilder builder) {
        this.columnName = builder.columnName;
        this.order = builder.order;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public static class SortByBuilder {
        private String columnName;
        private String order;

        public SortByBuilder columnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public SortByBuilder order(String order) {
            this.order = order;
            return this;
        }

        public SortBy build() {
            return new SortBy(this);
        }
    }
}

package com.opicarelli.abinbev.challenge.dto;

public class PageBy {

    private Integer startIndex;
    private Integer quantityPerPage;

    public PageBy(PageByBuilder builder) {
        this.startIndex = builder.startIndex;
        this.quantityPerPage = builder.quantityPerPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartiIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getQuantityPerPage() {
        return quantityPerPage;
    }

    public void setQuantityPerPage(Integer quantityPerPage) {
        this.quantityPerPage = quantityPerPage;
    }

    public static class PageByBuilder {
        private Integer startIndex;
        private Integer quantityPerPage;

        public PageByBuilder startIndex(Integer startIndex) {
            this.startIndex = startIndex;
            return this;
        }

        public PageByBuilder quantityPerPage(Integer quantityPerPage) {
            this.quantityPerPage = quantityPerPage;
            return this;
        }

        public PageBy build() {
            return new PageBy(this);
        }
    }
}

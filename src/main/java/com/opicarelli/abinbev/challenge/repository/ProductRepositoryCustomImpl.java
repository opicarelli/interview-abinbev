package com.opicarelli.abinbev.challenge.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.opicarelli.abinbev.challenge.data.Product;
import com.opicarelli.abinbev.challenge.dto.PageBy;
import com.opicarelli.abinbev.challenge.dto.ProductPaginatedSearchFilter;
import com.opicarelli.abinbev.challenge.dto.SortBy;

@Repository
@Transactional(readOnly = true)
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> search(ProductPaginatedSearchFilter filter) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT p FROM " + Product.class.getSimpleName() + " p");
        jpql.append(" LEFT JOIN FETCH p.brand b");
        jpql.append(" WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(filter.getName())) {
            jpql.append(" AND UPPER(p.name) LIKE :name");
            params.put("name", "%" + filter.getName().toUpperCase() + "%");
        }
        if (StringUtils.isNotEmpty(filter.getDescription())) {
            jpql.append(" AND UPPER(p.description) LIKE :description");
            params.put("description", "%" + filter.getDescription().toUpperCase() + "%");
        }
        if (filter.getPriceGreaterThan() != null) {
            jpql.append(" AND p.price >= :priceGreaterThan");
            params.put("priceGreaterThan", filter.getPriceGreaterThan());
        }
        if (filter.getPriceLessThan() != null) {
            jpql.append(" AND p.price <= :priceLessThan");
            params.put("priceLessThan", filter.getPriceLessThan());
        }
        if (filter.getBrandId() != null) {
            jpql.append(" AND p.brand.id = :brandId");
            params.put("brandId", filter.getBrandId());
        }

        appendSortBy(filter.getSortBy(), jpql, "p");

        TypedQuery<Product> query = entityManager.createQuery(jpql.toString(), Product.class);

        PageBy pageBy = filter.getPageBy();
        if (pageBy != null) {
            query = query
                    .setFirstResult(pageBy.getStartIndex())
                    .setMaxResults(pageBy.getQuantityPerPage());
        }

        for (Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }

    private void appendSortBy(List<SortBy> sortByList, StringBuilder jpql, String prefix) {
        if (sortByList != null && !sortByList.isEmpty()) {
            jpql.append(" ORDER BY");

            String orderBy = sortByList
                    .stream()
                    .map(sortBy -> prefix + "." + sortBy.getColumnName() + " " + sortBy.getOrder())
                    .collect(Collectors.joining(","));

            jpql.append(" " + orderBy);
        }
    }

}
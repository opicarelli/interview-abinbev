package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opicarelli.abinbev.challenge.dto.SortBy;
import com.opicarelli.abinbev.challenge.dto.SortBy.SortByBuilder;

public class SortUtils {

    private SortUtils() {
        // not instantiate class
    }

    public static List<SortBy> parseSortBy(String orderColunmName, String orderCriteria) {
        List<SortBy> sortByList = new ArrayList<>();

        if (!StringUtils.isEmpty(orderColunmName)) {
            String[] columnByParts = orderColunmName.split(",");
            String[] criteriaByParts = Arrays.copyOf(orderCriteria.split(","), columnByParts.length);

            for (int i = 0; i < columnByParts.length; i++) {
                String column = columnByParts[i];
                String order = criteriaByParts[i];
                SortBy sortBy = new SortByBuilder()
                    .columnName(column)
                    .order(order != null ? order : "asc")
                    .build();

                sortByList.add(sortBy);
            }
        }

        return sortByList;
    }

}

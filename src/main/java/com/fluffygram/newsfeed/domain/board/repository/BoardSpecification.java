package com.fluffygram.newsfeed.domain.board.repository;

import com.fluffygram.newsfeed.domain.board.entity.Board;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoardSpecification {
    public static Specification<Board> filterByDateType(String dateType) {
        return (root, query, criteriaBuilder) -> {
            if ("create".equals(dateType)) {
                query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            } else if ("modify".equals(dateType)) {
                query.orderBy(criteriaBuilder.desc(root.get("modifiedAt")));
            }
            return null;
        };
    }

    public static Specification<Board> filterByLikeManySort(String likeManySort) {
        return (root, query, criteriaBuilder) -> {
            if ("like".equals(likeManySort)) {
                query.orderBy(criteriaBuilder.desc(criteriaBuilder.size(root.get("boardLikeListList"))));
            }
            return null;
        };
    }

    public static Specification<Board> filterByModifyAtRange(LocalDate startAt, LocalDate endAt) {
        return (root, query, criteriaBuilder) -> {
            if (startAt != null && endAt != null) {
                return criteriaBuilder.between(
                        root.get("modifiedAt"),
                        startAt.atStartOfDay(),
                        endAt.atTime(23, 59, 59));

            } else if (startAt != null) {
                return criteriaBuilder.between(root.get("modifiedAt"), startAt.atStartOfDay(), LocalDateTime.now());

            }else if (endAt != null) {
                String str = "1970-01-01";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateTime = LocalDate.parse(str, formatter);
                return criteriaBuilder.between(root.get("modifiedAt"), dateTime, endAt);
            }
            return null;
        };
    }
}


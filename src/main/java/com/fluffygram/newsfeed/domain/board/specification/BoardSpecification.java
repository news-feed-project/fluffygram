package com.fluffygram.newsfeed.domain.board.specification;

import com.fluffygram.newsfeed.domain.base.enums.LikeStatus;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BoardSpecification {
    public static Specification<Board> filterByDateType(String dateType) {
        return (root, query, criteriaBuilder) -> {
            if ("modify".equals(dateType)) {
                query.orderBy(criteriaBuilder.desc(root.get("modifiedAt")));
            }else {
                query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            }
            return null;
        };
    }

    public static Specification<Board> filterByLikeManySortAndDateType(String dateType) {
        return (root, query, cb) -> {
            // LEFT JOIN board_like
            Join<Board, BoardLike> likesJoin = root.join("boardLikeList", JoinType.LEFT);

            // COUNT(CASE WHEN bl.likeStatus = 'REGISTER' THEN 1 END)
            Expression<Long> likeCount = cb.count(cb.selectCase()
                    .when(cb.equal(likesJoin.get("likeStatus"), LikeStatus.REGISTER), 1)
                    .otherwise((Long) null)
            );

            // Add group by for Board.id
            query.groupBy(root.get("id"));

            // Order by likeCount DESC as the primary sort
            List<Order> orderList = new ArrayList<>();
            orderList.add(cb.desc(likeCount));

            // Add secondary sort based on dateType (createAt or modifyAt)
            if ("create".equalsIgnoreCase(dateType)) {
                orderList.add(cb.desc(root.get("createdAt")));
            } else if ("modify".equalsIgnoreCase(dateType)) {
                orderList.add(cb.desc(root.get("modifiedAt")));
            }

            // Apply the order list
            query.orderBy(orderList);

            return null; // No specific where condition, just sorting/grouping
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
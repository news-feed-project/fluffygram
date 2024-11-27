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
import java.util.ArrayList;
import java.util.List;

public class BoardSpecification {
    public static Specification<Board> filterByDateType(String dateType) {
        return (root, query, criteriaBuilder) -> {
            if ("modifiedAt".equals(dateType)) {
                query.orderBy(criteriaBuilder.desc(root.get(dateType)));
            }else {
                query.orderBy(criteriaBuilder.desc(root.get(dateType)));
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
            if ("createdAt".equalsIgnoreCase(dateType)) {
                orderList.add(cb.desc(root.get(dateType)));
            } else {
                orderList.add(cb.desc(root.get(dateType)));
            }

            // Apply the order list
            query.orderBy(orderList);

            return null; // No specific where condition, just sorting/grouping
        };
    }

    public static Specification<Board> filterByModifyAtRange(String dateType, LocalDate startAt, LocalDate endAt) {

        return (root, query, criteriaBuilder) -> {
            if (startAt != null && endAt != null) {
                return criteriaBuilder.between(
                        root.get(dateType),
                        startAt.atStartOfDay(),
                        endAt.atTime(23, 59, 59));

            } else if (startAt != null) {
                return criteriaBuilder.between(root.get(dateType), startAt.atStartOfDay(), LocalDateTime.now());

            }else if (endAt != null) {
                LocalDate defaultStart = LocalDate.of(1970, 1, 1);
                return criteriaBuilder.between(
                        root.get(dateType),
                        defaultStart.atStartOfDay(),
                        endAt.atTime(23, 59, 59));
            }
            return null;
        };
    }
}
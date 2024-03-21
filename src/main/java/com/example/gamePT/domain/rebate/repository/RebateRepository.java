package com.example.gamePT.domain.rebate.repository;

import com.example.gamePT.domain.rebate.entity.Rebate;
import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RebateRepository extends JpaRepository<Rebate,Long> {

    @Query(
            value =
            "SELECT "
            + "     row_number() over() as id, "
            + "     YEAR(oi.create_date) AS sales_year,"
            + "     MONTH(oi.create_date) AS sales_month, "
            + "     SUM(oi.price) AS sales_price, "
            + "     COUNT(oi.id) AS sales_number, "
            + "     NOW() AS create_date, "
            + "     NOW() AS modified_date,  "
            + "     seller.id AS seller_id  "
            + "FROM "
            + "     order_item oi "
            + "INNER JOIN course AS c ON "
            + "     oi.course_id = c.id "
            + "INNER JOIN site_user AS seller ON "
            + "     c.author_id = seller.id "
            + "GROUP BY "
            + "     seller.id, sales_month "
            + "HAVING "
            + " seller.id=:sellerId and sales_month=:month and sales_year=:year",
            nativeQuery = true
    )
    Optional<Rebate> findBySellerAndSalePeriod(@Param("sellerId")Long sellerId ,@Param("year") Integer year,@Param("month") Integer month);
}

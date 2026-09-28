package com.demo.prices.adapter.db;

import com.demo.prices.adapter.db.entities.PricesJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PricesJpaRepository extends JpaRepository<PricesJpaEntity, Long> {

    List<PricesJpaEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderByPriorityDesc(LocalDateTime liveDate, LocalDateTime endDate, Long productId, Long brandId);

    Optional<PricesJpaEntity> findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderByPriority(LocalDateTime liveDate, LocalDateTime endDate, Long productId, Long brandId);


}

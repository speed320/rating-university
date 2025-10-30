package ru.ystu.rating.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ystu.rating.university.domain.BYearParams;

import java.util.List;
import java.util.Optional;

public interface BYearParamsRepository extends JpaRepository<BYearParams, Long> {
    List<BYearParams> findAllByYear(Integer year);
    List<BYearParams> findAllByOrderByYearAsc();
    Optional<BYearParams> findByYearAndIteration(Integer year, Integer iteration);

    @Query("select p from BYearParams p where p.year between :from and :to order by p.year asc, p.iteration asc")
    List<BYearParams> findRange(@Param("from") int from, @Param("to") int to);

    @Modifying
    @Query("update BYearParams p set p.iteration = :iteration")
    void updateIterationForAll(int iteration);
}


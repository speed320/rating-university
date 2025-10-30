package ru.ystu.rating.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ystu.rating.university.domain.BYearCalc;

import java.util.List;
import java.util.Optional;

public interface BYearCalcRepository extends JpaRepository<BYearCalc, Long> {
    Optional<BYearCalc> findByYearAndIteration(Integer year, Integer iteration);
    List<BYearCalc> findAllByYear(Integer year);
    List<BYearCalc> findAllByOrderByYearAsc();

    @Query("select max(c.iteration) from BYearCalc c")
    Integer findMaxIteration();

    List<BYearCalc> findAllByIterationOrderByYearAsc(Integer iteration);
    Optional<BYearCalc> findFirstByYearOrderByIterationDesc(Integer year);
}


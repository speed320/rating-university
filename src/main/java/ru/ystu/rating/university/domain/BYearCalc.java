package ru.ystu.rating.university.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "b_year_calc",
        uniqueConstraints = @UniqueConstraint(columnNames = {"year","iteration"}))
public class BYearCalc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Integer year;
    @Column(nullable = false) private Integer iteration = 1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "params_id")
    private BYearParams params;
    @Column(nullable = false) private double b11;
    @Column(nullable = false) private double b12;
    @Column(nullable = false) private double b13;
    @Column(nullable = false) private double b21;
    @Column(name = "total_b", nullable = false) private double totalB;
    @Column(name = "calculated_at", nullable = false)
    private OffsetDateTime calculatedAt = OffsetDateTime.now();
    public Long getId() { return id; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Integer getIteration() { return iteration; }
    public void setIteration(Integer iteration) { this.iteration = iteration; }
    public BYearParams getParams() { return params; }
    public void setParams(BYearParams params) { this.params = params; }
    public double getB11() { return b11; }
    public void setB11(double b11) { this.b11 = b11; }
    public double getB12() { return b12; }
    public void setB12(double b12) { this.b12 = b12; }
    public double getB13() { return b13; }
    public void setB13(double b13) { this.b13 = b13; }
    public double getB21() { return b21; }
    public void setB21(double b21) { this.b21 = b21; }
    public double getTotalB() { return totalB; }
    public void setTotalB(double totalB) { this.totalB = totalB; }
    public OffsetDateTime getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(OffsetDateTime calculatedAt) { this.calculatedAt = calculatedAt; }
}


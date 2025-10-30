package ru.ystu.rating.university.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "b_year_params",
        uniqueConstraints = @UniqueConstraint(columnNames = {"year","iteration"}))
public class BYearParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private Integer year;
    @Column(nullable = false) private Integer iteration = 1;
    @Column(name = "e_na", nullable = false) private double eNa;
    @Column(name = "e_nb", nullable = false) private double eNb;
    @Column(name = "e_nc", nullable = false) private double eNc;
    @Column(name = "eb", nullable = false) private double eb;
    @Column(name = "ec", nullable = false) private double ec;
    @Column(nullable = false) private double beta121;
    @Column(nullable = false) private double beta122;
    @Column(nullable = false) private double beta131;
    @Column(nullable = false) private double beta132;
    @Column(nullable = false) private double beta211;
    @Column(nullable = false) private double beta212;
    public Long getId() { return id; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Integer getIteration() { return iteration; }
    public void setIteration(Integer iteration) { this.iteration = iteration; }
    public double getENa() { return eNa; }
    public void setENa(double eNa) { this.eNa = eNa; }
    public double getENb() { return eNb; }
    public void setENb(double eNb) { this.eNb = eNb; }
    public double getENc() { return eNc; }
    public void setENc(double eNc) { this.eNc = eNc; }
    public double getEb() { return eb; }
    public void setEb(double eb) { this.eb = eb; }
    public double getEc() { return ec; }
    public void setEc(double ec) { this.ec = ec; }
    public double getBeta121() { return beta121; }
    public void setBeta121(double beta121) { this.beta121 = beta121; }
    public double getBeta122() { return beta122; }
    public void setBeta122(double beta122) { this.beta122 = beta122; }
    public double getBeta131() { return beta131; }
    public void setBeta131(double beta131) { this.beta131 = beta131; }
    public double getBeta132() { return beta132; }
    public void setBeta132(double beta132) { this.beta132 = beta132; }
    public double getBeta211() { return beta211; }
    public void setBeta211(double beta211) { this.beta211 = beta211; }
    public double getBeta212() { return beta212; }
    public void setBeta212(double beta212) { this.beta212 = beta212; }
}


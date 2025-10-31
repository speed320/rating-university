package ru.ystu.rating.university.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ystu.rating.university.domain.BMetric;
import ru.ystu.rating.university.domain.BYearCalc;
import ru.ystu.rating.university.domain.BYearParams;
import ru.ystu.rating.university.dto.BCalcDto;
import ru.ystu.rating.university.dto.BParamsBundle;
import ru.ystu.rating.university.dto.BParamsDto;
import ru.ystu.rating.university.repository.BYearCalcRepository;
import ru.ystu.rating.university.repository.BYearParamsRepository;
import ru.ystu.rating.university.util.Normalizer;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BService {

    private final BYearParamsRepository paramsRepo;
    private final BYearCalcRepository calcRepo;

    public BService(BYearParamsRepository paramsRepo, BYearCalcRepository calcRepo) {
        this.paramsRepo = paramsRepo;
        this.calcRepo = calcRepo;
    }

    // ---------- Import/Export & Read params ----------
    @Transactional
    public void importBundle(BParamsBundle bundle) {
        if (bundle == null || bundle.data() == null) return;
        paramsRepo.deleteAll();
        for (BParamsDto d : bundle.data()) {
            var e = paramsRepo.findAllByYear(d.year()).stream().findFirst().orElseGet(BYearParams::new);
            e.setYear(d.year());
            e.setENa(nz(d.eNa()));
            e.setENb(nz(d.eNb()));
            e.setENc(nz(d.eNc()));
            e.setEb(nz(d.eb()));
            e.setEc(nz(d.ec()));
            e.setBeta121(nz(d.beta121()));
            e.setBeta122(nz(d.beta122()));
            e.setBeta131(nz(d.beta131()));
            e.setBeta132(nz(d.beta132()));
            e.setBeta211(nz(d.beta211()));
            e.setBeta212(nz(d.beta212()));
            paramsRepo.save(e);
        }
    }

    public BParamsBundle exportAll() {
        var data = paramsRepo.findAllByOrderByYearAsc().stream()
                .map(p -> new BParamsDto(
                        p.getYear(),
                        p.getENa(), p.getENb(), p.getENc(),
                        p.getEb(), p.getEc(),
                        p.getBeta121(), p.getBeta122(),
                        p.getBeta131(), p.getBeta132(),
                        p.getBeta211(), p.getBeta212()
                ))
                .toList();
        return new BParamsBundle("B", data);
    }

    public List<BParamsDto> getParamsByYear(int year) {
        return paramsRepo.findAllByYear(year).stream()
                .map(p -> new BParamsDto(
                        p.getYear(),
                        p.getENa(), p.getENb(), p.getENc(),
                        p.getEb(), p.getEc(),
                        p.getBeta121(), p.getBeta122(),
                        p.getBeta131(), p.getBeta132(),
                        p.getBeta211(), p.getBeta212()
                ))
                .toList();
    }

    public List<BParamsDto> getParamsAll() {
        return exportAll().data();
    }

    // ----------------- Compute -----------------
    private BYearCalc computeOne(BYearParams p, int iteration) {
        // B11_raw = (ENa*100 + ENb*Eb + ENc*Ec) / (ENa + ENb + ENc)
        double b11raw = Normalizer.safeDiv(
                p.getENa() * 100.0 + p.getENb() * p.getEb() + p.getENc() * p.getEc(),
                p.getENa() + p.getENb() + p.getENc()
        );
        double b11 = Normalizer.clamp01(b11raw, BMetric.B11.vmin, BMetric.B11.vmax) * BMetric.B11.weight;

        // B12_raw = (beta121 / beta122) * 100
        double b12raw = Normalizer.safeDiv(p.getBeta121(), p.getBeta122()) * 100.0;
        double b12 = Normalizer.clamp01(b12raw, BMetric.B12.vmin, BMetric.B12.vmax) * BMetric.B12.weight;

        // B13_raw = beta131 / beta132
        double b13raw = Normalizer.safeDiv(p.getBeta131(), p.getBeta132());
        double b13 = Normalizer.clamp01(b13raw, BMetric.B13.vmin, BMetric.B13.vmax) * BMetric.B13.weight;

        // B21_raw = beta211 / beta212
        double b21raw = Normalizer.safeDiv(p.getBeta211(), p.getBeta212());
        double b21 = Normalizer.clamp01(b21raw, BMetric.B21.vmin, BMetric.B21.vmax) * BMetric.B21.weight;

        BYearCalc c = calcRepo.findByYearAndIteration(p.getYear(), p.getIteration()).orElse(new BYearCalc());
        c.setYear(p.getYear());
        c.setIteration(iteration);
        c.setParams(p);
        c.setB11(b11);
        c.setB12(b12);
        c.setB13(b13);
        c.setB21(b21);
        c.setTotalB(b11 + b12 + b13 + b21);
        c.setCalculatedAt(OffsetDateTime.now());
        return calcRepo.save(c);
    }

    @Transactional
    public List<BCalcDto> computeAllYears() {
        Integer maxIter = calcRepo.findMaxIteration();
        int nextIter = (maxIter == null ? 0 : maxIter) + 1;

        paramsRepo.updateIterationForAll(nextIter);

        return paramsRepo.findAllByOrderByYearAsc().stream()
                .map(p -> computeOne(p, nextIter))
                .map(this::toDto)
                .toList();
    }

//    @Transactional
//    public List<BCalcDto> computeYear(int year) {
//        return paramsRepo.findAllByYear(year).stream()
//                .map(this::computeOne).map(this::toDto).toList();
//    }

    public List<BCalcDto> getCalcAll() {
        Integer lastIter = calcRepo.findMaxIteration();
        if (lastIter == null) return List.of();
        return calcRepo.findAllByIterationOrderByYearAsc(lastIter).stream()
                .map(this::toDto)
                .toList();
    }

    public List<BCalcDto> getCalcByYear(int year) {
        Integer lastIter = calcRepo.findMaxIteration();
        if (lastIter == null) return List.of();
        return calcRepo.findByYearAndIteration(year, lastIter)
                .map(c -> List.of(toDto(c)))
                .orElse(List.of());
    }

    private BCalcDto toDto(BYearCalc c) {
        return new BCalcDto(
                c.getYear(), c.getIteration(),
                c.getB11(), c.getB12(), c.getB13(), c.getB21(),
                c.getTotalB(),
                c.getCalculatedAt().toString()
        );
    }

    private double nz(Double v) { return v == null ? 0.0 : v; }
}


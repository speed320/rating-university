--liquibase formatted sql
--changeset sergey:0001_indexes

-- Композитный уникальный ключ (year + iteration)
ALTER TABLE b_year_params
    ADD CONSTRAINT uq_b_year_params_year_iteration UNIQUE (year, iteration);

-- Индекс для диапазонных выборок по годам
CREATE INDEX IF NOT EXISTS idx_b_year_params_year
    ON b_year_params (year);

-- Частичный уникальный индекс: только одна запись current на год
CREATE UNIQUE INDEX IF NOT EXISTS uq_b_year_params_year_is_current_true
    ON b_year_params (year)
    WHERE is_current = TRUE;

ALTER TABLE b_year_calc
    ADD CONSTRAINT uq_b_year_calc_year_iteration UNIQUE (year, iteration);

CREATE INDEX IF NOT EXISTS idx_b_year_calc_year ON b_year_calc(year);
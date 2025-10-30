--liquibase formatted sql
--changeset sergey:0000_init_tables

CREATE TABLE IF NOT EXISTS b_year_params (
    id          BIGSERIAL PRIMARY KEY,

    -- версия и статус
    year        INTEGER NOT NULL,
    iteration   INTEGER NOT NULL DEFAULT 1,
    is_current  BOOLEAN NOT NULL DEFAULT TRUE,

    -- параметры расчёта
    e_na        DOUBLE PRECISION NOT NULL DEFAULT 0,
    e_nb        DOUBLE PRECISION NOT NULL DEFAULT 0,
    e_nc        DOUBLE PRECISION NOT NULL DEFAULT 0,
    eb          DOUBLE PRECISION NOT NULL DEFAULT 0,
    ec          DOUBLE PRECISION NOT NULL DEFAULT 0,

    beta121     DOUBLE PRECISION NOT NULL DEFAULT 0,
    beta122     DOUBLE PRECISION NOT NULL DEFAULT 0,
    beta131     DOUBLE PRECISION NOT NULL DEFAULT 0,
    beta132     DOUBLE PRECISION NOT NULL DEFAULT 0,
    beta211     DOUBLE PRECISION NOT NULL DEFAULT 0,
    beta212     DOUBLE PRECISION NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS b_year_calc (
    id               BIGSERIAL PRIMARY KEY,
    year             INTEGER       NOT NULL,
    iteration        INTEGER       NOT NULL DEFAULT 1,
    params_id        BIGINT        NULL,  -- ссылка на b_year_params.id (необязательно)
    b11              DOUBLE PRECISION NOT NULL,
    b12              DOUBLE PRECISION NOT NULL,
    b13              DOUBLE PRECISION NOT NULL,
    b21              DOUBLE PRECISION NOT NULL,
    total_b          DOUBLE PRECISION NOT NULL,
    calculated_at    TIMESTAMPTZ   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

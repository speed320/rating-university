package ru.ystu.rating.university.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BParamsDto(
        @JsonProperty("year")
        Integer year,

        @JsonProperty("eNa") @JsonAlias({"ENa"})
        Double eNa,

        @JsonProperty("eNb") @JsonAlias({"ENb"})
        Double eNb,

        @JsonProperty("eNc") @JsonAlias({"ENc"})
        Double eNc,

        @JsonProperty("eb")  @JsonAlias({"Eb"})
        Double eb,

        @JsonProperty("ec")  @JsonAlias({"Ec"})
        Double ec,

        @JsonProperty("beta121") @JsonAlias({"B121","b121"})
        Double beta121,

        @JsonProperty("beta122") @JsonAlias({"B122","b122"})
        Double beta122,

        @JsonProperty("beta131") @JsonAlias({"B131","b131"})
        Double beta131,

        @JsonProperty("beta132") @JsonAlias({"B132","b132"})
        Double beta132,

        @JsonProperty("beta211") @JsonAlias({"B211","b211"})
        Double beta211,

        @JsonProperty("beta212") @JsonAlias({"B212","b212"})
        Double beta212
) {}


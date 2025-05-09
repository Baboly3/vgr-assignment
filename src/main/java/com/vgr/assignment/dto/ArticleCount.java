package com.vgr.assignment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ArticleCount(
        @NotNull @Min(1) Integer type,
        @NotNull @Min(1) Integer count) { }

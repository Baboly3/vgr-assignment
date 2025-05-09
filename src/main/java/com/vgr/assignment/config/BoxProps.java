package com.vgr.assignment.config;

import com.vgr.assignment.model.Box;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@ConfigurationProperties(prefix = "boxes")
@Validated
public record BoxProps( @NotEmpty Map<Integer, Box> types) { }
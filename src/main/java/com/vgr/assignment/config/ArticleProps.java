package com.vgr.assignment.config;

import com.vgr.assignment.model.Article;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@ConfigurationProperties(prefix = "article-types")
@Validated
public record ArticleProps(@NotEmpty Map<Integer, Article> types) { }
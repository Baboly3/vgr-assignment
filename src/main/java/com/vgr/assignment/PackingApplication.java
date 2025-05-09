package com.vgr.assignment;

import com.vgr.assignment.config.ArticleProps;
import com.vgr.assignment.config.BoxProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ArticleProps.class, BoxProps.class })
public class PackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackingApplication.class, args);
	}

}

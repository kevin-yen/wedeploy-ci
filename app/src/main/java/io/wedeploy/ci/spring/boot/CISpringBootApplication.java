package io.wedeploy.ci.spring.boot;

import io.wedeploy.ci.jenkins.JenkinsLegion;
import io.wedeploy.ci.jenkins.JenkinsUpdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class CISpringBootApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CISpringBootApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CISpringBootApplication.class, args);

		JenkinsLegion jenkinsLegion = JenkinsLegion.getJenkinsLegion();

		JenkinsUpdater.start();
	}

}
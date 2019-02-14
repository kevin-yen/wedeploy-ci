package io.wedeploy.ci.spring.boot;

import java.util.Map;

import com.wedeploy.android.exception.WeDeployException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.wedeploy.ci.jenkins.JenkinsLegion;

@Controller
public class CISpringBootController {

	@RequestMapping("/")
	public String index(Map<String, Object> model) throws WeDeployException {
		JenkinsLegion jenkinsLegion = JenkinsLegion.getJenkinsLegion();

		model.put("jenkinsLegion", jenkinsLegion);

		return "index";
	}

}
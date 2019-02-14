package io.wedeploy.ci.spring.boot;

import com.wedeploy.android.exception.WeDeployException;
import io.wedeploy.ci.jenkins.JenkinsCohort;
import io.wedeploy.ci.jenkins.JenkinsLegion;
import io.wedeploy.ci.jenkins.JenkinsMaster;

import java.util.List;

import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CISpringBootRestController {

	@GetMapping("/counts")
	public String counts() throws WeDeployException {
		JSONObject jsonObject = new JSONObject();

		JenkinsLegion jenkinsLegion = JenkinsLegion.getJenkinsLegion();

		List<JenkinsCohort> jenkinsCohorts = jenkinsLegion.getCohorts();

		int totalOfflineSlaveCount = 0;

		for (JenkinsCohort jenkinsCohort : jenkinsCohorts) {
			List<JenkinsMaster> jenkinsMasters = jenkinsCohort.getMasters();

			for (JenkinsMaster jenkinsMaster : jenkinsMasters) {
				jsonObject.put(jenkinsMaster.getName(), jenkinsMaster.getOfflineSlaveCount());
			}

			totalOfflineSlaveCount += jenkinsCohort.getOfflineSlaveCount();
		}

		jsonObject.put("offline_slave_count", totalOfflineSlaveCount);

		return jsonObject.toString();
	}

}
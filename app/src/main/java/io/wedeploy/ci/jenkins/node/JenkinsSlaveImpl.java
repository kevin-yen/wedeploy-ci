package io.wedeploy.ci.jenkins.node;

import io.wedeploy.ci.util.CurlUtil;

import java.io.IOException;

import org.json.JSONObject;

public class JenkinsSlaveImpl extends JenkinsNode implements JenkinsSlave {

	public JenkinsSlaveImpl(String hostname, JenkinsMaster jenkinsMaster)
		throws IOException {

		this.hostname = hostname;

		_jenkinsMaster = jenkinsMaster;

		String masterHostname = _jenkinsMaster.getHostname();

		localURL = "http://" + masterHostname + "/computer/" + hostname + "/";
		remoteURL = "https://" + masterHostname + ".liferay.com/computer/" + hostname + "/";

		JSONObject slaveJSONObject = new JSONObject(CurlUtil.curl(
			remoteURL + "api/json?tree=offline,offlineCauseReason"));

		_offline = slaveJSONObject.getBoolean("offline");
		_offlineCause = slaveJSONObject.getString("offlineCauseReason");
	}

	public JenkinsSlaveImpl(
		JSONObject slaveJSONObject, JenkinsMaster jenkinsMaster) {

		this.hostname = slaveJSONObject.getString("displayName");

		_jenkinsMaster = jenkinsMaster;

		String masterHostname = _jenkinsMaster.getHostname();

		localURL = "http://" + masterHostname + "/computer/" + hostname + "/";
		remoteURL = "https://" + masterHostname + ".liferay.com/computer/" + hostname + "/";

		_offline = slaveJSONObject.getBoolean("offline");
		_offlineCause = slaveJSONObject.getString("offlineCauseReason");
	}

	public boolean isOffline() {
		return _offline;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("hostname", hostname);
		jsonObject.put("offline", _offline);
		jsonObject.put("offline_cause", _offlineCause);
		jsonObject.put("remote_url", remoteURL);
		jsonObject.put("local_url", localURL);

		return jsonObject;
	}

	private boolean _offline;
	private String _offlineCause;
	private JenkinsMaster _jenkinsMaster;

}
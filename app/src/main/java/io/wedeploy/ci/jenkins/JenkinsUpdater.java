package io.wedeploy.ci.jenkins;

import com.wedeploy.android.exception.WeDeployException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JenkinsUpdater implements Job {

	public void execute(JobExecutionContext context) {
		try {
			JenkinsLegion jenkinsLegion = JenkinsLegion.getJenkinsLegion();

			jenkinsLegion.update();
		}
		catch (WeDeployException e) {
			e.printStackTrace();
		}
	}

	public static void start() throws SchedulerException {
		if (_scheduler == null || _scheduler.isShutdown()) {
			JobDetail jobDetail = JobBuilder.newJob(JenkinsUpdater.class)
				.withIdentity("jenkinsUpdater", "group")
				.build();

			Trigger trigger = TriggerBuilder
				.newTrigger().withIdentity("cronTrigger", "group")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 */10 * ? * *"))
				.build();

			_scheduler = new StdSchedulerFactory().getScheduler();

			_scheduler.start();

			_scheduler.scheduleJob(jobDetail, trigger);
		}
	}

	public static void stop() throws SchedulerException {
		_scheduler.shutdown();
	}

	private static Scheduler _scheduler;

}
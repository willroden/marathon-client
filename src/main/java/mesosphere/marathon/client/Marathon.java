package mesosphere.marathon.client;

import java.util.List;

import feign.Param;
import mesosphere.marathon.client.model.v2.App;
import mesosphere.marathon.client.model.v2.DeleteAppTaskResponse;
import mesosphere.marathon.client.model.v2.DeleteAppTasksResponse;
import mesosphere.marathon.client.model.v2.Deployment;
import mesosphere.marathon.client.model.v2.GetAppResponse;
import mesosphere.marathon.client.model.v2.GetAppTasksResponse;
import mesosphere.marathon.client.model.v2.GetAppsResponse;
import mesosphere.marathon.client.model.v2.GetServerInfoResponse;
import mesosphere.marathon.client.model.v2.GetTasksResponse;
import mesosphere.marathon.client.model.v2.Group;
import mesosphere.marathon.client.model.v2.Result;
import mesosphere.marathon.client.utils.AppIdNormalizer;
import mesosphere.marathon.client.utils.MarathonException;
import feign.RequestLine;

public interface Marathon {
    // Apps
	@RequestLine("GET /v2/apps")
	GetAppsResponse getApps();

	@RequestLine("GET /v2/apps?cmd={cmd}&id={id}&label={labelSelector}")
	GetAppsResponse filterApps(@Param("cmd") String command,
			@Param(value = "id", expander = AppIdNormalizer.class) String id,
			@Param("labelSelector") String labelSelector);

	@RequestLine("GET /v2/apps/{id}")
	GetAppResponse getApp(@Param(value = "id", expander = AppIdNormalizer.class) String id) throws MarathonException;

	@RequestLine("GET /v2/apps/{id}/tasks")
	GetAppTasksResponse getAppTasks(@Param(value = "id", expander = AppIdNormalizer.class) String id);

	@RequestLine("GET /v2/tasks")
	GetTasksResponse getTasks();

	@RequestLine("POST /v2/apps")
	App createApp(App app) throws MarathonException;

	@RequestLine("PUT /v2/apps/{app_id}?force={force}")
	void updateApp(@Param(value = "app_id", expander = AppIdNormalizer.class) String appId, App app,
                   @Param("force") boolean force) throws MarathonException;

	@RequestLine("POST /v2/apps/{id}/restart?force={force}")
	void restartApp(@Param(value = "id", expander = AppIdNormalizer.class) String id,
                    @Param("force") boolean force);

	@RequestLine("DELETE /v2/apps/{id}")
	Result deleteApp(@Param(value = "id", expander = AppIdNormalizer.class) String id) throws MarathonException;

	@RequestLine("DELETE /v2/apps/{app_id}/tasks?host={host}&scale={scale}")
	DeleteAppTasksResponse deleteAppTasks(@Param(value = "app_id", expander = AppIdNormalizer.class) String appId,
			@Param("host") String host, @Param("scale") String scale);

	@RequestLine("DELETE /v2/apps/{app_id}/tasks/{task_id}?scale={scale}")
	DeleteAppTaskResponse deleteAppTask(@Param(value = "app_id", expander = AppIdNormalizer.class) String appId,
			@Param("task_id") String taskId, @Param("scale") String scale);

    // Groups
	@RequestLine("POST /v2/groups")
	Result createGroup(Group group) throws MarathonException;

	@RequestLine("PUT /v2/groups/{id}")
	Result updateGroup(@Param("id") String id, Group group, @Param("force") boolean force,
			@Param("dryRun") boolean dryRun) throws MarathonException;

	@RequestLine("DELETE /v2/groups/{id}")
	Result deleteGroup(@Param("id") String id) throws MarathonException;

	@RequestLine("GET /v2/groups/{id}")
	Group getGroup(@Param("id") String id) throws MarathonException;

    // Tasks

    // Deployments
	@RequestLine("GET /v2/deployments")
	List<Deployment> getDeployments();
	
	@RequestLine("DELETE /v2/deployments/{deploymentId}")
	void cancelDeploymentAndRollback(@Param("deploymentId") String id);
	
	@RequestLine("DELETE /v2/deployments/{deploymentId}?force=true")
	void cancelDeployment(@Param("deploymentId") String id);

    // Event Subscriptions

    // Queue

    // Server Info
    @RequestLine("GET /v2/info")
    GetServerInfoResponse getServerInfo();

    // Miscellaneous
}

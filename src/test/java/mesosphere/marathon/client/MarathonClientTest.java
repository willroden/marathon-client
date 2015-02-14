package mesosphere.marathon.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.rule.MockWebServerRule;

import mesosphere.marathon.client.model.v2.App;
import mesosphere.marathon.client.model.v2.HealthCheck;
import mesosphere.marathon.client.utils.MarathonException;
import okio.Buffer;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class MarathonClientTest {

    @Rule
    public final MockWebServerRule server = new MockWebServerRule();

    protected Marathon marathon;

    @Before
    public void before() {
        marathon = MarathonClient.getInstance("http://localhost:" + server.getPort());
    }

    @Test
    public void testGetApp() throws IOException, MarathonException {
        server.enqueue(new MockResponse().setBody(getBufferFromResourceFile("marathon-sample-app.json")));
        App app = marathon.getApp("foo").getApp();
        assertThat(app.getInstances(), equalTo(3));
        assertThat(app.getContainer().getDocker().getImage(), equalTo("group/image"));
        assertThat(app.getHealthChecks().size(), equalTo(3));
        List<HealthCheck> healthChecks = new ArrayList<HealthCheck>(app.getHealthChecks());
        assertThat(healthChecks, (Matcher) hasItem(hasProperty("path", is("/health"))));
    }

    public Buffer getBufferFromResourceFile(String resourceFileName) throws IOException {
        return new Buffer().readFrom(this.getClass().getResourceAsStream(resourceFileName));
    }

}

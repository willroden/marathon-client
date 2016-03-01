package mesosphere.marathon.client.model.v2;

import java.util.Collection;
import java.util.List;

import mesosphere.marathon.client.utils.ModelUtils;

public class Docker {
	private String image;
	private String network;
	private Collection<Port> portMappings;
    private Boolean forcePullImage;
	private List<Parameter> parameters;
	private boolean privileged;

    public Boolean getForcePullImage() {
        return forcePullImage;
    }

    public void setForcePullImage(Boolean forcePullImage) {
        this.forcePullImage = forcePullImage;
    }

    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public Collection<Port> getPortMappings() {
		return portMappings;
	}

	public void setPortMappings(Collection<Port> portMappings) {
		this.portMappings = portMappings;
	}

	public boolean isPrivileged() {
		return privileged;
	}

	public void setPrivileged(boolean privileged) {
		this.privileged = privileged;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}

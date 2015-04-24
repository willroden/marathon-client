package mesosphere.marathon.client.utils;

import feign.Param;

import java.util.Objects;

/**
 * Removes "/" prefix that is commonly used in Marathon app id.
 */
public class AppIdNormalizer implements Param.Expander {
    @Override
    public String expand(Object o) {
        String id = Objects.requireNonNull(o).toString().trim();
        if (id.startsWith("/")) return id.substring(1);
        return id;
    }
}

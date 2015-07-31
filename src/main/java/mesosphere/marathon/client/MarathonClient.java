package mesosphere.marathon.client;

import mesosphere.marathon.client.utils.MarathonException;
import mesosphere.marathon.client.utils.ModelUtils;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

public final class MarathonClient {
	private MarathonClient() {
	}

	static final class MarathonHeadersInterceptor implements RequestInterceptor {
		@Override
		public void apply(RequestTemplate template) {
			template.header("Accept", "application/json");
			if (requestSendsBody(template)) {
				template.header("Content-Type", "application/json");
			}
		}

		private static boolean requestSendsBody(RequestTemplate template) {
			return requestMethodSendsBody(template.method());
		}

		private static boolean requestMethodSendsBody(String method) {
			// RFC 2616 §5.1.1: Method tokens are case-sensitive.
			switch (method) {
			case "POST":
			case "PUT":
				return true;
			default:
				return false;
			}
		}
	}

	static final class MarathonErrorDecoder implements ErrorDecoder {
		@Override
		public Exception decode(String methodKey, Response response) {
			return new MarathonException(response.status(), response.reason());
		}
	}

	public static Marathon getInstance(String endpoint) {
		GsonDecoder decoder = new GsonDecoder(ModelUtils.GSON);
		GsonEncoder encoder = new GsonEncoder(ModelUtils.GSON);
		return Feign.builder().encoder(encoder).decoder(decoder)
				.errorDecoder(new MarathonErrorDecoder())
				.requestInterceptor(new MarathonHeadersInterceptor())
				.target(Marathon.class, endpoint);
	}
}

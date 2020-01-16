package br.com.rodrigoaccorsi.routes.response;

public final class ResponseBuilder {

	private String message;
	private Boolean success;
	private Integer responseCode;
	private Object object;

	public ResponseBuilder withMessage(String message) {
		this.message = message;
		return this;
	}

	public ResponseBuilder withSuccess(Boolean success) {
		this.success = success;
		return this;
	}

	public ResponseBuilder withResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
		return this;
	}

	public ResponseBuilder withObject(Object object) {
		this.object = object;
		return this;
	}

	public Response build() {
		return new Response(message, success, responseCode, object);
	}
}

package br.com.rodrigoaccorsi.routes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.rodrigoaccorsi.routes.response.Response;

public class RoutesResponse {

	private Response response;

	public void setResponse(Response response, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		this.response = response;
		httpResponse.setStatus(this.response.getResponseCode());
	}

	public Response getResponse() {
		return this.response;
	}
}

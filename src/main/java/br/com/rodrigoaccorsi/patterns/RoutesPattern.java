package br.com.rodrigoaccorsi.patterns;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import br.com.rodrigoaccorsi.routes.response.Response;

public interface RoutesPattern {

	public @ResponseBody Response handleCreate(@RequestBody String json, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException;

	public @ResponseBody Response handleGetAll(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException;
}

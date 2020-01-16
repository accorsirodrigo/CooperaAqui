package br.com.rodrigoaccorsi.routes;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.rodrigoaccorsi.DAO.AgendaDAO;
import br.com.rodrigoaccorsi.model.Agenda;
import br.com.rodrigoaccorsi.patterns.RoutesPattern;
import br.com.rodrigoaccorsi.routes.response.Response;
import br.com.rodrigoaccorsi.routes.response.ResponseBuilder;
import br.com.rodrigoaccorsi.routes.util.RoutesUtils;
import br.com.rodrigoaccorsi.validation.CPFValidation;

@RestController
@RequestMapping("agenda")
public class AgendaRoute implements RoutesPattern {

	@Autowired
	private AgendaDAO agendaDao;

	@Autowired
	private CPFValidation cpf;

	@RequestMapping(
			value = "/create", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleCreate(@RequestBody String json, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		Agenda agenda = RoutesUtils.castStrJsonToObject(json, Agenda.class);
		agendaDao.createEntry(new Gson().toJson(agenda));
		return new ResponseBuilder().build();
	}

	@RequestMapping(
			value = "/all", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleGetAll(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		httpResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
		Response response = agendaDao.getAll();
		return response;
	}

}

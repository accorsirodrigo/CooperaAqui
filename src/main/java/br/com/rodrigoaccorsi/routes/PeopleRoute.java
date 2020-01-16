package br.com.rodrigoaccorsi.routes;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.rodrigoaccorsi.DAO.PeopleDAO;
import br.com.rodrigoaccorsi.model.People;
import br.com.rodrigoaccorsi.patterns.RoutesPattern;
import br.com.rodrigoaccorsi.routes.response.Response;
import br.com.rodrigoaccorsi.routes.response.ResponseBuilder;
import br.com.rodrigoaccorsi.routes.util.RoutesUtils;
import br.com.rodrigoaccorsi.validation.CPFValidation;

@RestController
@RequestMapping("people")
public class PeopleRoute implements RoutesPattern{

	@Autowired
	private PeopleDAO peopleDao;

	@Autowired
	private CPFValidation cpfValidation;

	@RequestMapping(
			value = "/create", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleCreate(@RequestBody String json, HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		People people = RoutesUtils.castStrJsonToObject(json, People.class);
		Response response;
		String validateResponse = cpfValidation.checkCpfIsAbleToVote(people.getCPF());
		
		if(validateResponse.equalsIgnoreCase("ABLE_TO_VOTE")) {
			peopleDao.createEntry(new Gson().toJson(people));
			response = new ResponseBuilder()
					.withMessage("User sussefull created")
					.withResponseCode(HttpStatus.SC_CREATED)
					.build();
		} else if(validateResponse.equalsIgnoreCase("UNABLE_TO_VOTE")) {
			response = new ResponseBuilder()
					.withMessage("User not authorized to vote")
					.withResponseCode(HttpStatus.SC_UNAUTHORIZED)
					.build();
		} else {
			response = new ResponseBuilder()
					.withMessage("CPF not found")
					.withResponseCode(HttpStatus.SC_NOT_FOUND)
					.build();
		}
		
		httpResponse.setStatus(response.getResponseCode());
		return response;
	}

	@RequestMapping(
			value = "/all", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Response handleGetAll(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		httpResponse.setStatus(HttpStatus.SC_ACCEPTED);

		Response response = peopleDao.getAll();
		return response;
	}

}

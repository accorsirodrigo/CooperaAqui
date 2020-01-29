package br.com.rodrigoaccorsi.start;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer implements ServletContextInitializer {

	public void onStartup(ServletContext container) throws ServletException {
		// XmlWebApplicationContext context = new XmlWebApplicationContext();
		// context.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

		// ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
		// new DispatcherServlet(context));

		// dispatcher.setLoadOnStartup(1);
		// dispatcher.addMapping("/");
	}
}

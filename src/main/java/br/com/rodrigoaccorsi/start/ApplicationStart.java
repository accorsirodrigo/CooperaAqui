package br.com.rodrigoaccorsi.start;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.rodrigoaccorsi")
@EntityScan(basePackages = "br.com.rodrigoaccorsi")
public class ApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { ApplicationStart.class, Initializer.class }, args);
	}
}
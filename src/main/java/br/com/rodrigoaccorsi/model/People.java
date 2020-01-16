package br.com.rodrigoaccorsi.model;

import br.com.rodrigoaccorsi.patterns.ModelDefaults;

public class People extends ModelDefaults{

	private String firstName;
	private String lastName;
	private String CPF;

	public People() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String CPF) {
		String regexPattern = "[-\\.]";
		CPF = CPF.replaceAll(regexPattern, "");
		this.CPF = CPF;
	}
}

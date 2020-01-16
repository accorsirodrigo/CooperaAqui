package br.com.rodrigoaccorsi.model;

import br.com.rodrigoaccorsi.patterns.ModelDefaults;

public class Agenda extends ModelDefaults{

	private String name;

	public Agenda(){
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

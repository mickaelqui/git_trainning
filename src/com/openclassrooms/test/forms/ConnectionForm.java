package com.openclassrooms.test.forms;

import javax.servlet.http.HttpServletRequest;

public class ConnectionForm {
	private String resultat;
	
	public void verifierIdentifiant(HttpServletRequest request) {
		String login = request.getParameter("login");
		String pwd = request.getParameter("pass");
		
		if(pwd.equals(login+"123")) {
			resultat = "Vous êtes bien connecté :";
		} else resultat = "Identifiant incorrect !";
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

}

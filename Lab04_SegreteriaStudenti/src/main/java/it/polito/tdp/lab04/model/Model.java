package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	CorsoDAO daoC = new CorsoDAO();
	StudenteDAO daoS = new StudenteDAO();

	public List<Corso> getTuttiICorsi() {
		return daoC.getTuttiICorsi();
	}

	public Studente getStudenteFromMatricola(Studente s) {
		return daoS.getStudenteFromMatricola(s);
	}

	public List<Studente> getStudentiByCorso(Corso corso) {
		return daoC.getStudentiIscrittiAlCorso(corso);
	}

	public List<Corso> getCorsiDelloStudente(Studente s) {
		return daoS.getCorsiDelloStudente(s);
	}
	public boolean isStudenteIscritto(Studente s, Corso c) {
		return daoS.isStudenteIscritto(s, c);
	}
}

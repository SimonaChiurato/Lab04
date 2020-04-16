package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {


	
	public Studente getStudenteFromMatricola(Studente s) {
		final String sql = "SELECT * FROM studente WHERE matricola=?";
		Studente studente = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("CDS"));
			}
			conn.close();
			return studente;
		} catch (SQLException e) {
			throw new RuntimeException("Errore db", e);
		}

	}

	public List<Corso> getCorsiDelloStudente(Studente s) {
		String sql = "SELECT c.codins,c.crediti,c.nome,c.pd FROM corso AS c, iscrizione AS i WHERE i.matricola=? AND c.codins=i.codins";
		List<Corso> corsi = new LinkedList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				corsi.add(new Corso(codins,numeroCrediti,nome,periodoDidattico));
			}

			conn.close();

			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	public boolean isStudenteIscritto(Studente s, Corso c) {
		String sql = "SELECT * FROM iscrizione WHERE matricola= ? AND codins=? ";
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			st.setString(2, c.getCodins());
			ResultSet rs = st.executeQuery();
		
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
			


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}	
	}
}

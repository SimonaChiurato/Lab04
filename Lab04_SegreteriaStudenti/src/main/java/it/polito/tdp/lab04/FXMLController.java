package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;

import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	private List<Corso> corsi;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Corso> comboBox;

	@FXML
	private Button btnCercaIscritti;

	@FXML
	private TextField txtMatricola;

	@FXML
	private Button btnVerde;

	@FXML
	private Button btnCercaCorsi;

	@FXML
	private Button bntIscrivi;

	@FXML
	private TextArea txtResult;

	@FXML
	private Button btnReset;
   @FXML
	    private TextField txtNome;

	    @FXML
	    private TextField txtCognome;
	@FXML
	void addNomeCognome(ActionEvent event) {
		this.txtResult.clear();
		this.txtCognome.clear();
		this.txtNome.clear();
		String matricolaStringa= this.txtMatricola.getText();
		
		
		if(matricolaStringa.length()!=6 || matricolaStringa.equals("") || !matricolaStringa.matches("[^a-zA-Z\\\\=.,\\\\?\\\\/#!$%\\\\^$\\\\*;:{}\\\\-\\\\_()\\\\[\\\\]\\\"]*")) {
			this.txtResult.setText("Devi inserire una matricola numerica di 6 caratteri");
			this.txtMatricola.clear();
			return;
		}
		Studente studente= this.model.getStudenteFromMatricola(new Studente(Integer.parseInt(matricolaStringa),null,null,null));
		
		if(studente==null) {
			this.txtResult.setText("Non esiste uno studente con questa matricola");
			this.txtMatricola.clear();
			return;
		}
		this.txtNome.setText(studente.getNome());
		this.txtCognome.setText(studente.getCognome());
	 
	}

	@FXML
	void cercaCorsi(ActionEvent event) {
		this.txtResult.clear();
		String matricolaStringa= this.txtMatricola.getText();
		Corso corso= this.comboBox.getValue();
		
		if(matricolaStringa.length()!=6 || matricolaStringa.equals("") || !matricolaStringa.matches("[^a-zA-Z\\\\=.,\\\\?\\\\/#!$%\\\\^$\\\\*;:{}\\\\-\\\\_()\\\\[\\\\]\\\"]*")) {
			this.txtResult.setText("Devi inserire una matricola numerica di 6 caratteri");
			this.txtMatricola.clear();
			return;
		}
		Studente studente= this.model.getStudenteFromMatricola(new Studente(Integer.parseInt(matricolaStringa),null,null,null));
		if(studente==null) {
			this.txtResult.setText("Non esiste uno studente con questa matricola");
			this.txtMatricola.clear();
			return;
		}
		if(corso==null) {
		List<Corso> corsi =this.model.getCorsiDelloStudente(studente);
		if(corsi.size()==0) {
			this.txtResult.setText("Lo studente non frequenta alcun corso");
			return;
		}
		int n=1;
		for(Corso c:corsi) {
			this.txtResult.appendText(String.format("%3d", n)+". "+c.toStringCompleta()+"\n");
		}
		}else {
			
			if(this.model.isStudenteIscritto(studente, corso)) {
				this.txtResult.setText("Lo studente "+studente.getCognome()+" "+studente.getNome()+" è gia iscritto al corso "+corso.getNome());
			}else {
				this.txtResult.setText("Lo studente "+studente.getCognome()+" "+studente.getNome()+" non è iscritto al corso "+corso.getNome());
			}
		}
	}

	@FXML
	void cercaIscritti(ActionEvent event) {
		this.txtResult.clear();
		Corso c= this.comboBox.getValue();
		if(c==null) {
			this.txtResult.setText("Devi selezionare un corso!");
			return;
		}
		List<Studente> studenti= this.model.getStudentiByCorso(c);
		Collections.sort(studenti);
		int n=1;
		if(studenti.size()==0) {
			this.txtResult.setText("Il corso non contiene iscritti");
		}
		for(Studente s:studenti) {
			this.txtResult.appendText(String.format("%3d", n)+". "+s.toString()+"\n");
			n++;
		}
	}

	@FXML
	void doReset(ActionEvent event) {
		this.txtResult.clear();
	}

	@FXML
	void iscrivi(ActionEvent event) {
		this.txtResult.clear();
	}

	@FXML
	void initialize() {
		assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnVerde != null : "fx:id=\"btnVerde\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert bntIscrivi != null : "fx:id=\"bntIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		this.setComboItems();

	}

	private void setComboItems() {
	corsi = this.model.getTuttiICorsi();
		Collections.sort(corsi);
		this.comboBox.getItems().add(null);
		this.comboBox.getItems().addAll(corsi);
	}
}

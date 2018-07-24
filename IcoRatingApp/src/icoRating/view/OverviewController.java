package icoRating.view;

import java.beans.EventHandler;
import java.time.LocalDate;

import icoRating.MainApp;
import icoRating.model.Criteria;
import icoRating.model.Ico;
import icoRating.model.IcoCriteria;
import icoRating.util.Rating;
import icoRating.util.Weight;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.util.Callback;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Controller Class for the IcoOverview GUI.
 * @FXML ist anotation, damit fxml file zugriff auf die privaten felder und privaten methoden hat
 * @author beniw
 *
 */

/**
 * Variablen der Controller Klasse. die Variable "icoTable" ist vom Typ "TybleView aus der javafx.scene.control.TableView Klasse
 * https://docs.oracle.com/javafx/2/api/javafx/scene/control/TableView.html
 * @author beniw
 *
 */

public class OverviewController {
	
	/**
	 * Tab My ICO
	 */
	@FXML
	private TableView<Ico> icoTable;
	
	@FXML
	private TableColumn<Ico, String> icoTableNameColumn;
	
	@FXML
	private TableColumn<Ico, LocalDate> icoTableStartDateColumn;
	
	@FXML
	private TableColumn<Ico, LocalDate> icoTableEndDateColumn;
	
	@FXML
	private TableColumn<Ico, Float> icoTableInvestmentColumn;
	
	@FXML
	private TableColumn<Ico, Float> icoTableRatingColumn;

	
	/**
	 * Tab Rating Criteria
	 */
	@FXML
	private TableView<Criteria> criteriaTable;
	
	@FXML
	private TableColumn<Criteria, String> criteriaTableNameColumn;
	
	@FXML
	private TableColumn<Criteria, String> criteriaTableDescriptionColumn;
	
	@FXML
	private TableColumn <Criteria, String> criteriaTableCategoryColumn;
	
	@FXML
	private TableColumn <Criteria, String> criteriaTableWeightColumn;
	
	
	/**
	 * Wenn ich labels benutzen möchte:
	 * @fxml
	 * privat Label firstNameLabel;
	 */
	
	//Reference to the main application
	private MainApp mainApp;
	
	/**
	 * The constructor
	 * The constructor is called before the initialize() method.
	 */
	public OverviewController() {
	}
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		//Initialize the ico table with the columns
		//name column holt sich von der ico klasse den wert für name mit der name property methode
		//TODO: Nochmals genau nachvollziehen. Ist das Lambda?
		icoTableNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		icoTableStartDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		icoTableEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		//TODO: Wenn wert integer/float ist, muss ich noch "asObject" einfügen. - Wieso? mal im tutorial gucken
		icoTableInvestmentColumn.setCellValueFactory(cellData -> cellData.getValue().investmentProperty().asObject());
		icoTableRatingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
		//Programm weiss, dass die Properties von Criteria Klasse geholt werden weil in den Attributen ist das definiert (TableColumn<Criteria>)
		criteriaTableNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		criteriaTableDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		criteriaTableCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
		criteriaTableWeightColumn.setCellValueFactory(cellData -> cellData.getValue().getWeightAsStringProperty());
		
	}

	
	/**
	 * Is called by the main applicatin to give a reference back to itselv
	 * 
	 * @param mainApp
	 * TODO: Was bedeutet @param?
	 * setMainApp - Methodenname ; MainApp - Variablentyp ("Variable vom Typ Klasse "MainApp") ; mainApp - Variablenname
	 */
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		
		//add observable list data to the table
		mainApp.getIcoList().forEach(ico -> ico.calculateRating());
		icoTable.setItems(mainApp.getIcoList());
		
		icoTable.setRowFactory( tv -> {
		    TableRow<Ico> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            handleEditIco();
		        }
		    });
		    return row ;
		});
		
		criteriaTable.setItems(mainApp.getCriteriaList());
		
		criteriaTable.setRowFactory( tv -> {
		    TableRow<Criteria> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            handleEditCriteria();
		        }
		    });
		    return row ;
		});
	}
	
	/**
	 * Called when the user clicks on the delete ICO button
	 * holt sich den Index von der ausgewählten Reihe (ist ein Integer, daher int) und speichert ihn im selectedIndex. Nacher löscht er selected index
	 * Falls kein Eintrag ausgewählt (selectedIndex ist dann -1) -> Error Handling
	 */
	@FXML
	private void handleDeleteIco() {
		int selectedIndex = icoTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			icoTable.getItems().get(selectedIndex).getAllIcoCriteria().removeAll();
			icoTable.getItems().remove(selectedIndex);
		} else {
			//Nothing selected
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No ICO Selected");
			alert.setContentText("Please select an ICO to be deleted");
			
			alert.showAndWait();
		}
	}
	
	/**
	 * Called when the user clicks on the Edit ICO button
	 * holt sich den Index von der ausgewählten Reihe (ist ein Integer, daher int) und speichert ihn im selectedIndex. Nacher löscht er selected index
	 * Falls kein Eintrag ausgewählt (selectedIndex ist dann -1) -> Error Handling
	 */
	@FXML
	private void handleEditIco() {
		Ico selectedIco = icoTable.getSelectionModel().getSelectedItem();
		if (selectedIco != null) {
			boolean okClicked = mainApp.showIcoDialog(selectedIco);
			if (okClicked) {
			}
		} else {
			//Nothing selected
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No ICO selected");
			alert.setContentText("Please select an ICO to be edited");
					
			alert.showAndWait();
		}
	}
	
	
	/**
	 * Called, when the user clicks the Add New ICO Button. Opens a dialog to add a new ICO
	 * TODO: Methode noch genau studieren. Wartet bis ok clicked und speichert dann ICO oder so
	 */
	@FXML
	private void handleNewIco () {
		Ico tempIco = new Ico();
		mainApp.getCriteriaList().forEach(Criteria -> tempIco.addCriteria(new IcoCriteria(Criteria, tempIco)));
		
		//Der variablen "okClicked" wird der Wert vom Dialog "NewICO" zugewiesen. Wenn also dort okClicked dann ist auch hier okClicked
		boolean okClicked = mainApp.showIcoDialog(tempIco);
		if (okClicked) {
			mainApp.getIcoList().add(tempIco);
		}
	}
	
	@FXML
	private void handleDeleteCriteria() {
		int selectedIndex = criteriaTable.getSelectionModel().getSelectedIndex();
		Criteria selectedCriteria = criteriaTable.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			criteriaTable.getItems().remove(selectedIndex);
			mainApp.getIcoList().forEach(Ico -> Ico.removeIcoCriteria(selectedCriteria));
			mainApp.getIcoList().forEach(Ico -> Ico.calculateRating());
		} else {
			//Nothing selected
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Criteria Selected");
			alert.setContentText("Please select a Criteria to be deleted");
			
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleNewCriteria() {
		Criteria tempCriteria = new Criteria();
		boolean okClicked = mainApp.showCriteriaDialog(tempCriteria);
		if (okClicked) {
			mainApp.getCriteriaList().add(tempCriteria);
		}
	}
	
	@FXML
	private void handleEditCriteria() {
		Criteria selectedCriteria = criteriaTable.getSelectionModel().getSelectedItem();
		if (selectedCriteria != null) {
			boolean okClicked = mainApp.showCriteriaDialog(selectedCriteria);
			if (okClicked) {
			}
		} else {
			//Nothing selected
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Criteria selected");
			alert.setContentText("Please select an Criteria to be edited");
					
			alert.showAndWait();
		}
	}
	

	
	
}
 
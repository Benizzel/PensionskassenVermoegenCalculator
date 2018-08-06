package icoRating.view;

import java.time.LocalDate;

import icoRating.MainApp;
import icoRating.model.Criteria;
import icoRating.model.Ico;
import icoRating.model.IcoCriteria;
import icoRating.util.Weight;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller Class for the IcoOverview GUI.
 * The GUI provides an overview of all ICO and Criteria 
 * On the overview functionalities to add, edit and delete ICOs or Criterias are provided
 * @author Benjamin Wyss
 */
public class OverviewController {

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

	@FXML
	private TableView<Criteria> criteriaTable;
	@FXML
	private TableColumn<Criteria, String> criteriaTableNameColumn;
	@FXML
	private TableColumn<Criteria, String> criteriaTableDescriptionColumn;
	@FXML
	private TableColumn <Criteria, String> criteriaTableCategoryColumn;
	@FXML
	private TableColumn <Criteria, Weight> criteriaTableWeightColumn;
	
	
	private MainApp mainApp;
	
	/**
	 * Constructor
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
		icoTableNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		icoTableStartDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		icoTableEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		icoTableInvestmentColumn.setCellValueFactory(cellData -> cellData.getValue().investmentProperty().asObject());
		icoTableRatingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
		
		criteriaTableNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		criteriaTableDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		criteriaTableCategoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
		criteriaTableWeightColumn.setCellValueFactory(cellData -> cellData.getValue().WeightProperty());
	}

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		setData();
	}
	
	/**
	 * add observable list data and event handler to the ico and criteria table
	 * calculates rating of ico so that up to date rating is shown
	 */
	public void setData() {
		/*
		 * add observable list data and event handler to the ico table
		 * calculates rating of ICO so that up to date rating is shown
		 */
		mainApp.getIcoList().forEach(ico -> ico.calculateRating());
		icoTable.setItems(mainApp.getIcoList());
		icoTable.setRowFactory( tv -> {
		    TableRow<Ico> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
		            handleEditIco();
		        }
		    });
		    return row ;
		});
		
		//add observable list data and event handler to the criteria table
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
	 * Opens IcoDialog
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
	 * Called, when the user clicks the Add New ICO Button.
	 * Opens IcoDialog
	 */
	@FXML
	private void handleNewIco () {
		Ico tempIco = new Ico();
		mainApp.getCriteriaList().forEach(Criteria -> tempIco.addCriteria(new IcoCriteria(Criteria, tempIco)));
		boolean okClicked = mainApp.showIcoDialog(tempIco);
		
		if (okClicked) {
			mainApp.getIcoList().add(tempIco);
		}
	}
	
	/**
	 * Called, when the user clicks the Delete Criteria Button
	 * Deletes the selected Criteria and the related IcoCriteria
	 * Forces a recalculation of the ICO Rating
	 */
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
	
	/**
	 * Called when the user clicks on the Edit Criteria button
	 * Opens CriteriaDialog
	 */
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
	
	/**
	 * Called when the user clicks on the New button
	 * Opens CriteriaDialog
	 */
	@FXML
	private void handleNewCriteria() {
		Criteria tempCriteria = new Criteria();
		boolean okClicked = mainApp.showCriteriaDialog(tempCriteria);
		if (okClicked) {
			mainApp.getCriteriaList().add(tempCriteria);
		}
	}
}
 
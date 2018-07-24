package icoRating.view;

import javafx.scene.control.TextArea;

import icoRating.MainApp;
import icoRating.model.Criteria;
import icoRating.model.IcoCriteria;
import icoRating.util.Weight;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller Class for the NewCriteriaDialog GUI.
 * 
 * @author beniw
 *
 */

public class CriteriaDialogController {
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextArea descriptionField;
	
	@FXML
	private TextField categoryField;
	
	@FXML
	private ComboBox<Weight> cbWeight;
	
	
	private Stage dialogStage;
	private MainApp mainApp;
	private Criteria criteria;
	private boolean okClicked = false;

	public void initialize(Criteria criteria) {
		setCriteria(criteria);
		setWeight();
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		dialogStage.setMaxHeight(440);
		dialogStage.setMaxWidth(1200);
	}
	
	private void setCriteria (Criteria criteria) {
		this.criteria = criteria;
		nameField.setText(criteria.getName());
		descriptionField.setText(criteria.getDescription());
		categoryField.setText(criteria.getCategory());
		cbWeight.setValue(criteria.getWeight());
	}
	
	public void setMainApp (MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void setWeight () {
		cbWeight.getItems().setAll(Weight.values());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Called when user clicks ok. Makes an input validation and saves the criteria
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			criteria.setName(nameField.getText());
			criteria.setDescription(descriptionField.getText());
			criteria.setCategory(categoryField.getText());	
			criteria.setWeight(cbWeight.getValue());
			okClicked = true;
			dialogStage.close();
		}
		
		//Creates for each Ico an IcoCriteria and adds it to the Ico
		mainApp.getIcoList().forEach(Ico -> {
			 boolean hasIt = false;
	           for (final IcoCriteria c : Ico.getAllIcoCriteria()) {
	               if (c.getCriteria().getUuid().equals(criteria.getUuid())) {
	                   hasIt = true;
	               }
	           }
	           if (!hasIt) {
	               Ico.addCriteria(new IcoCriteria(criteria, Ico));
	           }
		});
	}
	
	/**
	 * Called when user clicks cancel
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	/**
	 * Validate the user input in the fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid name!\n";
		}
		
		if (cbWeight.getValue() == null)   {
			errorMessage += "No valid weight!\n";
		}
		
		if (errorMessage.length() == 0) {
			return true;
		} else {
			//show error message
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("InvalidFields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
			
		}
	}
}

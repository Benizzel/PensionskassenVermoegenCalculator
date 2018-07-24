package icoRating.view;

import icoRating.MainApp;
import icoRating.model.Criteria;
import icoRating.model.Ico;
import icoRating.model.IcoCriteria;
import icoRating.util.Rating;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;



/**
 * Controller Class for the EditIcoDialog GUI.
 *
 * @author beniw
 *
 */

public class IcoDialogController {
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextArea descriptionField;
	
	@FXML
	private DatePicker startDateField;
	
	@FXML
	private DatePicker endDateField;
	
	@FXML
	private TextField investmentField;
	
	@FXML
	private Text rating;
	
	@FXML
	private TableView<IcoCriteria> icoCriteriaTable;
	
	@FXML
	private TableColumn<IcoCriteria, Boolean> icoCriteriaActiveColumn;
	
	@FXML
	private TableColumn<IcoCriteria, String> icoCriteriaNameColumn;
	
	@FXML
	private TableColumn<IcoCriteria, String> icoCriteriaDescriptionColumn;
	
	@FXML
	private TableColumn<IcoCriteria, Rating> icoCriteriaRatingColumn;
	
	
	
	//@FXML
	//private TableColumn<Criteria, CheckBox> icoCriteriaTableSelectColumn; 
	
	
	private Stage dialogStage;
	private Ico ico;
	private boolean okClicked = false;
	private MainApp mainApp;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded
	 */
	public void initialize(Ico ico) {
		setIco(ico);
		setIcoCriteria(ico);
		setRatingColor();		
	}
	
	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	} 
	
	private void setRatingColor() {
		float r = ico.getRating();
		rating.setText("Rating: " + String.valueOf(ico.getRating())); 
		if (r >= 4) {
			rating.setFill(Color.GREEN);
		}
		else if (r < 4 && r >= 2) {
			rating.setFill(Color.ORANGE);
		}
		else if (r < 2 && r > 0) {
			rating.setFill(Color.RED);
		} 
		else {
			rating.setFill(Color.BLACK);
		}
	}
	
	private void setIco (Ico ico) {
		this.ico = ico;
				
		nameField.setText(ico.getName());
		descriptionField.setText(ico.getDescription());
		startDateField.setValue(ico.getStartDate());
		endDateField.setValue(ico.getEndDate());
		investmentField.setText(Float.toString(ico.getInvestment()));
		rating.setText("Rating: " + String.valueOf(ico.getRating()));
		ico.ratingProperty().addListener(new ChangeListener<Object>() { 
            @Override 
            public void changed(ObservableValue<?> o, Object oldVal, Object newVal) { 
            	setRatingColor();
            }
		});
	}

	private void setIcoCriteria (Ico ico) {
		//creates ico criteria if it is a new ico
		
		icoCriteriaTable.setEditable(true);
		
		icoCriteriaTable.setItems(ico.getAllIcoCriteria());
//		icoCriteriaActiveColumn.setCellValueFactory(cellData -> cellData.getValue().isActive());
		icoCriteriaNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCriteria().nameProperty());
		icoCriteriaDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getCriteria().descriptionProperty());
		ObservableList<Rating> ratingList = FXCollections.observableArrayList(Rating.values());		
		
		//setzt den Rating Wert vom ICO Criteria
		icoCriteriaRatingColumn.setCellValueFactory(new Callback<CellDataFeatures<IcoCriteria, Rating>, ObservableValue<Rating>>() {
            
        	@Override
            public ObservableValue<Rating> call(CellDataFeatures<IcoCriteria, Rating> param) {
              IcoCriteria criteria = param.getValue();
              if (criteria.getRating() != null) {
            	  Integer ratingCode = criteria.getRating();
            	  Rating rating = Rating.getByCode(ratingCode);
            	  return new SimpleObjectProperty<Rating>(rating);
            	  
              } else {
    			//Not rated - Rating auf 0 setzen
            	  criteria.setRating(Rating.NOTRATED);
            	  Integer ratingCode = criteria.getRating();
            	  Rating rating = Rating.getByCode(ratingCode);
            	  return new SimpleObjectProperty<Rating>(rating);
              }
            }
        });
		
		icoCriteriaRatingColumn.setCellFactory(ComboBoxTableCell.forTableColumn(ratingList));
		
		/*
		 * Geh�rt das in die seIcoCriteria Methode?
		 */
		
        icoCriteriaRatingColumn.setOnEditCommit((CellEditEvent<IcoCriteria, Rating> event) -> {
            TablePosition<IcoCriteria, Rating> pos = event.getTablePosition();
         	Rating newRating = event.getNewValue();
		    int row = pos.getRow();
        	IcoCriteria criteria = event.getTableView().getItems().get(row);
		    criteria.setRating(newRating);
		    ico.calculateRating();
		});
        
        icoCriteriaActiveColumn.setCellValueFactory(new Callback<CellDataFeatures<IcoCriteria, Boolean>, ObservableValue<Boolean>>() {
        	@Override
        	public ObservableValue<Boolean> call(CellDataFeatures<IcoCriteria, Boolean> param) {
        		IcoCriteria criteria = param.getValue();
        		SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(criteria.isActive());
        		booleanProp.addListener(new ChangeListener<Boolean>() {
        			@Override
        			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        				criteria.setActive(newValue);
        			}
        		});
        		return booleanProp;
        	}
        });
        
        icoCriteriaActiveColumn.setCellFactory(new Callback<TableColumn<IcoCriteria, Boolean>, TableCell<IcoCriteria, Boolean>>() {
        	@Override
        	public TableCell<IcoCriteria, Boolean> call(TableColumn<IcoCriteria, Boolean> active) {
        		CheckBoxTableCell<IcoCriteria, Boolean> cell = new CheckBoxTableCell<IcoCriteria, Boolean>();
        		cell.setAlignment(Pos.CENTER);
        		return cell;
        	}
        });
	}
	
	/**
	 * Returns true if the user clicked OK, false otherwise
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleActivateAll() {
		Ico ico = this.ico;
		ico.getAllIcoCriteria().forEach(Criteria -> Criteria.setActive(true));
		setIcoCriteria(ico);
		ico.calculateRating();
	}
	
	@FXML
	private void handleInactivateAll() {
		Ico ico = this.ico;
		ico.getAllIcoCriteria().forEach(Criteria -> Criteria.setActive(false));
		setIcoCriteria(ico);
		ico.calculateRating();
	}
	
	@FXML
	private void handleInfoButton() {
		String infoMessage = "Rate your ICO by doubleclick into the Rating column.\n\n" + "The calculated weighted overall rating of the active criteria is shown at the upper right corner.";
		Alert info = new Alert(AlertType.INFORMATION);
		info.initOwner(dialogStage);
		info.setTitle("Rating");
		info.setHeaderText("How to rate an Ico");
		info.setContentText(infoMessage);
		
		info.showAndWait();
		
	}
	
	/**
	 * Called when the user clicks ok. Makes an input validation and saves the Ico
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			ico.setName(nameField.getText());
			ico.setDescription(descriptionField.getText());
			ico.setStartDate(startDateField.getValue());
			ico.setEndDate(endDateField.getValue());
			ico.setInvestment(Float.parseFloat(investmentField.getText()));
			ico.calculateRating();
						
			okClicked = true;
			dialogStage.close();	
		}	
	}
	
	/**
	 * Called when the user clicks cancel
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	/**
	 * Validates the user input in the fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid name!\n";
		}
			
		if (investmentField.getText() == null || investmentField.getText().length() == 0) {
				errorMessage += "No valid investment amount!\n";
		} else {
				// try to parse the Investment amount into a float.
			try {
					Float.parseFloat(investmentField.getText());
			} catch (NumberFormatException e) {
					errorMessage += "No valid investment amount (must be a number)";
			}
		}
		
		if (errorMessage.length() == 0) {
			return true;
			
		} else {
			//show the error message
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
		}
	}
}

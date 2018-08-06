package icoRating.view;

import java.io.File;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import icoRating.MainApp;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Benjamin Wyss
 */
public class RootLayoutController {

    private MainApp mainApp;
    private Stage primaryStage;

    /**
     * Is called by the main application to give a reference back to itself.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Is called by the main application to give a reference to the Stage
     * @param primaryStage	the current Stage
     */
    public void setPrimaryStage (Stage primaryStage) {
    	this.primaryStage = primaryStage;
    }

    /**
     * Creates an empty portfolio.
     */
    @FXML
    private void handleNew() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("IcoRatingApp");
        alert.setHeaderText("New Portfolio");
        alert.setContentText("New Portfolio will be created. \n" + "Do you want to save changes at current portfolio?");
        ButtonType buttonTypeOne = new ButtonType("Save & New");
        ButtonType buttonTypeTwo = new ButtonType("Discard Changes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            handleSave();
            mainApp.getCriteriaList().clear();
            mainApp.getIcoList().clear();
            mainApp.setFilePath(null);
            alert.close();
        } else if (result.get() == buttonTypeTwo) {
        	mainApp.getCriteriaList().clear();
            mainApp.getIcoList().clear();
            mainApp.setFilePath(null);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    /**
     * Opens a FileChooser to let the user select a Portfolio to load.
     * Asks to save changes at the current Portfolio
     */
    @FXML
    private void handleOpen() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("IcoRatingApp");
        alert.setHeaderText("Save current Portfolio");
        alert.setContentText("Do you want to save changes?");
        ButtonType buttonSave = new ButtonType("Save");
        ButtonType buttonDiscard = new ButtonType("Discard Changes");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonSave, buttonDiscard, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonCancel) {
        	alert.close();
        } else if (result.get() != buttonCancel) {
        	
        	if (result.get() == buttonSave) {
        		handleSave();
        	}
        	
        	final FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show open file dialog
            final File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

            if (file != null) {
                mainApp.loadPortfolioDataFromFile(file);
            }        	
        }
    }

    /**
     * Saves the file to the portfolio file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
    	if (isPortfolioValid()) {
    	
    		final File personFile = mainApp.getFilePath();
    		if (personFile != null) {
    			mainApp.savePortfolioDataToFile(personFile);
    		} else {
    			handleSaveAs();
    		}
    	}
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
    	if (isPortfolioValid()) {
    	
    		final FileChooser fileChooser = new FileChooser();

    		// Set extension filter
    		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
    		fileChooser.getExtensionFilters().add(extFilter);

    		// Show save file dialog
    		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

    		if (file != null) {
            // Make sure it has the correct extension
    			if (!file.getPath().endsWith(".xml")) {
    				file = new File(file.getPath() + ".xml");
    			}
            mainApp.savePortfolioDataToFile(file);
    		}
    	}
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("IcoRatingApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Benjamin Wyss");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("IcoRatingApp");
        alert.setHeaderText("Exit");
        alert.setContentText("Do you want to save changes?");
        ButtonType buttonTypeOne = new ButtonType("Save");
        ButtonType buttonTypeTwo = new ButtonType("Discard Changes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            handleSave();
            alert.close();
            System.exit(0);
        } else if (result.get() == buttonTypeTwo) {
        	System.exit(0);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    /**
     * A portfolio must consists of at least one Ico and one Criteria.
     * Without that, loading existing data may throws a nullPointer.
     * @return		true if the portfolio is sufficient
     */
    
    private boolean isPortfolioValid() {
		String errorMessage = "";
		
		if (mainApp.getIcoList().isEmpty()) {
			errorMessage += "Please define at least one ico\n";
		}
			
		if (mainApp.getCriteriaList().isEmpty()) {
				errorMessage += "Please define at least one criteria\n";
		} 
		
		if (errorMessage.length() == 0) {
			return true;
			
		} else {
			//show the error message
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(primaryStage);
			alert.setTitle("No sufficient Portfolio.");
			alert.setHeaderText("No sufficient Portfolio. Could not save data.");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
		}
    }	
}
package icoRating;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import icoRating.model.Criteria;
import icoRating.model.Ico;
import icoRating.model.PortfolioWrapper;
import icoRating.view.IcoDialogController;
import icoRating.view.OverviewController;
import icoRating.view.RootLayoutController;
import icoRating.view.CriteriaDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Start the IcoRatingApp 
 * @author Benjamin Wyss
 */
public class MainApp extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    private final ObservableList<Criteria> criteriaList = FXCollections.observableArrayList();
    private final ObservableList<Ico> icoList = FXCollections.observableArrayList();
    
    /**
     * Default Constructor
     */
    public MainApp() {
    }
        
    /**
     * Returns all Criteria
     * @return List of all Criteria
     */
    public ObservableList<Criteria> getCriteriaList() {
        	return criteriaList;
        }
    
    /**
     * Returns all ICOs
     * @return List of ICOs
     */
    public ObservableList<Ico> getIcoList() {
        	return icoList;
    }
       	
    /**
     * Implements Application start and sets the App Icon
     * @param primaryStage Stage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IcoRatingApp");
        this.primaryStage.getIcons().add(new Image("file:resources/images/RocketMoon.png"));
        initRootLayout();
        showOverview();
    }  
    
    @Override
    public void stop(){

    	    System.out.println("Stage is closing");
    	    // Save file
    	}
        // Save file
    
    
    /**
     * Initializes the root layout and tries to load the last opened
     * portfolio file.
     * @throws Info Alert if last opened file cannot be found
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
                       
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPrimaryStage(primaryStage);
            
            // Catches Window Closing Event
            primaryStage.setOnCloseRequest(event -> {
            	event.consume();
            	controller.handleExit();
            });
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getFilePath();
        if (file != null && file.exists()) {
            loadPortfolioDataFromFile(file);
        } else {
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Portfolio");
    		alert.setHeaderText("Could not find last Portfolio");
    		alert.setContentText(
        		"Could not find an existing Portfolio.\n\n" +
//    			Attention: Do not use file.getPath because at initial installation there is no filePath which results in a nullPointer
//        		file.getPath() + "\n\n" + 
        		"A new Portfolio will be created.\n\n" +
        		"If you want to open an existing Portfolio use Menu 'File'->'Open'\n\n"
    		); 
    		alert.showAndWait();
        }
    }
    

    /**
     * Shows the Ico overview inside the root layout.
     */
    public void showOverview() {
    	try {
    		//Load ico overview
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApp.class.getResource("view/Overview.fxml"));
    		AnchorPane icoOverview = (AnchorPane) loader.load();
    		
    		//set ico overview into the center of root layout.
    		rootLayout.setCenter(icoOverview);
    		
    		//Give the controller access to the main app.
    		OverviewController controller = loader.getController();
    		controller.setMainApp(this);
    		controller.setData();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
   
    /**
     * Shows the Ico Dialog for edit an existing Ico or adding a new
     * @param ico Ico to be edited or new instanced Ico
     * @return true when ok clicked 
     */
    public boolean showIcoDialog (Ico ico) {
    	try {
    		//Load the fxml file and create a new stage for the popup dialog.
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApp.class.getResource("view/IcoDialog.fxml"));
    		AnchorPane page = (AnchorPane) loader.load();
    		
    		//Create the dialog Stage
    		Stage dialogStage = new Stage();
    		dialogStage.setTitle("Edit ICO");
    		dialogStage.initModality(Modality.WINDOW_MODAL);
    		dialogStage.getIcons().add(new Image("file:resources/images/RocketMoon.png"));
    		dialogStage.initOwner(primaryStage);
    		Scene scene = new Scene(page);
    		dialogStage.setScene(scene);
    		
    		//Show the dialog Stage and set controller
    		IcoDialogController controller = loader.getController();
    		controller.setDialogStage(dialogStage);
    		controller.initialize(ico);
    		//Show the dialog and wait until the user closes it
    		dialogStage.showAndWait();
    		return controller.isOkClicked();
    	} catch (IOException e) {
    		e.printStackTrace();
    		 Alert alert = new Alert(AlertType.ERROR);
    	     alert.setTitle("Error");
    	     alert.setHeaderText("Error occurd");
    	     alert.setContentText("Error Message:\n" + e);
    	     alert.showAndWait();
    		return false;
    	}
    } 
    
    /**
     * Shows the Criteria Dialog for edit an existing Ico or adding a new
     * @param criteria Criteria to be edited or new instanced Criteria
     * @return true when ok clicked 
     */
    
    public boolean showCriteriaDialog (Criteria criteria) {
    	try {
    		//Load the fxml file and create a new stage for the popup dialog.
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApp.class.getResource("view/CriteriaDialog.fxml"));
    		AnchorPane page = (AnchorPane) loader.load();
    		
    		//Create the dialog Stage
    		Stage dialogStage = new Stage();
    		dialogStage.setTitle("New Criteria");
    		dialogStage.initModality(Modality.WINDOW_MODAL);
    		dialogStage.getIcons().add(new Image("file:resources/images/RocketMoon.png"));
    		dialogStage.initOwner(primaryStage);
    		Scene scene = new Scene(page);
    		dialogStage.setScene(scene);
    		
    		//Show the dialog Stage and set controller
    		CriteriaDialogController controller = loader.getController();
    		controller.setDialogStage(dialogStage);
    		controller.initialize(criteria);
    		controller.setMainApp(this);

    		
    		//Show the dialog and wait until the user closes it
    		dialogStage.showAndWait();
    		
    		return controller.isOkClicked();
    	} catch (IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }    

    /**
     * @return	the main stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * @return file preference or null
     */
    public File getFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("IcoRatingApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("IcoRatingApp");
        }
    }
    
	/**
	 * Loads Portfolio data from the specified file. The current data will
	 * be replaced.
	 * @param file
	 */
	public void loadPortfolioDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PortfolioWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			PortfolioWrapper wrapper = (PortfolioWrapper) um.unmarshal(file);

			criteriaList.clear();
			criteriaList.addAll(wrapper.getCriterion());

			icoList.clear();
			icoList.addAll(wrapper.getIcos());

			// Save the file path to the registry.
			setFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error loading data");
			alert.setHeaderText("Could not load data from file.");
			alert.setContentText("File is not valid. Please chose another one.\n\n" + "Error Message:\n\n" + e);
			
			// Delete file path when exception occurs while opening
			Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
			prefs.remove("filePath");
			primaryStage.setTitle("IcoRatingApp");
			
		    alert.showAndWait();
		  }	    
	}

	/**
	 * Saves the current Portfolio data to the specified file. 
	 * @param file
	 */
	public void savePortfolioDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PortfolioWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our criteria data.
			PortfolioWrapper wrapper = new PortfolioWrapper();
			wrapper.setCriterion(criteriaList);
			wrapper.setIcos(icoList);
			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath() + "\n\n" + e);
			
			alert.showAndWait();
		}
	}
	
    /**
     * launches the app
     * @param args
     */
    
    public static void main(String[] args) {
        launch(args);
    }
}
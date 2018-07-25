package icoRating;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import icoRating.model.Criteria;
import icoRating.model.Ico;
import icoRating.model.IcoCriteria;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Start the ico rating app
 * @author BeniW
 */


public class MainApp extends Application {
	
	/**
	 * Variables
	 */

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * The data as an observable list of ICOs and Criteria. Variable Typ ist ObservableList (JavaFX Klasse) vom Typ "Ico" (meine Klasse)
     */
    //private ObservableList<Ico> icoData = FXCollections.observableArrayList();
    private ObservableList<Criteria> criteriaList = FXCollections.observableArrayList();
    private ObservableList<Ico> icoList = FXCollections.observableArrayList();
 //   private ObservableList<IcoCriteria> icoCriterionList = FXCollections.observableArrayList();
    private Ico ico;
    
    /**
     * Constructor
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
       	
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IcoRatingApp");
        this.primaryStage.getIcons().add(new Image("file:resources/images/RocketMoon.png"));
        initRootLayout();
        showIcoOverview();
    }
    
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
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

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getFilePath();
        if (file != null) {
            loadCriteriaDataFromFile(file);
        }
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showIcoOverview() {
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
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
   
    /**
     * ICO kommt von OverviewController
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
    		//TODO: Was macht controller, was macht stege etc....
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
    		//TODO: Was macht controller, was macht stege etc....
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
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
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
		     * Loads criteria data from the specified file. The current criteria data will
		     * be replaced.
		     * 
		     * @param file
		     */
		    public void loadCriteriaDataFromFile(File file) {
		        try {
		            JAXBContext context = JAXBContext
		                    .newInstance(PortfolioWrapper.class);
		            Unmarshaller um = context.createUnmarshaller();
		
		            // Reading XML from the file and unmarshalling.
		            PortfolioWrapper wrapper = (PortfolioWrapper) um.unmarshal(file);
		
		            criteriaList.clear();
		            criteriaList.addAll(wrapper.getCriterion());
		            
		            icoList.clear();
		            icoList.addAll(wrapper.getIcos());
		            
//		            icoCriterionList.addAll(wrapper.getIcoCriterion());
		            
//		            icoList.forEach(ico -> ico.getAllIcoCriteria().clear());
//		            icoList.forEach(ico -> {
//		            	for (IcoCriteria c : icoCriterionList) {
//		            		if (c.getIco() == ico) {
//		            			ico.addCriteria(c);
//		            		}
//		            	}
//		            });
		            
		
		            // Save the file path to the registry.
		            setFilePath(file);
		
		        } catch (Exception e) { // catches ANY exception
		            Alert alert = new Alert(AlertType.WARNING);
		            alert.setTitle("Warning");
		            alert.setHeaderText("Could not load data");
		            alert.setContentText(
		            		"Could not load data from file:\n" +
		            		file.getPath() + "\n\n" +
		            		"A new Portfolio will be created.\n\n" +
		            		"If you want to open an existing Portfolio use Menu 'File' -> 'Open'\n" +
		            		e
		            ); 
		            alert.showAndWait();
		        }
		    }

            /**
		     * Saves the current person data to the specified file.
		     * 
		     * @param file
		     */
		    public void saveCriteriaDataToFile(File file) {
		        try {
		            JAXBContext context = JAXBContext
		                    .newInstance(PortfolioWrapper.class);
		            Marshaller m = context.createMarshaller();
		            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		            // Wrapping our criteria data.
		            PortfolioWrapper wrapper = new PortfolioWrapper();
		            wrapper.setCriterion(criteriaList);
		            wrapper.setIcos(icoList);
//		            icoList.forEach(ico -> {
//		            	for (IcoCriteria c : ico.getAllIcoCriteria()) {
//		            		icoCriterionList.add(c);
//		            	}
//		            });
//		            wrapper.setIcoCriterion(icoCriterionList);
		            
		
		            // Marshalling and saving XML to the file.
		            m.marshal(wrapper, file);
		
		            // Save the file path to the registry.
		            setFilePath(file);
		        } catch (Exception e) { // catches ANY exception
		            Alert alert = new Alert(AlertType.ERROR);
		            alert.setTitle("Error");
		            alert.setHeaderText("Could not save data");
		            alert.setContentText("Could not save data to file:\n" + file.getPath()+ "\n\n" + e);
		
		            alert.showAndWait();
		        }
		    }		    
}
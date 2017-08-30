package cnrs.rhapsodie.treebankbrowser.view;

import java.io.File;

import cnrs.rhapsodie.treebankbrowser.MainApp;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob modifed by Gael Guibon
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @SuppressWarnings("static-access")
	@FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
            Notifier n = Notifier.INSTANCE;
            n.setWidth(400.00);
            n.notifySuccess("Success !", "Data saved to \n" + personFile.getName() + " !");
        } else {
            handleSaveAs();
        }
    }

    
    
    
    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @SuppressWarnings("static-access")
	@FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

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
            mainApp.savePersonDataToFile(file);
            Notifier n = Notifier.INSTANCE;
            n.setWidth(400.00);
            n.notifySuccess("Success !", "Data saved to \n" + file.getName() + " !");
        }
    }
    
    
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("TreebankBrowser");
    	alert.setHeaderText("About");
    	alert.setContentText("Author: Gaël Guibon\n\ngael.guibon@lsis.org\n"
    			+ "gael.guibon@gmail.com\n\n"
    			+ "http://www.lsis.org\n\n"
    			+ "Kim Gerdes\n\nkim.gerdes@univ-paris3.fr\n\n"
    			+ "Upgrading MarkJacob's example");    	
    	
    	alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
}
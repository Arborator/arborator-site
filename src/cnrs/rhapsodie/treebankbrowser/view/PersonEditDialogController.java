package cnrs.rhapsodie.treebankbrowser.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import cnrs.rhapsodie.treebankbrowser.MainApp;
import cnrs.rhapsodie.treebankbrowser.model.ProjectUI;
import cnrs.rhapsodie.treebankbrowser.util.DateUtil;
import eu.hansolo.enzo.notification.Notification;
import eu.hansolo.enzo.notification.Notification.Notifier;

/**
 * Dialog to edit details of a person.
 * 
 * @author Gaël Guibon
 */
public class PersonEditDialogController {

	// Reference to the main application
    private MainApp mainApp;
	
    @FXML
    private TextField titleField;
    @FXML
    private TextField subtitleField;
    @FXML
    private TextField licenceTitleField;
    @FXML
    private TextField licenceHtmlField;
    @FXML
    private TextField projectTitleField;
    @FXML
    private TextField projectHtmlField;
    @FXML
    private TextField rawDirField;
    @FXML
    private TextField authorField;

    private String path ="";

    private Stage dialogStage;
    private ProjectUI projectUI;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        
        // Set the dialog icon.
        Image img = new Image(MainApp.class.getResourceAsStream("/resources/images/edit.png"));
        this.dialogStage.getIcons().add(img);
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param projectUI
     */
    public void setPerson(ProjectUI projectUI) {
        this.projectUI = projectUI;
        titleField.setText(projectUI.getTitle());
        subtitleField.setText(projectUI.getSubtitle());
        projectTitleField.setText(projectUI.getProjectTitle());
        projectHtmlField.setText(projectUI.getProjectHtml());
        licenceTitleField.setText(projectUI.getLicenceTitle());
        licenceHtmlField.setText(projectUI.getLicenceHtml());
        rawDirField.setText(projectUI.getRawDir());
        authorField.setText(projectUI.getAuthor());
       
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    
    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleSelectDir() {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("Select Source Directory");
    	File defaultDirectory = new File(".");
    	chooser.setInitialDirectory(defaultDirectory);
    	File selectedDirectory = chooser.showDialog(
    			dialogStage);
    	Notifier.INSTANCE.notifySuccess("Directory selected !", 
				selectedDirectory.getAbsolutePath());
    	rawDirField.setText(selectedDirectory.getAbsolutePath());
    	
    }
    
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	projectUI.setTitle(titleField.getText());
        	projectUI.setSubtitle(subtitleField.getText());
        	projectUI.setProjectTitle(projectTitleField.getText());
        	projectUI.setProjectHtml(projectHtmlField.getText());
        	projectUI.setLicenceTitle(licenceTitleField.getText());
        	projectUI.setLicenceHtml(licenceHtmlField.getText());
        	projectUI.setRawDir(rawDirField.getText());
        	projectUI.setAuthor(authorField.getText());
        	

            okClicked = true;
            Notifier.INSTANCE.notifySuccess("Success !", "Do not forget to save your changes !");
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "No valid title!\n"; 
        }
//        if (subtitleField.getText() == null || subtitleField.getText().length() == 0) {
//            errorMessage += "No valid subtitle!\n"; 
//        }
//        if (licenceTitleField.getText() == null || licenceTitleField.getText().length() == 0) {
//            errorMessage += "No valid licenceTitle!\n"; 
//        }
//        if (licenceHtmlField.getText() == null || licenceHtmlField.getText().length() == 0) {
//            errorMessage += "No valid licenceHtml!\n"; 
//        }
//        if (projectTitleField.getText() == null || projectTitleField.getText().length() == 0) {
//            errorMessage += "No valid projectTitle!\n"; 
//        }
//        if (projectHtmlField.getText() == null || projectHtmlField.getText().length() == 0) {
//            errorMessage += "No valid projectHtml!\n"; 
//        }
        if (rawDirField.getText() == null || rawDirField.getText().length() == 0) {
            errorMessage += "No valid rawDir!\n"; 
        }
//        if (authorField.getText() == null || authorField.getText().length() == 0) {
//            errorMessage += "No valid author!\n"; 
//        }
        
//        else {
//            // try to parse the postal code into an int.
//            try {
//                Integer.parseInt(postalCodeField.getText());
//            } catch (NumberFormatException e) {
//                errorMessage += "No valid postal code (must be an integer)!\n"; 
//            }
//        }

        
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
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
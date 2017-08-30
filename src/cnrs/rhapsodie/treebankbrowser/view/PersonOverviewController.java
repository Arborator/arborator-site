package cnrs.rhapsodie.treebankbrowser.view;

import java.io.IOException;

import cnrs.rhapsodie.treebankbrowser.MainApp;
import cnrs.rhapsodie.treebankbrowser.StaticGenerator;
import cnrs.rhapsodie.treebankbrowser.model.ProjectUI;
import cnrs.rhapsodie.treebankbrowser.utils.Tools;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PersonOverviewController {
    @FXML
    private TableView<ProjectUI> personTable;
    @FXML
    private TableColumn<ProjectUI, String> titleColumn;
    @FXML
    private TableColumn<ProjectUI, String> authorColumn;
    
    
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private Label projectTitleLabel;
    @FXML
    private Label projectHtmlLabel;
    @FXML
    private Label licenceTitleLabel;
    @FXML
    private Label licenceHtmlLabel;
    @FXML
    private Label rawDirLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Button generateBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button newBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button downloadBtn;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//        projectHtmlColumn.setCellValueFactory(cellData -> cellData.getValue().projectHtmlProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        
        // Clear person details.
        showPersonDetails(null);

        downloadBtn.setDisable(true);
        deleteBtn.setDisable(true);
		newBtn.setDisable(false);
		editBtn.setDisable(true);
		generateBtn.setDisable(true);
        
        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        
        // Listen for selection changes and enable the buttons.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> disableBtn());
        
        
     // set default action for ENTER to show the edit panel if a person is selected
        personTable.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                	ProjectUI selectedProject = personTable.getSelectionModel().getSelectedItem();
                    if (selectedProject != null) {
                    	handleGenerateUI();
                    }
                }
            }
        });
        
        
        // Add event handler for double click on row
        personTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent event) {
        	    if (event.getClickCount()>1) {
        	    	handleGenerateUI();
//        	    	ProjectUI selectedPerson = personTable.getSelectionModel().getSelectedItem();
//        	    	if (selectedPerson != null) {
//        	    		
//        	            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
//        	            if (okClicked) {
//        	                showPersonDetails(selectedPerson);
//        	            }
//
//        	        } else {
//        	            // Nothing selected.
//        	            Alert alert = new Alert(AlertType.WARNING);
//        	            alert.initOwner(mainApp.getPrimaryStage());
//        	            alert.setTitle("No Selection");
//        	            alert.setHeaderText("No Person Selected");
//        	            alert.setContentText("Please select a person in the table.");
//        	            
//        	            alert.showAndWait();
//        	        }
        	    }
        	}
        });//end event handler double click
        
        
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param projectUI the person or null
     */
    private void showPersonDetails(ProjectUI projectUI) {
        if (projectUI != null) {
            // Fill the labels with info from the person object.
        	titleLabel.setText(projectUI.getTitle());
        	subtitleLabel.setText(projectUI.getSubtitle());
            projectTitleLabel.setText(projectUI.getProjectTitle());
            projectHtmlLabel.setText(projectUI.getProjectHtml());
            licenceTitleLabel.setText(projectUI.getLicenceTitle());
            licenceHtmlLabel.setText(projectUI.getLicenceHtml());
            rawDirLabel.setText(projectUI.getRawDir());
            authorLabel.setText(projectUI.getAuthor());
        } else {
            // Person is null, remove all the text.
        	titleLabel.setText("");
        	subtitleLabel.setText("");
            projectTitleLabel.setText("");
            projectHtmlLabel.setText("");
            licenceTitleLabel.setText("");
            licenceHtmlLabel.setText("");
            rawDirLabel.setText("");
            authorLabel.setText("");
        }
    }
    
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        ProjectUI tempPerson = new ProjectUI();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
            // automatically select the new element
            personTable.getSelectionModel().selectLast();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        ProjectUI selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Project Selected");
            alert.setContentText("Please select a person in the table.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the generate Interface button. 
     * Generate the static web interface given its informations.
     * @throws Exception 
     */
    @FXML
    private void handleGenerateUI()  {
        ProjectUI selectedProject = personTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            	StaticGenerator generator = new StaticGenerator();
            	if(Tools.dirFileExists(selectedProject.getRawDir())==false){
            		Alert alert = new Alert(AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Source directory does not exist !");
                    alert.setHeaderText("Source directory does not exist");
                    alert.setContentText("Please specify a directory where are the source conll files");
                    
                    alert.showAndWait();
            	}
            	try {
					generator.generateStaticInterface(
							selectedProject.getTitle(),
							selectedProject.getSubtitle(),
							selectedProject.getProjectTitle(),
							selectedProject.getProjectHtml(),
							selectedProject.getLicenceTitle(),
							selectedProject.getLicenceHtml(),
							selectedProject.getRawDir());
					Notifier.INSTANCE.notifySuccess("Opened in Browser !", 
							"The web interface is opened in your default browser !");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Project Selected");
            alert.setContentText("Please select a project in the table.");
            
            alert.showAndWait();
        }
    }
    
    
    @FXML
    private void handleDownload()  {
        ProjectUI selectedProject = personTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            	StaticGenerator generator = new StaticGenerator();
            	if(Tools.dirFileExists(selectedProject.getRawDir())==false){
            		Alert alert = new Alert(AlertType.WARNING);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Source directory does not exist !");
                    alert.setHeaderText("Source directory does not exist");
                    alert.setContentText("Please specify a directory where are the source conll files");
                    
                    alert.showAndWait();
            	}
            	try {
            	
					generator.dir2svg(
							selectedProject.getTitle(),
							selectedProject.getSubtitle(),
							selectedProject.getProjectTitle(),
							selectedProject.getProjectHtml(),
							selectedProject.getLicenceTitle(),
							selectedProject.getLicenceHtml(),
							selectedProject.getRawDir());
					Notifier.INSTANCE.notifySuccess("Opened in Browser !", 
							"The web interface is opened in your default browser !");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Project Selected");
            alert.setContentText("Please select a project in the table.");
            
            alert.showAndWait();
        }
    }
    
    
    
    /**
     * disable or enable buttons based on if there is a project selected
     */
    @FXML
    private void disableBtn(){
    	ProjectUI selectedPerson = personTable.getSelectionModel().getSelectedItem();
    	if (selectedPerson != null) {
    		downloadBtn.setDisable(false);
    		deleteBtn.setDisable(false);
    		newBtn.setDisable(false);
    		editBtn.setDisable(false);
    		generateBtn.setDisable(false);
        } else {
        	downloadBtn.setDisable(true);
        	deleteBtn.setDisable(true);
    		newBtn.setDisable(false);
    		editBtn.setDisable(true);
    		generateBtn.setDisable(true);
        }
    }
}
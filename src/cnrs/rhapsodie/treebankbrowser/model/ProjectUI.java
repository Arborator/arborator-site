package cnrs.rhapsodie.treebankbrowser.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cnrs.rhapsodie.treebankbrowser.util.LocalDateAdapter;

/**
 * Model class for a Person.
 *
 * @author Gaël Guibon
 */
public class ProjectUI {

    private final StringProperty title;
    private final StringProperty subtitle;
    private final StringProperty licenceTitle;
    private final StringProperty licenceHtml;
    private final StringProperty projectTitle;
    private final StringProperty projectHtml;
    private final StringProperty rawDir;
    private final StringProperty author;

    /**
     * Default constructor.
     */
    public ProjectUI() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param title
     * @param subtitle
     */
    public ProjectUI(String title, String subtitle) {
        this.title = new SimpleStringProperty(title);
        this.subtitle = new SimpleStringProperty(subtitle);
        
        // Some initial dummy data, just for convenient testing.
//        this.licenceTitle = new SimpleStringProperty("A very confined licence!");
//        this.licenceHtml = new SimpleStringProperty("<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>"
//    			+ "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>");
//        this.projectTitle = new SimpleStringProperty("My super project!");
//        this.projectHtml = new SimpleStringProperty("<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>"
//    			+ "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>");
//        this.rawDir = new SimpleStringProperty("Put the path of the dir with sources");
//        this.author = new SimpleStringProperty("Nobody");
        this.licenceTitle = new SimpleStringProperty("");
        this.licenceHtml = new SimpleStringProperty("");
        this.projectTitle = new SimpleStringProperty("");
        this.projectHtml = new SimpleStringProperty("");
        this.rawDir = new SimpleStringProperty("Put the path of the dir with sources");
        this.author = new SimpleStringProperty("");
        //        
//        
//        
//        this.street = new SimpleStringProperty("FR:Ecole doctorale (ex: ED 184)");
//        this.postalCode = new SimpleStringProperty("FR:Responsable (ex: M.Bellot)");
//        this.city = new SimpleStringProperty("FR:Contract (ex: CIFRE CDD)");
//        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
//        this.entranceDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2016, 3, 14));
//        this.exitDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2017, 3, 14));
//        this.desktop = new SimpleStringProperty("FR:Bureau. some desktop number (23 for instance)");
//        this.photoPath = new SimpleStringProperty("file:resources/images/person.png");
//        this.status = new SimpleStringProperty("PhD / Engineer / etc");
//        this.team = new SimpleStringProperty("Team A");
//        this.email = new SimpleStringProperty("an.email@email.com");
        
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getSubtitle() {
        return subtitle.get();
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.set(subtitle);
    }

    public StringProperty subtitleProperty() {
        return subtitle;
    }

    public String getLicenceTitle() {
        return licenceTitle.get();
    }

    public void setLicenceTitle(String licenceTitle) {
        this.licenceTitle.set(licenceTitle);
    }

    public StringProperty licenceTitleProperty() {
        return licenceTitle;
    }
    
    public String getLicenceHtml() {
        return licenceHtml.get();
    }

    public void setLicenceHtml(String licenceHtml) {
        this.licenceHtml.set(licenceHtml);
    }

    public StringProperty licenceHtmlProperty() {
        return licenceHtml;
    }
    
    public String getProjectTitle() {
        return projectTitle.get();
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle.set(projectTitle);
    }

    public StringProperty projectTitleProperty() {
        return projectTitle;
    }
    
    public String getProjectHtml() {
        return projectHtml.get();
    }

    public void setProjectHtml(String projectHtml) {
        this.projectHtml.set(projectHtml);
    }

    public StringProperty projectHtmlProperty() {
        return projectHtml;
    }
    
    public String getRawDir() {
        return rawDir.get();
    }

    public void setRawDir(String rawDir) {
        this.rawDir.set(rawDir);
    }

    public StringProperty rawDirProperty() {
        return rawDir;
    }
    
    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public StringProperty authorProperty() {
        return author;
    }

}
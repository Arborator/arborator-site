package cnrs.rhapsodie.treebankbrowser.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * 
 * @author Gaël Guibon
 */
@XmlRootElement(name = "persons")
public class PersonListWrapper {

    private List<ProjectUI> persons;
    
    private String mail;

    @XmlElement(name = "person")
    public List<ProjectUI> getPersons() {
        return persons;
    }

    public void setPersons(List<ProjectUI> persons) {
        this.persons = persons;
    }
    
    @XmlElement(name = "mail")
    public String getMail() {
        return mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
}
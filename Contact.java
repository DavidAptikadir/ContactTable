package sample;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleStringProperty FirstName=new SimpleStringProperty("");
    private SimpleStringProperty LastName=new SimpleStringProperty("");
    private SimpleStringProperty PhoneNumber=new SimpleStringProperty("");
    private SimpleStringProperty Notes=new SimpleStringProperty("");

    public Contact() {
    }

    public Contact(String firstName,String lastName,String phoneNumber,String notes){
        this.FirstName.set(firstName);
        this.LastName.set(lastName);
        this.PhoneNumber.set(phoneNumber);
        this.Notes.set(notes);
    }

    public String getFirstName() {
        return FirstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName.set(firstName);
    }

    public String getLastName() {
        return LastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName.set(lastName);
    }

    public String getPhoneNumber() {
        return PhoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber.set(phoneNumber);
    }

    public String getNotes() {
        return Notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return Notes;
    }

    public void setNotes(String notes) {
        this.Notes.set(notes);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "FirstName=" + FirstName +
                ", LastName=" + LastName +
                ", PhoneNumber=" + PhoneNumber +
                ", Notes=" + Notes +
                '}';
    }
}

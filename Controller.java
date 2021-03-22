package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane mainWindow;
    @FXML
    private TableView<Contact> contactsTable;
    private ContactData contactData;
    @FXML
    private ContextMenu contextMenu;

    public void initialize(){
        contactData=new ContactData();
        contactData.loadContacts();
        contactsTable.setItems(contactData.getContacts());

        contextMenu=new ContextMenu();
        MenuItem editItem=new MenuItem();
        MenuItem deleteItem=new MenuItem();
        deleteItem.setText("Delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contact contact=contactsTable.getSelectionModel().getSelectedItem();
                deleteContact(contact);
            }
        });
        editItem.setText("Edit");
        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contact contact=contactsTable.getSelectionModel().getSelectedItem();
                editContact(contact);
            }
        });

        contextMenu.getItems().addAll(deleteItem);
        contextMenu.getItems().addAll(editItem);

        contactsTable.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> contactTableView) {
                TableRow<Contact> row=new TableRow<>();
                row.emptyProperty().addListener(
                        (obs,wasEmpty,isEmpty)->{
                            if(isEmpty){
                                row.setContextMenu(null);
                            }else{
                                row.setContextMenu(contextMenu);
                            }
                        });
                return row;
            }
        });
    }

    @FXML
    public void showAddContactDialog(){
        Dialog<ButtonType> dialog=new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Dialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch(IOException e){
            System.out.println("Couldn't load the dialog");
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result=dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            DialogController dialogController=fxmlLoader.getController();
            contactData.addContact(dialogController.addContact());
            contactData.saveContacts();
        }
    }

    public void editContact(Contact contact){
        Dialog<ButtonType> dialog=new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Dialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch(IOException e){
            System.out.println("Could't open dialog");
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogController dialogController=fxmlLoader.getController();
        dialogController.editContact(contact);

        Optional<ButtonType> result=dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            dialogController.updateContact(contact);
            contactData.saveContacts();
        }
    }
    public void deleteContact(Contact contact){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete contact");
        alert.setHeaderText("Delete contact "+contact.getFirstName());
        alert.setContentText("Are you sure ,press OK to confirm");

        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK){
            contactData.deleteContact(contact);
            contactData.saveContacts();
        }
    }
}

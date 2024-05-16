package com.empresa.actividad019_ds_analysis;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;

public class HelloController {
    @FXML
    private TextField nameField, emailField, passwordField, searchField, updateField, deleteField, sortField;
    private MongoClient cliente;
    private MongoCollection<Document> tabla;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    protected void onRegisterButtonClick() {
        Document doc = new Document("name", nameField.getText())
                .append("email", emailField.getText())
                .append("password", passwordField.getText());
        insertDocument(doc);
    }

    @FXML
    protected void onSearchButtonClick() {
        Bson filter = Filters.eq("name", searchField.getText());
        findDocuments(filter);
    }

    @FXML
    protected void onUpdateButtonClick() {
        Bson filter = Filters.eq("name", updateField.getText());
        Bson update = Updates.set("email", updateField.getText());
        updateDocument(filter, update);
    }

    @FXML
    protected void onDeleteButtonClick() {
        Bson filter = Filters.eq("name", deleteField.getText());
        deleteDocument(filter);
    }

    @FXML
    protected void onSortButtonClick() {
        Bson sort = Sorts.ascending(sortField.getText());
        sortDocuments(sort);
    }

    @FXML
    protected void initialize() {
        // Crea una nueva instancia de MongoClient utilizando tu cadena de conexión
        MongoClient cliente = MongoClients.create("mongodb+srv://admin:admin@cluster0.gomt1im.mongodb.net/");

        // Obtiene una referencia a tu base de datos
        MongoDatabase db = cliente.getDatabase("actividad019");

        // Obtiene una referencia a la colección en la que deseas trabajar
        tabla = db.getCollection("usuarios");

        // Configura las columnas de la tabla
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Llena la tabla con los datos de los usuarios
        ObservableList<User> users = FXCollections.observableArrayList();
        // Aquí debes llenar la lista 'users' con los datos de tus usuarios
        userTable.setItems(users);
    }

    public void insertDocument(Document doc) {
        long startTime = System.nanoTime();
        tabla.insertOne(doc);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duración de insertar: " + duration + " nanosegundos");
    }

    public void findDocuments(Bson filter) {
        long startTime = System.nanoTime();
        tabla.find(filter);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duración de busqueda: " + duration + " nanosegundos");
    }

    public void updateDocument(Bson filter, Bson update) {
        long startTime = System.nanoTime();
        tabla.updateOne(filter, update);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duración de actalización: " + duration + " nanosegundos");
    }

    public void deleteDocument(Bson filter) {
        long startTime = System.nanoTime();
        tabla.deleteOne(filter);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duración de eliminación: " + duration + " nanosegundos");
    }

    public void sortDocuments(Bson sort) {
        long startTime = System.nanoTime();
        tabla.find().sort(sort);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duración de ordenamiento: " + duration + " nanosegundos");
    }
}
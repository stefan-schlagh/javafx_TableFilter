import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import TableFilter.FilterTable;
import TableFilter.Filterable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * @author https://code.makery.ch/blog/javafx-2-tableview-filter/
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Table Filtering");

        try {
            FilterTable<Person> filterTable = new FilterTable<>("Filter persons:");

            TableColumn<Person,String> firstNameColumn = new TableColumn<>("Vorname");
            TableColumn<Person,String> lastNameColumn = new TableColumn<>("Nachname");

            firstNameColumn.setCellValueFactory(
                    new PropertyValueFactory<Person, String>("firstName"));
            lastNameColumn.setCellValueFactory(
                    new PropertyValueFactory<Person, String>("lastName"));

            filterTable.getTable().getColumns().addAll(firstNameColumn,lastNameColumn);

            List<Person> data = new ArrayList<>();
            data.add(new Person("Hans", "Muster"));
            data.add(new Person("Ruth", "Mueller"));
            data.add(new Person("Heinz", "Kurz"));
            data.add(new Person("Cornelia", "Meier"));
            data.add(new Person("Werner", "Meyer"));
            data.add(new Person("Lydia", "Kunz"));
            data.add(new Person("Anna", "Best"));
            data.add(new Person("Stefan", "Meier"));
            data.add(new Person("Martin", "Mueller"));
            data.add(new Person("Ralph", "Mueller"));

            filterTable.addData(data);

            filterTable.getFilterableList().add(new Filterable<Person>() {
                @Override
                public String getFilterString(Person item) {
                    return item.getFirstName();
                }
            });
            filterTable.getFilterableList().add(new Filterable<Person>() {
                @Override
                public String getFilterString(Person item) {
                    return item.getLastName();
                }
            });

            Scene scene = new Scene(filterTable);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading TableFilter.FilterTable.fxml!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

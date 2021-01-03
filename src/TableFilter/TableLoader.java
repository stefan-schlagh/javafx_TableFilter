package TableFilter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

class TableLoader {

    public Parent parent;
    public FXMLLoader fxmlLoader;

    public TableLoader() throws IOException {
        fxmlLoader = new FXMLLoader();
        parent = fxmlLoader.load(getClass().getResource("filterTable.fxml").openStream());
    }
}

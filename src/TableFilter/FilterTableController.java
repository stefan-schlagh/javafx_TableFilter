package TableFilter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class FilterTableController {

    @FXML
    private Label labelSearchField;

    @FXML
    public TextField filterField;

    @FXML
    public BorderPane tablePane;

    public void setLabelSearchFieldText(String text){
        labelSearchField.setText(text);
    }
}

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterTable<S> extends BorderPane {

    private final TableView<S> table = new TableView<>();

    private final ObservableList<S> masterData = FXCollections.observableArrayList();
    private final ObservableList<S> filteredData = FXCollections.observableArrayList();

    private final TextField filterField;

    private final List<Filterable> filterableList = new ArrayList<>();

    public FilterTable() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(getClass().getResource("filterTable.fxml").openStream());

        setCenter(parent);

        FilterTableController filterTableController = fxmlLoader.getController();

        filterTableController.tablePane.setCenter(table);

        filterField = filterTableController.filterField;
        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                updateFilteredData();
            }
        });

        // Initially add all data to filtered data
        filteredData.addAll(masterData);

        // Add filtered data to the table
        table.setItems(filteredData);

        // Listen for changes in master data.
        // Whenever the master data changes we must also update the filtered data.
        masterData.addListener(new ListChangeListener<S>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends S> change) {
                updateFilteredData();
            }
        });

    }

    public TableView<S> getTable() {
        return table;
    }

    public void addData(List<S> list){
        masterData.addAll(list);
    }

    public void addData(S item){
        masterData.add(item);
    }

    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    private void updateFilteredData() {
        filteredData.clear();

        for (S item : masterData) {
            if (matchesFilter(item)) {
                filteredData.add(item);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    /**
     * Returns true if the item matches the current filter. Lower/Upper case
     * is ignored.
     *
     * @param item The item to be matched
     * @return true if there is a match
     */
    private boolean matchesFilter(S item) {
        String filterString = filterField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        for(int i = 0;i < filterableList.size();i++){
            if(filterableList.get(i).getFilterString(item).toLowerCase().indexOf(lowerCaseFilterString) != -1){
                return true;
            }
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<S, ?>> sortOrder = new ArrayList<>(table.getSortOrder());
        table.getSortOrder().clear();
        table.getSortOrder().addAll(sortOrder);
    }

    public void addFilterProperty(Filterable<S> f){
        filterableList.add(f);
    }
}

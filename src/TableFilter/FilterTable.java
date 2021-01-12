package TableFilter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

    private final List<Filterable<S>> filterableList = new ArrayList<>();

    /**
     * create the filterTable
     * @throws IOException if the fxml file could not be loaded
     */
    public FilterTable() throws IOException {

        TableLoader tableLoader = new TableLoader();

        setCenter(tableLoader.parent);

        FilterTableController filterTableController = tableLoader.fxmlLoader.getController();

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
    /**
     * returns the Table. Use this to add your columns
     * @return table
     */
    public TableView<S> getTable() {
        return table;
    }
    /**
     * add a list of data to the table.
     * @param list an instance of java.util.List
     */
    public void addData(List<S> list){
        masterData.addAll(list);
    }
    /**
     * add an item to the table
     * @param item the item
     */
    public void addData(S item){
        masterData.add(item);
    }
    /**
     * add a list of data to the table.
     * @param list an instance of java.util.List
     */
    public void removeData(List<S> list){
        masterData.removeAll(list);
    }
    /**
     * add an item to the table
     * @param item the item
     */
    public void removeData(S item){
        masterData.remove(item);
    }
    /**
     * remove all data
     */
    public void removeAllData(){
        List<S> dataClone = FXCollections.observableList(masterData);
        masterData.removeAll(dataClone);
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
    /**
     * add a filterable property.
     * @param f the filterable
     */
    public void addFilterProperty(Filterable<S> f){
        filterableList.add(f);
    }
    /**
     * get the filterable list. can be modified
     * @return the filterable list
     */
    public List<Filterable<S>> getFilterableList() {
        return filterableList;
    }
}

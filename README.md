# TableFilter

contains code from https://code.makery.ch/blog/javafx-2-tableview-filter/

## create a table that can be searched

- create table
````java
FilterTable<Person> filterTable = new FilterTable<>();

// create columns
TableColumn<Person,String> firstNameColumn = new TableColumn<>("Vorname");
TableColumn<Person,String> lastNameColumn = new TableColumn<>("Nachname");

// set setCellValueFactory
firstNameColumn.setCellValueFactory(
        new PropertyValueFactory<Person, String>("firstName"));
lastNameColumn.setCellValueFactory(
        new PropertyValueFactory<Person, String>("lastName"));

//add columns
filterTable.getTable().getColumns().addAll(firstNameColumn,lastNameColumn);
````

- add data
````java
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
````

- add filterable properties
````java
filterTable.addFilterProperty(new Filterable<Person>() {
    @Override
    public String getFilterString(Person item) {
        return item.getFirstName();
    }
});
filterTable.addFilterProperty(new Filterable<Person>() {
    @Override
    public String getFilterString(Person item) {
        return item.getLastName();
    }
});
````

### example

see src/MainApp

![image that shows the GUI of MainApp](https://raw.githubusercontent.com/stefan-schlagh/javafx_TableFilter/master/TableFilter.png)
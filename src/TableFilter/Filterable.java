package TableFilter;

public interface Filterable<T> {
    String getFilterString(T item);
}

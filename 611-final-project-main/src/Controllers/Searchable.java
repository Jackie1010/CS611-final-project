package Controllers;

import java.util.ArrayList;
import java.util.List;

public interface Searchable {

    /**
     * Search for a query in the data
     * @param query
     * @param data
     * @param column
     * @return
     */
    default Object[][] search(String query, ArrayList<List<String>> data, int column) {
        System.out.println("Searching for: " + query);
        Object[][] return_data = new Object[data.size()][];
        int index = 0;
        for (int i = 0; i < data.size(); i++){
            List<String> row = data.get(i);
            if (row.get(column).equals(query)){
                return_data[index] = row.toArray();
                index++;
            }
        }
        if (index == 0){
            System.out.println("No results found");
            return null;
        }
        Object[][] new_data = new Object[index][];
        System.arraycopy(return_data, 0, new_data, 0, index);
        return new_data;
    }
}

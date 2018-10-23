package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ComboBoxHelper {



    // static variable single_instance of type Singleton
    private static ReadXMLFile single_instance = null;

    // static method to create instance of Singleton class
    public static ReadXMLFile getInstance()
    {
        if (single_instance == null)
            single_instance = new ReadXMLFile();

        return single_instance;
    }






}

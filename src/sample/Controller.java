package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import java.util.Locale;

public class Controller {

    private ObservableList choices = FXCollections.observableArrayList();

    ReadXMLFile readXMLFile = ReadXMLFile.getInstance();
    Convert convert = Convert.getInstance();

    List<String> currencyCodeList = new ArrayList<>();
    List<Currency> currencyList = new ArrayList<>();

    @FXML
    private ComboBox fromComboBox;

    @FXML
    private ComboBox toComboBox;

    @FXML
    private TextField valueField;

    @FXML
    private TextField resultField;

    @FXML
    private Text dateView;

    public void pressButton(javafx.event.ActionEvent actionEvent) throws ParseException {
        //Double value = Doublethis.value.getText();
        String regex = "^\\d*||\\d+\\,\\d*$";
        if(!this.valueField.getText().isEmpty() && this.valueField.getText().matches(regex)){
            System.out.println("true");
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number = format.parse(this.valueField.getText());
            Double value = number.doubleValue();
            System.out.println(value);

            String sfrom = (String) fromComboBox.getValue();
            String sto = (String) toComboBox.getValue();
            currencyList = readXMLFile.getCurrencyList();
            Currency from = null, to = null;
            for(Currency currency : currencyList){
                if(currency.getCode().equals(sfrom)){
                    from = currency;
                }
                if(currency.getCode().equals(sto)){
                    to = currency;
                }
            }
            Double result = convert.calculate(to, from, value);
            String sccore = String.format("%.02f", result);
            this.resultField.setText(sccore.concat(" "+to.getCode()));
        }
        else{this.resultField.setText("incorrect data");}

        }


    @FXML
    private void initialize() throws ParserConfigurationException, SAXException, ParseException, IOException {
        readXMLFile.readXML();

        currencyCodeList = readXMLFile.getCurrencyCodeList();
        choices.addAll(currencyCodeList);
        // this.fromComboBox.getItems().addAll(choices);
        this.fromComboBox.getItems().addAll(choices);
        this.toComboBox.setItems(choices);
        fromComboBox.getSelectionModel().selectFirst();
        toComboBox.getSelectionModel().select(8);
        setDate();
    }

    private void setDate() {
        String date = readXMLFile.getdateData();
        dateView.setText("Data from: " + date);
    }

}

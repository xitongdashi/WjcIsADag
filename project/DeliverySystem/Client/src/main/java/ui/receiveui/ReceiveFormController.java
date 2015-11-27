package ui.receiveui;

import bl.blService.receiveblService.ReceiveBLService;
import tool.time.TimeConvert;
import factory.FormFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import message.OperationMessage;
import po.receivedata.StateEnum;
import tool.ui.Enum2ObservableList;
import tool.ui.OrderVO2ColumnHelper;
import tool.ui.SimpleEnumProperty;
import vo.ordervo.OrderVO;
import vo.receivevo.ReceiveVO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Sissel on 2015/11/21.
 */
public class ReceiveFormController{
    
    public DatePicker arrive_DatePicker;
    public TextField transitID_Field;
    public TextField departure_Field;
    public ChoiceBox<SimpleEnumProperty<StateEnum>> arriveState_Box;
    public TableView<Map.Entry<String, String>> order_Table;
    public TextField order_Field;
    public Label date_ErrLabel;
    public Label transit_errLabel;
    public Label departure_errLabel;
    public TableColumn<Map.Entry<String, String>, String> key_Column;
    public TableColumn<Map.Entry<String, String>, String> value_Column;

    private StateEnum stateEnum = StateEnum.Complete;

    ReceiveBLService receiveBLService = FormFactory.getReceiveBLService();

    public static Parent launch() throws IOException {

        FXMLLoader contentLoader = new FXMLLoader();
        contentLoader.setLocation(ReceiveFormController.class.getResource("ReceiveForm.fxml"));
        return contentLoader.load();
    }

    @FXML
    public void initialize(){
        // initialize the choice box
        arriveState_Box.setItems(Enum2ObservableList.transit(StateEnum.values()));
        arriveState_Box.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    stateEnum = newValue.getEnum();
                }
        );
        clear(null);

        order_Field.setOnAction(
                uselessParam->{
                    fillOrderTable();
                }
        );

        OrderVO2ColumnHelper.setKeyColumn(key_Column); 
        OrderVO2ColumnHelper.setValueColumn(value_Column);
    }

    public void commit(ActionEvent actionEvent) {
        OperationMessage msg = receiveBLService.submit(generateVO(receiveBLService.newID()));

        if(msg.operationResult){
            System.out.println("commit successfully");
            clear(null);
        }else{
            System.out.println("commit fail: " + msg.getReason());
        }
    }

    public void clear(ActionEvent actionEvent) {
        arrive_DatePicker.setValue(LocalDate.now());
        transitID_Field.clear();
        departure_Field.clear();
        order_Field.clear();
        order_Table.setItems(null);
        arriveState_Box.setValue(arriveState_Box.getItems().get(0));
    }

    public void saveDraft(ActionEvent actionEvent) {
        receiveBLService.saveDraft(generateVO(null));
    }

    private ReceiveVO generateVO(String formID){
        Calendar calendar = TimeConvert.convertDate(arrive_DatePicker.getValue());
        return new ReceiveVO(formID, order_Field.getText(), transitID_Field.getText(),
                calendar, departure_Field.getText(), stateEnum);
    }

    private void fillOrderTable(){
        OrderVO orderVO = receiveBLService.getOrderVO(order_Field.getText());

//        OrderVO orderVO =
//                new OrderVO("11","程翔", "王嘉琛", "南京", "北京", "", "",
//                        "18351890356", "13724456739", "3", "图书", "", "", "", null, null, null,
//                        DeliverTypeEnum.NORMAL, PackingEnum.BAG);

        order_Table.setItems(FXCollections.observableArrayList(new OrderVO2ColumnHelper().VO2Entries(orderVO)));
    }

}
package ui.hallui;

import java.io.IOException;

import po.memberdata.SexEnum;
import po.orderdata.DeliverTypeEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import tool.ui.Enum2ObservableList;
import tool.ui.SimpleEnumProperty;
import vo.managevo.staff.DriverVO;

public class DriverEditDialogController {

	public TextField ID_Field;
	public TextField name_Field;
	public TextField personID_Field;
	public TextField tel_Field;
	public TextField carUnit_Field;
	public DatePicker birth_Picker;
	public DatePicker licencePeriod_Picker;
	
	ChoiceBox<SimpleEnumProperty<SexEnum>> sex_Box;
	
	public static Label age_Label;
	
	SexEnum sexEnum = SexEnum.MAN;
	private DriverVO editVO =new DriverVO(null);
	public Stage stage;

	public static DriverEditDialogController newDialog(DriverVO editVO) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(DriverEditDialogController.class.getResource("dirverEditDialog.fxml"));
		Pane pane = loader.load();

		Stage stage = new Stage();
		stage.initOwner(Main.primaryStage);
		stage.setScene(new Scene(pane));

		DriverEditDialogController controller = (DriverEditDialogController)loader.getController();
		controller.editVO = editVO;
		controller.stage = stage;
		
		
		
		return controller;
		
	}
	
    @FXML
    public void initialize(){
    	sex_Box.setItems(
                Enum2ObservableList.transit(SexEnum.values())
        );
    	sex_Box.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					sexEnum = newValue.getEnum();
                }
		);
    	
    }


	public void ok(ActionEvent actionEvent){
		//TODO solve sex and Picker
		editVO.setID(ID_Field.getText());
		editVO.setName(name_Field.getText());
		//editVO.setSex(sex_Box.getValue());
		//editVO.setBirth(birth_Picker.getValue());
		editVO.setPersonID(personID_Field.getText());
		editVO.setTel(tel_Field.getText());
		//editVO.setLicence_period(licencePeriod_Picker.getValue());
		
		stage.close();
		
		
	}

	public void cancel(ActionEvent actionEvent){
		stage.close();
	}
}
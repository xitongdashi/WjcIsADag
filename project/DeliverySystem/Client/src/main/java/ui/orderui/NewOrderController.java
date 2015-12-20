package ui.orderui;

import bl.blImpl.orderbl.OrderBLController;
import bl.blService.orderblService.OrderBLService;
import factory.FormFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import message.OperationMessage;
import po.orderdata.DeliverTypeEnum;
import po.orderdata.PackingEnum;
import tool.time.TimeConvert;
import tool.ui.Enum2ObservableList;
import tool.ui.SimpleEnumProperty;
import ui.hallui.RevenueFormController;
import ui.informui.InformController;
import userinfo.UserInfo;
import vo.ordervo.OrderVO;
import vo.ordervo.PredictVO;
import vo.receivevo.ReceiveVO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Charles_M on 2015/11/22.
 */
public class NewOrderController {

	public TextField name_From;
	public TextField address_From;
	public TextField unit_From;
	public TextField tel_From;
	public TextField phone_From;

	public TextField name_To;
	public TextField address_To;
	public TextField unit_To;
	public TextField tel_To;
	public TextField phone_To;

	public TextField goods_Number;
	public TextField goods_Weight;
	public TextField goods_Volume;
	public TextField goods_Name;
	public TextField goods_Type;

	public ChoiceBox<SimpleEnumProperty<DeliverTypeEnum>> type_Box;
	public ChoiceBox<SimpleEnumProperty<PackingEnum>> pack_Box;

	public Label predict_Expense;
	public Label predict_Date;
	public Label date_ErrLabel;
	public Label transit_errLabel;
	public Label departure_errLabel;
	public Label date_ErrLabel1;
	public Label transit_errLabel1;
	public Label departure_errLabel1;
	public Label date_ErrLabel2;
	public Label transit_errLabel2;
	public Label departure_errLabel2;

	private DeliverTypeEnum deliverType = DeliverTypeEnum.NORMAL;
	private PackingEnum packing = PackingEnum.PAPER;

	int money = 0;// 预计运费
	int day = 0;
	String predictDate;// 预计送达日期

	OrderBLService obl = FormFactory.getOrderBLService();

	private InformController informController;

	public static Parent launch() throws IOException {
		FXMLLoader loader = new FXMLLoader(NewOrderController.class.getResource("NewOrder.fxml"));
        Pane pane = loader.load();
        NewOrderController controller = loader.getController();
        controller.informController = InformController.newInformController(pane);

        return controller.informController.stackPane;
	}

	@FXML
	public void initialize() {
		// initialize the choice box
		type_Box.setItems(Enum2ObservableList.transit(DeliverTypeEnum.values()));
		type_Box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			deliverType = newValue.getEnum();
		});

		pack_Box.setItems(Enum2ObservableList.transit(PackingEnum.values()));
		pack_Box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			packing = newValue.getEnum();
		});

		clear(null);

	}

	public void commit(ActionEvent actionEvent) {

		OperationMessage msg = obl.submit(generateVO(obl.newID()));

		if (msg.operationResult) {
			System.out.println("commit successfully");
			clear(null);
		} else {
			System.out.println("commit fail: " + msg.getReason());
		}

	}

	private OrderVO generateVO(String FormID) {
		return new OrderVO(FormID, name_From.getText(), name_To.getText(), unit_From.getText(), unit_To.getText(),
				address_From.getText(), address_To.getText(), tel_From.getText(), tel_To.getText(),
				phone_From.getText(), phone_To.getText(), goods_Number.getText(), goods_Name.getText(),
				goods_Weight.getText(), goods_Volume.getText(), predict_Expense.getText(), goods_Type.getText(),
				deliverType, packing, UserInfo.getUserID());
	}

	@FXML
	private void fillPrediction() {

		PredictVO predictVO = obl.predict(generateVO(null));
		predict_Date.setText(TimeConvert.getDisplayDate(predictVO.getPredictDate()));
		predict_Expense.setText(predictVO.getExpense());
	}

	public void clear(ActionEvent actionEvent) {
		name_From.clear();
		address_From.clear();
		unit_From.clear();
		tel_From.clear();
		phone_From.clear();

		name_To.clear();
		address_To.clear();
		unit_To.clear();
		tel_To.clear();
		phone_To.clear();

		goods_Number.clear();
		goods_Weight.clear();
		goods_Volume.clear();
		goods_Name.clear();
		goods_Type.clear();

		SimpleEnumProperty<PackingEnum> pe = pack_Box.getItems().get(0);
		pack_Box.setValue(pe);
		packing = pe.getEnum();

		SimpleEnumProperty<DeliverTypeEnum> dte = type_Box.getItems().get(0);
		type_Box.setValue(dte);
		deliverType = dte.getEnum();
	}

	public void saveDraft(ActionEvent actionEvent) {

		OrderVO ovo = generateVO(null);
		obl.saveDraft(ovo);
	}

}

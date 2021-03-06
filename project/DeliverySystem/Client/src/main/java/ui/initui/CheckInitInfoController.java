package ui.initui;

import bl.blService.initblService.InitializationBLService;
import factory.InitBLFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import po.systemdata.SystemState;
import tool.ui.AnchorSet;
import tool.ui.Enum2ObservableList;
import tool.ui.SimpleEnumProperty;
import ui.informui.InformController;
import userinfo.UserInfo;
import util.EnumObservable;
import vo.financevo.BankAccountVO;
import vo.initialdata.InitialDataVO;
import vo.managevo.car.CarVO;
import vo.managevo.institution.InstitutionVO;
import vo.managevo.staff.StaffVO;

import java.io.IOException;

/**
 * Created by Sissel on 2015/11/27.
 */

enum  InitType implements EnumObservable<InitType> {

    BANK_ACCOUNT_INIT("账户期初信息"),
    INSTITUTION_INIT("机构期初信息"),
    STAFF_INIT("员工期初信息"),
    STORE_INIT("仓库期初信息"),
    CAR_INIT("车辆期初信息");

    private String chinese;

    InitType(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String getChinese() {
        return chinese;
    }
}

public class CheckInitInfoController {
    private InitializationBLService initBLService = InitBLFactory.getInitializationBLService();

    public AnchorPane content_Pane;
    public Label systemState_Label;
    public ChoiceBox<SimpleEnumProperty<InitType>> initType_ChoiceBox;

    public TableView info_TableView = new TableView();

    private InitialDataVO initialDataVO;
    private Pane father;

    private InformController informController;

    public static Parent launch(Pane father) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CheckInitInfoController.class.getResource("checkInitInfo.fxml"));
        Pane pane = loader.load();
        CheckInitInfoController controller = loader.getController();
        controller.informController = InformController.newInformController(pane);
        controller.father = father;
        return controller.informController.stackPane;
    }

    @FXML
    public void initialize(){
        // initialize choiceBox
        initType_ChoiceBox.setItems(Enum2ObservableList.transit(InitType.values()));
        initType_ChoiceBox.setValue(initType_ChoiceBox.getItems().get(0));

        initType_ChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (newValue.getEnum()) {
                        case BANK_ACCOUNT_INIT:
                            showBankAccounts();
                            break;
                        case INSTITUTION_INIT:
                            showInstitutions();
                            break;
                        case STAFF_INIT:
                            showStaffs();
                            break;
                        case STORE_INIT:
                            showStores();
                            break;
                        case CAR_INIT:
                            showCars();
                            break;
                    }
                }
        );

        systemState_Label.setText(UserInfo.getSystemState().getChinese());
        initialDataVO = initBLService.getInitialDataVO();
        AnchorSet.set0(info_TableView);
        showBankAccounts();
    }

    @FXML
    public void applyForInitialization(ActionEvent actionEvent) {
        initBLService.requestInitData();
        father.getChildren().clear();
        try {
            Pane pane = (Pane)NewInitController.launch(father);
            AnchorSet.set0(pane);
            father.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBankAccounts(){
        TableColumn<BankAccountVO, String> name_TableColumn = new TableColumn("账户名");
        TableColumn<BankAccountVO, String> balance_TableColumn = new TableColumn("余额");

        name_TableColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getAccountName())
        );
        balance_TableColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getBalance())
        );

        reconstructColumns(name_TableColumn, balance_TableColumn);

        info_TableView.getItems().addAll(initialDataVO.getBankAccounts());
        content_Pane.getChildren().clear();
        content_Pane.getChildren().add(info_TableView);
    }

    private void showCars(){
        TableColumn<CarVO, String> id_TableColumn = new TableColumn<>("车辆代号");
        TableColumn<CarVO, String> licence_TableColumn = new TableColumn<>("车牌号");
        TableColumn<CarVO, String> time_TableColumn = new TableColumn<>("服役时间");

        id_TableColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getCarID())
        );
        licence_TableColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNameID())
        );
        time_TableColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty()
        );

        reconstructColumns(id_TableColumn, licence_TableColumn, time_TableColumn);
        info_TableView.getItems().addAll(initialDataVO.getCars());
        content_Pane.getChildren().clear();
        content_Pane.getChildren().add(info_TableView);
    }

    private void showInstitutions(){
        TableColumn<InstitutionVO, String> id_TableColumn = new TableColumn<>("机构编号");
        TableColumn<InstitutionVO, String> type_TableColumn = new TableColumn<>("机构类型");
        TableColumn<InstitutionVO, String> city_TableColumn = new TableColumn<>("所在城市");
        TableColumn<InstitutionVO, String> staff_TableColumn = new TableColumn<>("职工人数");

        id_TableColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getInstitutionID())
        );
        type_TableColumn.setCellValueFactory(
                cell -> {
                    switch (cell.getValue().getInfoEnum()){
                        case HALL:
                            return new SimpleStringProperty("营业厅");
                        case CENTER:
                            return new SimpleStringProperty("中转中心");
                    }
                    return new SimpleStringProperty("???");
                }
        );
        city_TableColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getCityID())
        );
        staff_TableColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getStaffCount()+"")
        );

        reconstructColumns(id_TableColumn, type_TableColumn, city_TableColumn, staff_TableColumn);
        info_TableView.getItems().addAll(initialDataVO.getHalls());
        info_TableView.getItems().addAll(initialDataVO.getCenters());
        content_Pane.getChildren().clear();
        content_Pane.getChildren().add(info_TableView);
    }

    private void showStaffs(){
        TableColumn<StaffVO, String> id_TableColumn = new TableColumn<>("员工编号");
        TableColumn<StaffVO, String> name_TableColumn = new TableColumn<>("姓名");
        TableColumn<StaffVO, String> type_TableColumn = new TableColumn<>("职务");
        TableColumn<StaffVO, String> institution_TableColumn = new TableColumn<>("单位");

        id_TableColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getID())
        );
        name_TableColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getName())
        );
        type_TableColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getStaff().getChinese())
        );
        institution_TableColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getInstitutionID())
        );

        reconstructColumns(id_TableColumn, name_TableColumn, type_TableColumn, institution_TableColumn);
        info_TableView.getItems().addAll(initialDataVO.getStaffs());
        content_Pane.getChildren().clear();
        content_Pane.getChildren().add(info_TableView);
    }

    private void showStores(){
        Pane pane = null;
        try {
            pane = FXMLLoader.load(CheckStoreInitPane.class.getResource("checkStoreInitPane.fxml"));
            AnchorSet.set0(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        content_Pane.getChildren().clear();
        content_Pane.getChildren().add(pane);
    }

    private void reconstructColumns(TableColumn...columns){
        info_TableView.getColumns().clear();
        info_TableView.getItems().clear();
        for (TableColumn column : columns) {
            info_TableView.getColumns().add(column);
        }
    }
}

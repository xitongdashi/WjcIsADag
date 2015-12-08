package ui.navigationui;

import factory.FinanceBLFactory;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import ui.common.TabMaker;
import ui.financeui.*;
import ui.initui.CheckInitInfoController;
import ui.initui.NewInitController;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Sissel on 2015/11/27.
 */
public class FinanceNavigation {

    public static Parent launch() throws IOException {
        Pane initPane = new Pane();
        initPane.getChildren().add(
                CheckInitInfoController.launch(initPane));

        Parent node = TabMaker.makeTabs(Arrays.asList(
                new Pair<String, Parent>("财务报表", CheckFinanceChartController.launch()),
                new Pair<String, Parent>("管理账户",
                        ManageBankAccountController.launch( FinanceBLFactory.getBankAccountBLService())),
                new Pair<String, Parent>("成本收益表", CheckFinanceSummaryController.launch()),
                new Pair<String, Parent>("新建付款单", PaymentFormController.launch()),
                new Pair<String, Parent>("查看收款单", CheckRevenueFormController.launch()),
                new Pair<String, Parent>("查看系统日志", CheckLogController.launch()),
                new Pair<String, Parent>("查看期初建账", initPane)
        ));

        return node;
    }
}

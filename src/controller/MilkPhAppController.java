package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Department;
import model.Municipality;
import tools.Database;

public class MilkPhAppController {
    @FXML private Button butAddDepartment;
    @FXML private Button butAddMunicipality;
    @FXML private Button butDeleteDepartment;
    @FXML private Button butDeleteMunicipality;
    @FXML private Button butUpdateDepartment;
    @FXML private Button butUpdateMunicipality;
    
    @FXML private ComboBox<Department> cmbMunicipalityDepartment;

    @FXML private Label labDepartmentId;
    @FXML private Label labMunicipalityId;

    @FXML private MenuItem mniAbout;
    @FXML private MenuItem mniCloseApp;
    
    @FXML private Tab tabCompanies;
    @FXML private Tab tabDepartments;
    @FXML private Tab tabFarms;
    @FXML private Tab tabMilkings;
    @FXML private Tab tabMunicipalities;
    @FXML private Tab tabProcessing;
    @FXML private Tab tabUsers;

    @FXML private TableView<Department> tbvDepartments;
    @FXML private TableView<Municipality> tbvMunicipalities;
    
    @FXML private TextField txtDepartmentDANE;
    @FXML private TextField txtDepartmentName;
    @FXML private TextField txtMunicipalityDANE;
    @FXML private TextField txtMunicipalityLatitude;
    @FXML private TextField txtMunicipalityLongitude;
    @FXML private TextField txtMunicipalityName;

    private Database _db;

    @FXML void butAddDepartmentClick(ActionEvent event) {
        DepartmentController dc = new DepartmentController(_db);
        dc.add(
            txtDepartmentDANE.getText().toString(),
            txtDepartmentName.getText(),
            tbvDepartments
        );
    }

    @FXML void butAddMunicipalityClick(ActionEvent event) {

    }

    @FXML void butDeleteDepartmentClick(ActionEvent event) {
        if (labDepartmentId.getText().length() > 0) {
            DepartmentController dc = new DepartmentController(_db);
            if (dc.delete(labDepartmentId.getText(), tbvDepartments)) {
                clearDepartmentForm();
            }
        }
        else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("No department selected! Please double-click on the department.");
            a.show();
        }
    }

    @FXML void butDeleteMunicipalityClick(ActionEvent event) {

    }

    @FXML void butUpdateDepartmentClick(ActionEvent event) {
        if (labDepartmentId.getText().length() > 0) {
            DepartmentController dc = new DepartmentController(_db);
            dc.update(
                labDepartmentId.getText(),
                txtDepartmentDANE.getText().toString(),
                txtDepartmentName.getText(),
                tbvDepartments
            );
        }
        else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("No department selected! Please double-click on the department.");
            a.show();
        }
    }

    @FXML void butUpdateMunicipalityClick(ActionEvent event) {

    }

    @FXML void cmbMunicipalityDepartmentClick(ActionEvent event) {

    }

    @FXML void mniAboutClick(ActionEvent event) {

    }

    @FXML void mniCloseAppClick(ActionEvent event) {

    }

    public void initialize() {
        connectDB();

        Tab[] tabArray = {
            tabCompanies, tabDepartments, tabFarms, tabMilkings, tabMunicipalities, 
            tabProcessing, tabUsers
        };
        for (Tab tab : tabArray) {
            prepareTab(tab);
        }

        prepareDepartmentTableView();
    }

    private void connectDB() {
        String dbConnect = "jdbc:sqlite:";
        String dbName = "db/milkph.db";
        this._db = new Database(dbConnect + dbName);
    }

    private void prepareTab(Tab tab) {
        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tab.isSelected()) {
                    switch (tab.getText()) {
                        case "Departments": {
                            DepartmentController dc = new DepartmentController(_db);
                            dc.fillTable(tbvDepartments);
                        } break;
                        case "Municipalities" : break;
                        case "Farms" : break;
                        case "Users" : break;
                        case "Milkings" : break;
                        case "Companies" : break;
                        case "Milk processings" : break;
                    }
                }
            }
        });
    }

    private void prepareDepartmentTableView() {
        tbvDepartments.setRowFactory(tr -> {
            TableRow<Department> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    Department rowData = row.getItem();
                    labDepartmentId.setText("" + rowData.getId());
                    txtDepartmentDANE.setText("" + rowData.getDANEId());
                    txtDepartmentName.setText(rowData.getName());
                }
            });
            return row;
        });
    }

    private void clearDepartmentForm() {
        labDepartmentId.setText("");
        txtDepartmentDANE.setText("");
        txtDepartmentName.setText("");
    }
}

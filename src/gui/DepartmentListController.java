package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	
	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;//lista para carregar os departamentos que serao mostrados atraves do metodo setDepartmentService
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");//só testando o botao
	}
	
	public void setDepartmentService(DepartmentService service) {//metodo para injetar a dependencia sem cria um acoplamento forte
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {//iniciar o comportamento das colunas
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//organizando o redimencionamento da janela e seus componentes
		Stage stage = (Stage) Main.getMainScene().getWindow();//paga uma referencia para o Stage fazendo downcast da super classe Window
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());//faz o tableView acompanhar o redimencionamento da janela atravez da propriedade altura
		
	}

	public void updateTableView() {//acessar os servicos, carregar os departments e joga-los na ObservableList
		if (service == null) {//testa e preveni que o programador esqueça de injetar a dependencia
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();//Lista que recebe os departamentos Mockados
		obsList = FXCollections.observableArrayList(list);//carraga a lista dentro do obsList
		tableViewDepartment.setItems(obsList);//carrega os itens e mostra na tela
	}
}

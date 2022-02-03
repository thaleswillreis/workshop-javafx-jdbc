package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;

	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {//parametro passa a acao de inicializacao do controller DepartmentListController como segundo parametro em formato de funcao lambda
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView(); 
		});
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x-> {});//parametro de funcao lambda vazia pois a janela about nao faz nada
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {//funcao generica que manipula a cena principal para incluir alem do mainMenu os filhos da janela que for aberta
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();//recebe o return com a referencia da scene
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();//variavel do tipo VBox recebendo um casting do Vbox no casting do getRoot para o ScrollPane
			
			Node mainMenu = mainVBox.getChildren().get(0);//guarda referencia para o menu numa var do tipo Node
			mainVBox.getChildren().clear();//limpa todos os filhos do mainVBox para os substituirmos
			mainVBox.getChildren().add(mainMenu); //adiciona o mainMenu nos filhos do mainVBox que limpamos
			mainVBox.getChildren().addAll(newVBox.getChildren());//adiciona a colecao com os filhos do newVBox
			
			T controller = loader.getController(); //retorna o controlador do tipo que for chamado no segundo paramentro do loadView
			initializingAction.accept(controller); //executa a acao initializingAction
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}

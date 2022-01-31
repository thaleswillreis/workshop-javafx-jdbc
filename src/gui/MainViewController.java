package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
		loadView("/gui/DepartmentList.fxml");
	}

	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	private synchronized void loadView(String absoluteName) {//manipulando a cena principal para incluir alem do mainMenu os filhos da janela que for aberta
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();//recebe o return com a referencia da scene
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();//variavel do tipo VBox recebendo um casting do Vbox no casting do getRoot para o ScrollPane
			
			Node mainMenu = mainVBox.getChildren().get(0);//guarda referencia para o menu numa var do tipo Node
			mainVBox.getChildren().clear();//limpa todos os filhos do mainVBox para os substituirmos
			mainVBox.getChildren().add(mainMenu); //adiciona o mainMenu nos filhos do mainVBox que limpamos
			mainVBox.getChildren().addAll(newVBox.getChildren());//adiciona a colecao com os filhos do newVBox
			
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}

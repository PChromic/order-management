package pchromic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class OrderManagementApplication extends Application {

	private ConfigurableApplicationContext springContext;
	private Parent rootNode;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Order management");
		primaryStage.setScene(new Scene(rootNode));
		primaryStage.show();

	}

	@Override
	public void init() throws Exception{

		springContext = SpringApplication.run(OrderManagementApplication.class);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rootLayout.fxml"));
		fxmlLoader.setControllerFactory(springContext::getBean);

		rootNode = fxmlLoader.load();
	}

	public static void main(String[] args) {
		Application.launch(OrderManagementApplication.class,args);
	}

	@Override
	public void stop() {
		springContext.close();
	}

}

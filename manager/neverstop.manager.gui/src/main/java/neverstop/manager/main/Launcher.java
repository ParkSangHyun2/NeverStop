package neverstop.manager.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import neverstop.manager.main.controller.MainController;

/**
 * Launcher
 *
 * @author @author <a href="mailto:mhjang@nextree.co.kr">Jang, Mihyeon</a>
 * @since 08/09/2018
 */
public class Launcher extends Application {

    private MainController controller;

    public Launcher() {
        //
        this.controller = new MainController();
    }

    public void start(Stage primaryStage) throws Exception {
        //
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/neverstop/manager/main/MainView.fxml"));
        loader.setController(controller);
        BorderPane rootLayout = new BorderPane();
        rootLayout.setCenter((Node) loader.load());
        Scene scene = new Scene(rootLayout, 1100, 800);
        scene.getStylesheets().add("layout/layout.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
        controller.initialize();
    }

    public static void main(String[] args) {
        //
        launch(args);
    }

}

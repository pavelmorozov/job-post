package jobpost;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import jobpost.intel.Engine;

public class Jobpost extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/MainForm.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Job post");
        stage.setScene(scene);
        stage.show();
    }	
	
	public static void main(String[] args) {
		//UI
		launch(args);
		
		
		// Starts application engine
	//	Engine appEngine = new Engine(); 
	//	appEngine.postJobs();
	}

}

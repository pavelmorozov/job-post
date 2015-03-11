package jobpost;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jobpost.ui.MainController;
import jobpost.ui.SettingsFile;
import jobpost.ui.Task;

public class Jobpost extends Application{
	
	private ObservableList<Task> tasksList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("ui/MainForm.fxml"));
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("ui/MainForm.fxml"));
        
        Scene scene = new Scene(loader.load());
        
        stage.setTitle("Job post");
        stage.setScene(scene);
        stage.show();
        
        // Give the controller access to the main app.
        MainController controller = loader.getController();
        controller.setMainApp(this);        
        controller.loadTasksList();
    }	
	
	public static void main(String[] args) {
		//UI
		launch(args);
		
		
	// Starts application engine
	//	Engine appEngine = new Engine(); 
	//	appEngine.postJobs();
	}

	/**
     * Reads tasks
     */
    public ObservableList<Task> loadTasksList() {
    	SettingsFile.load(".//tasklist.ser", tasksList);
    	
//    	Task jobPostTask= new Task();
//    	jobPostTask.setActive(true);
//    	jobPostTask.setTestMode(true);    	
//    	jobPostTask.setLoginURL("A1");
//
//    	tasksList.add(jobPostTask);
//    	
//    	jobPostTask= new Task();
//    	jobPostTask.setActive(false);
//    	jobPostTask.setTestMode(true);    	
//    	jobPostTask.setLoginURL("A2");
//    	tasksList.add(jobPostTask);    	
//    	
//    	jobPostTask= new Task();
//    	jobPostTask.setActive(true);
//    	jobPostTask.setTestMode(true);    	
//    	jobPostTask.setLoginURL("A3");
//    	tasksList.add(jobPostTask);
    	
		return tasksList;    	
    }
    
    
    public ObservableList<Task> getTasksList() {
		return tasksList;    	
    }
}
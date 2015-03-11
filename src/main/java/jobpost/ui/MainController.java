package jobpost.ui;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jobpost.Jobpost;
import jobpost.JobsQueue;

import org.controlsfx.dialog.Dialogs;

public class MainController implements Initializable{
	
	 @FXML private TableView<Task> tableViewTasks;
	 @FXML private Button buttonCancel;
	 @FXML private CheckBox checkBoxActive;
	 @FXML private CheckBox checkBoxTestMode;
	 @FXML private TextField loginURL;
	 @FXML private TextField login;
	 @FXML private TextField password;
	 @FXML private TextField keyword;
	 @FXML private TextField questionsXML;
	 JobsQueue jobsQueue;
	 
    //@FXML
    //private Label label;
    
    // Reference to the main application.
    private Jobpost mainApp;    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }

//    
//    @FXML
//    private void handleSaveTask(ActionEvent event){
//        System.out.println("SaveTask!");
//        
//        Task jobPostTask = new Task();
//        
//        jobPostTask.setActive(checkBoxActive.isSelected());
//        jobPostTask.setTestMode(checkBoxTestMode.isSelected());
//        jobPostTask.setLoginURL(loginURL.getText());
//        jobPostTask.setLoginURL(login.getText());
//        jobPostTask.setLoginURL(password.getText());
//        jobPostTask.setLoginURL(keyword.getText());
//        jobPostTask.setLoginURL(questionsXML.getText());
//        
//        ObservableList<Task> tasksList = tableViewTasks.getItems();
//        
//        Stage stage = (Stage) buttonCancel.getScene().getWindow();
//        stage.close();        
//    }
//    
//    @FXML
//    private void handleCancelSaveTask(ActionEvent event){
//        System.out.println("CancelSaveTask!");
//        Stage stage = (Stage) buttonCancel.getScene().getWindow();
//        stage.close();        
//    }
    
    public void showEditDialog(Task task, String mode) {
        //open form
        try{
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTaskForm.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Job post task");
	        stage.setScene(new Scene(root1));  
	        stage.show();
	        // Give the controller access to the main app.
	        EditTaskController editController = fxmlLoader.getController();
	        editController.setMainFormController(this);
	        editController.setTask(task);
	        editController.setMode(mode);
	        //editController.loadTasksList();
        }catch(IOException e){
        	e.printStackTrace();
        }    	
    }
    
    public void refreshTable(){
	   	 tableViewTasks.getColumns().get(0).setVisible(false);
	   	 tableViewTasks.getColumns().get(0).setVisible(true);
    }
    
    @FXML
    private void handleEditTask(ActionEvent event) {
    	Task taskSelected = tableViewTasks.getSelectionModel().getSelectedItem();
    	 if (taskSelected != null){
    		 showEditDialog(taskSelected, "edit");
    		 System.out.println("Edit "+tableViewTasks.getSelectionModel().getSelectedItem().getLoginURL());
    	 }else{
    		 noSelectionDialog();
    	 } 
    }
    
    private void noSelectionDialog(){
		 // Nothing selected.
		 Dialogs.create()
				.title("No Selection")
				.masthead("No Task Selected")
				.message("Please select a Task in the table.")
				.showWarning();    	
    }
    
    public void runJobsQueue(){
    	jobsQueue = new JobsQueue();
    	
    	List<Task> taskQueue = new LinkedList(tableViewTasks.getItems());
    	
    	jobsQueue.setTasksList(taskQueue);
    	Thread jobsQueueThread = new Thread(jobsQueue);
    	jobsQueueThread.start();
    } 
    
    @FXML
    private void handleRunActive(ActionEvent event) {
    	System.out.println("Run!");
    	runJobsQueue();
    }    
        
    @FXML
    private void handleStop(ActionEvent event) {
    	System.out.println("Stop!");
    	jobsQueue.setStopThread(true);
    }       
    @FXML
    private void handleAddTask(ActionEvent event) {
        System.out.println("Add!");
        Task task = new Task();
        showEditDialog(task, "add");
    }    

    @FXML
    private void handleDeleteTask(ActionEvent event) {
        int selectedRow = tableViewTasks.getSelectionModel().getSelectedIndex();
        System.out.println("Delete task!: " + selectedRow);
        ObservableList<Task> tasksList = tableViewTasks.getItems();
        if (selectedRow >= 0 ) {
        	tasksList.remove(selectedRow);
        }else{
        	noSelectionDialog();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Jobpost mainApp) {
        this.mainApp = mainApp;
    }
    
    /**
     * Loads tasks list to tableView
     */
    public void loadTasksList(){
        // Add observable list data to the table
        tableViewTasks.setItems(mainApp.loadTasksList());
    }
    
    public ObservableList<Task> getTasksList(){
    	return tableViewTasks.getItems();
    } 
}

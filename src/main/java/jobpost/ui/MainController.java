package jobpost.ui;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jobpost.Jobpost;
import jobpost.JobsQueue;

import org.controlsfx.dialog.Dialogs;
/**
 * Main form controller 
 */
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
	 @FXML private TextArea logText;
	 @FXML private GridPane anchorPane;
	 /**
	  * This label listener fires when form should append new messages
	  */
	 @FXML private Label messageLabel;
	 /**
	  * Tasks from main form are processed in separate thread
	  */
	 JobsQueue jobsQueue;
	 /**
	  * Messages from other threads passed as queue. 
	  */
	 Queue<String> messageQueue;
	 
    /**
     * Reference to the main application.
     */
    private Jobpost mainApp;    
    
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
    
    /**
     * Workaround to refresh table, when new task appears  
     */
    public void refreshTable(){
	   	 tableViewTasks.getColumns().get(0).setVisible(false);
	   	 tableViewTasks.getColumns().get(0).setVisible(true);
    }
    
    @FXML
    private void handleEditTask(ActionEvent event) {
    	Task taskSelected = tableViewTasks.getSelectionModel().getSelectedItem();
    	 if (taskSelected != null){
    		 sendToLog("Edit "+tableViewTasks.getSelectionModel().getSelectedItem().getLoginURL());
    		 showEditDialog(taskSelected, "edit");
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
    	logText.clear();
    	sendToLog("Attempt to run JobsQueue");
    	Stage stage = (Stage) anchorPane.getScene().getWindow();
    	
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
            	jobsQueue.setStopThread(true);
            }
        });      	
    	
    	messageLabel.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
    	        //System.out.println("text changed !");
    	        //System.out.println(messageLabel.getText());
    			//logText.appendText("\n"+messageLabel.getText());
    			while (!messageQueue.isEmpty()){
    				String s = messageQueue.poll();
    				sendToLog(s);
    			}
    	    }
    	});
    	
    	jobsQueue = new JobsQueue();
    	
    	List<Task> taskQueue = new LinkedList(tableViewTasks.getItems());
    	jobsQueue.setTasksList(taskQueue);
    	messageQueue = new LinkedList<String>();
    	jobsQueue.setMessageQueue(messageQueue);
    	jobsQueue.setMainController(this);
    	Thread jobsQueueThread = new Thread(jobsQueue);
    	messageLabel.textProperty().bind(jobsQueue.messageProperty());    	
    	jobsQueueThread.start();
    } 
    
    @FXML
    private void handleRunActive(ActionEvent event) {
    	sendToLog("Run!");
    	runJobsQueue();
    }    
        
    @FXML
    private void handleStop(ActionEvent event) {
    	sendToLog("Stop!");
    	if (jobsQueue!=null) jobsQueue.setStopThread(true);
    }
    
    public void sendToLog(String message){
    	System.out.println(message);
    	logText.appendText("\n"+message);
    	logText.setScrollLeft(0);
    }
    
    @FXML
    private void handleAddTask(ActionEvent event) {
    	sendToLog("Add!");
        Task task = new Task();
        showEditDialog(task, "add");
    }    

    @FXML
    private void handleDeleteTask(ActionEvent event) {
        int selectedRow = tableViewTasks.getSelectionModel().getSelectedIndex();
        sendToLog("Delete task!: " + selectedRow);
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

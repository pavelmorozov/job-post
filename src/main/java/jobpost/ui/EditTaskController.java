package jobpost.ui;

import java.io.IOException;
import java.net.URL;
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

public class EditTaskController implements Initializable{

	MainController mainFormController;
	Task jobPostTask;
	String mode;
	
	 @FXML private Button buttonCancel;
	 @FXML private CheckBox checkBoxActive;
	 @FXML private CheckBox checkBoxTestMode;
	 @FXML private TextField loginURL;
	 @FXML private TextField login;
	 @FXML private TextField password;
	 @FXML private TextField keyword;
	 @FXML private TextField questionsXML;
	 
    //@FXML
    //private Label label;
    
    // Reference to the main application.
    private Jobpost mainApp;    

    public void setTask(Task jobPostTask){
    	this.jobPostTask = jobPostTask;
    	checkBoxActive.setSelected(jobPostTask.getActive());
    	checkBoxTestMode.setSelected(jobPostTask.getTestMode());
    	loginURL.setText(jobPostTask.getLoginURL());
    	login.setText(jobPostTask.getLogin());
    	password.setText(jobPostTask.getPassword());
    	keyword.setText(jobPostTask.getKeywords());
    	questionsXML.setText(jobPostTask.getQuestionsXml());
    }
    
    public void setMainFormController(MainController mainFormController){
    	this.mainFormController = mainFormController; 
    }
    
    public void setMode(String mode){
    	this.mode = mode;
    }
    
    @FXML
    private void handleSaveTask(ActionEvent event){
        System.out.println("SaveTask!");
        
        //Task jobPostTask = new Task();
        
        jobPostTask.setActive(checkBoxActive.isSelected());
        jobPostTask.setTestMode(checkBoxTestMode.isSelected());
        jobPostTask.setLoginURL(loginURL.getText());
        jobPostTask.setLogin(login.getText());
        jobPostTask.setPassword(password.getText());
        jobPostTask.setKeywords(keyword.getText());
        jobPostTask.setQuestionsXml(questionsXML.getText());
        
        ObservableList<Task> tasksList = mainFormController.getTasksList();
        
        //add Task to list if necessary
        if (mode.equals("add")){
        	tasksList.add(jobPostTask);
        }else if(mode.equals("edit")){
        	//tasksList.setAll(jobPostTask);
        	mainFormController.refreshTable();
        }  
        
        Button b = (Button)event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        //Stage stage = (Stage) buttonCancel.getScene().getWindow();
        
        SettingsFile.save(".//tasklist.ser", tasksList);
        
        stage.close();        
    }
    
    @FXML
    private void handleCancelSaveTask(ActionEvent event){
        System.out.println("CancelSaveTask!");
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();        
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}

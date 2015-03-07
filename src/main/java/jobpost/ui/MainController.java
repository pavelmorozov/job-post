package jobpost.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable{
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    @FXML
    private void handleAddTask(ActionEvent event) {
        System.out.println("Add!");
        //open form
        try{
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTaskForm.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        //stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("Job post task");
	        stage.setScene(new Scene(root1));  
	        stage.show();
        }catch(IOException e){
        	e.printStackTrace();
        }
        
        
    }    

    @FXML
    private void handleDeleteTask(ActionEvent event) {
        System.out.println("Delete!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}

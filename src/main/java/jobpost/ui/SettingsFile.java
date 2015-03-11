package jobpost.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;

public class SettingsFile {

	private static class TaskSerializible implements Serializable{
		private static final long serialVersionUID = 6529685098267757777L;
		private Boolean active, testMode;
		private String loginURL, login, password, 
		keywords, questionsXml, progress;
		
		public TaskSerializible(Task t) {
			active = t.getActive();
			testMode = t.getTestMode();
			loginURL = t.getLoginURL();
			login = t.getLogin();
			password = t.getPassword();
			keywords = t.getKeywords();
			questionsXml = t.getQuestionsXml();
		}
		
		public void fill(Task t){
			t.setActive(active);
			t.setKeywords(keywords);
			t.setLogin(login);
			t.setLoginURL(loginURL);
			t.setPassword(password);
			t.setQuestionsXml(questionsXml);
			t.setTestMode(testMode);
		}		
	}
	
	public static void save(String fileName, ObservableList<Task> data){
		List<TaskSerializible> l = new LinkedList<TaskSerializible>();
		//convert to serializible
		for (Task t: data){
			TaskSerializible ts = new TaskSerializible(t);
			l.add(ts);
		}
		
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectOutputStream oos = null;
		try {
			if (fout!=null){
				oos = new ObjectOutputStream(fout);
				oos.writeObject(l);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	public static ObservableList<Task> load(String fileName, ObservableList<Task> data){
		List<TaskSerializible> l = null;
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectInputStream ois = null;
		try {
			if (fin!=null){
				ois = new ObjectInputStream(fin);
				l = (List<TaskSerializible>) ois.readObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		data.removeAll();
		Task t;
		//put list to data object
		for (TaskSerializible ts : l){
			t = new Task();
			ts.fill(t);
			data.add(t);
		}
		
		return data;
	}
}

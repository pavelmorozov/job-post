package jobpost;

import java.util.List;

import javafx.collections.ObservableList;
import jobpost.intel.Engine;
import jobpost.ui.Task;

public class JobsQueue extends Thread{
	boolean stopThread;
	List<Task> tasksList;
	Thread engineThread;
	Engine engine;
	
	public JobsQueue(){
		stopThread = false;
		tasksList = null;
	}
	
	public void run(){
		if (tasksList!=null){
	        for (Task jobPostTask: tasksList){
	        	if (stopThread){
	        		return;
	        	}
	        	engine = new Engine(jobPostTask);
	        	engineThread = new Thread(engine);
	        	engineThread.start();
	        	try {
					engineThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }			
		}
	}

	public boolean isStopThread() {
		return stopThread;
	}

	public void setStopThread(boolean stopThread) {
		this.stopThread = stopThread;
		engine.setStopProcess(stopThread);
	}

	public List<Task> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<Task> tasksList) {
		this.tasksList = tasksList;
	}
}

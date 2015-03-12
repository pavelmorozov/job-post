package jobpost;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Queue;

import jobpost.intel.Engine;
import jobpost.ui.MainController;
import jobpost.ui.Task;

public class JobsQueue extends javafx.concurrent.Task{
	boolean stopThread;
	List<Task> tasksList;
	Thread engineThread;
	Engine engine;
	MainController mainController;
	Queue<String> messageQueue;
	
	public JobsQueue(){
		stopThread = false;
		tasksList = null;
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

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void setMessageQueue(Queue<String> messageQueue){
		this.messageQueue = messageQueue;
	}
	
	public void setMessage(String message){
		messageQueue.add(message);
		updateMessage(message);
	}
	
	@Override
	protected Object call() throws Exception {
		if (tasksList!=null){
	        for (Task jobPostTask: tasksList){
	        	
	        	setMessage("========= New task ! =========");
	        	setMessage(jobPostTask.getTestMode().toString());
	        	setMessage(jobPostTask.getLoginURL());
	        	setMessage(jobPostTask.getLogin());
	        	setMessage(jobPostTask.getKeywords());
	        	setMessage("==============================");

	        	if (stopThread){
	        		return null;
	        	}
	        	engine = new Engine(jobPostTask);
	        	engine.setMainController(mainController);
	        	engine.setJobsQueue(this);
	        	engineThread = new Thread(engine);
	        	engineThread.start();
	        	try {
					engineThread.join();
				} catch (InterruptedException e) {
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					setMessage(errors.toString());
				}
	        }			
		}
		return null;
	}
}

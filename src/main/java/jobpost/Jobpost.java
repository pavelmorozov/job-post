package jobpost;

import jobpost.intel.Engine;

public class Jobpost {

	public static void main(String[] args) {
		// Starts application engine
		Engine appEngine = new Engine(); 
		appEngine.postJobs();
	}

}

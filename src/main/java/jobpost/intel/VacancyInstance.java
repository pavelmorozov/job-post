package jobpost.intel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;

class VacancyInstance {
	private String jobTitle;
	private String jobId;
	private String jobLocation;
	private String jobDatePosted;
	private String applyButtonUrl;
	
	public int hashCode() {
		return Integer.parseInt(getJobId());
	}
	
	public boolean equals(Object v) {

		if ( this == v) return true;
		
	    if (!(v instanceof VacancyInstance))
            return false;
	    
        if (((VacancyInstance)v).getJobId().equals(getJobId())){
            return true;		
        }else{return false;}
        
	}
	
	public VacancyInstance(String id){
		jobId = id; 
	}
	
	public VacancyInstance(){
	}
	
	public void setJobIdByUrl(String url){
		//takes job ID from url parameter
		Pattern p = Pattern.compile("(.*job=)(.*)");
		Matcher m = p.matcher(url);
		if (m.find()) {
			jobId = m.group(2);
		}else{
			jobId="";
		}
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobLocation() {
		return jobLocation;
	}
	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}
	public String getJobDatePosted() {
		return jobDatePosted;
	}
	public void setJobDatePosted(String jobDatePosted) {
		this.jobDatePosted = jobDatePosted;
	}
	public String getApplyButtonUrl() {
		return applyButtonUrl;
	}
	public void setApplyButtonUrl(String applyButtonUrl) {
		this.applyButtonUrl = applyButtonUrl;
	}
	
	
	
}

package jobpost.ui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
//	private static final boolean testMode = true;
//	private static final String LOGIN_URL = "https://intel.taleo.net/careersection/10000/mysubmissions.ftl";
//	private static final String LOGIN = "pavelmorozov@mail.ru";
//	private static final String PASSWORD = "gEsjhf35";
//	private static final String KEYWORD = "electrical engineer";
//	/**
//	 * File that contains predefined questions and answers
//	 */
//	private static final String QUESTIONS_XML = "Questions.xml";	
	
	private BooleanProperty active;
	private BooleanProperty testMode;
	private SimpleStringProperty loginURL;
	private SimpleStringProperty login;
	private SimpleStringProperty password;
	private SimpleStringProperty keywords;
	// File that contains predefined questions and answers
	private SimpleStringProperty questionsXml;
	// progress indicator data
	private SimpleStringProperty progress;

	public Task(){
		setActive(false);
		setTestMode(false);
		setLoginURL("http://");
		setLogin("");
		setPassword("");
		setKeywords("");
		setQuestionsXml("");
		setProgress("");
	}
	
    public BooleanProperty testModeProperty() {
        return testMode;
    }
	public final Boolean getTestMode() {
		if (testMode!=null){
			return testMode.get();
		}else{
			return null;
		}		
	}
	public final void setTestMode(Boolean testMode) {
		this.testMode = new SimpleBooleanProperty(testMode);
	}
    public BooleanProperty activeProperty() {
        return active;
    }
	public final Boolean getActive() {
		if (active!=null){
			return active.get();
		}else{
			return null;
		}		
	}
	public final void setActive(Boolean active) {
		this.active = new SimpleBooleanProperty(active);
	}
    public SimpleStringProperty loginURLProperty() {
        return loginURL;
    }
	public final String getLoginURL() {
		if (loginURL!=null){
			return loginURL.get();
		}else{
			return null;
		}
	}
	public final void setLoginURL(String loginURL) {
		this.loginURL = new SimpleStringProperty(loginURL);
	}
    public SimpleStringProperty loginProperty() {
        return login;
    }
	public final String getLogin() {
		if (login!=null){
			return login.get();
		}else{
			return null;
		}
	}
	public final void setLogin(String login) {
		this.login = new SimpleStringProperty(login);
	}
    public SimpleStringProperty passwordProperty() {
        return password;
    }
	public final String getPassword() {
		if (password!=null){
			return password.get();
		}else{
			return null;
		}		
	}
	public final void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}
    public SimpleStringProperty keywordsProperty() {
        return keywords;
    }
	public final String getKeywords() {
		if (keywords!=null){
			return keywords.get();
		}else{
			return null;
		}		
	}
	public final void setKeywords(String keywords) {
		this.keywords = new SimpleStringProperty(keywords);
	}
    public SimpleStringProperty questionsXmlProperty() {
        return questionsXml;
    }
	public final String getQuestionsXml() {
		if (questionsXml!=null){
			return questionsXml.get();
		}else{
			return null;
		}		
	}
	public final void setQuestionsXml(String questionsXml) {
		this.questionsXml = new SimpleStringProperty(questionsXml);
	}
    public SimpleStringProperty progressProperty() {
        return progress;
    }
	public final String getProgress() {
		if (progress!=null){
			return progress.get();
		}else{
			return null;
		}		
	}
	public final void setProgress(String progress) {
		this.progress = new SimpleStringProperty(progress);
	}
}

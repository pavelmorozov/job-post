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
	
    public BooleanProperty testModeProperty() {
        return testMode;
    }
	public final Boolean getTestMode() {
		return testMode.get();
	}
	public final void setTestMode(Boolean testMode) {
		this.testMode = new SimpleBooleanProperty(testMode);
	}
	
    public BooleanProperty activeProperty() {
        return active;
    }
	public final Boolean getActive() {
		return active.get();
	}
	public final void setActive(Boolean active) {
		this.active = new  SimpleBooleanProperty(active);
	}

    public SimpleStringProperty loginURLProperty() {
        return loginURL;
    }
	public final String getLoginURL() {
		return loginURL.get();
	}
	public final void setLoginURL(String loginURL) {
		this.loginURL = new SimpleStringProperty(loginURL);
	}
    public SimpleStringProperty loginProperty() {
        return login;
    }
	public final String getLogin() {
		return login.get();
	}
	public final void setLogin(String login) {
		this.login = new SimpleStringProperty(login);
	}
    public SimpleStringProperty passwordProperty() {
        return password;
    }
	public final String getPassword() {
		return password.get();
	}
	public final void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}
    public SimpleStringProperty keywordsProperty() {
        return keywords;
    }
	public final String getKeywords() {
		return keywords.get();
	}
	public final void setKeywords(String keywords) {
		this.keywords = new SimpleStringProperty(keywords);
	}
    public SimpleStringProperty questionsXmlProperty() {
        return questionsXml;
    }
	public final String getQuestionsXml() {
		return questionsXml.get();
	}
	public final void setQuestionsXml(String questionsXml) {
		this.questionsXml = new SimpleStringProperty(questionsXml);
	}
    public SimpleStringProperty progressProperty() {
        return progress;
    }
	public final String getProgress() {
		return progress.get();
	}
	public final void setProgress(String progress) {
		this.progress = new SimpleStringProperty(progress);
	}
}

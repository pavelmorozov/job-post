package jobpost.intel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jobpost.JobsQueue;
import jobpost.ui.MainController;
import jobpost.ui.Task;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Engine class used to operate with browser.
 * To start run postJobs() method
 * @author P
 *
 */
public class Engine  extends javafx.concurrent.Task{

//	private boolean testMode = true;
//	private String LOGIN_URL = "https://intel.taleo.net/careersection/10000/mysubmissions.ftl";
//	private String LOGIN = "pavelmorozov@mail.ru";
//	private String PASSWORD = "gEsjhf35";
//	private String KEYWORD = "electrical engineer";
//	/**
//	 * File that contains predefined questions and answers
//	 */
//	private String QUESTIONS_XML = "Questions.xml";
	/**
	 * In test mode application will not finish, the last "submit will not be
	 * pressed.
	 */	
	private boolean testMode = true;
	private String loginURL = "";
	private String login = "";
	private String password = "";
	private String keyword = "";
	/**
	 * File that contains predefined questions and answers
	 */
	private String questionsXML = "Questions.xml";

	private WebDriver webDriver;
	private WebDriverWait webDriverWait;
	private Set<VacancyInstance> appliedVacancies = new HashSet<VacancyInstance>();
	private Map<String, Question> questions = null;
	private boolean stopProcess;
	private MainController mainController;
	private JobsQueue jobsQueue;
	
	@Override
	protected Object call() throws Exception {
		postJobs();
		return null;
	}	
	
	public Engine(Task jobPostTask){
		testMode = jobPostTask.getTestMode();
		loginURL = jobPostTask.getLoginURL();
		login = jobPostTask.getLogin();
		password = jobPostTask.getPassword();
		keyword = jobPostTask.getKeywords();
		questionsXML = jobPostTask.getQuestionsXml();
		stopProcess = false;
	}
	
	public void setMainController(MainController mainController){
		this.mainController = mainController; 
	}	
	
	public void setStopProcess(boolean stopProcess){
		this.stopProcess = stopProcess;
	}

	private String currentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private boolean login() {
		try {
			jobsQueue.setMessage(currentTime() + " Connect to: " + loginURL);
			webDriver.navigate().to(loginURL);

			jobsQueue.setMessage(currentTime() + " Start login with user: "
					+ login);
			// login
			webDriver
					.findElement(
							By.id("dialogTemplate-dialogForm-StatementBeforeAuthentificationContent-ContinueButton"))
					.click();

			webDriverWait.until(ExpectedConditions
					.visibilityOfElementLocated(By
							.id("dialogTemplate-dialogForm-login-name1")));

			webDriver.findElement(
					By.id("dialogTemplate-dialogForm-login-name1")).sendKeys(
					login);
			webDriver.findElement(
					By.id("dialogTemplate-dialogForm-login-password"))
					.sendKeys(password);
			webDriver.findElement(
					By.id("dialogTemplate-dialogForm-login-defaultCmd"))
					.click();
			return true;
		} catch (WebDriverException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean search() {
		try{
			jobsQueue.setMessage(currentTime() + " Start search with: " + keyword);
			// keyword
			webDriver.findElement(By.id("topNavInterface.jobSearchTabAction"))
					.click();
			webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By
					.id("progressIndicator")));
			webDriverWait.until(ExpectedConditions.elementToBeClickable(By
					.id("jobs")));
			webDriver.findElement(By.id("KEYWORD")).sendKeys(keyword);

			webDriver.findElement(By.id("search")).click();
			
			webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By
					.id("progressIndicator")));
			webDriverWait.until(ExpectedConditions.elementToBeClickable(By
					.id("jobs")));
			
			//Location - USA
			webDriver.findElement(By.xpath(
					"//div[@id = 'filter-LOCATION']"
					+ "//span[contains(text(),'United States')]/../.."
					+ "/div[contains(@class, 'checkboxp')]")).click();;

			webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By
					.id("progressIndicator")));
			webDriverWait.until(ExpectedConditions.elementToBeClickable(By
					.id("jobs")));
			return true;
		}catch(WebDriverException e){
			e.printStackTrace();
			return false;
		}
	}

	private boolean processJobsPage() {
		try{
			jobsQueue.setMessage(currentTime() + " Start jobs process");

			// list jobs
			webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By
					.id("progressIndicator")));
			List<VacancyInstance> vacancyInstancesList = new LinkedList<VacancyInstance>();
			List<WebElement> vacanciesNodes = webDriver.findElements(By
					.xpath("//div[@id='jobsTableContainer']/descendant::"
							+ "tr[not(contains(@class,'headers'))]"));

			jobsQueue.setMessage("vacancies find: " + vacanciesNodes.size());

			for (WebElement vacancyNode : vacanciesNodes) {

				VacancyInstance vacancy = new VacancyInstance();

				vacancy.setJobTitle(vacancyNode.findElement(By.xpath(".//td[2]"))
						.getText());
				vacancy.setJobIdByUrl(vacancyNode.findElement(
						By.xpath(".//td[2]/descendant::a")).getAttribute("href"));
				vacancy.setJobLocation(vacancyNode
						.findElement(By.xpath(".//td[3]")).getText());
				vacancy.setJobDatePosted(vacancyNode.findElement(
						By.xpath(".//td[4]")).getText());
				vacancy.setApplyButtonUrl(vacancyNode.findElement(
						By.xpath(".//td[5]/descendant::a")).getAttribute("href"));
				vacancy.setApplyButtonText(vacancyNode.findElement(
						By.xpath(".//td[5]/descendant::a")).getText());
				
				vacancyInstancesList.add(vacancy);

				//System.out.println(vacancy.getJobId());
			}

			//System.out.println(currentTime() + " Will apply: " + vacancyInstancesList.size() + " vacancies." );

			for (VacancyInstance vacancy : vacancyInstancesList) {
				if (!stopProcess){
					applyVacancy(vacancy);
				}else{
					//System.out.println("Stop command accepted!");
				}
			}
			return true;
		}catch(WebDriverException e){
			e.printStackTrace();
			return false;
		}
	}

	private boolean applyVacancy(VacancyInstance vacancy) {
		try{
			
			if (vacancy.getApplyButtonText().equals("View/Edit Submission")){
				jobsQueue.setMessage("This vacancy: "+ vacancy.getJobId()+" "+ 
						vacancy.getJobTitle()  +", applied already. Skip.");
				return false;
			}
			
			jobsQueue.setMessage(currentTime()+" Apply to: " + vacancy.getJobId() + " "
					+ vacancy.getJobTitle() + " " + vacancy.getApplyButtonUrl());

			webDriver.navigate().to(vacancy.getApplyButtonUrl());
			
			webDriverWait
			.until(ExpectedConditions.visibilityOfElementLocated(By
			.xpath("//span[@class='infojob']")));
			
			jobsQueue.setMessage("Process \"My info\"");
			try {

				//WebElement spanMyInfo = webDriver.findElement(By.xpath("//div[@class='datatrain-focus']"
				//		+ "//span[contains(text(),'My Info')]"));
				
				Boolean spanMyInfoPresent = (webDriver.findElements(By.xpath("//div[@class='datatrain-focus']"
						+ "//span[contains(text(),'My Info')]")).size()>0);
				if (!spanMyInfoPresent){
					webDriver.findElement(By.linkText("My Info")).click();
					webDriverWait
						.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath("//span[@class='mastercontentpanel']//span[contains(text(),'My Info')]")));				
				} 

				webDriver
						.findElement(By
						.xpath("//input[contains(@id,'et-ef-content-ftf-saveContinueCmdBottom')]"))
						.click();
				
			} catch (Exception e) {
				jobsQueue.setMessage("not passed \"My info\" page");
				//e.printStackTrace();
				return false;
			}

			
			jobsQueue.setMessage("Process \"Prescreening\"");
			try {
				webDriverWait
						.until(ExpectedConditions.visibilityOfElementLocated(By
								.xpath("//span[@class='mastercontentpanel']//span[contains(text(),'Prescreening')]")));
				/*
				 * Find all questions on page, and loop through. Find questions in
				 * stored list, and fill answers
				 */
				List<WebElement> questionsWEList = webDriver.findElements(By
						.xpath("//span[@class='questionnaire']/fieldset"));
				for (WebElement questionWE : questionsWEList) {
					//System.out.println(questionWE.getText());
					WebElement questionSpanWE = questionWE.findElement(By
							.xpath(".//span[@class='description']"));
					Question q = questions.get(questionSpanWE.getText());
					if (q != null) {
						if (q.getQuestionType().equals("select")) {
							WebElement selectWE = questionWE.findElement(By
									.xpath(".//select"));
							Select select = new Select(selectWE);
							select.selectByVisibleText(q.getAnswers().iterator()
									.next());
						}
						if (q.getQuestionType().equals("selectmultiple")) {
							WebElement selectWE = questionWE.findElement(By
									.xpath(".//select"));
							Select select = new Select(selectWE);
							select.deselectAll();
							for (String answer : q.getAnswers()) {
								select.selectByVisibleText(answer);
							}
						}

						if (q.getQuestionType().equals("radio")) {
							List<WebElement> selectWE = questionWE.findElements(By
									.xpath(".//input"));
							for (WebElement input : selectWE) {
								WebElement labelWE = input.findElement(By
										.xpath(".."));
								if (q.getAnswers().contains(labelWE.getText())) {
									input.click();
								}
							}
						}

						if (q.getQuestionType().equals("checkbox")) {
							// uncheck all previous answers
							List<WebElement> children = questionWE.findElements(By
									.cssSelector("input:checked[type='checkbox']"));
							for (WebElement we : children)
								we.click();
							// put answers to checkboxes
							List<WebElement> selectWE = questionWE.findElements(By
									.xpath(".//input"));
							for (WebElement input : selectWE) {
								WebElement labelWE = input.findElement(By
										.xpath(".."));
								//System.out.println("Answers " + labelWE.getText());
								if (q.getAnswers().contains(labelWE.getText())) {
									input.click();
								}
							}
						}
					}else{
						jobsQueue.setMessage("Question not found: "+questionSpanWE.getText());
					}
				}

				webDriver
						.findElement(
								By.xpath("//input[contains(@id,'et-ef-content-ftf-saveContinueCmdBottom')]"))
						.click();
			} catch (Exception e) {
				jobsQueue.setMessage("not passed \"Prescreening\" page");
				//e.printStackTrace();
				return false;
			}

			jobsQueue.setMessage("Process \"Attachments\"");
			try {
				webDriverWait
						.until(ExpectedConditions.visibilityOfElementLocated(By
								.xpath("//span[@class='mastercontentpanel']//span[contains(text(),'Attachments')]")));
				webDriver
						.findElement(
								By.xpath("//input[contains(@id,'editTemplateMultipart-editForm-content-ftf-saveContinueCmdBottom')]"))
						.click();
			} catch (Exception e) {
				jobsQueue.setMessage("not passed \"Attachments\" page");
				//e.printStackTrace();
				return false;
			}

			jobsQueue.setMessage("Process \"Summary\"");
			try {
				webDriverWait
						.until(ExpectedConditions.visibilityOfElementLocated(By
								.xpath("//span[@class='mastercontentpanel']//span[contains(text(),'Summary')]")));

				// Apply when not in test mode
				if (!testMode) {
					// press submit button
					jobsQueue.setMessage("While not in test mode, press \"Submit\" button");
					webDriver
							.findElement(
									By.xpath("//input[contains(@id,'et-ef-content-ftf-submitCmdBottom')]"))
							.click();
					// wait until submit finish
					webDriverWait
							.until(ExpectedConditions.visibilityOfElementLocated(By
									.xpath("//h1[@class='no-change-header']//span[contains(text(),'Thank you')]")));
				}else{
					jobsQueue.setMessage("While in test mode, will not press \"Submit\" button");
				}
			} catch (Exception e) {
				jobsQueue.setMessage("not passed \"Summary\" page");
				//e.printStackTrace();
				return false;
			}

			jobsQueue.setMessage(currentTime()+" Application ended sucssesfully!");
			return true;			
		}catch (WebDriverException e){
			//e.printStackTrace();
			return false;
		}
	}

	public void setJobsQueue(JobsQueue jobsQueue){
		this.jobsQueue = jobsQueue;
	}	
	
	public void postJobs() {
		jobsQueue.setMessage(currentTime() + " Create web driver");

		webDriver = new FirefoxDriver();
		webDriver.manage().window().maximize();
		webDriverWait = new WebDriverWait(webDriver, 10);

		Boolean flagDone = true;
		
		if (flagDone) flagDone = login();

		if (flagDone) flagDone = search();

		//To create xml config file uncomment this line
		//Question.saveExampleXML(QUESTIONS_XML);
		
		//Load questions hash map from xml config
		questions = Question.readQuestionsXML(questionsXML);

//		VacancyInstance vacancy = new VacancyInstance();
//		vacancy.setApplyButtonUrl("https://intel.taleo.net/careersection/application.jss?type=1&lang=en&portal=660190084&reqNo=874196");
//		applyVacancy(vacancy);
		
		if (flagDone) flagDone=processJobsPage();
		
		webDriver.quit();
	}
}

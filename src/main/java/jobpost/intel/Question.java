package jobpost.intel;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Question{
	private String questionText;
	private String questionType;
	private Set<String> answers;
	private static HashMap<String, Question> questions = null;

	/**
	 * reads questions and answers from xml file
	 */
	public static Map<String, Question> readQuestionsXML(String fileName){
		Map<String, Question> readQuestions = new HashMap<String, Question>();
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(fileName)));
			readQuestions = (Map<String, Question>) decoder.readObject();
		} catch (Exception e) {

		} finally {
			if (decoder != null) {
				decoder.close();
			}
		}
		
		//		Iterator<Map.Entry<String, Question>> iterator = readQuestions
		//		.entrySet().iterator();
		//while (iterator.hasNext()) {
		//	Map.Entry<String, Question> entry = iterator.next();
		//	System.out.println(entry.getKey());
		//	System.out.println(entry.getValue().toString());
		//}
		
		
		return readQuestions;
	}
	
	/**
	 * Creates example XML file
	 * @param fileName
	 * @return
	 */
	public static void saveExampleXML(String fileName){
		questions = new HashMap<String, Question>();

		Question q = new Question();
		q.setQuestionText("Can you, on your first day of employment, submit verification of eligibility of the legal right to work in the country or countries in which you are interested?");
		q.setQuestionType("select");
		Set<String> a = new HashSet<String>();
		a.add("Yes");// 1-yes
		q.setAnswers(a);

		questions.put(q.getQuestionText(), q);

		q = new Question();
		q.setQuestionText("You have expressed interest in a US based location; please designate your US worker status as 'A' or 'B'. All candidates fall into only one category; it is critical that you answer this question correctly.");
		q.setQuestionType("select");
		a = new HashSet<String>();
		a.add("(A) US Citizen or National; Permanent Resident; Refugee or Granted Asylum");
		q.setAnswers(a);
		questions.put(q.getQuestionText(), q);

		q = new Question();
		q.setQuestionText("If you answered B for question #1, please indicate what category(ies) currently apply to you. If you answered A, please select “N/A” as this is a required question.");
		q.setQuestionType("selectmultiple");
		a = new HashSet<String>();
		a.add("N/A; US Citizen or National; Permanent Resident; Refugee or Granted Asylum");
		q.setAnswers(a);
		questions.put(q.getQuestionText(), q);

		q = new Question();
		q.setQuestionText("How many years of industry experience in the areas of software prototyping, systems integration, wireless communications, and computer programming do you have?");
		q.setQuestionType("radio");
		a = new HashSet<String>();
		a.add("2-4 years");
		q.setAnswers(a);
		questions.put(q.getQuestionText(), q);

		q = new Question();
		q.setQuestionText("Please select those skills you have working experience with. Multiple selections allowed.");
		q.setQuestionType("checkbox");
		a = new HashSet<String>();
//		a.add("Hands-on experience in software architecture and design, object-oriented programming, design patterns and their implementation");
		a.add("Strong coding skills in multiple systems languages such as Java, C, C++, etc.");
		a.add("System software experience with various hardware platforms, mobile devices, wearables, Intel Architecture based and SoC platforms");
		q.setAnswers(a);
		questions.put(q.getQuestionText(), q);
		
		q = new Question();
		q.setQuestionText("Please select which skills you have working experience with. (multiple selections allowed)");
		q.setQuestionType("checkbox");
		a = new HashSet<String>();
		a.add("Strong knowledge and experience of RF/Analog circuits, with ability to drive architecture and topology choices");
		a.add("Strong knowledge and experience on lower power/ultra-low power RF architecture and design");
		a.add("Background and experience in latest design, integration and timing flow and methodology");
		q.setAnswers(a);
		questions.put(q.getQuestionText(), q);
		
		q = new Question();
		q.setQuestionText("What is your highest level of education?");
		q.setQuestionType("radio");
		a = new HashSet<String>();
		a.add("Master's degree");
		q.setAnswers(a);
		questions.put(q.getQuestionText(), q);				
				
		// Write XML
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream(fileName)));
			encoder.writeObject(questions);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (encoder != null) {
				encoder.close();
			}
		}		
	}

	@Override
	public String toString(){
		
		String questionData="[ Question:\n"+
				"questionText :" + questionText + "\n" +
				"questionType :" + questionType + "\n" +
				"answers :";
		
		String delim = "";
		for (String answer:answers){
			questionData +=  delim + answer;
			delim = ","; 
		}
				
		questionData += " ]";
		
		return questionData;
		
	}
	
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		//list
		//checkbox
		this.questionType = questionType;
	}
	public Set<String> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<String> answers) {
		this.answers = answers;
	}
}

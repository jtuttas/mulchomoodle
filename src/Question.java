import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.util.LinkedList;

import javax.xml.bind.DatatypeConverter;

public class Question {

	LinkedList answers;
	String text;
	String imageFile=null;
	String topic;
	
	public Question(String to,String t,String i) {
		text=t;
		if (i!="") imageFile=i;
		topic=to;
		answers = new LinkedList();
	}
	
	public void addAnswer(Answer a) {
		answers.add(a);
	}
	
	
	public String getImageFile() {
		return imageFile;
	}
	
	
	public float getCorrectFraction() {
		int n=0;
		for (int i=0;i<answers.size();i++) {
			Answer a = (Answer)answers.get(i);
			if (a.isCorrect()) n++;
		}
		if (n==0)return 0; 
		else {
			long f=100000/n;
			return (float)f/1000;
		}
		
	}
	
	
	public int getCorrectAntwers() {
		int n=0;
		for (int i=0;i<answers.size();i++) {
			Answer a = (Answer)answers.get(i);
			if (a.isCorrect()) n++;
		}
		return n;
		
	}
	
	public float getIncorrectFraction() {
		int n=0;
		for (int i=0;i<answers.size();i++) {
			Answer a = (Answer)answers.get(i);
			if (!a.isCorrect()) n++;
		}
		if (n==0)return 0; 
		else {
			long f=100000/n;
			return (float)f/1000;
		}
	}
	
	
	public static String format(String s) {
		String os="";
		for (int i=0;i<s.length();i++) {
			if (s.charAt(i)=='<') os=os+"&lt;";
			else if (s.charAt(i)=='>') os=os+"&gt;";
			else os=os+s.charAt(i);
		}
		return os;
	}
	
	public String toString() {
		return text;
	}
	
	public String toMoodleString(boolean displayCorrect,int grade, float penalty, File f) {
		String s="<question type=\"multichoice\">\n";
		s=s+"<name>\n";
		if (displayCorrect) {
			s=s+"<text>"+"("+getCorrectAntwers()+") "+topic+"</text>\n";
		}
		else {
			s=s+"<text>"+topic+"</text>\n";
		}
		s=s+"</name>\n";
		s=s+"<questiontext format=\"html\">\n";
		s=s+"<text>\n";
		if (displayCorrect) {
			s=s+"<![CDATA["+Question.format("("+getCorrectAntwers()+") "+text);			
		}
		else {
			s=s+"<![CDATA["+Question.format(text);
		}
		if (imageFile!=null) {
			s=s+"<p><img src=\"@@PLUGINFILE@@/"+this.getImageFile()+"\" alt=\"\" /></p>";
		}
		
		
		s=s+"]]></text>\n";
		if (imageFile!=null) {
			//s=s+"<image>"+imageFile+"</image>\n";
			s=s+"<file name=\""+imageFile+"\" encoding=\"base64\">"+this.getEncoding(f)+"</file>\n";
		}
        s=s+"</questiontext>\n";
        s=s+"<generalfeedback>\n";
        s=s+"<text></text>\n";
        s=s+"</generalfeedback>\n";
        s=s+"<defaultgrade>"+grade+"</defaultgrade>\n";
        s=s+"<penalty>"+penalty+"</penalty>\n";
        s=s+"<hidden>0</hidden>\n";
        s=s+"<shuffleanswers>true</shuffleanswers>\n";
        s=s+"<single>false</single>\n";
        s=s+"<correctfeedback>\n";
        s=s+"<text></text>\n";
        s=s+"</correctfeedback>\n";
        s=s+"<partiallycorrectfeedback>\n";
        s=s+"<text></text>\n";
        s=s+"</partiallycorrectfeedback>\n";
        s=s+"<incorrectfeedback>\n";
        s=s+"<text></text>\n";
        s=s+"</incorrectfeedback>\n";
        s=s+"<answernumbering>abc</answernumbering>\n";
        
    	float correct = this.getCorrectFraction(); 
    	float incorrect = this.getIncorrectFraction(); 
        for (int i=0;i<answers.size();i++) {
        	Answer a = (Answer)answers.get(i);
        	s=s+a.toString(correct,incorrect);
        }
        
        s=s+"</question>\n";
        return s;
	}

	
	private String getEncoding(File f) {
		File fi = new File(f.getAbsoluteFile()+"\\"+this.getImageFile());
		try {
			byte[] fileContent;
			fileContent = Files.readAllBytes(fi.toPath());
			return DatatypeConverter.printBase64Binary(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "null";
	}
	
	
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}
}

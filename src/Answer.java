
public class Answer {

	String text;
	boolean state;
	
	public Answer (String a,boolean s) {
		text=a;
		state=s;
	}
	
	public boolean isCorrect() {
		return state;
	}
	
	
	
	public String toString(float fc,float fic) {
		if (this.isCorrect()) {
			return "<answer fraction=\""+fc+"\">\n<text>\n<![CDATA["+Question.format(text)+"]]>\n</text>\n<feedback>\n<text></text>\n</feedback>\n</answer>\n";
		}
		else {
			return "<answer fraction=\"-"+fic+"\">\n<text>\n<![CDATA["+Question.format(text)+"]]>\n</text>\n<feedback>\n<text></text>\n</feedback>\n</answer>\n";
			
		}
	}
}

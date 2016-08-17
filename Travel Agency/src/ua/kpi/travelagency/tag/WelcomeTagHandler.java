package ua.kpi.travelagency.tag;

import java.io.IOException;

import javax.servlet.jsp.tagext.TagSupport;

public class WelcomeTagHandler extends TagSupport {

	private String login;
	private String message;
	
	public void setLogin(String login){
		this.login = login;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public int doStartTag(){
        try {
            pageContext.getOut().write(message +", " + login + "!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return SKIP_BODY;
    }
	
}

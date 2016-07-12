package ua.kpi.travelagency.bean;

import java.io.Serializable;

public class Users implements Serializable {

	private int usersId;
	private String login;
	private String password;
	private int userType;
	private String typeName;

	public Users() {

	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	public String getTypeName(){
		return typeName;
	}
	
	public void setTypeName(String typeName){
		this.typeName = typeName;
	}
	
	

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("ua.kpi.jdbc.bean.Users: ");
		res.append("usersId=").append(usersId);
		res.append(", login=").append(login);
		res.append(", password=").append(password);
		res.append(", userType=").append(userType);
		res.append(", typeName=").append(typeName);
		return res.toString();
	}
}

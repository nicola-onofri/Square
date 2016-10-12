package pajc.square.model;

import java.util.ArrayList;
import java.util.Date;

public class Comment {
	private String text;
	private User user;
	private Date date;
	private ArrayList<String> hashtags;

	//Comment constructor
	public Comment(String text, User user, Date date, ArrayList<String> hashtags) {
		this.text = text;
		this.user = user;
		this.date = date;
		this.hashtags = new ArrayList<>();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(ArrayList<String> hashtags) {
		this.hashtags = hashtags;
	}

}

package pajc.square.model;

import java.util.Date;

public class Comment {
	private String text;
	private User user;
	private Post post;
	private Date date;

	// Comment constructor
	public Comment(String text, User user, Post post, Date date) {
		this.text = text;
		this.user = user;
		this.post = post;
		this.date = date;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
}

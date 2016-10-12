package pajc.square.model;

import java.util.Date;

public class Notification {
	private User sender;
	private User receiver;
	private Post likedPost;
	private Date date;
	private boolean newFollower;
	private boolean seen;

	// Notification Constructor
	public Notification(User sender, User receiver, Post likedPost, boolean newFollower, boolean seen, Date date) {
		this.sender = sender;
		this.receiver = receiver;
		this.likedPost = likedPost;
		this.newFollower = newFollower;
		this.seen = seen;
		this.date = date;
	}

	// Getters and Setters
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Post getLikedPost() {
		return likedPost;
	}

	public void setLikedPost(Post likedPost) {
		this.likedPost = likedPost;
	}

	public boolean isNewFollower() {
		return newFollower;
	}

	public void setNewFollower(boolean newFollower) {
		this.newFollower = newFollower;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
}

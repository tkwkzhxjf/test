package model;

public class RequestBook {
private String title;
private String content;
private String name;
private String date;
private int no;

	/*
	 * public RequestBook(String title, String content, String name) { super();
	 * this.title = title; this.content = content; this.name = name; }
	 */
public RequestBook(String title, String content, String name, String date) {
	super();
	this.title = title;
	this.content = content;
	this.name = name;
	this.date = date;
}

	/*
	 * public RequestBook(String title, String content, String name, int no) {
	 * super(); this.title = title; this.content = content; this.name = name;
	 * this.no = no; }
	 */


public RequestBook(String title, String content, String name, String date, int no) {
	super();
	this.title = title;
	this.content = content;
	this.name = name;
	this.date = date;
	this.no = no;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getNo() {
	return no;
}
public void setNo(int no) {
	this.no = no;
}


}

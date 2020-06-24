package model;

public class Notice {
	private String title;
	private String content;
	private String date;
	private int no;
	
	
	public Notice(String title, String content, String date, int no) {
		super();
		this.title = title;
		this.content = content;
		this.date = date;
		this.no = no;
	}


	
	public Notice(String title, String content, String date) {
		super();
		this.title = title;
		this.content = content;
		this.date = date;
	}






	@Override
	public String toString() {
		return "Notice [title=" + title + ", content=" + content + ", date=" + date + ", no=" + no + "]";
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


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}
	
	
	
	
	
	
}

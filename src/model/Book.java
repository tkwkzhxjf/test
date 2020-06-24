package model;

public class Book {
	private String isbn;
	private String title;
	private String category;
	private String writer;
	private String company;
	private String date;
	private String fileimg;
	private String information;
	private boolean rental;

	public Book(String isbn, String title, String category, String writer, String company, String date,
			String information, boolean rental) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.category = category;
		this.writer = writer;
		this.company = company;
		this.date = date;
		this.information = information;
		this.rental = rental;
	}

	public Book(String isbn, String title, String category, String writer, String company, String date, String fileimg,
			String information, boolean rental) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.category = category;
		this.writer = writer;
		this.company = company;
		this.date = date;
		this.fileimg = fileimg;
		this.information = information;
		this.rental = rental;
	}


	public Book() {
		super();
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFileimg() {
		return fileimg;
	}

	public void setFileimg(String fileimg) {
		this.fileimg = fileimg;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public boolean isRental() {
		return rental;
	}

	public void setRental(boolean rental) {
		this.rental = rental;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", category=" + category + ", writer=" + writer
				+ ", company=" + company + ", date=" + date + ", fileimg=" + fileimg + ", information=" + information
				+ ", rental=" + rental + ", getIsbn()=" + getIsbn() + ", getTitle()=" + getTitle() + ", getCategory()="
				+ getCategory() + ", getWriter()=" + getWriter() + ", getCompany()=" + getCompany() + ", getDate()="
				+ getDate() + ", getFileimg()=" + getFileimg() + ", getInformation()=" + getInformation()
				+ ", isRental()=" + isRental() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	
	
	
}

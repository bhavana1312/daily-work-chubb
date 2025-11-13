import java.util.*;

public class Book {
	private String title;
	private List<String> authors;
	private double price;
	private String publisher;
	private Date dateOfPublication;

	public Book(String title, List<String> authors, double price, String publisher, Date dateOfPublication) {
		this.title = title;
		this.authors = authors;
		this.price = price;
		this.publisher = publisher;
		this.dateOfPublication = dateOfPublication;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthor() {
		return authors;
	}

	public void setAuthor(List<String> authors) {
		this.authors = authors;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDateOfPublication() {
		return dateOfPublication;
	}

	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}

	public void displayDetails() {
		System.out.println("Title:" + title);
		System.out.print("Authors:");
		for (String author : authors) {
			System.out.print(author + " ");
		}
		System.out.println();
		System.out.println("Prive:" + price);
		System.out.println("Publisher:" + publisher);
		System.out.println("Date of Publication"+dateOfPublication);
	}

	public static void main(String[] args) {
		ArrayList<String> authors = new ArrayList<String>();
		authors.add("Bhavana");
		Book b1 = new Book("Tinkle", authors, 400.00, "Keerthika", new Date());
		b1.displayDetails();
	}

}

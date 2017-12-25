package luocaca.studentdemo.Model;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class Book {

    public String book_id = "10086";
    public String number;
    public String detail;
    public String name;


    @Override
    public String toString() {
        return "Book{" +
                "book_id='" + book_id + '\'' +
                ", number='" + number + '\'' +
                ", detail='" + detail + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

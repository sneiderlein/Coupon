package coupon.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Coupon {

    /*
     *Attributes
     */

    private long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private CouponType type;
    private String message;
    private double price;
    private String imagePath;

    /*
     * Constructors
     */

    public Coupon(
            String title,
            LocalDate startDate,
            LocalDate endDate,
            CouponType type,
            int amount,
            double price,
            String imagePath,
            String message

    ) {
        this.imagePath = imagePath;
        this.title = title;
        this.endDate = endDate;
        this.amount = amount;
        this.startDate = startDate;
        this.price = price;
        this.type = type;
        this.message = message;
    }

    /*
    ** Getters/Setters
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /*
    Methods
     */



    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

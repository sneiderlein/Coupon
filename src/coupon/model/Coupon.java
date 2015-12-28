package coupon.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Coupon {

    /*
     *Attributes
     */

    private long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private int amount;
    private CouponType type;
    private String message;
    private double price;
    private String imagePath;

    /*
     * Constructors
     */

    public Coupon(


              long id,
              String title,
              Date endDate,
              int amount,
              Date startDate,
              CouponType type,
              String message,
              double price,
              String imagePath

    ) {
        this.id = id;
        this.title = title;
        this.endDate = endDate;
        this.amount = amount;
        this.startDate = startDate;
        this.type = type;
        this.message = message;
        this.price = price;
        this.imagePath = imagePath;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

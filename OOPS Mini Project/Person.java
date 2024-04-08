package management;

import java.util.Date;

public abstract class Person {
    private String name;
    private Date dob;
    private String address;

    public Person(String name, Date dob, String address) {
        this.name = name;
        this.dob = dob;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    abstract public void getReport();
    abstract public int getId();
}

package dto;

public class Contact {
    private String Name;
    private String Phone;
    public Contact(String Name, String Phone){
        this.setName(Name);
        this.setPhone(Phone);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}

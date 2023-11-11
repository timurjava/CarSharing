package carsharing.datalayer.company;



public class Company  {
    String name;
    int id;

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

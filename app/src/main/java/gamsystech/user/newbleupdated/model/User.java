package gamsystech.user.newbleupdated.model;

public class User
{
    private long id;
    private String mobileno;
    private String password;

    public User()
    {

    }

    /*parameterized constructor*/
    public User(long id, String mobileno, String password)
    {
        this.id = id;
        this.mobileno = mobileno;
        this.password = password;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getMobileno()
    {
        return mobileno;
    }

    public void setMobileno(String mobileno)
    {
        this.mobileno = mobileno;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    /*object to string representation*/
    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", mobileno='" + mobileno + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

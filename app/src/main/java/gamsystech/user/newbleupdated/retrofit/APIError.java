package gamsystech.user.newbleupdated.retrofit;

/**
 * Created BY Siddharth.
 */

public class APIError
{
    private String error;

    public APIError()
    {
        this.error = null;
    }

    public APIError(String error)
    {
        this.error = error;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}

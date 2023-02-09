package requests;

public class UserToken {
    public String accessToken;
    public String getAccessToken(){
        return  accessToken;
    }
    public  void setAccessToken(){
        this.accessToken = accessToken;
    }
    public String refreshToken;
    public String getRefreshToken(){
        return  refreshToken;
    }
    public  void setRefreshToken(){
        this.refreshToken = refreshToken;
    }

}

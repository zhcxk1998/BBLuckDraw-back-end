public class userInfo {
    public userInfo(){}

    public userInfo(String username, String password,String prize){
        this.username = username;
        this.password = password;
        this.prize = prize;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    private String username;
    private String password;
    private String prize;
}

package sample.model.data;

public class Data {
    private String userName;
    private int highScore;
    public Data(){}

    public Data(String userName, int highScore) {
        this.userName = userName;
        this.highScore = highScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return "Data{" +
                "userName='" + userName + '\'' +
                ", highScore=" + highScore +
                '}';
    }
}

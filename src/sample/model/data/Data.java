package sample.model.data;

import sample.model.BotMap;
import sample.model.PlayerMap;

public class Data {
    private String userName;
    private int highScore = 0;
    private int stt;

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

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


    // tinh diem
//    public void  increaseScore() {
//        this.highScore += 15;
//    }
//    public void decreaseScore() {
//        this.highScore -= 10;
//    }

    @Override
    public String toString() {
        return "Data{" +
                "userName='" + userName + '\'' +
                ", highScore=" + highScore +
                '}';
    }
}

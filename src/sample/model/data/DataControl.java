package sample.model.data;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class DataControl {
    private FileWriter fileWriter; // ghi cac dữ liệu theo định dạng kí tự của một file;
    private BufferedWriter bufferedWriter;  // ghi File
    private PrintWriter printWriter;
    private Scanner scanner; // đọc dư liệu;

    //ghi doi tượng vào file
    public void openFileToWrite(String fileName) {
        try {
            fileWriter = new FileWriter(fileName, true); // mở file để thêm dữ liệu vào cuối file;
            bufferedWriter = new BufferedWriter(fileWriter); //đưa nội dung vào buffer trước khi ghi vào file;
            printWriter = new PrintWriter(bufferedWriter); // khi buffer đầy đẩy nội dung vào trong file;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeFileAfterWrite(String fileName) throws IOException {
        printWriter.close();
        bufferedWriter.close();
        fileWriter.close();
    }

    public void writeDataToFile(Data data, String fileName) throws IOException {
        openFileToWrite(fileName);
        printWriter.println(data.getUserName() + "|" + data.getHighScore() + "|");
        closeFileAfterWrite(fileName);
    }


    // doc file
    public void openFileToRead(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {     //file chua ton tai
                file.createNewFile();
            }
            scanner = new Scanner(Paths.get(fileName), "UTF-8");  // Paths.get(): duong dan
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String fileName) {
        scanner.close();
    }

    public ArrayList<Data> readReaderFromFile(String fileName) {
        openFileToRead(fileName);
        ArrayList<Data> datas = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String tmp = scanner.nextLine();
            Data data = createDataFromTMP(tmp);
            datas.add(data);
        }
        closeFileAfterRead(fileName);
        // sap xep danh sach
        Collections.sort(datas, new Comparator<Data>() {
            @Override
            public int compare(Data sv1, Data sv2) {
                if (sv1.getHighScore() < sv2.getHighScore()) {
                    return 1;
                } else {
                    if (sv1.getHighScore() == sv2.getHighScore()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }
        });
        // thêm số thứ tự
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setStt(i+1);
        }

        return datas;
    }





    private Data createDataFromTMP(String tmp) {
        String[] tmps = tmp.split("\\|");
        Data data = new Data(tmps[0], Integer.parseInt(tmps[1]));
        return data;
    }


    // reset file
    public void resetFile(String fileName) throws IOException{
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        openFileToWrite(fileName);
        closeFileAfterWrite(fileName);
    }

}

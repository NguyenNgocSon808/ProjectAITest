import view.*;
import static machineMoveChoice.SelectMove.*;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i <= 21; i++) {
            for (int j = 0; j <= 21; j++) {
                if(i == 21 || j == 21) {
                    E[i][j] = -1; // Biên
                    map[i][j] = -1;
                } else {
                    E[i][j] = 0;
                    map[i][j] = 0;
                }
            }
        }
        new AIVsAIView();
        // Khởi tạo mảng E
    }
}

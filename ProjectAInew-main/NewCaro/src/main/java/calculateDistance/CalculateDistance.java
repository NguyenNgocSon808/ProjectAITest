package calculateDistance;


import static moveSet.Advance.*;
import static moveSet.Move.*;

import java.util.ArrayList;
import java.util.List;

import machineMoveChoice.ContinuousATK;
import machineMoveChoice.SelectMove;
import moveSet.Point;
public class CalculateDistance {
    public static int turz = 0;
    public static List<Point> CanATK = new ArrayList<>();
    public static List<Point> A = new ArrayList<>();
    public static int mark = 0;
    public static int cal(Point tmp, int[][] E, int a,int turn) {
        //turz = turn;
        int score = 0;
        int b = 0;
        if(a == 1){
            b = 2;
        }
        if(a == 2){
            b = 1;
        }
        int alpha = checkSurround(tmp, E, 1); // dùng để check với X

        int beta = checkSurround(tmp, E, 2);  // dùng để check với O

        int gamma = checkSurround(tmp, E, 3); // dùng để check với X trong lượt của O (xét khả năng tấn công)

        int epsilon = findBestloc2(tmp, E, 2);

        // ATK
        // Hàng ngang
        score += checkHangNgang(tmp, E, a);
        // Hàng dọc
        score += checkHangDoc(tmp, E, a);
        // Đường chéo 1
        score += checkDuongcheo1(tmp, E, a);
        // Đường chéo 2
        score += checkDuongcheo2(tmp, E, a);

        // DEF
        // Hàng ngang
        score += checkHangNgang(tmp, E, b);
        // Hàng dọc
        score += checkHangDoc(tmp, E, b);
        // Đường chéo 1
        score += checkDuongcheo1(tmp, E, b);
        // Đường chéo 2
        score += checkDuongcheo2(tmp, E, b);

        if(a == 2 && beta == 7){
            score = 0;
        }

        if (a == 1) {
            if (score < 50000) {
                if(alpha != 0){
                    if(alpha == 1 || alpha == 3){
                        mark = 1;
                    }
                    score += 20000;
                    return score;
                }
            }
        }

        if (a == 2) {
            if (score <50000) {
                // Thứ tự xét
                //      1) Đánh các nước kết hợp 3,4 hoặc nước 4 chưa bị chặn
                //      2) Chặn các nước kết hợp 3,4 hoặc nước 3 chưa bị chặn của địch
                //      3) Đánh các nước kết hợp 3,3
                //      4) Chặn các nước kết hợp 3,3 của địch
                //      5) Đánh các nước tạo thế cờ
                //      6) Chặn các nước tạo thế cờ của địch

                //      1) Đánh các nước kết hợp 3,4 hoặc nước 4 chưa bị chặn
                if (beta == 1 || beta == 2 || beta == 3 ) {
//                    System.out.println(tmp.getX() + " " + tmp.getY());
//                    System.out.println(beta);

                    // beta = 1 : nước 4 bị chặn 1 đầu
                    // beta = 2 : nuớc 3 chưa bị chặn 2 đầu
                    // beta = 3 : nước kết hợp 3 + 4
                    score += 40000;

                    return score; // Dừng hàm ngay khi có chiến thắng tuyệt đối
                }

                //      2) Chặn các nước kết hợp 3,4 hoặc nước 3 chưa bị chặn của địch
                if(gamma == 1 || gamma == 2 || gamma == 3){
                    //System.out.println(gamma);
                    // gamma được xét sau khi giả sử X đánh vào vị trí xét
                        // gamma = 1 : nước 4 bị chặn 1 đầu
                        // gamma = 2 : nuớc 3 chưa bị chặn 2 đầu
                        // gamma = 3 : nước kết hợp 3 + 4
                        // beta = 5: tạo được nước 3 hoặc 4 sau khi O đánh
                    //System.out.println(gamma);
                    score += 14000;
                    if(beta == 5){
                        score += 4000;
                    }
                    return score;
                }
                if(gamma == 5 && beta == 5){
                    // gamma = 5: nước 3 chưa bị chặn dạng _EX_XXE_ và X đánh vào 1 trong 2 vị trí E
                    // beta = 5: tạo được nước 3 hoặc 4 sau khi O đánh
                    score += 10000;
                    return score;
                }

                //      3) Đánh các nước kết hợp 3,3
                if (beta == 4) {
                    // beta = 4: tạo được nước 2 nước 3
                    score += 8000;
                    return score;
                }

                //      4) Chặn các nước kết hợp 3,3 của địch
                if(gamma == 4){
                    // gamma = 4: tạo được nước 2 nước 3
                    score += 6000;
                    return score;
                }
                //      5) Đánh các nước tạo thế cờ
                //      6) Chặn các nước tạo thế cờ của địch
                score += epsilon;
            }
        }
        if(turn == 1 && score >= 50000){
            ContinuousATK.ContinuousATK.clear();
            ContinuousATK.ContinuousATK2.clear();
            SelectMove.check = 0;
            if(score >= 99999){
                SelectMove.check = 1;
            }
        }
        tmp.setScore(score);
        return score;
    }
    public static void firstLayer(int i, int j, int[][] E) {
        //System.out.println("firstLayer: " + i + " " + j);
        int[][] D = new int[21][21];

        // Đặt giá trị cho mảng D dựa trên trạng thái của E
        for (int a = 0; a <= 20; a++) {
            for (int b = 0; b <= 20; b++) {
                if (E[a][b] == 1 || E[a][b] == 2) {
                    D[a][b] = 1;
                } else {
                    D[a][b] = 0;
                }
            }
        }

        // Kiểm tra vùng xung quanh (các ô từ (i-1, j-1) đến (i+1, j+1))
        for (int a = i - 1; a < i + 2; a++) {
            for (int b = j - 1; b < j + 2; b++) {
                if (a >= 0 && b >= 0 && a <= 20 && b <= 20 && E[a][b] == 0 && D[a][b] == 0) {
                    Point tmp = new Point(a, b,0,0);
                    A.add(tmp);
                    D[a][b] = 1;  // Đánh dấu ô đã được xét
                }
            }
        }
    }

    // Hàm secondLayer tương đương với C++ code
    public static void secondLayer(int i, int j, int[][] E) {
        int[][] D = new int[21][21];

        // Đặt giá trị cho mảng D dựa trên trạng thái của E
        for (int a = 0; a <= 20; a++) {
            for (int b = 0; b <= 20; b++) {
                if (E[a][b] == 1 || E[a][b] == 2) {
                    D[a][b] = 1;
                } else {
                    D[a][b] = 0;
                }
            }
        }

        // Kiểm tra vùng xung quanh mở rộng (các ô từ (i-6, j-6) đến (i+6, j+6))
        for (int a = i - 5; a < i + 5; a++) {
            for (int b = j - 5; b < j + 5; b++) {
                if (a >= 0 && b >= 0 && a <= 20 && b <= 20 && E[a][b] == 0 && D[a][b] == 0) {
                    Point tmp = new Point(a, b,0,0);
                    A.add(tmp);
                    D[a][b] = 1;  // Đánh dấu ô đã được xét
                }
            }
        }
    }
}


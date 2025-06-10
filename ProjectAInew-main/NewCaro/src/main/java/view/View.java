package view;
import javax.swing.*;

import calculateDistance.Check;
import machineMoveChoice.ContinuousATK;
import machineMoveChoice.SelectMove;

import static machineMoveChoice.SelectMove.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    Color background_cl = Color.white;
    Color x_cl = Color.red;
    Color y_cl = Color.blue;
    int column = 20, row = 20, count = 0;
    int  Undo[][] = new int[column+2][row+2];
    boolean tick[][] = new boolean[column + 2][row + 2];
    int Size = 0;
    Container cn;
    JPanel pn, pn2;
    JLabel lb;
    JButton newGame_bt, undo_bt,show_bt;
    JFrame frame;
    private JButton b[][] = new JButton[column + 2][row + 2];

    public View(String gameDemo) {

        cn = this.getContentPane();
        pn = new JPanel();
        pn.setLayout(new GridLayout(column+1, row+1));
        for (int i = 0; i <= column + 1; i++)
            for (int j = 0; j <= row + 1; j++) {
                b[i][j] = new JButton(i +" " + j);
                b[i][j].setActionCommand(i + " " + j);
                b[i][j].setBackground(background_cl);
                b[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                b[i][j].setForeground(Color.lightGray);
                b[i][j].addActionListener(this);
                tick[i][j] = true;
            }
        for (int i = 0; i <= column; i++)
            for (int j = 0; j <= row; j++)
                pn.add(b[i][j]);
        lb = new JLabel("X Đánh Trước");
        lb.setFont(new Font("Arial", Font.BOLD, 24));
        newGame_bt = new JButton("New Game");
        undo_bt = new JButton("Undo");
        show_bt = new JButton("Show");
        newGame_bt.addActionListener(this);
        undo_bt.addActionListener(this);
        show_bt.addActionListener(this);
        cn.add(pn);
        pn2 = new JPanel();
        pn2.setLayout(new FlowLayout());
        pn2.add(lb);
        pn2.add(newGame_bt);
        pn2.add(undo_bt);
        pn2.add(show_bt);
        cn.add(pn2, "North");
        this.setVisible(true);
        this.setSize(1400, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        undo_bt.setEnabled(true);

        if(SelectMove.turn %1 == 0){
//           b[7][7].setText("O");
//           b[7][7].setForeground(y_cl);
//           b[7][7].setFont(new Font("Arial", Font.BOLD, 24));
//           tick[7][7] = false;
//           SelectMove.E[7][7] = 2;
 //           Các vị trí với "O" (số 2)
            int[][] oPositions = {
                    //Test 1
//                    {4, 2}, {5, 2}, {9, 3}, {6, 4}, {8, 4}, {5, 5}, {7, 5}, {10, 5},
//                    {7, 6}, {8, 6}, {10, 7}, {9, 8},{7,11},
//                    {7,4},{6,5},
//                    {10,16}
                    //Test 2
//                    {7,7},{7,8},{6,8},{8,8},{7,9},{6,9},{5,9}
                    //Test 3
            //        {7,10},{8,10},{8,11},{9,9},{9,10},{9,11},{10,8}

            };

// Các vị trí với "X" (số 1)
            int[][] xPositions = {
                    //Test 1
//                    {7, 2}, {5, 3}, {6, 3}, {10, 2}, {9, 4}, {8, 5}, {9, 6}, {3, 7},
//                    {8, 7}, {9, 7}, {10, 11}, {7, 13}, {8, 11}, {9, 11},
//                    {6,6},{7,7},
//                    {8,14},{9,15}
                    //Test 2
//                    {5,6},{4,7},{8,7},{8,9},{7,10},{4,10}
                    //Test 3
                    //{7,7},{7,8},{7,9},{6,11},{10,10},{9,12}
            };


// Cập nhật bảng với giá trị "O" và "X"
            for (int i = 0; i < oPositions.length; i++) {
                int row = oPositions[i][0];
                int col = oPositions[i][1];
                b[row][col].setText("O");
                b[row][col].setForeground(y_cl);
                b[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                tick[row][col] = false;
                SelectMove.E[row][col] = 2; // O là số 2
            }

            for (int i = 0; i < xPositions.length; i++) {
                int row = xPositions[i][0];
                int col = xPositions[i][1];
                b[row][col].setText("X");
                b[row][col].setForeground(x_cl); // Bạn có thể thay đổi màu nếu cần
                b[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                tick[row][col] = false;
                SelectMove.E[row][col] = 1; // X là số 1
            }




        }
    }
    public void addPoint(int i, int j) {

        b[locx][locy].setBackground(background_cl);
        if(SelectMove.E[i][j] != 0){ // chống đánh vào ô dã đánh
            return;
        }
        SelectMove.E[i][j] = 1;
        Undo[i][j] = SelectMove.startMove;
        b[i][j].setText("X");

        b[i][j].setForeground(x_cl);
        b[i][j].setFont(new Font("Arial", Font.BOLD, 24));
        tick[i][j] = false;
        if(Check.checkWin(i,j,E,1)){
            System.out.println("check");
            frame = new JFrame(); // Sửa: dùng biến instance, không tạo biến cục bộ
            frame.setSize(300, 150);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null); // Hiển thị giữa màn hình
            frame.setVisible(true);
            // Thêm nhãn thông báo
            JLabel label = new JLabel("You Have Win!", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 24));
            frame.add(label, BorderLayout.CENTER);

            // Tạo panel chứa 2 nút
            JPanel buttonPanel = new JPanel();
            JButton restartButton = new JButton("Restart");
            JButton quitButton = new JButton("Quit");
            restartButton.addActionListener(this);
            quitButton.addActionListener(this);
            buttonPanel.add(restartButton);
            buttonPanel.add(quitButton);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            for (int k = 0; k < 20; k++) {
                for (int l = 0; l < 20; l++) {
                    SelectMove.E[k][l] = 1;
                }
            }
        }
        
        lb.setText("Lượt Của O");
        lb.setFont(new Font("Arial", Font.BOLD, 24));
        SelectMove.machineTurn();
        SelectMove.E[locx][locy] = 2;
        Undo[locx][locy] = SelectMove.startMove;
        b[locx][locy].setText("O");
        b[locx][locy].setForeground(y_cl);
        b[locx][locy].setFont(new Font("Arial", Font.BOLD, 24));
        b[locx][locy].setBackground(null);
        tick[locx][locy] = false;
        lb.setText("Lượt Của X");
        if(Check.checkWin(locx,locy,E,2)){
            System.out.println("check");
            frame = new JFrame();
            frame.setSize(300, 150);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null); // Hiển thị giữa màn hình
            frame.setVisible(true);
            // Thêm nhãn thông báo
            JLabel label = new JLabel("You Have Lose!", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 24));
            frame.add(label, BorderLayout.CENTER);
            // Tạo panel chứa 2 nút
            JPanel buttonPanel = new JPanel();
            JButton restartButton = new JButton("Restart");
            restartButton.addActionListener(this);
            JButton quitButton = new JButton("Quit");
            quitButton.addActionListener(this);
            buttonPanel.add(restartButton);
            buttonPanel.add(quitButton);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            for (int k = 0; k < 20; k++) {
                for (int l = 0; l < 20; l++) {
                    SelectMove.E[k][l] = 1;
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Game") || e.getActionCommand().equals("Restart")) {
            for (int i = 0; i <= 20; i++) {
                for (int j = 0; j <= 20; j++) {
                    SelectMove.E[i][j] = 0;
                }
            }
            turn +=1;
            startMove = 0;
            if(frame != null){
                frame.setVisible(false);
            }
            ContinuousATK.ContinuousATK2.clear();
            ContinuousATK.ContinuousATK.clear();
            SelectMove.check = 0;
            new View("GAME DEMO");
            this.dispose();
        } else if (e.getActionCommand().equals("Exit") || e.getActionCommand().equals("Quit")) {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("Undo")) {

            if(SelectMove.startMove == 0){
                return;
            }
            for (int i = 0; i < column+2; i++) {
                for (int j = 0; j < row +2; j++) {
                    if(Undo[i][j] == SelectMove.startMove || (Undo[i][j] == SelectMove.startMove - 1 && SelectMove.E[i][j] == 1)){
                        Undo[i][j] = 0;
                        SelectMove.E[i][j] = 0;
                        b[i][j].setText(i + " " + j);
                        b[i][j].setBackground(background_cl);
                        b[i][j].setForeground(Color.lightGray);
                        b[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                        tick[i][j] = true;
                    }
                }
            }
            SelectMove.startMove -= 1;
        }
        else if(e.getActionCommand() == "Show") {
            for (int i = 0; i < column+2; i++) {
                for (int j = 0; j < row +2; j++) {
                    if (E[i][j] == 0) {
                        System.out.printf("%6.0f ",SelectMove.map[i][j]);
                    }
                    else if(E[i][j] == 1){
                        System.out.printf(ANSI_RED + "%6.0f " + ANSI_RESET, SelectMove.map[i][j]);
                    }
                    else{
                        System.out.printf(ANSI_BLUE + "%6.0f " + ANSI_RESET, SelectMove.map[i][j]);
//                        if(SelectMove.map[i][j] != 0){
//                            System.out.printf(ANSI_YELLOW + "%6.0f " + ANSI_RESET, SelectMove.map[i][j]);
//                        }
                    }
                }
                System.out.println();
            }
        }
        else {
            String s = e.getActionCommand();
            int k = s.indexOf(32); // lấy vị trí ký tự cách
            int i = Integer.parseInt(s.substring(0, k)); // lấy số đầu tiên trước cách
            int j = Integer.parseInt(s.substring(k + 1, s.length())); // lấy số sau cách
            if (tick[i][j]) {
                addPoint(i, j);
            }
        }
    }
}

package view;

import javax.swing.*;

import static machineMoveChoice.SelectMove.locx;
import static machineMoveChoice.SelectMove.locy;

import java.awt.*;
import minimax.Board;
import minimax.Minimax;
import calculateDistance.Check;
import machineMoveChoice.SelectMove;
import java.util.Random;

public class AIVsAIView extends JFrame {
    Color background_cl = Color.white;
    Color x_cl = Color.red;
    Color y_cl = Color.blue;
    int column = 20, row = 20;
    int Undo[][] = new int[column+2][row+2];
    boolean tick[][] = new boolean[column + 2][row + 2];
    Container cn;
    JPanel pn, pn2;
    JLabel lb;
    JButton newGame_bt, show_bt;
    private JButton b[][] = new JButton[column + 2][row + 2];

    Board board = new Board(column+2);

    // Track the AI game thread
    private Thread aiGameThread;

    public AIVsAIView() {
        cn = this.getContentPane();
        pn = new JPanel();
        pn.setLayout(new GridLayout(column+1, row+1));
        for (int i = 0; i <= column + 1; i++)
            for (int j = 0; j <= row + 1; j++) {
                b[i][j] = new JButton(i + " " + j);
                b[i][j].setBackground(background_cl);
                b[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                b[i][j].setForeground(Color.lightGray);
                tick[i][j] = true;
            }
        
        for (int i = 0; i <= column; i++)
            for (int j = 0; j <= row; j++)
                pn.add(b[i][j]);
        
        lb = new JLabel("AI vs AI");
        lb.setFont(new Font("Arial", Font.BOLD, 24));
        newGame_bt = new JButton("New Game");
        show_bt = new JButton("Show");
        newGame_bt.addActionListener(e -> restartGame());
        show_bt.addActionListener(e -> showBoard());
        cn.add(pn);
        pn2 = new JPanel();
        pn2.setLayout(new FlowLayout());
        pn2.add(lb);
        pn2.add(newGame_bt);
        pn2.add(show_bt);
        cn.add(pn2, "North");
        this.setVisible(true);
        this.setSize(1400, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        startAIGame();
    }    
    
    private void startAIGame() {
        // Stop any previous game thread
        if (aiGameThread != null && aiGameThread.isAlive()) {
            aiGameThread.interrupt();
        }
        aiGameThread = new Thread(() -> {
            int turn = 0; // 0: X (Minimax), 1: O (Algorithm)
            int moves = 0;
            int maxMoves = (column+1)*(row+1);
            int winner = 0;
            // initial delay before the first move
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            // Place Minimax's first move at a random empty cell
            // int firstX, firstY;
            // Random rand = new Random();
            // while (true) {
            //     firstX = rand.nextInt(column + 1);
            //     firstY = rand.nextInt(row + 1);
            //     if (SelectMove.E[firstX][firstY] == 0) break;
            // }
            // final int fx = firstX, fy = firstY;

            final int fx = column/2, fy = row/2; // Center position for first move
            
            SwingUtilities.invokeLater(() -> {
                addAIPoint(fx, fy, 1); // 1 = X
            });
            moves++;
            turn = 1; // Next is O
            
            while (moves < maxMoves && winner == 0) {
                // Add delay between moves
                try {
                    Thread.sleep(500); // Wait 2 seconds between moves
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                if (Thread.currentThread().isInterrupted()) return;
                final int currentTurn = turn;
                
                // Use SwingUtilities.invokeLater for UI updates
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        if (currentTurn == 0) {
                            Minimax minimax = new Minimax(board);
                            int[] mv = minimax.calculateNextMove(4);
                            if (mv != null) {
                                addAIPoint(mv[1], mv[0], 1); // 1 = X
                                if (Check.checkWin(mv[1], mv[0], SelectMove.E, 1)) {
                                    showGameResult(1); // X wins
                                }
                            }
                        } else {
                            int[] mv = getTaDucMove();
                            if (mv != null) {
                                addAIPoint(mv[0], mv[1], 2); // 2 = O
                                if (Check.checkWin(mv[0], mv[1], SelectMove.E, 2)) {
                                    showGameResult(2); // O wins
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                
                turn = 1 - turn;
                moves++;
            }
            
            // Show draw if no winner
            if (winner == 0) {
                SwingUtilities.invokeLater(() -> showGameResult(0)); // Draw
            }
        });
        aiGameThread.start();
    }
    
    private void showGameResult(int winner) {
        String result = (winner == 1) ? "Minimax (X) wins!" : 
                       (winner == 2) ? "TaDuc (O) wins!" : 
                       "Draw!";
        JOptionPane.showMessageDialog(this, result);
        // Exit the program after the dialog is closed
        System.exit(0);
    }

    private void addAIPoint(int i, int j, int player) {
        // Always update both SelectMove.E and the board
        if (SelectMove.E[i][j] != 0) return;
        SelectMove.E[i][j] = player;
        Undo[i][j] = SelectMove.startMove;
        b[i][j].setText(player == 1 ? "X" : "O");
        b[i][j].setForeground(player == 1 ? x_cl : y_cl);
        b[i][j].setFont(new Font("Arial", Font.BOLD, 24));
        tick[i][j] = false;
        board.addStoneNoGUI(i, j, player == 2);
    }

    private int[] getTaDucMove() {
        // Save current board state
        int[][] tempBoard = new int[column + 2][row + 2];
        for (int i = 0; i <= column + 1; i++) {
            for (int j = 0; j <= row + 1; j++) {
                tempBoard[i][j] = SelectMove.E[i][j];
            }
        }

        // Let TaDuc algorithm calculate next move
        SelectMove.machineTurn();
        int[] move = new int[]{locx, locy};

        // Restore board state since machineTurn() might modify it
        for (int i = 0; i <= column + 1; i++) {
            for (int j = 0; j <= row + 1; j++) {
                SelectMove.E[i][j] = tempBoard[i][j];
            }
        }

        // Return the selected move
        return move;
    }

    private void showBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= column; i++) {
            for (int j = 0; j <= row; j++) {
                sb.append(b[i][j].getText()).append(" ");
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Board State", JOptionPane.INFORMATION_MESSAGE);
    }

    private void restartGame() {
        // Interrupt the running AI game thread if any
        if (aiGameThread != null && aiGameThread.isAlive()) {
            aiGameThread.interrupt();
        }
        for (int i = 0; i <= column + 1; i++)
            for (int j = 0; j <= row + 1; j++) {
                SelectMove.E[i][j] = 0;
                Undo[i][j] = 0;
                b[i][j].setText(i + " " + j);
                b[i][j].setBackground(background_cl);
                b[i][j].setForeground(Color.lightGray);
                b[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                tick[i][j] = true;
            }
        board = new Board(column+2);
        startAIGame();
    }
}

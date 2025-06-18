package minimax;
import java.util.ArrayList;

public class Minimax {
	private Board board;

	public Minimax(Board board) {
		this.board = board;
	}
	
	public int[] calculateNextMove(int depth) {
		// board.thinkingStarted();
		int[] move = new int[2];
		long startTime = System.currentTimeMillis();
		Object[] bestMove = MarkCount.searchWinningMove(board);
		if(bestMove != null ) {
			move[0] = (Integer)(bestMove[1]);
			move[1] = (Integer)(bestMove[2]);
		} else {
			bestMove = minimaxSearchAB(depth, new Board(board), true, -1.0, MarkCount.getWinScore());
			if(bestMove[1] == null) {
				move = null;
			} else {
				move[0] = (Integer)(bestMove[1]);
				move[1] = (Integer)(bestMove[2]);
			}
		}
		System.out.println("Cases calculated: " + MarkCount.evaluationCount + " Calculation time: " + (System.currentTimeMillis() - startTime) + " ms");
		// board.thinkingFinished();
		MarkCount.evaluationCount=0;
		return move;
	}
	
	private static Object[] minimaxSearchAB(int depth, Board dummyBoard, boolean max, double alpha, double beta) {
		if(depth == 0) {
			Object[] x = {MarkCount.evaluateBoardForWhite(dummyBoard, !max), null, null};
			return x;
		}
		ArrayList<int[]> allPossibleMoves = dummyBoard.generateMoves();
		if(allPossibleMoves.size() == 0) {
			Object[] x = {MarkCount.evaluateBoardForWhite(dummyBoard, !max), null, null};
			return x;
		}
		Object[] bestMove = new Object[3];
		if(max) {
			bestMove[0] = -1.0;
			for(int[] move : allPossibleMoves) {
				dummyBoard.addStoneNoGUI(move[1], move[0], false);
				Object[] tempMove = minimaxSearchAB(depth-1, dummyBoard, false, alpha, beta);
				dummyBoard.removeStoneNoGUI(move[1],move[0]);
				if((Double)(tempMove[0]) > alpha) {
					alpha = (Double)(tempMove[0]);
				}
				if((Double)(tempMove[0]) >= beta) {
					return tempMove;
				}
				if((Double)tempMove[0] > (Double)bestMove[0]) {
					bestMove = tempMove;
					bestMove[1] = move[0];
					bestMove[2] = move[1];
				}
			}
		}
		else {
			bestMove[0] = 100_000_000.0;
			bestMove[1] = allPossibleMoves.get(0)[0];
			bestMove[2] = allPossibleMoves.get(0)[1];
			for(int[] move : allPossibleMoves) {
				dummyBoard.addStoneNoGUI(move[1], move[0], true);
				Object[] tempMove = minimaxSearchAB(depth-1, dummyBoard, true, alpha, beta);
				dummyBoard.removeStoneNoGUI(move[1],move[0]);
				if(((Double)tempMove[0]) < beta) {
					beta = (Double)(tempMove[0]);
				}
				if((Double)(tempMove[0]) <= alpha) {
					return tempMove;
				}
				if((Double)tempMove[0] < (Double)bestMove[0]) {
					bestMove = tempMove;
					bestMove[1] = move[0];
					bestMove[2] = move[1];
				}
			}
		}
		return bestMove;
	}
}

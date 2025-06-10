package minimax;
import java.util.ArrayList;

public class MarkCount {
    public static int evaluationCount = 0;
    private static final int winGuarantee = 1000000;
    private static final int winScore = 100000000;

    public static int getWinScore() {
        return winScore;
    }

    public static Object[] searchWinningMove(Board board) {
		ArrayList<int[]> allPossibleMoves = board.generateMoves();
		Object[] winningMove = new Object[3];
		for(int[] move : allPossibleMoves) {
			MarkCount.evaluationCount++;
			Board dummyBoard = new Board(board);
			dummyBoard.addStoneNoGUI(move[1], move[0], false);
			if(MarkCount.getScore(dummyBoard,false,false) >= MarkCount.getWinScore()) {
				winningMove[1] = move[0];
				winningMove[2] = move[1];
				return winningMove;
			}
		}
		return null;
	}

    public static double evaluateBoardForWhite(Board board, boolean blacksTurn) {
		evaluationCount++;
		
		double blackScore = MarkCount.getScore(board, true, blacksTurn);
		double whiteScore = MarkCount.getScore(board, false, blacksTurn);
		
		if(blackScore == 0) blackScore = 1.0;
		return whiteScore / blackScore;
	}

    public static int getScore(Board board, boolean forBlack, boolean blacksTurn) {
		int[][] boardMatrix = board.getBoardMatrix();
		return MarkCount.evaluateHorizontal(boardMatrix, forBlack, blacksTurn) +
				MarkCount.evaluateVertical(boardMatrix, forBlack, blacksTurn) +
				MarkCount.evaluateDiagonal(boardMatrix, forBlack, blacksTurn);
	}

    public static int evaluateHorizontal(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		int[] evaluations = {0, 2, 0};
		for(int i=0; i<boardMatrix.length; i++) {
			for(int j=0; j<boardMatrix[0].length; j++) {
				evaluateDirections(boardMatrix,i,j,forBlack,playersTurn,evaluations);
			}
			evaluateDirectionsAfterOnePass(evaluations, forBlack, playersTurn);
		}
		return evaluations[2];
	}
	
	public static  int evaluateVertical(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		int[] evaluations = {0, 2, 0};
		for(int j=0; j<boardMatrix[0].length; j++) {
			for(int i=0; i<boardMatrix.length; i++) {
				evaluateDirections(boardMatrix,i,j,forBlack,playersTurn,evaluations);
			}
			evaluateDirectionsAfterOnePass(evaluations,forBlack,playersTurn);
		}
		return evaluations[2];
	}

	public static  int evaluateDiagonal(int[][] boardMatrix, boolean forBlack, boolean playersTurn ) {
		int[] evaluations = {0, 2, 0};
		for (int k = 0; k <= 2 * (boardMatrix.length - 1); k++) {
		    int iStart = Math.max(0, k - boardMatrix.length + 1);
		    int iEnd = Math.min(boardMatrix.length - 1, k);
		    for (int i = iStart; i <= iEnd; ++i) {
		        evaluateDirections(boardMatrix,i,k-i,forBlack,playersTurn,evaluations);
		    }
		    evaluateDirectionsAfterOnePass(evaluations,forBlack,playersTurn);
		}
		for (int k = 1-boardMatrix.length; k < boardMatrix.length; k++) {
		    int iStart = Math.max(0, k);
		    int iEnd = Math.min(boardMatrix.length + k - 1, boardMatrix.length-1);
		    for (int i = iStart; i <= iEnd; ++i) {
				evaluateDirections(boardMatrix,i,i-k,forBlack,playersTurn,evaluations);
		    }
			evaluateDirectionsAfterOnePass(evaluations,forBlack,playersTurn);
		}
		return evaluations[2];
	}
	public static void evaluateDirections(int[][] boardMatrix, int i, int j, boolean isBot, boolean botsTurn, int[] eval) {
		if (boardMatrix[i][j] == (isBot ? 2 : 1)) {
			eval[0]++;
		}
		else if (boardMatrix[i][j] == 0) {
			if (eval[0] > 0) {
				eval[1]--;
				eval[2] += getConsecutiveSetScore(eval[0], eval[1], isBot == botsTurn);
				eval[0] = 0;
			}
			eval[1] = 1;
		}
		else if (eval[0] > 0) {
			eval[2] += getConsecutiveSetScore(eval[0], eval[1], isBot == botsTurn);
			eval[0] = 0;
			eval[1] = 2;
		} else {
			eval[1] = 2;
		}
	}
	private static void evaluateDirectionsAfterOnePass(int[] eval, boolean isBot, boolean playersTurn) {
		if (eval[0] > 0) {
			eval[2] += getConsecutiveSetScore(eval[0], eval[1], isBot == playersTurn);
		}
		eval[0] = 0;
		eval[1] = 2;
	}

	public static  int getConsecutiveSetScore(int count, int blocks, boolean currentTurn) {
		if(blocks == 2 && count < 5) return 0;
		switch(count) {
		case 5: {
			return winScore;
		}
		case 4: {
			if(currentTurn) return winGuarantee;
			else {
				if(blocks == 0) return winGuarantee/4;
				else return 200;
			}
		}
		case 3: {
			if(blocks == 0) {
				if(currentTurn) return 50_000;
				else return 200;
			}
			else {
				if(currentTurn) return 10;
				else return 5;
			}
		}
		case 2: {
			if(blocks == 0) {
				if(currentTurn) return 7;
				else return 5;
			}
			else {
				return 3;
			}
		}
		case 1: {
			return 1;
		}
		}
		return winScore*2;
	}
}

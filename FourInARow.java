import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class FourInARow {
	final static int row = 8;
	final static int col = 8;
	static Random rand = new Random();
	static Point board[][] = new Point[row][col];
	static int avaliablePlaces[] = new int [col];
	public enum States{
		BLUEWON , REDWON , NOONEWON ,TIE, HARD , MEDIUM , EASY ,  BLUE , RED , DEMO ,REAL
	}
	public static void main(String[] args) {
	initializeBard();
		new FourInARowGraphic();
	}
	public static void initializeBard() {
		for(int i =0; i< col; i ++)
			avaliablePlaces[i] = row-1;
	}
	public static States isWin(boolean turn, States state) {

		String color = "rrrr";
		String result = "";
		States conditon = States.REDWON;
		if(turn)
		{
			color = "bbbb";
			conditon = States.BLUEWON;
		}
		
		for(int i = row -1; i >=0; i-- ) // find if there 4 in a row
		{
			for(int j = 0 ; j < col ; j++)
			{
				if(board[i][j] == null)
					result+= "X";
				else
					result += board[i][j].getColor();

			}
			if( result.indexOf(color)!= -1)
			{
				if(state == States.REAL)  markWinner(i,result.indexOf(color),1);
				return conditon;
			}	
		}
		result = "";
		for(int j = 0; j < col; j++ ) // find if there 4 in a col
		{
			for(int i = 0 ; i   < row ; i++)
			{
				if(board[i][j] == null)
					result+= "X";
				else
					result += board[i][j].getColor();

			}
			if( result.indexOf(color)!= -1)
			{
				if(state == States.REAL) markWinner(result.indexOf(color),j,2);
					return conditon;

			}
			result = "";	

		}			
		for(int i = row -1; i >=0; i-- )
		{
			for(int j = 0 ; j < col ; j++)
				{
					for(int k = i, l = j; k >= 0 && l < col ; k--,l++ )
					{
						if(board[k][l] == null)
							result+= "X";
						else
							result += board[k][l].getColor();

					}
					if( result.indexOf(color)!= -1)
					{
						if(state == States.REAL) markWinner(i -result.indexOf(color) ,j + result.indexOf(color),3);
						return conditon;

					}
					result = "";	

					for(int k = i, l = j; k >= 0 && l >= 0 ; k--,l-- )
					{
						if(board[k][l] == null)
							result+= "X";
						else
							result += board[k][l].getColor();

					}
					if( result.indexOf(color)!= -1)
					{
						if(state == States.REAL) markWinner(i -result.indexOf(color) ,j - result.indexOf(color),4);
						return conditon;

					}
					result = "";	

				}
					
		}
		int x;
		for( x = 0 ; x< col; x++)
		{
			if(board[0][x] == null)
				break;
		}
		if( x == board[0].length) //if all the fields in the top row are full
			return States.TIE;
		return States.NOONEWON;
	}
	
	public static int getCPUPlace(States level, boolean turn) {
		
		if(level == States.EASY)
			return rand.nextInt(col) ;
		int bestScore = Integer.MIN_VALUE;
		int bestPlace = 1;
		ArrayList<Integer> zero = new ArrayList<Integer>();
		int depth = 8;
		if(level == States.MEDIUM)
			depth = 4;
		for(int i =0; i< avaliablePlaces.length; i++)
		{
			if(board[avaliablePlaces[i]][i] == null)		
			{
				if(turn)
					board[avaliablePlaces[i]][i] = new Point("b");
				else
					board[avaliablePlaces[i]][i] = new Point("r");
				if(avaliablePlaces[i] != 0) avaliablePlaces[i]--;
				int score = miniMax(depth, false, Integer.MIN_VALUE, Integer.MAX_VALUE, (!turn));
				if(avaliablePlaces[i] != 0 || board[avaliablePlaces[i]][i] == null) avaliablePlaces[i]++;
				board[avaliablePlaces[i]][i] = null;
				if(score > bestScore) {
					
					bestScore = score;
					bestPlace = i;
					
					}
				if(score == 0)
					zero.add(i);
			}
		}
		if(bestScore == 0)
		
				return zero.get((zero.size()) /2);
		
		return bestPlace;
	}
	
	
	// CPU - false - the CPU did  the last move now its player turn, true -the player did  the last move now its CPU turn
	private static int miniMax(int depth, boolean CPU, int alpha, int beta , boolean turn) {
		
		States result = isWin(!turn, States.DEMO);
		if(result != States.NOONEWON)
		{
			if(result == States.BLUEWON)
				return -10 * depth;
			else if(result == States.REDWON)
				return 10 * depth;
			else 
				return 0;
		}
		if(depth == 1)
			return 0;
		if(CPU)
		{
			int bestScore = Integer.MIN_VALUE;
			for(int i =0; i< avaliablePlaces.length; i++)
			{
				if(board[avaliablePlaces[i]][i] == null)
				{
					if(turn)
						board[avaliablePlaces[i]][i] = new Point("b");
					else
						board[avaliablePlaces[i]][i] = new Point("r");
					if(avaliablePlaces[i] != 0)
						avaliablePlaces[i]--;
					int score = miniMax(depth-1 , false, alpha, beta, (!turn));
					if(avaliablePlaces[i] != 0 || board[avaliablePlaces[i]][i] == null) avaliablePlaces[i]++;
					board[avaliablePlaces[i]][i] = null;
					alpha = Math.max(alpha, score);
					bestScore = Math.max(score, bestScore);
					if(beta <= alpha)
						break;
				}
			}
			return bestScore;
		}else {
			int bestScore = Integer.MAX_VALUE;
			for(int i =0; i< avaliablePlaces.length; i++)
			{
				if(board[avaliablePlaces[i]][i] == null)
				{
					if(turn)
						board[avaliablePlaces[i]][i] = new Point("b");
					else
						board[avaliablePlaces[i]][i] = new Point("r");
					if(avaliablePlaces[i] != 0) avaliablePlaces[i]--;
					int score = miniMax(depth-1 , true, alpha, beta, (!turn));
					if(avaliablePlaces[i] != 0 || board[avaliablePlaces[i]][i] == null) avaliablePlaces[i]++;
					board[avaliablePlaces[i]][i] = null;
					bestScore = Math.min(score, bestScore);
					beta =Math.min(beta, score);
					if(beta <= alpha)
						break;

				}
			}
			return bestScore;
			
		}
	}
	private static void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if(board[i][j] == null)
					System.out.print("n ");
				else
					System.out.print(board[i][j]+ " ");
			}
			System.out.println();
		}
		for (int i = 0; i < avaliablePlaces.length; i++) {
			System.out.print(avaliablePlaces[i] + "  ");
		}
		
	}
	private static void markWinner(int i, int j, int type) {
		switch(type)
		{
		case 1: // 4 in a row
			for(Integer l = 1; l<= 4; l++)
				FourInARowGraphic.buttons[i][j++].setText(l.toString());
			break;
		case 2:// 4 in a col
			for(Integer l = 1; l<= 4; l++)
				FourInARowGraphic.buttons[i++][j].setText(l.toString());
			break;
		case 3: // 4 in a cross1
			for(Integer l = 1; l<= 4; l++)
				FourInARowGraphic.buttons[i--][j++].setText(l.toString());
			break;
		case 4:// 4 in a cross2
			for(Integer l = 1; l<= 4; l++)
				FourInARowGraphic.buttons[i--][j--].setText(l.toString());
			break;
		}
	}	
}

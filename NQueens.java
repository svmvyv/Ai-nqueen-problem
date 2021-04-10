import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
GROUP PROJECT DONE BY: SAMAYA NUQAYTI 1776229, HUDA BOUGES 1876639, SHAIMAA ALHARBI 1808175
* AI PROJECT
* N QUEENS PROBELM USING AI IMPLMENTIONS
* CCAI-221 Artificial Intelligence
* 
 *
 */
public class NQueens {

	public static void main(String[] args) {
		NQueens program = new NQueens();
		program.run();

	}

	public void run() {
		Scanner input = new Scanner(System.in);

		System.out.print("Enter an integer for N-Queens bigger than 3 (N>3): ");
		int size = Integer.parseInt(input.nextLine());
		input.close();

		if (size <= 3) {
			System.out.println("Board size must be greater than 3!! try again please");
		} else {
			Board startBoard = new Board(size);

			System.out.println("~~~~~~~~~~~~~~~Board Information~~~~~~~~~~~~~~~\n" + "N-Queens Board Size: "
					+ size + "\nEvaluation Function Is: " + startBoard.getSafeQueenPairs()
					+ "\nBoard View:\n" + startBoard.toString());

			System.out.println("^^Results Using The Hill Climbing Algorithm^^\n");
			hillClimbing_algorithm(startBoard);

			
		}
	}

	/**
	 * Implementation of the Hill Climbing Algorithm for N-Queens Problem.
         * AI Group Project 
	 *
	 */
	public void hillClimbing_algorithm(Board board) {
		boolean isLocalMax = false, continueSearch = true;
		Board currentBoard = new Board(board.getBoard()); // copy of the board
		int iter = 0;
		int globalMax = getGoalValue(currentBoard.getBoard().length);

		while (continueSearch) {
			if (currentBoard.getSafeQueenPairs() == globalMax) { // did we reach goal board? check if yes
				System.out.println("=================Solution Found=================" + "\nNumber of iterations: "
						+ iter + "\nEvaluation Function: " + currentBoard.getSafeQueenPairs()
						+ "\nBoard Configuration:\n" + currentBoard.toString());
				continueSearch = false;
				break;
			} else {
				for (int i = 0; i < currentBoard.getBoard().length; i++) {
					Board bestSuccessor = successor(currentBoard, i);
					if (bestSuccessor.getSafeQueenPairs() > currentBoard.getSafeQueenPairs()) {
						currentBoard = bestSuccessor;
						iter++;
						isLocalMax = false;
					} else {
						isLocalMax = true;
					}
				}

				if (isLocalMax) {
					System.out.println(
							"=================Local Maximum Board Info=================" + "\nNumber of iterations done: "
									+ iter + "\nEvaluation Function Is: " + currentBoard.getSafeQueenPairs()
									+ "\nLocal Maximum Board View:\n" + currentBoard.toString());
					continueSearch = false;
					break;
				}
			}
		}
	}

	
	public Board successor(Board board, int row) {
		ArrayList<Board> children = new ArrayList<Board>();
		Board bestChild;

		for (int col = 0; col < board.getBoard().length; col++) {
			if (board.getBoard()[row][col] != 1) { // if the element is not a queen then do 
				int child[][] = new int[board.getBoard().length][board.getBoard().length];
				child[row][col] = 1; // move this queen to this column 

				for (int i = 0; i < child.length; i++) {
					if (i != row) {
						child[i] = board.getBoard()[i];
					}
				}
				children.add(new Board(child)); 
			}
		}

		bestChild = children.get(0);

		for (int z = 1; z < children.size(); z++) {
			int bestEv = bestChild.getSafeQueenPairs();
			int nextEv = children.get(z).getSafeQueenPairs();

			if (nextEv > bestEv) {
				bestChild = children.get(z);
			} else if (nextEv == bestEv) {
				Random rand = new Random();
				int choose = (int) (rand.nextInt(2));
				if (choose == 1) {
					bestChild = children.get(z);
				}
			}
		}

		return bestChild;

	}

	// sum of the integers from 1 up to (N-1)
	public int getGoalValue(int size) {
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += i;
		}
		return sum;
	}

}

class Board {
	private int board[][];
	private int safeQueenPairs; // number of non-attacking pair of queens

	// call a board object
	public Board(int[][] b) {
		this.board = b;
		ev();
	}

	//random board with queens
	public Board(int size) {
		board = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = 0;
			}
		}
		generateRandomQueens(size);
		ev();
	}

	
	public void generateRandomQueens(int size) {
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			board[i][rand.nextInt(size - 1)] = 1;
		}
	}

	public int ev() {
		safeQueenPairs = 0;
		ArrayList<Integer> pos = getQueenPositions();

		for (int i = 0; i < board.length - 1; i++) {
			safeQueenPairs += countSafe(pos, i);
		}

		return safeQueenPairs;
	}

	public int countSafe(ArrayList<Integer> pos, int row) {
		int i, safePairQ = 0, count = 0;

		for (i = row; i < board.length - 1; i++) {
			count = 0;
			if (pos.get(row) == pos.get(i + 1)) { // check if same column
				count++;
			}

			if ((pos.get(row) + row) == (pos.get(i + 1) + (i + 1))) { // check diagonal1
				count++;
			}

			if ((pos.get(row) - row) == (pos.get(i + 1) - (i + 1))) { // check diagonal2
				count++;
			}

			if (count <= 0) {
				safePairQ++;
			}
		}
		return safePairQ;

	}

	public ArrayList<Integer> getQueenPositions() {
		ArrayList<Integer> pos = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++) {
			pos.add(getColumn(i));
		}
		return pos;
	}

	
	public int[][] getBoard() {
		return board;
	}

	
	public void setBoard(int[][] board) {
		this.board = board;
	}

	
	public int getSafeQueenPairs() {
		return safeQueenPairs;
	}

	
	public void setSafeQueenPairs(int safeQueenPairs) {
		this.safeQueenPairs = safeQueenPairs;
	}

	
	public int getColumn(int row) {
		int index = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[row][i] == 1) {
				index = i;
			}
		}

		return index;
	}

	
	@Override
	public String toString() {
		String b = "";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 0) {
					b += " * ";
				} else {
					b += " Q ";
				}

			}
			b += "\n";
		}
		return b;
	}

}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solution {

	private static final int ROW = 0;
	private static final int COLUMN = 1;

	// Complete the queensAttack function below.
	static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles) {

		Context context = createContext(n, r_q, c_q, obstacles);

		int moves = 0;
		moves += new NorthMovesRetriever().retrieve(context);
		moves += new NortheastMovesRetriever().retrieve(context);
		moves += new EastMovesRetriever().retrieve(context);
		moves += new SoutheastMovesRetriever().retrieve(context);
		moves += new SouthMovesRetriever().retrieve(context);
		moves += new SouthwestMovesRetriever().retrieve(context);
		moves += new WestMovesRetriever().retrieve(context);
		moves += new NorthwestMovesRetriever().retrieve(context);

		return moves;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(System.getenv("OUTPUT_PATH")));

		String[] nk = scanner.nextLine()
				.split(" ");

		int n = Integer.parseInt(nk[0]);

		int k = Integer.parseInt(nk[1]);

		String[] r_qC_q = scanner.nextLine()
				.split(" ");

		int r_q = Integer.parseInt(r_qC_q[0]);

		int c_q = Integer.parseInt(r_qC_q[1]);

		int[][] obstacles = new int[k][2];

		for (int i = 0; i < k; i++) {
			String[] obstaclesRowItems = scanner.nextLine()
					.split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

			for (int j = 0; j < 2; j++) {
				int obstaclesItem = Integer.parseInt(obstaclesRowItems[j]);
				obstacles[i][j] = obstaclesItem;
			}
		}

		int result = queensAttack(n, k, r_q, c_q, obstacles);
		System.out.println(result);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

	public static final Position[] createObjectsPosition(int[][] objects) {

		Position[] objectsPosition = new Position[objects.length];

		for (int index = 0; index < objectsPosition.length; index++) {
			int[] object = objects[index];
			objectsPosition[index] = new Position(object[ROW], object[COLUMN]);
		}

		return objectsPosition;
	}

	public static final Context createContext(int boardSize, int queenRow, int queenColumn,
			int[][] objects) {
		Position queenPosition = new Position(queenRow, queenColumn);
		Position[] objectsPositions = createObjectsPosition(objects);
		return new Context(boardSize, queenPosition, objectsPositions);
	}

	private static class Position {
		private int row;

		private int column;

		public Position(int row, int column) {
			super();
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}
	}

	private static class Context {
		private int boardSize;

		private Position queen;

		private Position[] obstacles;

		public Context(int boardSize, Position queen, Position[] obstacles) {
			super();
			this.boardSize = boardSize;
			this.queen = queen;
			this.obstacles = obstacles;
		}

		public int getBoardSize() {
			return boardSize;
		}

		public Position getQueen() {
			return queen;
		}

		public Position[] getObstacles() {
			return obstacles;
		}
	}

	private static interface MovesRetriever {
		public Position retrieveClosestObstacle(Context context);

		public int retrieve(Context context);
	}

	private abstract static class AbstractStraightMovesRetriever implements MovesRetriever {

		@Override
		public Position retrieveClosestObstacle(Context context) {
			Position closestObstacle = null;

			for (Position obstacle : context.getObstacles()) {
				if (considerObstacle(obstacle, context.getQueen())) {
					if (closestObstacle == null) {
						closestObstacle = obstacle;
					} else {
						if (shouldReplaceClosestObstacle(closestObstacle, obstacle)) {
							closestObstacle = obstacle;
						}
					}
				}
			}
			return closestObstacle;
		}

		protected abstract boolean considerObstacle(Position obstacle, Position queen);

		protected abstract boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle);
	}

	private static class NorthMovesRetriever extends AbstractStraightMovesRetriever {

		@Override
		public int retrieve(Context context) {
			Position obstacle = retrieveClosestObstacle(context);
			int limit = (obstacle != null ? obstacle.getRow() : context.getBoardSize() + 1);
			return limit - context.getQueen()
					.getRow() - 1;
		}

		@Override
		protected boolean considerObstacle(Position obstacle, Position queen) {
			return obstacle.getColumn() == queen.getColumn() && obstacle.getRow() > queen.getRow();
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getRow() < closestObstacle.getRow();
		}
	}

	private static class EastMovesRetriever extends AbstractStraightMovesRetriever {

		@Override
		public int retrieve(Context context) {
			Position obstacle = retrieveClosestObstacle(context);
			int limit = (obstacle != null ? obstacle.getColumn() : context.getBoardSize() + 1);
			return limit - context.getQueen()
					.getColumn() - 1;
		}

		@Override
		protected boolean considerObstacle(Position obstacle, Position queen) {
			return obstacle.getRow() == queen.getRow() && obstacle.getColumn() > queen.getColumn();
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getColumn() < closestObstacle.getColumn();
		}
	}

	private static class SouthMovesRetriever extends AbstractStraightMovesRetriever {

		@Override
		public int retrieve(Context context) {
			Position obstacle = retrieveClosestObstacle(context);
			int limit = (obstacle != null ? obstacle.getRow() : 0);
			return context.getQueen()
					.getRow() - limit - 1;
		}

		@Override
		protected boolean considerObstacle(Position obstacle, Position queen) {
			return obstacle.getColumn() == queen.getColumn() && obstacle.getRow() < queen.getRow();
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getRow() > closestObstacle.getRow();
		}

	}

	private static class WestMovesRetriever extends AbstractStraightMovesRetriever {

		@Override
		public int retrieve(Context context) {
			Position obstacle = retrieveClosestObstacle(context);
			int limit = (obstacle != null ? obstacle.getColumn() : 0);
			return context.getQueen()
					.getColumn() - limit - 1;
		}

		@Override
		protected boolean considerObstacle(Position obstacle, Position queen) {
			return obstacle.getRow() == queen.getRow() && obstacle.getColumn() < queen.getColumn();
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getColumn() > closestObstacle.getColumn();
		}

	}

	private abstract static class AbstractDiagonalMovesRetriever implements MovesRetriever {

		@Override
		public int retrieve(Context context) {
			Position obstacle = retrieveClosestObstacle(context);

			Position difference;
			if (obstacle != null) {
				difference = calculateQueenObstacleDifference(context.getQueen(), obstacle);
			} else {
				difference = calculateQueenBoardLimitDifference(context.getQueen(),
						context.getBoardSize());
			}

			return minimalDistance(difference);
		}

		@Override
		public Position retrieveClosestObstacle(Context context) {
			Position closestObstacle = null;

			Position difference = null;

			for (Position obstacle : context.getObstacles()) {
				difference = calculateQueenObstacleDifference(context.getQueen(), obstacle);
				if (difference.getRow() >= 0 && difference.getColumn() >= 0
						&& difference.getRow() == difference.getColumn()) {
					if (closestObstacle == null) {
						closestObstacle = obstacle;
					} else {
						if (shouldReplaceClosestObstacle(closestObstacle, obstacle)) {
							closestObstacle = obstacle;
						}
					}
				}
			}
			return closestObstacle;
		}

		private int minimalDistance(Position position) {
			return Integer.min(position.getRow(), position.getColumn());
		}

		protected abstract Position calculateQueenObstacleDifference(Position queen,
				Position obstacle);

		protected abstract boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle);

		protected abstract Position calculateQueenBoardLimitDifference(Position queen,
				int boardSize);
	}

	private static class NortheastMovesRetriever extends AbstractDiagonalMovesRetriever {

		@Override
		protected Position calculateQueenObstacleDifference(Position queen, Position obstacle) {
			int row = obstacle.getRow() - queen.getRow() - 1;
			int column = obstacle.getColumn() - queen.getColumn() - 1;
			return new Position(row, column);
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getRow() < closestObstacle.getRow();
		}

		@Override
		protected Position calculateQueenBoardLimitDifference(Position queen, int boardSize) {
			int row = boardSize - queen.getRow();
			int column = boardSize - queen.getColumn();
			return new Position(row, column);
		}

	}

	private static class SoutheastMovesRetriever extends AbstractDiagonalMovesRetriever {

		@Override
		protected Position calculateQueenObstacleDifference(Position queen, Position obstacle) {
			int row = queen.getRow() - obstacle.getRow() - 1;
			int column = obstacle.getColumn() - queen.getColumn() - 1;
			return new Position(row, column);
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getRow() > closestObstacle.getRow();
		}

		@Override
		protected Position calculateQueenBoardLimitDifference(Position queen, int boardSize) {
			int row = queen.getRow() - 1;
			int column = boardSize - queen.getColumn();
			return new Position(row, column);
		}

	}

	private static class SouthwestMovesRetriever extends AbstractDiagonalMovesRetriever {

		@Override
		protected Position calculateQueenObstacleDifference(Position queen, Position obstacle) {
			int row = queen.getRow() - obstacle.getRow() - 1;
			int column = queen.getColumn() - obstacle.getColumn() - 1;
			return new Position(row, column);
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getRow() > closestObstacle.getRow();
		}

		@Override
		protected Position calculateQueenBoardLimitDifference(Position queen, int boardSize) {
			int row = queen.getRow() - 1;
			int column = queen.getColumn() - 1;
			return new Position(row, column);
		}

	}

	private static class NorthwestMovesRetriever extends AbstractDiagonalMovesRetriever {

		@Override
		protected Position calculateQueenObstacleDifference(Position queen, Position obstacle) {
			int row = obstacle.getRow() - queen.getRow() - 1;
			int column = queen.getColumn() - obstacle.getColumn() - 1;
			return new Position(row, column);
		}

		@Override
		protected boolean shouldReplaceClosestObstacle(Position closestObstacle,
				Position obstacle) {
			return obstacle.getRow() < closestObstacle.getRow();
		}

		@Override
		protected Position calculateQueenBoardLimitDifference(Position queen, int boardSize) {
			int row = boardSize - queen.getRow();
			int column = queen.getColumn() - 1;
			return new Position(row, column);
		}

	}
}

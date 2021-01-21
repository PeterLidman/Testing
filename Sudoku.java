package Sudoku;

import java.util.Scanner;

public class Sudoku {
	static Scanner scanner = new Scanner(System.in);
	static int[][] grid = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, //
			{ 6, 0, 0, 1, 9, 5, 0, 0, 0 }, //
			{ 0, 9, 8, 0, 0, 0, 0, 6, 0 }, //
			{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, //
			{ 4, 0, 0, 8, 0, 3, 0, 0, 1 }, //
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, //
			{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, //
			{ 0, 0, 0, 4, 1, 9, 0, 0, 5 }, //
			{ 0, 0, 0, 0, 8, 0, 0, 0, 0 } };

//	static int[][] grid = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 }, //
//			{ 6, 0, 0, 1, 9, 5, 0, 0, 0 }, //
//			{ 0, 9, 8, 0, 0, 0, 0, 6, 0 }, //
//			{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, //
//			{ 4, 0, 0, 8, 0, 3, 0, 0, 1 }, //
//			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, //
//			{ 0, 6, 0, 0, 0, 0, 2, 8, 0 }, //
//			{ 0, 0, 0, 4, 1, 9, 0, 0, 5 }, //
//			{ 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
	private static boolean isPossible(int y, int x, int n) {
		for (int i = 0; i < 9; i++) {
			if (grid[y][i] == n) {
				return false;
			}
			if (grid[i][x] == n) {
				return false;
			}
		}
		int x0 = (x / 3) * 3;
		int y0 = (y / 3) * 3;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid[y0 + i][x0 + j] == n) {
					return false;
				}
			}
		}
		return true;
	}

	private static void print() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				System.out.print(" " + grid[y][x]);
			}
			System.out.println("");
		}
	}

	private static void solve() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (grid[y][x] == 0) {
					for (int n = 1; n < 10; n++) {
						if (isPossible(y, x, n)) {
							grid[y][x] = n;
							solve();
							grid[y][x] = 0;
						}
					}
					return;
				}
			}
		}
		print();
		System.out.println("More?");
		scanner.nextLine();
	}

	public static void main(String[] args) {
//		System.out.println(isPossible(5,5,5));
//		System.out.println(isPossible(5,5,4));
//		print();
		solve();
	}
}

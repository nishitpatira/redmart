package com.nishit.redmart;

//http://geeks.redmart.com/2015/01/07/skiing-in-singapore-a-coding-diversion/
import java.util.LinkedList;
import java.util.List;

public class SkiingMain {

	private static int largestNodeCount = -1;

	private static int maxDepth = -1;

	public static void main(final String[] args) {

		final int[][] map = ReadInputFile.parseFile("src//main//resources//map.txt");
		final int[][] longestSkiMap = longestSki(map);

		final List<Pair> possiblePaths = findMaxLongest(longestSkiMap);

		findMaxDepth(map, longestSkiMap, possiblePaths, largestNodeCount);

		System.out.println("Longest Path is  : " + (largestNodeCount + 1));
		System.out.println("Largest Drop is  : " + maxDepth);
	}

	private static int[][] longestSki(final int[][] map) {
		final int[][] longestSkiAux = new int[map.length][map[0].length];
		initializeAux(longestSkiAux);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				longestSkiUtil(map, longestSkiAux, i, j);
			}
		}
		return longestSkiAux;

	}

	private static int longestSkiUtil(final int[][] map, final int[][] aux, final int i, final int j) {
		if (aux[i][j] != -1) {
			return aux[i][j];
		}

		// Move only in 2 directions
		if (isCorner(i, j, map.length, map[0].length)) {
			if (i == 0) {
				if (j == 0) {
					// Top Left Corner
					final int countEast = map[i][j] > map[i][j + 1] ? longestSkiUtil(map, aux, i, j + 1) : -1;
					final int countSouth = map[i][j] > map[i + 1][j] ? longestSkiUtil(map, aux, i + 1, j) : -1;
					aux[i][j] = 1 + Math.max(countEast, countSouth);

				} else {
					// Top Right Corner
					final int countSouth = map[i][j] > map[i + 1][j] ? longestSkiUtil(map, aux, i + 1, j) : -1;
					final int countWest = map[i][j] > map[i][j - 1] ? longestSkiUtil(map, aux, i, j - 1) : -1;
					aux[i][j] = 1 + Math.max(countWest, countSouth);
				}
			} else {
				if (j == 0) {
					// Bottom Left Corner
					final int countNorth = map[i][j] > map[i - 1][j] ? longestSkiUtil(map, aux, i - 1, j) : -1;
					final int countEast = map[i][j] > map[i][j + 1] ? longestSkiUtil(map, aux, i, j + 1) : -1;
					aux[i][j] = 1 + Math.max(countEast, countNorth);
				} else {
					// Bottom Right Corner
					final int countNorth = map[i][j] > map[i - 1][j] ? longestSkiUtil(map, aux, i - 1, j) : -1;
					final int countWest = map[i][j] > map[i][j - 1] ? longestSkiUtil(map, aux, i, j - 1) : -1;
					aux[i][j] = 1 + Math.max(countWest, countNorth);
				}
			}
			return aux[i][j];
		}

		// Move only in 3 directions
		else if (isEdge(i, j, map.length, map[0].length)) {
			if (i == 0) {
				final int countWest = map[i][j] > map[i][j - 1] ? longestSkiUtil(map, aux, i, j - 1) : -1;
				final int countEast = map[i][j] > map[i][j + 1] ? longestSkiUtil(map, aux, i, j + 1) : -1;
				final int countSouth = map[i][j] > map[i + 1][j] ? longestSkiUtil(map, aux, i + 1, j) : -1;
				aux[i][j] = 1 + Math.max(countWest, Math.max(countEast, countSouth));

			} else if (i == map.length - 1) {
				final int countWest = map[i][j] > map[i][j - 1] ? longestSkiUtil(map, aux, i, j - 1) : -1;
				final int countEast = map[i][j] > map[i][j + 1] ? longestSkiUtil(map, aux, i, j + 1) : -1;
				final int countNorth = map[i][j] > map[i - 1][j] ? longestSkiUtil(map, aux, i - 1, j) : -1;
				aux[i][j] = 1 + Math.max(countWest, Math.max(countEast, countNorth));

			} else if (j == 0) {
				final int countEast = map[i][j] > map[i][j + 1] ? longestSkiUtil(map, aux, i, j + 1) : -1;
				final int countSouth = map[i][j] > map[i + 1][j] ? longestSkiUtil(map, aux, i + 1, j) : -1;
				final int countNorth = map[i][j] > map[i - 1][j] ? longestSkiUtil(map, aux, i - 1, j) : -1;
				aux[i][j] = 1 + Math.max(countSouth, Math.max(countEast, countNorth));

			} else {
				final int countWest = map[i][j] > map[i][j - 1] ? longestSkiUtil(map, aux, i, j - 1) : -1;
				final int countSouth = map[i][j] > map[i + 1][j] ? longestSkiUtil(map, aux, i + 1, j) : -1;
				final int countNorth = map[i][j] > map[i - 1][j] ? longestSkiUtil(map, aux, i - 1, j) : -1;
				aux[i][j] = 1 + Math.max(countWest, Math.max(countSouth, countNorth));
			}
			return aux[i][j];

		} else {
			// Move in all 4 directions
			final int countEast = map[i][j] > map[i][j + 1] ? longestSkiUtil(map, aux, i, j + 1) : -1;
			final int countWest = map[i][j] > map[i][j - 1] ? longestSkiUtil(map, aux, i, j - 1) : -1;
			final int countSouth = map[i][j] > map[i + 1][j] ? longestSkiUtil(map, aux, i + 1, j) : -1;
			final int countNorth = map[i][j] > map[i - 1][j] ? longestSkiUtil(map, aux, i - 1, j) : -1;
			aux[i][j] = 1 + Math.max(Math.max(countEast, countWest), Math.max(countSouth, countNorth));
			return aux[i][j];

		}

	}

	private static List<Pair> findMaxLongest(final int[][] matrix) {
		final List<Pair> list = new LinkedList<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] > largestNodeCount) {
					list.clear();
					largestNodeCount = matrix[i][j];
					final Pair pair = new Pair(i, j);
					list.add(pair);
				} else if (matrix[i][j] == largestNodeCount) {
					final Pair duplicate = new Pair(i, j);
					list.add(duplicate);
				}
			}
		}
		return list;
	}

	private static void findMaxDepth(final int[][] map, final int[][] longestSkiAux, final List<Pair> possiblePaths,
			final int maxCount) {
		possiblePaths.stream()
				.forEach(pair -> findMaxDepthUtil(map, longestSkiAux, pair.i, pair.j, maxCount, map[pair.i][pair.j]));
	}

	private static void findMaxDepthUtil(final int[][] map, final int[][] longestSkiAux, final int i, final int j,
			final int maxCount, final int currMax) {
		if (i < 0 || j < 0 || i >= map.length || j >= map[0].length) {
			return;
		}

		if (maxCount == 0) {
			if (currMax - map[i][j] > maxDepth) {
				maxDepth = currMax - map[i][j];
			}
			return;
		}

		// Move only in 2 directions
		if (isCorner(i, j, map.length, map[0].length)) {
			if (i == 0) {
				if (j == 0) {
					// Top Left Corner can move only east or south
					if (longestSkiAux[i][j + 1] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i, j + 1, maxCount - 1, currMax);
					}
					if (longestSkiAux[i + 1][j] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i + 1, j, maxCount - 1, currMax);
					}
				} else {
					// Top Right Corner can move only west or south
					if (longestSkiAux[i][j - 1] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i, j - 1, maxCount - 1, currMax);
					}
					if (longestSkiAux[i + 1][j] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i + 1, j, maxCount - 1, currMax);
					}
				}
			} else {
				if (j == 0) {
					// Bottom Left Corner can move only east or north
					if (longestSkiAux[i][j + 1] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i, j + 1, maxCount - 1, currMax);
					}
					if (longestSkiAux[i - 1][j] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i - 1, j, maxCount - 1, currMax);
					}
				} else {
					// Bottom Right Corner can move only west or north
					if (longestSkiAux[i][j - 1] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i, j - 1, maxCount - 1, currMax);
					}
					if (longestSkiAux[i - 1][j] == maxCount - 1) {
						findMaxDepthUtil(map, longestSkiAux, i - 1, j, maxCount - 1, currMax);
					}
				}
			}
			return;
		}

		// Move only in 3 directions
		else if (isEdge(i, j, map.length, map[0].length)) {
			if (i == 0) {
				// Can move east, west, south
				if (longestSkiAux[i][j + 1] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i, j + 1, maxCount - 1, currMax);
				}
				if (longestSkiAux[i][j - 1] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i, j - 1, maxCount - 1, currMax);
				}
				if (longestSkiAux[i + 1][j] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i + 1, j, maxCount - 1, currMax);
				}
			} else if (i == map.length - 1) {
				// Can move east, west, north
				if (longestSkiAux[i][j + 1] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i, j + 1, maxCount - 1, currMax);
				}
				if (longestSkiAux[i][j - 1] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i, j - 1, maxCount - 1, currMax);
				}
				if (longestSkiAux[i - 1][j] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i - 1, j, maxCount - 1, currMax);
				}
			} else if (j == 0) {
				// Can move east, north, south
				if (longestSkiAux[i][j + 1] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i, j + 1, maxCount - 1, currMax);
				}
				if (longestSkiAux[i - 1][j] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i - 1, j, maxCount - 1, currMax);
				}
				if (longestSkiAux[i + 1][j] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i + 1, j, maxCount - 1, currMax);
				}
			} else {
				// Can move west, north, south
				if (longestSkiAux[i][j - 1] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i, j - 1, maxCount - 1, currMax);
				}
				if (longestSkiAux[i - 1][j] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i - 1, j, maxCount - 1, currMax);
				}
				if (longestSkiAux[i + 1][j] == maxCount - 1) {
					findMaxDepthUtil(map, longestSkiAux, i + 1, j, maxCount - 1, currMax);
				}
			}
			return;
		}

		else {
			// Move in all 4 directions
			if (longestSkiAux[i][j - 1] == maxCount - 1) {
				findMaxDepthUtil(map, longestSkiAux, i, j - 1, maxCount - 1, currMax);
			}
			if (longestSkiAux[i][j + 1] == maxCount - 1) {
				findMaxDepthUtil(map, longestSkiAux, i, j + 1, maxCount - 1, currMax);
			}
			if (longestSkiAux[i - 1][j] == maxCount - 1) {
				findMaxDepthUtil(map, longestSkiAux, i - 1, j, maxCount - 1, currMax);
			}
			if (longestSkiAux[i + 1][j] == maxCount - 1) {
				findMaxDepthUtil(map, longestSkiAux, i + 1, j, maxCount - 1, currMax);
			}
			return;
		}

	}

	private static void initializeAux(final int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = -1;
			}
		}
	}

	private static boolean isCorner(final int i, final int j, final int rows, final int columns) {
		if (i == 0 || i == rows - 1) {
			if (j == 0 || j == columns - 1) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEdge(final int i, final int j, final int rows, final int columns) {
		if ((i == 0 || i == rows - 1) && j > 0 && j <= columns - 1
				|| (j == 0 || j == columns - 1) && i > 0 && i <= rows - 1) {
			return true;
		}
		return false;
	}

	static class Pair {
		int i;
		int j;

		public Pair(final int i, final int j) {
			this.i = i;
			this.j = j;
		}

		public int getI() {
			return i;
		}

		public int getJ() {
			return j;
		}

	}
}
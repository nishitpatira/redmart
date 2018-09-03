package com.nishit.redmart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadInputFile {
	public static int[][] parseFile(final String fileName) {
		final File file = new File(fileName);
		int[][] map = null;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			final String[] rowAndColumn = br.readLine().split(" ");
			map = new int[Integer.parseInt(rowAndColumn[0])][Integer.parseInt(rowAndColumn[1])];
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				final String[] array = line.split(" ");
				for (int j = 0; j < Integer.parseInt(rowAndColumn[1]); j++) {
					map[i][j] = Integer.parseInt(array[j]);
				}
				i++;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}

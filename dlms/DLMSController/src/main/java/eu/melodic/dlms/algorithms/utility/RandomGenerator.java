package eu.melodic.dlms.algorithms.utility;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * For random generation
 */
public class RandomGenerator {

	public static int generateNum(int min, int max) {
		// swap numbers if min greater than max
		if (min > max) {
			int tmp = max;
			max = min;
			min = tmp;
		}
		return (ThreadLocalRandom.current().nextInt(min, max + 1));
	}

	public static long generateNum(long min, long max) {
		// swap numbers if min greater than max
		if (min > max) {
			long tmp = max;
			max = min;
			min = tmp;
		}
		return (ThreadLocalRandom.current().nextLong(min, max + 1));
	}

	public static int randNumFromSize(int size) {
		Random rand = new Random();
		return (rand.nextInt(size));
	}

}

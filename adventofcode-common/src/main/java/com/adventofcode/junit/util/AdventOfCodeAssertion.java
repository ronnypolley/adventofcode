package com.adventofcode.junit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AdventOfCodeAssertion {

	private static Logger logger = LogManager.getLogger();

	private AdventOfCodeAssertion() {
		// empty ctor for util class
	}

	public static <I extends Object> void assertAdventOfCode(Path file, I expected, I actual) {
		if (file.toString().endsWith("test")) {
			// if the input is a "test" file, we asset against the given expected value
			assertEquals(expected, actual);
		} else {
			// if it is the real input, we just print the result, as we do not know the
			// expected value
			StackWalker.getInstance().walk(in -> in.filter(stack -> stack.getClassName().endsWith("Test")).findFirst())
					.ifPresent(stack -> logger.error("Actual result for {}#{}: {}", stack.getClassName(),
							stack.getMethodName(), actual));
		}
	}

}

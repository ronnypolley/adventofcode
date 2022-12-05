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
			assertEquals(expected, actual);
		} else {
			StackWalker.getInstance().walk(in -> in.filter(stack -> stack.getClassName().endsWith("Test")).findFirst())
					.ifPresent(stack -> logger.error("Actual result for {}#{}: {}", stack.getClassName(),
							stack.getMethodName(), actual));
		}
	}

}

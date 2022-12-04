package com.adventofcode.junit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.StackWalker.StackFrame;
import java.nio.file.Path;
import java.util.Optional;

public class AdventOfCodeAssertion {

	public static void assertAdventOfCode(Path file, Integer expected, Integer actual) {
		if (file.toString().endsWith("test")) {
			assertEquals(expected, actual);
		} else {
			Optional<StackFrame> walk = StackWalker.getInstance()
					.walk(in -> in.filter(stack -> stack.getClassName().endsWith("Test")).findFirst());
			System.out.println(walk.get().getClassName() + "#" + walk.get().getMethodName() + ": " + actual);
		}
	}

}

package com.adventofcode.junit.extension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class AdventOfCodeDailyArgumentsProvider implements ArgumentsProvider {

	/**
	 * This class searchs for files named like the days for Advent of Code and
	 * provides them through {@link Path} parameter in {@link ParameterizedTest}.
	 *
	 * Filenames can be dayXXinput(_part(1|2))?(_test)?
	 *
	 * Examples: - day01input used for the main input - day01input_test used for the
	 * test input if it is the same for part 1 and 2 - day01input_part2_test used if
	 * the input is specific for the parts
	 */
	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		// compute day number from class, might by empty if not in the correct format
		var day = context.getTestClass()
				.map(cla -> cla.getCanonicalName().replaceAll(".*Day(\\d{2})Test", "day$1input")).orElse("");

		// get the part name from the method
		var partFile = context.getTestMethod()
				.map(method -> "_part" + method.getName().substring(method.getName().length() - 1) + "_test")
				.orElse("");
		// look if there is a file like in the method comment to be loaded and provided
		// to the test method
		return Stream.of(day).map(in -> "src/test/resources/" + day)
				.map(in -> Stream.of("", "_test", partFile).map(suff -> in + suff).toList()).flatMap(List::stream)
				.filter(file -> Paths.get(file).toFile().exists()).map(Arguments::of);
	}

}

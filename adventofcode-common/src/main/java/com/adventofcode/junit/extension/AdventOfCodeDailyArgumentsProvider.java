package com.adventofcode.junit.extension;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class AdventOfCodeDailyArgumentsProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		var day = context.getTestClass()
				.map(cla -> cla.getCanonicalName().replaceAll(".*Day(\\d{2})Test", "day$1input")).orElse("");
		var partFile = context.getTestMethod().map(method -> "_part"+ method.getName().substring(method.getName().length()-1) + "_test").orElse("");
		return Stream.of(day).map(in -> "src/test/resources/" + day)
				.map(in -> Stream.of("", "_test", partFile).map(suff -> in + suff).toList()).flatMap(List::stream)
				.filter(file -> Paths.get(file).toFile().exists()).map(Arguments::of);
	}

}

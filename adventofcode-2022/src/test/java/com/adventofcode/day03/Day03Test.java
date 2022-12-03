package com.adventofcode.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.GroupLayout.Group;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Day03Test {

	@ParameterizedTest
	@ValueSource(strings = {"day03input_test", "day03input"})
	void testPart1(String filename) throws IOException {
		Optional<Integer> reduce = Files.lines(Paths.get("src/test/resources/"+filename)).map(rucksack -> {
			List<Integer> firstHalf = new ArrayList<>(); 
			firstHalf.addAll(rucksack.chars().boxed().limit(rucksack.length()/2).toList());
			List<Integer> secondHalf = rucksack.chars().boxed().skip(rucksack.length()/2).toList();
			firstHalf.retainAll(secondHalf);
			return firstHalf.get(0);
		}).map(ch -> ch > 97 ? ch-96 : ch-64+26).reduce(Integer::sum);
		
		if (filename.endsWith("test")) {
			assertEquals(157, reduce.get());
		} 
		System.out.println(reduce.get());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"day03input_test", "day03input"})
	void testPart2(String filename) throws IOException {
		AtomicInteger counter = new AtomicInteger();
		Optional<Integer> reduce = Files.lines(Paths.get("src/test/resources/"+filename)).collect(Collectors.groupingBy(x -> counter.getAndIncrement()/3)).values().stream().map(group -> {
			
			List<Integer> firstElve = new ArrayList<>();
			firstElve.addAll(group.get(0).chars().boxed().toList());
			firstElve.retainAll(group.get(1).chars().boxed().toList());
			firstElve.retainAll(group.get(2).chars().boxed().toList());
			return firstElve.get(0);
		}).map(ch -> ch > 97 ? ch-96 : ch-64+26).reduce(Integer::sum);
		
		if (filename.endsWith("test")) {
			assertEquals(70, reduce.get());
		} 
		System.out.println(reduce.get());
	}

}

package prob10a;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Or {

	public static void main(String[] args) {
		Or or = new Or();
		List<Simple> list = Arrays.asList(new Simple(false), new Simple(false), new Simple(true));
		System.out.println("\n"+or.someSimpleIsTrue(list));

		//Question b
		Stream<String> stringStream = Stream.of("Bill", "Thomas", "Mary");
		System.out.println("\n"+stringStream.collect(Collectors.joining(", ")));

		//Question c
		Stream<Integer> myIntStream = Stream.of(3, 5, 7, 11, 13);
		IntSummaryStatistics summary = myIntStream.collect(Collectors.summarizingInt(Integer::intValue));
		int max = summary.getMax();
		int min = summary.getMin();
		System.out.println("\nMin "+min);
		System.out.println("Max "+max);
	}
	
	public boolean someSimpleIsTrue(List<Simple> list) {
		return list.stream()
				.map(Simple::isFlag)
				.reduce(false, (f1, f2) -> f1 || f2);


		/*boolean accum = false;
		for(Simple s: list) {
			accum = accum || s.flag;
		}
		return accum;*/
	}

}

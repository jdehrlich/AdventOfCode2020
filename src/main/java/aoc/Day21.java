package aoc;

import aoc.utils.AbstractDay;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * --- Day 21: Allergen Assessment ---
 * You reach the train's last stop and the closest you can get to your vacation island without getting wet. There aren't even any boats here, but nothing can stop you now: you build a raft. You just need a few days' worth of food for your journey.
 *
 * You don't speak the local language, so you can't read any ingredients lists. However, sometimes, allergens are listed in a language you do understand. You should be able to use this information to determine which ingredient contains which allergen and work out which foods are safe to take with you on your trip.
 *
 * You start by compiling a list of foods (your puzzle input), one food per line. Each line includes that food's ingredients list followed by some or all of the allergens the food contains.
 *
 * Each allergen is found in exactly one ingredient. Each ingredient contains zero or one allergen. Allergens aren't always marked; when they're listed (as in (contains nuts, shellfish) after an ingredients list), the ingredient that contains each listed allergen will be somewhere in the corresponding ingredients list. However, even if an allergen isn't listed, the ingredient that contains that allergen could still be present: maybe they forgot to label it, or maybe it was labeled in a language you don't know.
 *
 * For example, consider the following list of foods:
 *
 * mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
 * trh fvjkl sbzzf mxmxvkd (contains dairy)
 * sqjhc fvjkl (contains soy)
 * sqjhc mxmxvkd sbzzf (contains fish)
 * The first food in the list has four ingredients (written in a language you don't understand): mxmxvkd, kfcds, sqjhc, and nhms. While the food might contain other allergens, a few allergens the food definitely contains are listed afterward: dairy and fish.
 *
 * The first step is to determine which ingredients can't possibly contain any of the allergens in any food in your list. In the above example, none of the ingredients kfcds, nhms, sbzzf, or trh can contain an allergen. Counting the number of times any of these ingredients appear in any ingredients list produces 5: they all appear once each except sbzzf, which appears twice.
 *
 * Determine which ingredients cannot possibly contain any of the allergens in your list. How many times do any of those ingredients appear?
 *
 * Your puzzle answer was 2389.
 *
 * --- Part Two ---
 * Now that you've isolated the inert ingredients, you should have enough information to figure out which ingredient contains which allergen.
 *
 * In the above example:
 *
 * mxmxvkd contains dairy.
 * sqjhc contains fish.
 * fvjkl contains soy.
 * Arrange the ingredients alphabetically by their allergen and separate them by commas to produce your canonical dangerous ingredient list. (There should not be any spaces in your canonical dangerous ingredient list.) In the above example, this would be mxmxvkd,sqjhc,fvjkl.
 *
 * Time to stock your raft with supplies. What is your canonical dangerous ingredient list?
 *
 * Your puzzle answer was fsr,skrxt,lqbcg,mgbv,dvjrrkv,ndnlm,xcljh,zbhp.
 */
public class Day21 extends AbstractDay {

    Map<String, Set<String>> appearsWithAllergens = new HashMap<>();
    Map<String, Set<String>> possibleAllergens = new HashMap<>();
    Map<String, Integer> ingredientFrequency = new HashMap<>();
    Pattern p = Pattern.compile("(.*) \\(contains (.*)\\)");
    private Day21() {
        super(Day21.class.getCanonicalName().replaceAll("\\.", "/").toLowerCase());
        parseAllergens();
    }

    @Override
    public String solve1() {
        return "" + possibleAllergens.entrySet().stream()
                .filter(e -> e.getValue().stream().allMatch(a -> !appearsWithAllergens.get(a).contains(e.getKey())))
                .mapToLong(e -> ingredientFrequency.get(e.getKey()))
                .sum();
    }

    private void parseAllergens() {
        lines.stream().forEach(s -> {
            Matcher m = p.matcher(s);
            if (m.matches()) {
                List<String> ingredients = Arrays.asList(m.group(1).split(" "));
                List<String> allergens = Arrays.asList(m.group(2).split(",\\s+"));
                ingredients.forEach(i -> {
                    if (possibleAllergens.containsKey(i)) {
                        possibleAllergens.get(i).addAll(allergens);
                    } else {
                        possibleAllergens.put(i, new HashSet<>(allergens));
                    }
                    ingredientFrequency.put(i, ingredientFrequency.getOrDefault(i, 0) + 1);
                });
                allergens.forEach(a -> {
                    if (appearsWithAllergens.containsKey(a)) {
                        appearsWithAllergens.get(a).retainAll(ingredients);
                    } else {
                        appearsWithAllergens.put(a, new HashSet<>(ingredients));
                    }
                });
            }
        });
    }

    @Override
    public String solve2() {
        Map<String, String> dangerousIngredients = new HashMap<>();
        appearsWithAllergens.entrySet().stream().sorted(Comparator.comparingInt(e -> e.getValue().size())).forEach(e ->
            e.getValue()
                    .stream()
                    .filter(i -> !dangerousIngredients.containsKey(i))
                    .findAny().ifPresent(i -> dangerousIngredients.put(i, e.getKey())));

        return dangerousIngredients.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(","));
    }

    public static void main(String args[]) {
        new Day21().main();
    }
}

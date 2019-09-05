package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.ItemsContainer;
import com.mobiquityinc.model.Package;
import com.mobiquityinc.parser.ItemListParser;

public class Packer {

	private Packer() {
	}

	public static String pack(String filePath) throws APIException {
		/*
		 * An items container represents the items in each line of input. A container has
		 * a list of items and maxPackageWeight
		 */
		List<ItemsContainer> allItemsContainer = ItemListParser.parse(filePath);
		StringBuilder builder = new StringBuilder();
		for (ItemsContainer itemsContainer : allItemsContainer) {
			// Create a new list of potential packages
			List<Package> packages = new ArrayList<>();
			Package pkg = new Package(itemsContainer.getMaxPackageWeight());
			for (int firstItemIndex = 0; firstItemIndex < itemsContainer.getItems().size(); firstItemIndex++) {
				/*
				 * The first loop is to get the first potential element of the potential package
				 */
				Item i = itemsContainer.getItems().get(firstItemIndex);
				pkg.addItem(i.getWeight(), i.getIndex(), i.getCost());
				for (int secondItemIndex = firstItemIndex + 1; secondItemIndex < itemsContainer.getItems()
						.size(); secondItemIndex++) {
					/*
					 * The second loop is used to get the following elements of the potential
					 * package
					 */
					Item j = itemsContainer.getItems().get(secondItemIndex);
					pkg.addItem(j.getWeight(), j.getIndex(), j.getCost());
				}
				/* Add the package to the list of potential packages */
				packages.add(pkg);
				pkg = new Package(itemsContainer.getMaxPackageWeight());
			}
			/*
			 * Sort package list first by cost descending and second by weight ascending
			 * (default order)
			 */
			packages = packages.stream()
					.sorted(Comparator.comparingDouble(Package::getTotalCost).reversed()
							.thenComparing(Comparator.comparingDouble(Package::getPackageWeight)))
					.collect(Collectors.toList());
			String outputLine = packages.get(0).getItemIndexes().stream().map(n -> n.toString())
					.collect(Collectors.joining(","));
			builder.append(outputLine.isEmpty() ? "-" : outputLine).append("\n");
		}
		return builder.toString();
	}
}

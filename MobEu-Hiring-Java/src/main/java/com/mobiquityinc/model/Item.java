package com.mobiquityinc.model;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.util.Constants;

public class Item {

	private int index;
	private double weight;
	private double cost;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) throws APIException {
		this.index = index;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) throws APIException {
		if (weight > Constants.MAX_ALLOWED_WEIGHT) {
			throw new APIException("Item weight cannot exceed: " + Constants.MAX_ALLOWED_WEIGHT);
		}
		this.weight = weight;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) throws APIException {
		if (cost > Constants.MAX_ALLOWED_COST) {
			throw new APIException("Item cost cannot exceed: " + Constants.MAX_ALLOWED_COST);
		}
		this.cost = cost;
	}
}

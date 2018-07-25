package icoRating.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import icoRating.util.LocalDateAdapter;
import icoRating.util.RoundUtil;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model class for an ICO.
 * 
 * @author Benjamin Wyss
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Ico {
	
	/**
	 * UUID must be stored as String because JAXB accepts only String as a XmlID
	 * Do not delete variable and add XmlID to the method "getUuidAsString".
	 * Elsewhere existing Portfolios cannot be loaded
	 */
	@XmlAttribute
	@XmlID
	private String uuidString;
	private UUID uuid;
	
	/**
	 * PropertyVariable must be final so that the ui controller is able to listen to it. 
	 * Variable is final but the String it represents can change using setter method
	 */
	private final StringProperty name;
	private final StringProperty description;
	private final FloatProperty rating;
	private final ObjectProperty<LocalDate> startDate;
	private final ObjectProperty<LocalDate> endDate;
	private final FloatProperty investment;
	
	/**
	 * IcoCriteria are added as Element from Ico to the XML
	 */
	@XmlElement(name="icoCriteriaReference")
	private ArrayList<IcoCriteria> allIcoCriteria;
	
	
	/**
	 * Default constructor.
	 */
	public Ico() {
		this(null, null);
		allIcoCriteria = new ArrayList<IcoCriteria>();
	}

	/**
	 * Constructor
	 * @param name
	 * @param description
	 */
	public Ico(String name, String description) {
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.rating = new SimpleFloatProperty();
		this.startDate = new SimpleObjectProperty<LocalDate>();
		this.endDate = new SimpleObjectProperty<LocalDate>();
		this.investment = new SimpleFloatProperty();
		uuid = UUID.randomUUID();
		uuidString = uuid.toString();
	}
	
	/**
	 * @return the UUID as a String
	 */
	public String getUuidAsString() {
		return uuidString;
	}
	
	/**
	 * @return the UUID
	 */
	public UUID getUuid() {
		return uuid;
	}
	
	/**
	 * @return name as String
	 */
	public String getName() {
		return name.get();
	}
	
	/**
	 * @param name
	 */
	public void setName (String name) {
		this.name.set(name);
	}
	
	/**
	 * @return name as StringProperty
	 */
	public StringProperty nameProperty() {
		return name;
	}
	
	/**
	 * @return description as String
	 */
	public String getDescription() {
		return description.get();
	}
	
	/**
	 * @param description
	 */
	public void setDescription (String description) {
		this.description.set(description);
	}
	
	/**
	 * @return description as StringProperty
	 */
	public StringProperty descriptionProperty() {
		return description;
	}
	
	/**
	 * @return Overall ICO rating as Float
	 */
	public Float getRating() {
		return rating.get();
	}
	
	/**
	 * @return Overall ICO rating as FloatProperty
	 */
	public FloatProperty ratingProperty() {
		return rating;
	}
	
	/**
	 * Uses LocalDateAdapter so save date into XML
	 * @return start date as LocalDate
	 */
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getStartDate() {
		return startDate.get();
	}
	
	/**
	 * @param startDate
	 */
	public void setStartDate (LocalDate startDate) {
		this.startDate.set(startDate);
	}
	
	/**
	 * @return start date a ObjectProperty of type LocalDate
	 */
	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}
	
	/**
	 * Uses LocalDateAdapter so save date into XML
	 * @return end date as LocalDate
	 */
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getEndDate() {
		return endDate.get();
	}
	
	/**
	 * @param endDate
	 */
	public void setEndDate (LocalDate endDate) {
		this.endDate.set(endDate);
	}
	
	/**
	 * @return end date a ObjectProperty of type LocalDate
	 */
	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate;
	}
	
	/**
	 * @return investement as float
	 */
	public Float getInvestment () {
		return investment.get();
	}
	
	/**
	 * @param investment
	 */
	public void setInvestment (Float investment) {
		this.investment.set(investment);
	}
	
	/**
	 * @return investment as FloatProperty
	 */
	public FloatProperty investmentProperty() {
		return investment;
	}
	
	/**
	 * @return ObservableList of all IcoCriteria from the ICO
	 */
	public ObservableList<IcoCriteria> getAllIcoCriteria() {
		ObservableList<IcoCriteria> oAllIcoCriteria = FXCollections.observableArrayList(allIcoCriteria);
		return oAllIcoCriteria;
	}
	
	/**
	 * Adds list of IcoCriteria to the Ico
	 * Current IcoCriteria will be overwritten
	 * Used to load data from XML
	 * @param criteriaList
	 */
	public void setIcoCriterion(ArrayList<IcoCriteria> criteriaList) {
		allIcoCriteria = criteriaList;
	}
	
	/**
	 * Adds an IcoCriteria to the ICO. 
	 * Typically needed when a new Criteria or a new ICO is created
	 * @param IcoCriteria
	 */
	public void addCriteria (IcoCriteria criteria) {
		allIcoCriteria.add(criteria);
	}
	
	/**
	 * Returns all IcoCriteria of the ICO in state inactive
	 * @return ObservableList 
	 */
	public ObservableList<IcoCriteria> getInactiveIcoCriteria() {
		ObservableList<IcoCriteria> inactiveCriteria = FXCollections.observableArrayList();
		for (IcoCriteria c : allIcoCriteria) {
			if (c.isActive() == false) {
				inactiveCriteria.add(c);
			}
		}
		return inactiveCriteria;
	}
	
	/**
	 * Returns all IcoCriteria of the ICO in state active
	 * @return ObservableList
	 */
	public ObservableList<IcoCriteria> getActiveIcoCriteria() {
		ObservableList<IcoCriteria> activeCriteria = FXCollections.observableArrayList();
		for (IcoCriteria c : allIcoCriteria) {
			if (c.isActive() == true) {
				activeCriteria.add(c);
			}
		}
		return activeCriteria;
	}
	
	/**
	 * Removes an IcoCriteria from the ICO
	 * Typically used when a Criteria is deleted
	 * @param Criteria
	 */
	public void removeIcoCriteria (Criteria criteria) {
		for (IcoCriteria c : allIcoCriteria) {
			if (c.getCriteria() == criteria) {
				allIcoCriteria.remove(c);		
			}
		}
	}
	
	/**
	 * Calculates the weighted overall rating of the ICO.
	 * Formula:
	 * - For each ACTIVE IcoCriteria calculate [rating] * [weight from Criteria]
	 * - Sum up the weighted rating of each IcoCriteria = SUM(WeightedRating)
	 * - Sum up the used weights from Criteria = SUM(Weights)
	 * - SUM(WeightedRating) DIVIDED BY SUM(Weights) = Rating
	 * 
	 */
	public void calculateRating() {
		
		ArrayList<Integer> weightedRatings = new ArrayList<Integer>();
		
		int sumWeights = 0;
		float r = 0;
		float rRound = 0;

		for (IcoCriteria c : getActiveIcoCriteria()) {
			if (c.getRating() != 0) {
				int weightedRating = (c.getRating() * c.getCriteria().getWeightAsInt());
				int weight = c.getCriteria().getWeightAsInt();
				weightedRatings.add(weightedRating);
				sumWeights += weight; // => sums up all weight
			}
		}
		if (sumWeights > 0) { // prevent division by 0
			for (Integer weightedRating : weightedRatings) {
				/*
				 * divides each rating with the sum of weights and sums up the result
				 * mathematically the same would be to sum up the rating first an divide it afterwards of the sum of weights
				 * one of the parameter must be float to get a float as a result
				 */
				r += (weightedRating / (float)sumWeights); // => 
			}
		}
		rRound = RoundUtil.round(r, 2);
		rating.set(rRound);
	}	
}

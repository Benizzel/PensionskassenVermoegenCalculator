package icoRating.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import icoRating.util.LocalDateAdapter;
import icoRating.util.RoundUtil;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model class for an ICO.
 * 
 * @author beniw
 *
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Ico {
	
//	private static int count = 0;
	
	@XmlAttribute
	@XmlID
	//
	private String uuidString;
	private UUID uuid;
	
	private StringProperty name;
	private final StringProperty description;
	private FloatProperty rating;
	private ObjectProperty<LocalDate> startDate;
	private ObjectProperty<LocalDate> endDate;
	private FloatProperty investment;
	
	//@XmlIDREF
	@XmlElement(name="icoCriteriaReference")

	private ArrayList<IcoCriteria> allIcoCriteria;
	
	
	/**
	 * Default constructor.
	 */
	
	//TODO
	//scheinbar muss im constructor, wenn objekt ohne instanzvariablen instanziert wird, für alle string attribut ein "null" mitgegeben werden.. also hier this(null) - mal
	//nachforschen, wieso das so ist...
	public Ico() {
		this(null, null);
		allIcoCriteria = new ArrayList<IcoCriteria>();
		
		//id=Integer.toString(count++);
	}
	
	/**
	 * Constructor with some initial data.
	 * Die Auskommentierten Codezeilen würden "richtige" Daten erzeugen. Die anderen instanzieren mit 0
	 */
	
	public Ico(String name, String description) {
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		//Some initial dummy data, just for convenient testing - diese dummy daten werden jedem ICO, den ich mit di
		this.rating = new SimpleFloatProperty();
		//this.startDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2018, 05, 12));
		this.startDate = new SimpleObjectProperty<LocalDate>();
		//this.endDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2018, 05.15));
		this.endDate = new SimpleObjectProperty<LocalDate>();
		//this.investment = new SimpleFloatProperty((float) 999.25);
		this.investment = new SimpleFloatProperty();
		//private ObservableList<Criteria> possibleCriteria = FXCollections.observableArrayList()
		uuid = UUID.randomUUID();
		uuidString = uuid.toString();
	}

	public String getName() {
		return name.get();
	}
	
	//nimmt den String "name" und speichert es als StringObject in der variable "name" vom Object
	public void setName (String name) {
		this.name.set(name);
	}
	
	//wird für das GUI benötigt - Ich nenne die Variable nameProperty, inhalt ist variable name
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getDescription() {
		return description.get();
	}
	
	public void setDescription (String description) {
		this.description.set(description);
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
	
	//wert aus variable rating wird als Integer Object zurückgegeben	
	public Float getRating() {
//		calculateRating();
		return rating.get();
	}
	
	public FloatProperty ratingProperty() {
//		calculateRating();
		return rating;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getStartDate() {
		return startDate.get();
	}
	
	public void setStartDate (LocalDate startDate) {
		this.startDate.set(startDate);
	}
	
	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getEndDate() {
		return endDate.get();
	}
	
	public void setEndDate (LocalDate endDate) {
		this.endDate.set(endDate);
	}
	
	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate;
	}
	
	public Float getInvestment () {
		return investment.get();
	}

	public void setInvestment (Float investment) {
		this.investment.set(investment);
	}
	
	public FloatProperty investmentProperty() {
		return investment;
	}
	
	/**
	 * 
	 * @return List of all Criteria from the Ico
	 */
	
//	@XmlElement(name="icoCriteriaReference")
//	@XmlIDREF
	public ObservableList<IcoCriteria> getAllIcoCriteria() {
		ObservableList<IcoCriteria> oAllIcoCriteria = FXCollections.observableArrayList(allIcoCriteria);
		return oAllIcoCriteria;
	}
	
	public void setIcoCriterion(ArrayList<IcoCriteria> criteriaList) {
		allIcoCriteria = criteriaList;
//		allIcoCriteria = (ObservableList<IcoCriteria>) criteriaList;
	}
	
	public void addCriteria (IcoCriteria criteria) {
		allIcoCriteria.add(criteria);
	}
	
	public ObservableList<IcoCriteria> getInactiveIcoCriteria() {
		ObservableList<IcoCriteria> inactiveCriteria = FXCollections.observableArrayList();
		for (IcoCriteria c : allIcoCriteria) {
			if (c.isActive() == false) {
				inactiveCriteria.add(c);
			}
		}
		return inactiveCriteria;
	}
	
	public ObservableList<IcoCriteria> getActiveIcoCriteria() {
		ObservableList<IcoCriteria> activeCriteria = FXCollections.observableArrayList();
		for (IcoCriteria c : allIcoCriteria) {
			if (c.isActive() == true) {
				activeCriteria.add(c);
			}
		}
		return activeCriteria;
	}
	
	public void removeIcoCriteria (Criteria criteria) {
		for (IcoCriteria c : allIcoCriteria) {
			if (c.getCriteria() == criteria) {
				allIcoCriteria.remove(c);		
				
			}
		}
	}
	
	/**
	 * @return the id
	 */
//	@XmlAttribute
//	@XmlID
	public String getUuidAsString() {
		return uuidString;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void calculateRating() {
		
		ArrayList<Integer> weightedRatings = new ArrayList<Integer>();
//		ArrayList<Integer> weights = new ArrayList<Integer>();
		
		int sumWeights = 0;
		float r = 0;
		float rRound = 0;

		for (IcoCriteria c : getActiveIcoCriteria()) {
			if (c.getRating() != 0) {
				int weightedRating = (c.getRating() * c.getCriteria().getWeightAsInt());
				int weight = c.getCriteria().getWeightAsInt();
				weightedRatings.add(weightedRating);
//				weights.add(weight);
				sumWeights += weight; // => totalWeight = totalWeight + weight. kann ich mit += machen und brauche kein array
			}
		}
//		Variante 1
		if (sumWeights > 0) {
			for (Integer weightedRating : weightedRatings) {
				r += (weightedRating / (float)sumWeights);
			//rechnet: Gewichtetes Rating / Gewicht - jedes einzeln und summiert fortlaufend
			}
		}
		rRound = RoundUtil.round(r, 2);
		
//		Variante 2
//		int sumWeigthedRatings = 0;
//		sumWeigthedRatings = weightedRatings.stream().mapToInt(Integer::intValue).sum();
//		sumWeights = weights.stream().mapToInt(Integer::intValue).sum();
//		if (sumWeights > 0) {
//			r = (sumWeigthedRatings / (float)sumWeights);
//			rRound = RoundUtil.round(r, 2);
//		} else {
//			r = 0;
//		}
		
		rating.set(rRound);
	}
		
}

package icoRating.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

import icoRating.util.Weight;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model Class for a Criteria
 * @author Benjamin Wyss
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Criteria {
	
	@XmlAttribute
	@XmlID
	/**
	 * UUID must be stored as String because JAXB accepts only String as a XmlID
	 * Do not delete variable and add XmlID to the method "getUuidAsString".
	 * Elsewhere existing Portfolios cannot be loaded
	 */
	private String uuidString; //must be a variable because of JAXB accepts only String as an XmlID
	private final UUID uuid;
	private final StringProperty name;
	private final StringProperty description;
	private final StringProperty category;
	private Weight weight;
	
	/**
	 * Default Constructor
	 */
	public Criteria() {
		this(null, null, null, Weight.MEDIUM);
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param description
	 * @param category
	 * @param weight
	 */
	public Criteria(String name, String description, String category, Weight weight) {
		this.name = new SimpleStringProperty(name);
		this.description = new SimpleStringProperty(description);
		this.category = new SimpleStringProperty(category);	
		this.setWeight(weight);
		uuid = UUID.randomUUID();
		uuidString = uuid.toString();
	}
	
	/**
	 * @return the UUID as a String
	 */
	public String getUuidAsString() {
		return uuid.toString();
	}
	
	/**
	 * @return the UUID
	 */
	public UUID getUuid() {
		return uuid;
	}
	
	/**
	 * @return name as a String
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
	 * 
	 * @return name as a StringProperty
	 */
	public StringProperty nameProperty() {
		return name;
	}
	
	/**
	 * @return description as a String
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
	 * @return description as a StringProperty
	 */
	public StringProperty descriptionProperty() {
		return description;
	}
	
	/**
	 * @return category as a String
	 */
	public String getCategory() {
		return category.get();
	}
	
	/**
	 * @param category
	 */
	public void setCategory (String category) {
		this.category.set(category);
	}
	
	/**
	 * @return category as a StringProperty
	 */
	public StringProperty categoryProperty() {
		return category;
	}

	/**
	 * @return the weight as enum Weight
	 */
	public Weight getWeight() {
		return weight;
	}
	
	/**
	 * @return text of Weight as a StringProperty
	 */
	public StringProperty getWeightAsStringProperty() {
		StringProperty weightString = new SimpleStringProperty();
		weightString.setValue(weight.getText());
		return weightString;
	}
	
	/**
	 * @return code of Weight as an int
	 */
	public int getWeightAsInt() {
		int weightInt = weight.getCode();
		return weightInt;
	}

	/**
	 * @param enum weight to set
	 */
	public void setWeight(Weight weight) {
		this.weight = weight;
	}
}

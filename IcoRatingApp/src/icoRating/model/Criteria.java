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
 * Model Class for a rating criteria of an ICO.
 * 
 * @author beniw
 *
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Criteria {
	
//	private static int count = 0;
	
	@XmlAttribute
	@XmlID
	private String uuidString;
	private UUID uuid;
	
	
	private final StringProperty name;
	private final StringProperty description;
	private final StringProperty category;
	private Weight weight;
	
	/**
	 * Default Constructor
	 */
	
	public Criteria() {
		this(null, null, null, Weight.MEDIUM);
		//id=Integer.toString(count++);
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
	
	//holt sich das StringObject und gibt es als String zurück
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
	
	public String getCategory() {
		return category.get();
	}
	
	public void setCategory (String category) {
		this.category.set(category);
	}
	
	public StringProperty categoryProperty() {
		return category;
	}

	/**
	 * @return the weight
	 */
	public Weight getWeight() {
		return weight;
	}
	
	public StringProperty getWeightAsStringProperty() {
		StringProperty weightString = new SimpleStringProperty();
		weightString.setValue(weight.getText());
		return weightString;
	}
	
	public int getWeightAsInt() {
		int weightInt = weight.getCode();
		return weightInt;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Weight weight) {
		this.weight = weight;
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
}

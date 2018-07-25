package icoRating.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import icoRating.util.Rating;

/**
 * Model class for an IcoCriteria
 * Each Criteria becomes an IcoCriteria for each ICO
 * If an ICO is added, all Criteria must be added to the ICO as an IcoCriteria
 * @author Benjamin Wyss
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class IcoCriteria {

	/**
	 * UUID must be stored as String because JAXB accepts only String as a XmlID
	 * Do not delete variable and add XmlID to the method "getUuidAsString".
	 * Elsewhere existing Portfolios cannot be loaded
	 */
	@XmlAttribute
	@XmlID
	private String uuidString;
	private UUID uuid;
	
	@XmlIDREF
	private Criteria criteria;
	
	@XmlIDREF
	private Ico ico;
	
	@XmlElement	
	private Rating rating;
	private boolean isActive;
	
	/**
	 * Default constructor.
	 */
	public IcoCriteria() {
		this(null, null);
	}

	/**
	 * Constructor to 
	 * @param criteria
	 * @param ico
	 */
	public IcoCriteria(Criteria criteria, Ico ico) {
		this.criteria = criteria;
		this.ico = ico;
		rating = Rating.NOTRATED;
		isActive = false;
		uuid = UUID.randomUUID();
		uuidString = uuid.toString();
	}
	
	/**
	 * @return the UUID as String
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
	 * @return the underlined Criteria
	 */
	public Criteria getCriteria() {
		return criteria;
	}
	
	/**
	 * @return the ICO the IcoCriteria refers to
	 */
	public Ico getIco() {
		return ico;
	}

	/**
	 * activated IcoCriteria are used to calculate the ICOs overall rating
	 * @return if the IcoCriteria is activate
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * @return the specific rating of this IcoCriteria as int
	 */
	public Integer getRating() {
		Integer ratingInt = rating.getCode();
		return ratingInt;
	}

	/**
	 * @param rating as enum Rating
	 */
	public void setRating (Rating rating) {
		this.rating = rating;
	}
}

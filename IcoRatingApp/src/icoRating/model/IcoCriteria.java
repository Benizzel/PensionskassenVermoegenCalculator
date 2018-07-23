package icoRating.model;

import java.util.Observable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import icoRating.util.LocalDateAdapter;
import icoRating.util.Rating;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

/**
 * Model class for a criteria added to an Ico
 * 
 * @author beniw
 *
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
public class IcoCriteria {
	
//	private static int count = 0;
	
	@XmlAttribute
	@XmlID
	private String uuidString;
	private UUID uuid;
	
	
	@XmlIDREF
	private Criteria criteria;
	
	@XmlIDREF
	private Ico ico;
	
		
	private Rating rating;
	private boolean isActive;
	
	/**
	 * Default constructor.
	 */
	public IcoCriteria() {
		this(null, null);
	}


	/*
	 * Constructor to initialise a new Criteria on all ICO
	 */
	
	public IcoCriteria(Criteria criteria, Ico ico) {
		this.criteria = criteria;
		this.ico = ico;
		rating = Rating.NOTRATED;
		isActive = false;
		uuid = UUID.randomUUID();
		uuidString = uuid.toString();
	}
	
	/*
	 * Constructor to add a Criteria to an ICO
	 */
	
	public IcoCriteria(Criteria criteria, Ico ico, Rating rating, boolean isActive) {
		this.criteria = criteria;
		this.ico = ico;
		this.rating = rating;
		this.isActive = isActive;
		uuid = UUID.randomUUID();
		uuidString = uuid.toString();
	}
	
//	@XmlIDREF
//	@XmlElement
	public Criteria getCriteria() {
		return criteria;
	}
	
	//@XmlIDREF
	public Ico getIco() {
		return ico;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	public Integer getRating() {
		Integer ratingInt = rating.getCode();
		return ratingInt;
	}

	public void setRating (Rating rating) {
		this.rating = rating;
	}
	
//	@XmlAttribute
//	XmlID
	public String getUuidAsString() {
		return uuidString;
	}
	
	public UUID getUuid() {
		return uuid;
	}


}

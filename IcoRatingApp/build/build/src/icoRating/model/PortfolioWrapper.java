package icoRating.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap Portfolio data. This is used for saving the
 * list of criteria to XML.
 * This Class acts as root element for the XML
 * 
 * @author Benjamin Wyss
 */
@XmlRootElement(name = "Portfolio")
public class PortfolioWrapper {

	private List<Criteria> criteria;
	private List<Ico> ico;
	
	/**
	 * @return list of the Criteria from the XML
	 */
	@XmlElement(name = "criteria")
	public List<Criteria> getCriterion() {
        return criteria;
    }

	/**
	 * @param List of Criteria for the XML
	 */
    public void setCriterion(List<Criteria> criteria) {
        this.criteria = criteria;
    }
    
    /**
     * An ICO in the XML includes the IcoCriteria referring to the ICO
     * @return list of the ICO from the XML
     */
    @XmlElement(name = "ico")
    public List<Ico> getIcos() {
    	return ico;
    }
    
    /**
     * @param List of ICO for the XML
     */
    public void setIcos(List<Ico> ico) {
    	this.ico = ico;
    }  
}

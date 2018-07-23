package icoRating.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of criteria. This is used for saving the
 * list of criteria to XML.
 * 
 * @author BeniW
 */
@XmlRootElement(name = "Portfolio")
public class PortfolioWrapper {

	private List<Criteria> criteria;
	private List<Ico> ico;
//	private List<IcoCriteria> icoCriteria;
	
	@XmlElement(name = "criteria")
//	@XmlIDREF
	public List<Criteria> getCriterion() {
        return criteria;
    }

    public void setCriterion(List<Criteria> criteria) {
        this.criteria = criteria;
    }
    
    @XmlElement(name = "ico")
    public List<Ico> getIcos() {
    	return ico;
    }
    
    public void setIcos(List<Ico> ico) {
    	this.ico = ico;
    }
    
//    @XmlElement(name = "icoCriteria")
//    public List<IcoCriteria> getIcoCriterion() {
//    	return icoCriteria;
//    }
//    
//    public void setIcoCriterion(List<IcoCriteria> icoCriteria) {
//    	this.icoCriteria = icoCriteria;
//    }
    
    
}

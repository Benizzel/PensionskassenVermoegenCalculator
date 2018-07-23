package icoRating.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of criteria. This is used for saving the
 * list of criteria to XML.
 * 
 * @author BeniW
 */
@XmlRootElement(name = "Portfolio")
public class IcoListWrapper {
	
	private List<Criteria> criterion;
	private List<Ico> ico;
	private List<IcoCriteria> icoCriteria;
	
	@XmlElement(name = "criterion")
    public List<Criteria> getCriterion() {
        return criterion;
    }

    public void setCriterion(List<Criteria> criterion) {
        this.criterion = criterion;
    }
    
    @XmlElement(name = "ico")
    public List<Ico> getIcos() {
    	return ico;
    }
    
    public void setIcos(List<Ico> ico) {
    	this.ico = ico;
    }
    
    @XmlElement(name = "icoCriteria")
    public List<IcoCriteria> getIcoCriteria() {
    	return icoCriteria;
    }
    
    public void setIcoCriterion(List<IcoCriteria> icoCriteria) {
    	this.icoCriteria = icoCriteria;
    }   
    
    	

}

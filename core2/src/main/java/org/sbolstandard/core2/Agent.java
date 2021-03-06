package org.sbolstandard.core2;

import static org.sbolstandard.core2.URIcompliance.createCompliantURI;

import java.net.URI;

/**
 * Represents an Agent object in the SBOL data model.
 * 
 * @author Chris Myers
 * @version 2.2
 */

public class Agent extends TopLevel {

	/**
	 * @param identity
	 * @throws SBOLValidationException if any of the following condition is satisfied:
	 * <ul>
	 * <li>an SBOL validation rule violation occurred in {@link #setHadRole(URI)}.</li>
	 * </ul>
	 */
	Agent(URI identity) throws SBOLValidationException {
		super(identity);
	}

	/**
	 * @param agent
	 * @throws SBOLValidationException
	 */
	private Agent(Agent agent) throws SBOLValidationException {
		super(agent);
	}
	
	void copy(Agent agent) throws SBOLValidationException {
		((TopLevel)this).copy((TopLevel)agent);
	}
	
	/* (non-Javadoc)
	 * @see org.sbolstandard.core2.abstract_classes.TopLevel#copy(java.lang.String, java.lang.String, java.lang.String)
	 */
	/**
	 * @throws SBOLValidationException if an SBOL validation rule violation occurred in 
	 * any of the following methods:
	 * <ul>
	 * <li>{@link #deepCopy()},</li>
	 * <li>{@link URIcompliance#createCompliantURI(String, String, String)},</li>
	 * <li>{@link #setDisplayId(String)},</li>
	 * <li>{@link #setVersion(String)},</li>
	 * <li>{@link #setWasDerivedFrom(URI)}, or</li>
	 * <li>{@link #setIdentity(URI)}.</li>
	 * </ul>
	 */
	@Override
	Agent copy(String URIprefix, String displayId, String version) throws SBOLValidationException {
		Agent cloned = this.deepCopy();
		cloned.setPersistentIdentity(createCompliantURI(URIprefix,displayId,""));
		cloned.setDisplayId(displayId);
		cloned.setVersion(version);
		URI newIdentity = createCompliantURI(URIprefix,displayId,version);
		if (!this.getIdentity().equals(newIdentity)) {
			cloned.addWasDerivedFrom(this.getIdentity());
		} else {
			cloned.setWasDerivedFroms(this.getWasDerivedFroms());
		}
		cloned.setIdentity(newIdentity);
		return cloned;
	}

	@Override
	Agent deepCopy() throws SBOLValidationException {
		return new Agent(this);
	}

	/**
	 * Updates this participation with a compliant URI.
	 * 
	 * @throws SBOLValidationException if an SBOL validation rule violation occurred in any of the following methods:
	 * <ul>
	 * <li>{@link URIcompliance#createCompliantURI(String, String, String)},</li>
	 * <li>{@link #setWasDerivedFrom(URI)},</li>
	 * <li>{@link #setIdentity(URI)},</li>
	 * <li>{@link #setDisplayId(String)}, or</li>
	 * <li>{@link #setVersion(String)}.</li>
	 * </ul>
	 */
	void updateCompliantURI(String URIprefix, String displayId, String version) throws SBOLValidationException {
		if (!this.getIdentity().equals(createCompliantURI(URIprefix,displayId,version))) {
			this.addWasDerivedFrom(this.getIdentity());
		}
		this.setIdentity(createCompliantURI(URIprefix,displayId,version));
		this.setPersistentIdentity(createCompliantURI(URIprefix,displayId,""));
		this.setDisplayId(displayId);
		this.setVersion(version);
	}
	
	/* (non-Javadoc)
	 * @see org.sbolstandard.core2.abstract_classes.TopLevel#checkDescendantsURIcompliance()
	 */
	@Override
	void checkDescendantsURIcompliance() {//throws SBOLValidationException {
		//URIcompliance.isTopLevelURIformCompliant(this.getIdentity());
	}

	@Override
	public String toString() {
		return "Agent ["
				+ super.toString()
				+ "]";
	}

}

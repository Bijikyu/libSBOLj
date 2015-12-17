package org.sbolstandard.core2;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhen Zhang
 * @author Tramy Nguyen
 * @author Nicholas Roehner
 * @author Matthew Pocock
 * @author Goksel Misirli
 * @author Chris Myers
 * @version 2.0-beta
 */

public class SBOLValidate {
	
	/**
	 * the current SBOL version
	 */
	private static final String SBOLVersion = "2.0";
	private static List<String> errors = null;
	
	public static void clearErrors() {
		errors = new ArrayList<String>();
	}
	
	public static List<String> getErrors() {
		return errors;
	}
	
	public static int getNumErrors() {
		return errors.size();
	}
	
	private static void usage() {		
		System.err.println("libSBOLj version " + SBOLVersion);
		System.err.println("Description: Validates the contents of an SBOL document,\n" 
				+ "converting from SBOL 1.1 to SBOL " + SBOLVersion + ", if necessary,\n" 
				+ "and printing the document contents if validation succeeds");
		System.err.println();
		System.err.println("Usage:");
		System.err.println("\tjava --jar libSBOLj.jar [options] <inputFile> [-o <outputFile> -p <URIprefix> -v <version>]");
		System.err.println();
		System.err.println("-t  uses types in URIs");
		System.err.println("-i  incomplete SBOL document");
		System.err.println("-n  non-compliant SBOL document");
		System.exit(1);
	}
	
	/**
	 * Validate SBOL objects are compliant in the given {@code sbolDocument}.
	 * 
	 * @param sbolDocument
	 * @throws SBOLValidationException if any top-level objects or any of their children or grandchildren 
	 * in the given {@code sbolDocument} contain a non-compliant URI.
	 */
	public static void validateCompliance(SBOLDocument sbolDocument) {
		for (Collection collection : sbolDocument.getCollections()) {
			if (!URIcompliance.isTopLevelURIcompliant(collection) || !collection.checkDescendantsURIcompliance()) {
				errors.add("Collection " + collection.getIdentity() + " is not URI compliant.");
			}
		}
		for (Sequence sequence : sbolDocument.getSequences()) {
			if (!URIcompliance.isTopLevelURIcompliant(sequence) || !sequence.checkDescendantsURIcompliance()) {
				errors.add("Sequence " + sequence.getIdentity() + " is not URI compliant.");
			}
		}
		for (ComponentDefinition componentDefinition : sbolDocument.getComponentDefinitions()) {
			if (!URIcompliance.isTopLevelURIcompliant(componentDefinition) || !componentDefinition.checkDescendantsURIcompliance()) {
				errors.add("ComponentDefinition " + componentDefinition.getIdentity() + " is not URI compliant.");
			}
		}
		for (ModuleDefinition moduleDefinition : sbolDocument.getModuleDefinitions()) {
			if (!URIcompliance.isTopLevelURIcompliant(moduleDefinition) || !moduleDefinition.checkDescendantsURIcompliance()) 	
				errors.add("ModuleDefinition " + moduleDefinition.getIdentity() + " is not URI compliant.");
		}
		for (Model model : sbolDocument.getModels()) {
			if (!URIcompliance.isTopLevelURIcompliant(model) || !model.checkDescendantsURIcompliance()) 
				errors.add("Model " + model.getIdentity() + " is not URI compliant.");
		}
		for (GenericTopLevel genericTopLevel : sbolDocument.getGenericTopLevels()) {
			if (!URIcompliance.isTopLevelURIcompliant(genericTopLevel) || !genericTopLevel.checkDescendantsURIcompliance()) 
				errors.add("GenericTopLevel " + genericTopLevel.getIdentity() + " is not URI compliant.");
		}
	}
	
	protected static void validateCollectionCompleteness(SBOLDocument sbolDocument,Collection collection) {
		for (URI member : collection.getMemberURIs()) {
			if (sbolDocument.getTopLevel(member)==null) {
				errors.add("Collection " + collection.getIdentity() + " member " + member + " not found in document.");
			}
		}
	}


	protected static void validateComponentDefinitionCompleteness(SBOLDocument sbolDocument,ComponentDefinition componentDefinition) {
		for (URI sequenceURI : componentDefinition.getSequenceURIs()) {
			if (sbolDocument.getSequence(sequenceURI)==null) {
				errors.add("ComponentDefinition " + componentDefinition.getIdentity() + " sequence " + 
						sequenceURI + " not found in document.");
			}
		}
		for (Component component : componentDefinition.getComponents()) {
			if (component.getDefinition()==null) {
				errors.add("Component " + component.getIdentity() + " definition " + 
						component.getDefinitionURI() + " not found in document.");
			}
		}
	}

	protected static void validateModuleDefinitionCompleteness(SBOLDocument sbolDocument,ModuleDefinition moduleDefinition) {
		for (URI modelURI : moduleDefinition.getModelURIs()) {
			if (sbolDocument.getModel(modelURI) == null) {
				errors.add("ModuleDefinition " + moduleDefinition.getIdentity() + " model " + 
						modelURI + " not found in document.");
			}
		}
		for (FunctionalComponent functionalComponent : moduleDefinition.getFunctionalComponents()) {
			if (functionalComponent.getDefinition() == null) {
				errors.add("FunctionalComponent " + functionalComponent.getIdentity() + " definition " + 
						functionalComponent.getDefinitionURI() + " not found in document.");
			}
		}
		for (Module module : moduleDefinition.getModules()) {
			if (module.getDefinition() == null) {
				errors.add("Module " + module.getIdentity() + " definition " + 
						module.getDefinitionURI() + " not found in document.");
			}
			for (MapsTo mapsTo : module.getMapsTos()) {
				if (mapsTo.getRemote().getAccess().equals(AccessType.PRIVATE)) {
					errors.add("MapsTo '" + mapsTo.getIdentity() + "' has a private remote Functional Component '" + mapsTo.getRemoteURI());
				}
				if (mapsTo.getRefinement().equals(RefinementType.VERIFYIDENTICAL)) {
					if (!mapsTo.getLocal().getDefinitionURI().equals(mapsTo.getRemote().getDefinitionURI())) {
						errors.add("MapsTo '" + mapsTo.getIdentity() + "' have non-identical local and remote Functional Component");
					}
				}
			}
		}
	}

	/**
	 * Validate if all URI references to SBOL objects are in the same given {@code sbolDocument}.
	 * 
	 * @param sbolDocument
	 * @throws SBOLValidationException if any reference made by Collection, ComponentDefinition,
	 * or ModuleDefinition is not in the given {@code sbolDocument}
	 */
	public static void validateCompleteness(SBOLDocument sbolDocument) {
		for (Collection collection : sbolDocument.getCollections()) {
			validateCollectionCompleteness(sbolDocument,collection);
		}
		for (ComponentDefinition componentDefinition : sbolDocument.getComponentDefinitions()) {
			validateComponentDefinitionCompleteness(sbolDocument,componentDefinition);
		}
		for (ModuleDefinition moduleDefinition : sbolDocument.getModuleDefinitions()) {
			validateModuleDefinitionCompleteness(sbolDocument,moduleDefinition);
		}
	}
	
	public static void validateOntologyUsage(SBOLDocument sbolDocument) {
		SequenceOntology so = new SequenceOntology();
		SystemsBiologyOntology sbo = new SystemsBiologyOntology();
		for (Sequence sequence : sbolDocument.getSequences()) {
			if (!sequence.getEncoding().equals(Sequence.IUPAC_DNA) &&
				!sequence.getEncoding().equals(Sequence.IUPAC_RNA) &&
				!sequence.getEncoding().equals(Sequence.IUPAC_PROTEIN) &&
				!sequence.getEncoding().equals(Sequence.SMILES)) {
				errors.add("Sequence " + sequence.getIdentity() + " has unrecoginized encoding (see Table 1): " + sequence.getEncoding());
			}
		}
		for (ComponentDefinition compDef : sbolDocument.getComponentDefinitions()) {
			int numBioPAXtypes = 0;
			for (URI type : compDef.getTypes()) {
				if (type.equals(ComponentDefinition.DNA) ||
					type.equals(ComponentDefinition.RNA) ||
					type.equals(ComponentDefinition.PROTEIN) ||
					type.equals(ComponentDefinition.COMPLEX) ||
					type.equals(ComponentDefinition.SMALL_MOLECULE)) {
					numBioPAXtypes++;
				}
			}
			if (numBioPAXtypes == 0) {
				errors.add("ComponentDefinition " + compDef.getIdentity() + " does not have a recognized BioPAX type (see Table 2).");
			} else if (numBioPAXtypes > 1){
				errors.add("ComponentDefinition " + compDef.getIdentity() + " has conflicting BioPAX types (see Table 2).");
			}
			if (compDef.getTypes().contains(ComponentDefinition.DNA)) {
				boolean foundSO = false;
				for (URI role : compDef.getRoles()) {
					try { 
						so.getName(role);
						foundSO = true;
						break;
					} catch (Exception e){
					}
				}
				if (!foundSO) {
					errors.add("DNA ComponentDefinition " + compDef.getIdentity() + " does not have a recognized SO role.");
				}
			}
			for (SequenceConstraint sc : compDef.getSequenceConstraints()) {
				try {
					sc.getRestriction();
				}
				catch (Exception e) {
					errors.add("SequenceConstraint " + sc.getIdentity() + " does not have a recognized restriction type (Table 7): " + sc.getRestrictionURI());
				}
			}
		}
		for (Model model : sbolDocument.getModels()) {
			if (!model.getLanguage().equals(Model.SBML) &&
				!model.getLanguage().equals(Model.CELLML) &&
				!model.getLanguage().equals(Model.BIOPAX)) {
				errors.add("Model " + model.getIdentity() + " has unrecoginized language (see Table 8): " + model.getLanguage());
			}
			try {
				if (!sbo.isDescendantOf(model.getFramework(), SystemsBiologyOntology.MODELING_FRAMEWORK)) {
					errors.add("Model " + model.getIdentity() + " does not have a recoginized SBO modeling framework: " + model.getFramework());
				}
			}
			catch (Exception e) {
				errors.add("Model " + model.getIdentity() + " does not have a recoginized SBO modeling framework: " + model.getFramework());
			}
		}
		for (ModuleDefinition modDef : sbolDocument.getModuleDefinitions()) {
			for (Interaction interaction : modDef.getInteractions()) {
				int numSBOtype = 0;
				for (URI type : interaction.getTypes()) {
					try {
						if (sbo.isDescendantOf(type, SystemsBiologyOntology.OCCURRING_ENTITY_REPRESENTATION)) {
							numSBOtype++;
						}
					}
					catch (Exception e) {
					}
				}
				if (numSBOtype == 0) {
					errors.add("Interaction " + interaction.getIdentity() + 
							" has no type from occurring entity branch of the SBO.");
				} else if (numSBOtype > 1) {
					errors.add("Interaction " + interaction.getIdentity() + 
							" has more than one type from occurring entity branch of the SBO.");
				}
				for (Participation participation : interaction.getParticipations()) {
					int numSBOrole = 0;
					for (URI role : participation.getRoles()) {
						try {
							if (sbo.isDescendantOf(role, SystemsBiologyOntology.PARTICIPANT_ROLE)) {
								numSBOrole++;
							}
						}
						catch (Exception e) {
						}
					}
					if (numSBOrole == 0) {
						errors.add("Participation " + participation.getIdentity() + 
								" has no role from participant role branch of the SBO.");
					} else if (numSBOrole > 1) {
						errors.add("Participation " + participation.getIdentity() + 
								" has more than one role from participant role branch of the SBO.");
					}
				}
			}
		}
	}
	
	/**
	 * Command line method for reading an input file and producing an output file. 
	 * <p>
	 * By default, validations on compliance and completeness are performed, and types
	 * for top-level objects are not used in URIs.
	 * <p>
	 * Options:
	 * <p> 
	 * "-t" uses types in URIs,
	 * <p>
	 * "-i" turns off completeness checking,
	 * <p>
	 * "-n" indicates a non-compliant SBOL document,
	 * <p>
	 * "-o" specifies an output filename,
	 * <p>
	 * "-p" specifies the default URI prefix of the output file, and 
	 * <p>
	 * "-v" returns the version of the libSBOLj. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "";
		String outputFile = "";
		String URIPrefix = "";
		String version = "";
		boolean complete = true;
		boolean compliant = true;
		boolean typesInURI = false;
		boolean bestPractice = false;
		int i = 0;
		while (i < args.length) {
			if (args[i].equals("-i")) {
				complete = false;
			} else if (args[i].equals("-t")) {
				typesInURI = true;
			} else if (args[i].equals("-b")) {
				bestPractice = true;
			} else if (args[i].equals("-n")) {
				compliant = false;
			} else if (args[i].equals("-o")) {
				if (i+1 >= args.length) {
					usage();
				}
				outputFile = args[i+1];
				i++;
			} else if (args[i].equals("-p")) {
				if (i+1 >= args.length) {
					usage();
				}
				URIPrefix = args[i+1];
				i++;
			} else if (args[i].equals("-v")) {
				if (i+1 >= args.length) {
					usage();
				}
				version = args[i+1];
				i++;
			} else if (fileName.equals("")) {
				fileName = args[i];
			} else {
				usage();
			}
			i++;
		}
		if (fileName.equals("")) usage();
		try {
			if (!URIPrefix.equals("")) {
				SBOLReader.setURIPrefix(URIPrefix);
			}
			if (!compliant) {
				SBOLReader.setCompliant(false);
			}
			SBOLReader.setTypesInURI(typesInURI);
			SBOLReader.setVersion(version);
	        SBOLDocument doc = SBOLReader.read(fileName);
	        doc.setTypesInURIs(typesInURI);
			clearErrors();
	        if (compliant) validateCompliance(doc);
	        if (complete) validateCompleteness(doc);
	        if (bestPractice) validateOntologyUsage(doc);
	        if (getNumErrors()==0) {
	        	//System.out.println("Validation successful, no errors.");
	        	if (outputFile.equals("")) {
	        		SBOLWriter.write(doc, (System.out));
	        	} else {
	        		SBOLWriter.write(doc, outputFile);
	        	}
	        } else {
	        	for (String error : getErrors()) {
	        		System.err.println(error);
	        	}
	        	System.err.println("Validation failed.\n");
	        }
		}
		catch (Exception e) {
        	System.err.println(e.getMessage()+"\nValidation failed.");
		}
		catch (Throwable e) {
        	System.err.println(e.getMessage()+"\nValidation failed.");
		}
	}

}

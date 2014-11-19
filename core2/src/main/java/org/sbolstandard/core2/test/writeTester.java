package org.sbolstandard.core2.test;

import static uk.ac.ncl.intbio.core.datatree.Datatree.NamedProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.sbolstandard.core2.*; 
import org.sbolstandard.core2.abstract_classes.Documented;
import org.sbolstandard.core2.abstract_classes.Identified;
import org.sbolstandard.core2.abstract_classes.Location;

import uk.ac.ncl.intbio.core.datatree.NamedProperty;

public class writeTester {
	
	/*
	 * TODO: rename getData() to getIdentificationData()
	 */
	public static void main( String[] args ) throws Exception
	{
		SBOLDocument SBOL2Doc_test = new SBOLDocument(); 
		get_myParts(SBOL2Doc_test);
		SBOLWriter.write(SBOL2Doc_test,(System.out));
	}
	
	private static Collection get_myParts (SBOLDocument SBOL2Doc_test)
	{	
		Collection myParts = createCollection(SBOL2Doc_test,
				getData("myParts/1/0","myParts","1.0","myParts","myParts", "myParts"), 
				getAnnotation_List(createAnnotation(getURI("myPart_relation"),createTurtle()))); 
		
		myParts.addMember(get_pLacSeq(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_tetRSeq(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_pLactetRSeq(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_pLac(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_tetR(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_pLactetR(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_LacI_Inv(SBOL2Doc_test).getIdentity());
		
		myParts.addMember(get_LacI(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_TetR(SBOL2Doc_test).getIdentity());
		
		myParts.addMember(get_ptetSeq(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_lacISeq(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_ptetlacISeq(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_ptet(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_lacI(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_ptetlacI(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_TetR_Inv(SBOL2Doc_test).getIdentity());
		
		myParts.addMember(get_Toggle(SBOL2Doc_test).getIdentity());
		myParts.addMember(get_ToggleModel(SBOL2Doc_test).getIdentity());
		
		return myParts; 
	}
	
	private static Sequence get_pLacSeq (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceData(SBOL2Doc_test, 
				getData("pLacSeq/1/0","pLacSeq","1.0","pLacSeq","pLacSeq", "pLacSeq", "pLacSeq_element", "pLacSeq_encoding")); 
	}
	
	private static Sequence get_tetRSeq (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceData(SBOL2Doc_test, 
				getData("tetRSeq/1/0","tetRSeq","1.0","tetRSeq","tetRSeq", "tetRSeq", "tetRSeq_element", "tetRSeq_encoding")); 
	}
	
	private static Sequence get_pLactetRSeq (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceData(SBOL2Doc_test, 
				getData("pLactetRSeq/1/0","pLactetRSeq","1.0","pLactetRSeq","pLactetRSeq", "pLactetRSeq", "pLactetRSeq_element", "pLactetRSeq_encoding")); 
	}
	
	private static ComponentDefinition get_pLac (SBOLDocument SBOL2Doc_test)
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("DNA"),
				getSetOfURI("Promoter"),
				getData("pLac/1/0","pLac","1.0","pLac","pLac", "pLac"),
				get_pLacSeq(SBOL2Doc_test), 
				null, null, null); 	
	}
	
	private static ComponentDefinition get_tetR (SBOLDocument SBOL2Doc_test)
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("DNA"),
				getSetOfURI("CDS"),
				getData("tetR/1/0","tetR","1.0","tetR","tetR", "tetR"),
				get_tetRSeq(SBOL2Doc_test), 
				null, null, null);	
	}
	
	private static Component get_P (SBOLDocument SBOL2Doc_test)
	{
		return createComponentData(
				getData("P/1/0", "P", "P_1.0", "P", "P", "P", "public"), 
				get_pLac(SBOL2Doc_test));
	}
	
	private static Component get_C (SBOLDocument SBOL2Doc_test)
	{
		return createComponentData(
				getData("C/1/0", "C", "C_1.0", "C", "C", "C", "public"), 
				get_tetR(SBOL2Doc_test));
	}
	
	private static SequenceAnnotation get_p_structAnnotate (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceAnnotationData(
				getData("p_structAnnotate", "p_structAnnotate", "1.0", "p_structAnnotate", "p_structAnnotate", "p_structAnnotate"), 
				get_P(SBOL2Doc_test), 
				0, 10);
	}
	
	private static SequenceAnnotation get_c_structAnnotate (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceAnnotationData(
				getData("p_structAnnotate", "p_structAnnotate", "1.0", "p_structAnnotate", "p_structAnnotate", "p_structAnnotate"), 
				get_P(SBOL2Doc_test), 
				11, 20);
	}
	
	private static SequenceConstraint get_sequence_constraint (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceConstraintData(
				getData("struct_constraint", "struct_constraint", "1.0", "precedes"), 
				get_P(SBOL2Doc_test), 
				get_C(SBOL2Doc_test));
	}
	
	private static ComponentDefinition get_pLactetR (SBOLDocument SBOL2Doc_test)
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("DNA"),
				getSetOfURI("Gene"),
				getData("pLactetR/1/0","pLactetR","1.0","pLactetR","pLactetR", "pLactetR"),
				get_pLactetRSeq(SBOL2Doc_test), 
				getComponent_List(get_P(SBOL2Doc_test), get_C(SBOL2Doc_test)), 
				null,  
				getSequenceConstraint_List(get_sequence_constraint(SBOL2Doc_test))); 
	}
	
	private static ComponentDefinition get_LacI (SBOLDocument SBOL2Doc_test)
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("Protein"),
				getSetOfURI("Transcriptionfactor"),
				getData("LacI/1/0","LacI","1.0","LacI", "LacI", "LacI"),
				null, 
				null, null, null); 
	}
	
	private static ComponentDefinition get_TetR (SBOLDocument SBOL2Doc_test)
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("Protein"),
				getSetOfURI("Transcriptionfactor"),
				getData("TetR/1/0","TetR","1.0","TetR", "TetR", "TetR"),
				null, 
				null, null, null); 
	}
	
	private static FunctionalComponent get_LacIIn (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("LacIIn/1/0","LacIIn","1.0","LacIIn","LacIIn", "LacIIn","public", "input"),
				get_LacI(SBOL2Doc_test)); 
	}
	
	private static FunctionalComponent get_TetROut (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("TetROut/1/0","TetROut","1.0","TetROut","TetROut", "TetROut","public", "output"),
				get_TetR(SBOL2Doc_test));
	}
	
	private static FunctionalComponent get_LacIInv (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("LacIInv/1/0","LacIInv","1.0","LacInv","LacIInv", "LacIInv","private", "none"),
				get_pLactetR(SBOL2Doc_test));
	}
	
	private static Participation get_p1a (SBOLDocument SBOL2Doc_test, String identity)
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("repressor"), 
				get_LacIIn(SBOL2Doc_test));
	}
	
	private static Participation get_p2a (SBOLDocument SBOL2Doc_test, String identity )
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("repressed"), 
				get_LacIInv(SBOL2Doc_test));
	}
	
	private static Participation get_p4a (SBOLDocument SBOL2Doc_test, String identity )
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("producer"), 
				get_TetRInv(SBOL2Doc_test));
	}
	
	private static Participation get_p3a (SBOLDocument SBOL2Doc_test, String identity)
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("produced"), 
				get_TetROut(SBOL2Doc_test));
	}
	
	private static Interaction get_interact1a (SBOLDocument SBOL2Doc_test)
	{
		return createInteractionData(
				getData("interact1/1/0","interact1","1.0","interact1","interact1", "interact1"),
				getParticipation_List(get_p1a(SBOL2Doc_test, "p1a"), get_p2a(SBOL2Doc_test, "p2a")), 
				getSetOfURI("repression")); 
	}
	
	private static Interaction get_interact2a (SBOLDocument SBOL2Doc_test)
	{
		return createInteractionData(
				getData("interact2/1/0","interact2","1.0","interact2","interact2", "interact2"),
				getParticipation_List(get_p4a(SBOL2Doc_test,"p4a"), get_p3a(SBOL2Doc_test,"p3a")), 
				getSetOfURI("production")); 
	}
	
	private static ModuleDefinition get_LacI_Inv (SBOLDocument SBOL2Doc_test)
	{
		return createModuleDefinitionData(SBOL2Doc_test, 
				getSetOfURI("Inverter"),
				getSetOfURI("Transcriptionfactor"),
				getData("LacI_Inv/1/0","LacI_Inv","1.0","LacI_Inv","LacI_Inv", "LacI_Inv"),
				getFunctionalInstantiation_List(
						get_LacIIn(SBOL2Doc_test), 
						get_TetROut(SBOL2Doc_test), 
						get_LacIInv(SBOL2Doc_test)), 
				getInteraction_List(
						get_interact1a(SBOL2Doc_test), 
						get_interact2a(SBOL2Doc_test)), 
				null, null, 
				getAnnotation_List(createAnnotation(getURI("LacI_Inv_relation"),createTurtle()))
				);
	}
	
	
	// ------------------------------ CREATING TetR_Inverter ------------------------------
	private static Sequence get_ptetSeq (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceData(SBOL2Doc_test, 
				getData("ptetSeq/1/0","ptetSeq","1.0","ptetSeq","ptetSeq", "ptetSeq", "ptetSeq_element", "ptetSeq_encoding")); 
	}
	
	private static Sequence get_lacISeq (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceData(SBOL2Doc_test, 
				getData("lacISeq/1/0","lacISeq","1.0","lacISeq","lacISeq", "lacISeq", "lacISeq_element", "lacISeq_encoding")); 
	}
	
	private static Sequence get_ptetlacISeq (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceData(SBOL2Doc_test, 
				getData("ptetlacISeq/1/0","ptetlacISeq","1.0","ptetlacISeq","ptetlacISeq", "ptetlacISeq", "ptetlacISeq_element", "ptetlacISeq_encoding")); 
	}
	
	private static ComponentDefinition get_ptet(SBOLDocument SBOL2Doc_test)
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("DNA"),
				getSetOfURI("Promoter"),
				getData("ptet/1/0","ptet","1.0","ptet","ptet", "ptet"),
				get_ptetSeq(SBOL2Doc_test), 
				null, null, null);  	
	}
	
	private static ComponentDefinition get_lacI (SBOLDocument SBOL2Doc_test) 
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("DNA"),
				getSetOfURI("CDS"),
				getData("lacI/1/0","lacI","1.0","lacI","lacI", "lacI"),
				get_lacISeq(SBOL2Doc_test), 
				null, null, null);
	}
	
	private static Component get_T (SBOLDocument SBOL2Doc_test)
	{
		return createComponentData(
				getData("T/1/0", "T", "T_1.0", "T", "T", "T", "public"), 
				get_ptet(SBOL2Doc_test));
	}
	
	private static Component get_L (SBOLDocument SBOL2Doc_test)
	{
		return createComponentData(
				getData("L/1/0", "L", "L_1.0", "L", "L", "L", "public"), 
				get_lacI(SBOL2Doc_test));
	}
	
	private static SequenceAnnotation get_t_sequenceAnnotate (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceAnnotationData(
				getData("p2_structAnnotate", "p2_structAnnotate", "1.0", "p2_structAnnotate", "p2_structAnnotate", "p2_structAnnotate"), 
				get_T(SBOL2Doc_test), 
				0, 10);
	}
	
	private static SequenceAnnotation get_l_structAnnotate (SBOLDocument SBOL2Doc_test)
	{
		return createSequenceAnnotationData(
				getData("c2_structAnnotate", "c2_structAnnotate", "1.0", "c2_structAnnotate", "c2_structAnnotate", "c2_structAnnotate"), 
				get_L(SBOL2Doc_test), 
				11, 20);
	}
	
	private static ComponentDefinition get_ptetlacI (SBOLDocument SBOL2Doc_test)
	{
		return createComponentDefinitionData(SBOL2Doc_test,
				getSetOfURI("DNA"),
				getSetOfURI("Gene"),
				getData("ptetlacI/1/0","ptetlacI","1.0","ptetlacI","ptetlacI", "ptetlacI"),
				get_ptetlacISeq(SBOL2Doc_test), 
				getComponent_List(get_T(SBOL2Doc_test), get_L(SBOL2Doc_test)), 
				getSequenceAnnotation_List(get_t_sequenceAnnotate(SBOL2Doc_test), get_l_structAnnotate(SBOL2Doc_test)), 
				null); 
	}
	
	private static FunctionalComponent get_TetRIn (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("TetRIn/1/0","TetRIn","1.0","TetRIn","TetRIn", "TetRIn","public", "input"),
				get_TetR(SBOL2Doc_test)); 
	}
	
	private static FunctionalComponent get_LacIOut (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("LacIOut/1/0","LacIOut","1.0","LacIOut","LacIOut", "LacIOut","public", "output"),
				get_LacI(SBOL2Doc_test));
	}
	
	private static FunctionalComponent get_TetRInv (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("TetRInv/1/0","TetRInv","1.0","TetRInv","TetRInv", "TetRInv","private", "none"),
				get_ptetlacI(SBOL2Doc_test));
	}
	
	private static Participation get_p1b (SBOLDocument SBOL2Doc_test, String identity)
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("repressor"), 
				get_TetRIn(SBOL2Doc_test));
	}
	
	private static Participation get_p2b (SBOLDocument SBOL2Doc_test, String identity )
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("repressed"), 
				get_TetRInv(SBOL2Doc_test));
	}
	
	private static Participation get_p4b (SBOLDocument SBOL2Doc_test, String identity )
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("producer"), 
				get_TetRInv(SBOL2Doc_test));
	}
	
	private static Participation get_p3b (SBOLDocument SBOL2Doc_test, String identity)
	{
		return createParticipationData(
				getURI(identity), 
				getSetOfURI("produced"), 
				get_LacIOut(SBOL2Doc_test));
	}
	
	private static Interaction get_interact1b (SBOLDocument SBOL2Doc_test)
	{
		return createInteractionData(
				getData("interact1b/1/0","interact1b","1.0","interact1b","interact1b", "interact1b"),
				getParticipation_List(get_p1b(SBOL2Doc_test, "p1b"), get_p2b(SBOL2Doc_test, "p2b")), 
				getSetOfURI("repression")); 
	}
	
	private static Interaction get_interact2b (SBOLDocument SBOL2Doc_test)
	{
		return createInteractionData(
				getData("interact2b/1/0","interact2b","1.0","interact2b","interact2b", "interact2b"),
				getParticipation_List(get_p4b(SBOL2Doc_test,"p4b"), get_p3b(SBOL2Doc_test,"p3b")), 
				getSetOfURI("production")); 
	}
	
	private static ModuleDefinition get_TetR_Inv (SBOLDocument SBOL2Doc_test)
	{
		return createModuleDefinitionData(SBOL2Doc_test, 
				getSetOfURI("Inverter"),
				getSetOfURI("Transcriptionfactor"),
				getData("TetR_Inv/1/0","TetR_Inv","1.0","TetR_Inv","TetR_Inv", "TetR_Inv"),
				getFunctionalInstantiation_List(
						get_TetRIn(SBOL2Doc_test), 
						get_LacIOut(SBOL2Doc_test), 
						get_TetRInv(SBOL2Doc_test)), 
				getInteraction_List(
						get_interact1b(SBOL2Doc_test), 
						get_interact2b(SBOL2Doc_test)), 
				null, null,
				getAnnotation_List(createAnnotation(getURI("TetR_Inv_relation"),createTurtle()))
				);
	}
	
	// ------------------------------ CREATING Toggle Top Module ------------------------------
	private static FunctionalComponent get_LacISp (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("LacISp/1/0","LacISp","1.0","LacISp","LacISp", "LacISp", "public", "input"),
				get_LacI(SBOL2Doc_test));
	}
	
	private static FunctionalComponent get_TetRSp (SBOLDocument SBOL2Doc_test)
	{
		return createFunctionalInstantiationData(
				getData("TetRSp/1/0","TetRSp","1.0","TetRSp","TetRSp", "TetRSp", "public", "input"),
				get_TetR(SBOL2Doc_test));
	}
	
	private static Module get_Inv1 (SBOLDocument SBOL2Doc_test)
	{
		return createModuleData(
				getData("Inv1/1/0","Inv1","1.0","Inv1","Inv1", "Inv1"),
				get_LacI_Inv(SBOL2Doc_test),
				getMapsTo_List(
						createMapTo(getURI("Inv1a_identity"), getRefinement("useLocal"), get_LacISp(SBOL2Doc_test), get_LacIIn(SBOL2Doc_test)),
						createMapTo(getURI("Inv2a_TetRSp_identity"), getRefinement("useLocal"), get_TetRSp(SBOL2Doc_test), get_TetROut(SBOL2Doc_test))
						)
				); 
	}
	
	private static Module get_Inv2 (SBOLDocument SBOL2Doc_test)
	{
		return createModuleData(
				getData("Inv2/1/0","Inv2","1.0","Inv2","Inv2", "Inv2"),
				get_TetR_Inv(SBOL2Doc_test),
				getMapsTo_List(
						createMapTo(getURI("Inv1b_identity"), getRefinement("useLocal"), get_LacISp(SBOL2Doc_test), get_LacIOut(SBOL2Doc_test)),
						createMapTo(getURI("Inv2b_identity"), getRefinement("useLocal"), get_TetRSp(SBOL2Doc_test), get_TetRIn(SBOL2Doc_test))
						)
				); 
	}
	
	private static Model get_ToggleModel (SBOLDocument SBOL2Doc_test)
	{
		return createModelData(SBOL2Doc_test, 
				getData("ToggleModel/1/0","ToggleModel","1.0","ToggleModel","ToggleModel", "ToggleModel", "ToggleModel_source", "ToggleModel_language", "ToggleModel_framework"), 
				getSetOfURI("ToggleModel_role"));
	}

	private static ModuleDefinition get_Toggle (SBOLDocument SBOL2Doc_test)
	{
		return createModuleDefinitionData(SBOL2Doc_test, 
				getSetOfURI("Toggle_type"),
				getSetOfURI("Toggle_role"),
				getData("Toggle/1/0","Toggle","1.0","Toggle","Toggle", "Toggle"),
				getFunctionalInstantiation_List(get_LacISp(SBOL2Doc_test), get_TetRSp(SBOL2Doc_test)), 
				null, getModuleInstantiation_List(get_Inv1(SBOL2Doc_test), get_Inv2(SBOL2Doc_test)), 
				getSetOfURI(get_ToggleModel(SBOL2Doc_test).getIdentity()),
				getAnnotation_List(createAnnotation(getURI("Toggle_relation"),createTurtle()))
				);
	}
	
	
	private static void setCommonTopLevelData (TopLevel t, URI identity, URI persistentIdentity,
			String version, String displayId, String name, String description)
	{
		setCommonDocumentedData(t, identity, persistentIdentity, version, displayId, name, description);
	}
	
	private static void setCommonDocumentedData (Documented d, URI identity, URI persistentIdentity,
			String version, String displayId, String name, String description)
	{
		d.setDisplayId(displayId);
		d.setName(name);
		d.setDescription(description);
		
		setCommonIdentifiedData(d, identity, persistentIdentity, version);
	}
	
	private static void setCommonIdentifiedData (Identified i, URI identity, URI persistentIdentity,
			String version)
	{
		i.setIdentity(identity);
		i.setPersistentIdentity(persistentIdentity);
		i.setVersion(version);
//		i.setTimeStamp(timeStamp);
	}
	
	
	private static Collection createCollection(SBOLDocument SBOL2Doc_test, List<String> collectionData, 
			List<Annotation> annotations)
	{	
		URI identity 		   = getURI(collectionData.get(0)); 
		URI persistentIdentity = getURI(collectionData.get(1)); 
		String version 		   = collectionData.get(2); 
		String displayId 	   = collectionData.get(3); 
		String name 		   = collectionData.get(4); 
		String description 	   = collectionData.get(5); 
		
		Collection collection = SBOL2Doc_test.createCollection(identity);
		setCommonTopLevelData(collection, identity, persistentIdentity, version, displayId, name, description);
		collection.setAnnotations(annotations);
		return collection; 
	}
	
	private static Annotation createAnnotation(URI relation, Turtle literal)
	{	
		return new Annotation(relation, literal);
		
	}
	
	private static ComponentDefinition createComponentDefinitionData(SBOLDocument SBOL2Doc_test, 
			Set<URI> type, Set<URI> roles,
			List<String> componentData,
			Sequence structureData, 
			List<Component> structureInstantiationData, 
			List<SequenceAnnotation> structureAnnotationData, 
			List<SequenceConstraint> structureConstraintData)
	{
		URI identity 		   = getURI(componentData.get(0));
		URI persistentIdentity = getURI(componentData.get(1));
		String version 		   = componentData.get(2);
		String displayId 	   = componentData.get(3);
		String name 		   = componentData.get(4);
		String description 	   = componentData.get(5);
		
		ComponentDefinition c = SBOL2Doc_test.createComponentDefinition(identity, type, roles);
		setCommonTopLevelData(c, identity, persistentIdentity, version, displayId, name, description);
		
		if(structureData != null)
			c.setSequence(structureData.getIdentity()); 
		if(structureInstantiationData != null)
		{
			c.setSubComponents(structureInstantiationData);
			if(structureAnnotationData != null && structureConstraintData == null)
				c.setSequenceAnnotations(structureAnnotationData);
			else if(structureConstraintData != null)
				c.setSequenceConstraints(structureConstraintData);
		}
		
		return c; 
	}
	
	private static FunctionalComponent createFunctionalInstantiationData(
			List<String> functionalInstantiation_data, 
			ComponentDefinition c)
	{	
		URI identity 		   = getURI(functionalInstantiation_data.get(0)); 
		URI persistentIdentity = getURI(functionalInstantiation_data.get(1)); 
		String version 		   = functionalInstantiation_data.get(2); 
		String displayId 	   = functionalInstantiation_data.get(3);
		String name 		   = functionalInstantiation_data.get(4); 
		String description 	   = functionalInstantiation_data.get(5); 
		
		AccessType access = null; 
		if(functionalInstantiation_data.get(6).equals("public"))
			access = AccessType.PUBLIC; 
		else if(functionalInstantiation_data.get(6).equals("private"))
			access = AccessType.PRIVATE; 
		
		DirectionType direction = null; 
		if(functionalInstantiation_data.get(7).equals("input"))
			direction = DirectionType.input;
		else if(functionalInstantiation_data.get(7).equals("output"))
			direction = DirectionType.output;
		else if(functionalInstantiation_data.get(7).equals("inout"))
			direction = DirectionType.inout; 
		else if(functionalInstantiation_data.get(7).equals("none"))
			direction = DirectionType.none; 
		
		URI instantiatedComponent = c.getIdentity();
		
		FunctionalComponent f = new FunctionalComponent(identity, access, instantiatedComponent, direction);
		setCommonDocumentedData(f, identity, persistentIdentity, version, displayId, name, description);

		
		return f;
	}
	
	private static Interaction createInteractionData(
			List<String> interaction_data, 
			List<Participation> participations,
			Set<URI> type)
	{	
		URI identity 		   = getURI(interaction_data.get(0)); 
		URI persistentIdentity = getURI(interaction_data.get(1));
		String version 		   = interaction_data.get(2);
		String displayId 	   = interaction_data.get(3); 
		String name 		   = interaction_data.get(4); 
		String description 	   = interaction_data.get(5); 
		
		Interaction interaction = new Interaction(identity, type, participations);
		
		setCommonDocumentedData(interaction, identity, persistentIdentity, version, displayId, name, description);

		
		return interaction;
	}
	
	private static Turtle createTurtle()
	{
		return null;
		//return new Turtle(); 
	}
	
	private static MapsTo createMapTo (URI identity, RefinementType refinement, 
			FunctionalComponent pre_fi, FunctionalComponent post_fi)
	{
		return new MapsTo(identity, refinement, pre_fi.getIdentity(), post_fi.getIdentity());
	}
	

	private static Model createModelData(SBOLDocument doc, List<String> modeldata, Set<URI> roles)
	{	
		URI identity 		   = getURI(modeldata.get(0)); 
		URI persistentIdentity = getURI(modeldata.get(1)); 
		String version 		   = modeldata.get(2);
		String displayId 	   = modeldata.get(3); 
		String name 		   = modeldata.get(4);
		String description     = modeldata.get(5);  
		URI source 			   = getURI(modeldata.get(6)); 
		URI language 		   = getURI(modeldata.get(7));
		URI framework 		   = getURI(modeldata.get(8)); 
		
		Model model = doc.createModel(identity, source, language, framework, roles);
		setCommonTopLevelData(model, identity, persistentIdentity, version, displayId, name, description);
		
		return model;
	}
	
	private static ModuleDefinition createModuleDefinitionData(SBOLDocument SBOL2Doc_test, 
			Set<URI> type, Set<URI> roles,
			List<String> module_data,
			List<FunctionalComponent> functionalInstantiation_data,
			List<Interaction> interactionData,
			List<Module> moduleInstantiation_data,
			Set<URI> model_data,
			List<Annotation> annotations)
	{
		URI identity 		   = getURI(module_data.get(0)); 
		URI persistentIdentity = getURI(module_data.get(1));
		String version 		   = module_data.get(2); 
		String displayId 	   = module_data.get(3); 
		String name 		   = module_data.get(4); 
		String description 	   = module_data.get(5);  
		
		ModuleDefinition m = SBOL2Doc_test.createModuleDefinition(identity, roles);
		setCommonTopLevelData(m, identity, persistentIdentity, version, displayId, name, description);
		m.setAnnotations(annotations);
		
		if(functionalInstantiation_data != null)
			m.setComponents(functionalInstantiation_data);
		if(interactionData != null)
			m.setInteractions(interactionData);
		if(moduleInstantiation_data != null)
			m.setSubModules(moduleInstantiation_data);
		if(model_data != null)
			m.setModels(model_data);
		
		return m; 
	}
	
	private static Module createModuleData(
			List<String> moduleInstantiation_data, 
			ModuleDefinition m, 
			List<MapsTo> maps)
	{	
		URI identity 		   = getURI(moduleInstantiation_data.get(0)); 
		URI persistentIdentity = getURI(moduleInstantiation_data.get(1));
		String version 		   = moduleInstantiation_data.get(2); 
		String displayId 	   = moduleInstantiation_data.get(3); 
		String name 		   = moduleInstantiation_data.get(4); 
		String description 	   = moduleInstantiation_data.get(5); 
		
		Module modInstantiation = new Module(identity, m.getIdentity()); 
		setCommonDocumentedData(modInstantiation, identity, persistentIdentity, version, displayId, name, description);

		for(MapsTo map : maps)
			modInstantiation.addMapping(map);
		
		return modInstantiation; 
	}
	
	private static Participation createParticipationData(
			URI identity, Set<URI> roles, FunctionalComponent fi)
	{
		return new Participation(identity, roles, fi.getIdentity());
	}
	
	private static SequenceAnnotation createSequenceAnnotationData(
			List<String> structuralAnnotations_data,
			Component ref_structInstant,
			int startRange, int endRange)
	{
		URI identity 		   = getURI(structuralAnnotations_data.get(0));
		URI persistentIdentity = getURI(structuralAnnotations_data.get(1));
		String version 		   = structuralAnnotations_data.get(2);
		String displayId 	   = structuralAnnotations_data.get(3);
		String name 		   = structuralAnnotations_data.get(4);
		String description 	   = structuralAnnotations_data.get(5);
		Location location 	   = new Range(ref_structInstant.getIdentity(), startRange, endRange);
		
		SequenceAnnotation s = new SequenceAnnotation(identity, location);
		setCommonDocumentedData(s, identity, persistentIdentity, version, displayId, name, description);

		
		return s; 
	}
	
	private static SequenceConstraint createSequenceConstraintData(
			List<String> structuralConstraints_data,  
			Component pre_structInstant, 
			Component post_structInstant)
	{
		URI identity 		   = getURI(structuralConstraints_data.get(0));
		URI persistentIdentity = getURI(structuralConstraints_data.get(1));
		String version 		   = structuralConstraints_data.get(2);
		URI restriction 	   = getURI(structuralConstraints_data.get(3)); 
		URI subject 		   = pre_structInstant.getIdentity(); 
		URI object 			   = post_structInstant.getIdentity(); 
		
		SequenceConstraint s = new SequenceConstraint(identity, restriction, subject, object);
		
		s.setPersistentIdentity(persistentIdentity);
		s.setVersion(version);
		
		return s; 
	}
	
	private static Component createComponentData(
			List<String> structuralInstantiations_data, 
			ComponentDefinition c)
	{
		URI identity 		   = getURI(structuralInstantiations_data.get(0));
		URI persistentIdentity = getURI(structuralInstantiations_data.get(1));
		String version 		   = structuralInstantiations_data.get(2);
		String displayId 	   = structuralInstantiations_data.get(3);
		String name 		   = structuralInstantiations_data.get(4);
		String description	   = structuralInstantiations_data.get(5);
		
		AccessType access = null; 
		if(structuralInstantiations_data.get(6).equals("public"))
			access = AccessType.PUBLIC; 
		else if(structuralInstantiations_data.get(6).equals("private"))
			access = AccessType.PRIVATE; 
		
		URI instantiatedComponent = c.getIdentity(); 
		
		Component s = new Component(identity, access, instantiatedComponent);
		setCommonDocumentedData(s, identity, persistentIdentity, version, displayId, name, description);

		
		return s; 
	}
	
	private static Sequence createSequenceData(SBOLDocument SBOL2Doc_test, List<String> structureData)
	{
		URI identity 		   = getURI(structureData.get(0)); 
		URI persistentIdentity = getURI(structureData.get(1)); 
		String version 		   = structureData.get(2); 
		String displayId 	   = structureData.get(3); 
		String name 		   = structureData.get(4); 
		String description 	   = structureData.get(5); 
		String element 		   = structureData.get(6); 
		URI encoding  		   = getURI(structureData.get(7));
		
		Sequence structure = SBOL2Doc_test.createSequence(identity, element, encoding);
		setCommonTopLevelData(structure, identity, persistentIdentity, version, displayId, name, description);

		return structure;
	}
	
	/**
	 * data[] = identity
	 * data[] = persistentIdentity
	 * data[] = version
	 * data[] = displayID
	 * data[] = Name
	 * data[] = Description
	 * @param data
	 * @return
	 */
	private static List<String> getData(String ... data)
	{
		List<String> list = new ArrayList<String>();
		for(String d : data)
		{
			list.add(d);
		}
		return list;
	}
	
	private static List<Annotation> getAnnotation_List(Annotation ... a)
	{
		return new ArrayList<Annotation>(Arrays.asList(a)); 
	}
	
	private static List<FunctionalComponent> getFunctionalInstantiation_List(FunctionalComponent ... fi)
	{
		return new ArrayList<FunctionalComponent>(Arrays.asList(fi)); 
	}
	
	private static List<Module> getModuleInstantiation_List(Module ... mi)
	{
		return new ArrayList<Module>(Arrays.asList(mi)); 
	}
	
	private static List<Interaction> getInteraction_List(Interaction ... i)
	{
		return new ArrayList<Interaction>(Arrays.asList(i)); 
	}
	
	private static List<MapsTo> getMapsTo_List(MapsTo ... maps)
	{
		return new ArrayList<MapsTo>(Arrays.asList(maps));
	}
	
	private static List<URI> getListOfURI(String ... appends)
	{
		List<URI> list = new ArrayList<URI>();
		for(String append : appends)
		{
			list.add(getURI(append));
		}
		return list; 
	}
	
	private static RefinementType getRefinement(String s)
	{
		if(s.equals("verifyIdentical"))
			return RefinementType.verifyIdentical; 
		else if(s.equals("useLocal"))
			return RefinementType.useLocal; 
		else if(s.equals("useRemote"))
			return RefinementType.useRemote; 
		else if(s.equals("merge"))
			return RefinementType.merge; 
		return RefinementType.merge; 
	}
	
	private static List<Participation> getParticipation_List(Participation ... p)
	{
		return new ArrayList<Participation>(Arrays.asList(p)); 
	}
	
	private static Set<URI> getSetOfURI(URI ... uriS)
	{
		Set<URI> list = new HashSet<URI>();
		for(URI uri : uriS)
		{
			list.add(uri);
		}
		return list; 
	}
	
	private static Set<URI> getSetOfURI(String ... appends)
	{
		Set<URI> list = new HashSet<URI>();
		for(String append : appends)
		{
			list.add(getURI(append));
		}
		return list; 
	}
	
	private static List<SequenceAnnotation> getSequenceAnnotation_List(SequenceAnnotation ... sa)
	{
		return new ArrayList<SequenceAnnotation>(Arrays.asList(sa)); 
	}
	
	private static List<SequenceConstraint> getSequenceConstraint_List(SequenceConstraint ... sc)
	{
		return new ArrayList<SequenceConstraint>(Arrays.asList(sc)); 
	}
	
	private static List<Component> getComponent_List(Component ... si)
	{
		return new ArrayList<Component>(Arrays.asList(si)); 
	}
	
	
	private static URI getURI(String append)
	{
		return URI.create("http://www.async.ece.utah.edu/" + append);
	}


}
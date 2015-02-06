package org.sbolstandard.core2;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sbolstandard.core2.abstract_classes.Location;

public class MultiRange extends Location {

	private HashMap<URI, Range> ranges;

	public MultiRange(URI identity) {
		super(identity);
		this.ranges = new HashMap<URI, Range>();
	}

	/**
	 * Test if field variable <code>ranges</code> is set.
	 * 
	 * @return <code>true</code> if the field variable is not an empty list
	 */
	public boolean isSetRanges() {
		if (ranges.isEmpty())
			return false;
		else
			return true;
	}

	/**
	 * Calls the Range constructor to create a new instance using the specified
	 * parameters, then adds to the list of Range instances owned by this
	 * instance.
	 * 
	 * @param identity
	 * @param location
	 * @return the created Range instance.
	 */
	public Range createRange(URI identity, Integer start, Integer end) {
		Range range = new Range(identity, start, end);
		addRange(range);
		return range;
	}

	/**
	 * Adds the specified instance to the list of structuralAnnotations.
	 * 
	 * @param range
	 */
	public void addRange(Range range) {
		// TODO: @addRange, Check for duplicated entries.
		ranges.put(range.getIdentity(), range);
	}

	/**
	 * Removes the instance matching the specified URI from the list of
	 * structuralAnnotations if present.
	 * 
	 * @param rangeURI
	 * @return the matching instance if present, or <code>null</code> if not
	 *         present.
	 */
	public Range removeRange(URI rangeURI) {
		return ranges.remove(rangeURI);
	}

	/**
	 * Returns the instance matching the specified URI from the list of
	 * structuralAnnotations if present.
	 * 
	 * @param rangeURI
	 * @return the matching instance if present, or <code>null</code> if not
	 *         present.
	 */
	public Range getRange(URI rangeURI) {
		return ranges.get(rangeURI);
	}

	/**
	 * Returns the list of structuralAnnotation instances owned by this
	 * instance.
	 * 
	 * @return the list of structuralAnnotation instances owned by this
	 *         instance.
	 */
	public List<Range> getRanges() {
		List<Range> ranges = new ArrayList<Range>();
		ranges.addAll(this.ranges.values());
		return ranges;
	}

	/**
	 * Removes all entries of the list of structuralAnnotation instances owned
	 * by this instance. The list will be empty after this call returns.
	 */
	public void clearRanges() {
		Object[] keySetArray = ranges.keySet().toArray();
		for (Object key : keySetArray) {
			removeRange((URI) key);
		}
	}

	/**
	 * Clears the existing list of structuralAnnotation instances, then appends
	 * all of the elements in the specified collection to the end of this list.
	 * 
	 * @param ranges
	 */
	public void setRanges(List<Range> ranges) {
		clearRanges();
		for (Range range : ranges) {
			addRange(range);
		}
	}

}

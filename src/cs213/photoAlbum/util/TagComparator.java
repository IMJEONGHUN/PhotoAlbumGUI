package cs213.photoAlbum.util;

import java.util.Comparator;

import cs213.photoAlbum.model.Tag;


/**
 * Comparator for tags that compares according to the type and value fields.
 */
public class TagComparator implements Comparator<Tag>{

	public int compare(Tag tag1, Tag tag2) {
		if (tag1.getType().equals(tag2.getType())) {
			return tag1.getData().compareTo(tag2.getData());
		}
		return tag1.getType().compareTo(tag2.getType());
	}
	
}
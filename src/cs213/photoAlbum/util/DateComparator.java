package cs213.photoAlbum.util;

import java.util.Comparator;

import cs213.photoAlbum.model.Photo;

/**
 * Comparator for photos that compares according to the date field.
 */
public class DateComparator implements Comparator<Photo>{

	public int compare(Photo photo1, Photo photo2) {
		return photo1.getCalendar().compareTo(photo2.getCalendar());
	}
	
}
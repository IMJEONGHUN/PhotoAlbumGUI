package cs213.photoAlbum.model;

import java.io.Serializable;

/**
 * Tag Class.
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 01-20-2015
 */

public class Tag implements Serializable{
	//private static final long serialVersionUID = 1;
	/**
	 * Tag's Type, i.e. location
	 */
	private String type;
	/**
	 * Tag's data, i.e. New York
	 */
	private String data;
	
	/**
	 * Default Constructor
	 */
	public Tag(){
		
	}
	
	/**
	 * @param type is the type of the tag 
	 * @param data is the data of the tag
	 */
	public Tag(String type, String data){
		this.type = type;
		this.data = data;
	}
	
	/**
	 * @return tag's type
	 */
	public String getType(){
		return this.type;
	}
	
	/**
	 * @return tag's data
	 */
	public String getData(){
		return this.data;
	}
	

	/**
	 * @param nType is new type field to set in tag
	 * set tag's type to input nType
	 */
	public void setType(String nType){
		this.type = nType;
	}
	
	/**
	 * @param nData is new data field to set in tag
	 * set tag's data to input nData
	 */
	public void setData(String nData){
		this.data = nData;
	}
	
	
	/**
	 * @return Print out tag in format:  type:"data"
	 */
	@Override
	public String toString() {
		return this.type + ":\"" + this.data + "\"";
	}
}
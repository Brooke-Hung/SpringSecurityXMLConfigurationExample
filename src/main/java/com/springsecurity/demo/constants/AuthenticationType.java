package com.springsecurity.demo.constants;

/**
 * Available Authentication Types
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public enum AuthenticationType {
	
	VIA_PASSWORD(1),
	VIA_VERIFICATION_CODE(2);

	private final int typeId;

	public int getTypeId() {
		return typeId;
	}

	private AuthenticationType(int typeId) {
		this.typeId = typeId;
	}
	
	public static AuthenticationType fromTypeId(int typeId){
		for (AuthenticationType item : AuthenticationType.values()) {
			if (typeId == item.typeId) {
				return item;
			}
		}
		
		throw new IllegalArgumentException(); 
	}
	
}

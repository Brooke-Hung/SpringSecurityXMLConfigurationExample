package com.springsecurity.demo.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * Available Account Statuses
 * @author Brooke
 * @since JDK1.8
 * @created on 2018/06/16
 */
public enum AccountStatus {
	
	IN_USE(Byte.valueOf("1"), "in.use"),
	SUSPENDED(Byte.valueOf("2"), "suspended"),
	TERMINATED(Byte.valueOf("3"), "terminated");
	
	private final Byte statusId;

    private final String code;

	public Byte getStatusId() {
		return statusId;
	}

	public String getCode() {
		return code;
	}

	private AccountStatus(Byte statusId, String code) {
		this.statusId = statusId;
		this.code = code;
	}
       
	public static AccountStatus fromStatusId(Byte statusId){
		if (statusId != null) {
			for (AccountStatus item : AccountStatus.values()) {
				if (statusId == item.statusId) {
					return item;
				}
			}
		}
		
		throw new IllegalArgumentException(); 
	}
	
	public static AccountStatus fromCode(String code){
		if (StringUtils.isNotEmpty(code)) {			
			for (AccountStatus item : AccountStatus.values()) {
				if (code.equals(item.code)) {
					return item;
				}
			}
		}
		
		throw new IllegalArgumentException(); 
	}
}

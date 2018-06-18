package com.springsecurity.demo.entity;

public class AccountStatus {
    private Byte statusId;

    private String code;

    private String description;

    public Byte getStatusId() {
        return statusId;
    }

    public void setStatusId(Byte statusId) {
        this.statusId = statusId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", statusId=").append(statusId);
        sb.append(", code=").append(code);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }
}
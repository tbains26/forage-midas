package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transfer {

    private long fromUserId;
    private long toUserId;
    private float value;
    private float bonus;

    public Transfer() {
        // default constructor
    }

    public Transfer(long fromUserId, long toUserId, float value) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.value = value;
        this.bonus = 0f;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Transfer {fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", value=" + value +
                ", bonus=" + bonus + "}";
    }
}


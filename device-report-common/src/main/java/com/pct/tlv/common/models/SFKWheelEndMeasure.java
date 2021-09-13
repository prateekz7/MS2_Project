package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class SFKWheelEndMeasure {
    public String axleLocation;
    public String side;
    public String sStatus;
    public String health;
    public String battery;
    public float currentTempF;
    public float lowTempF;
    public float highTempF;
}

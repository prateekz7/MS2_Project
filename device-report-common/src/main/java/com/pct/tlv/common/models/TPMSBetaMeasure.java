package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class TPMSBetaMeasure {
    public String primaryCurbStatus;
    public String innerCurbStatus;
    public String innerRoadsideStatus;
    public String primaryRoadsideStatus;
    public int primaryCurbsidePressure;
    public float primaryCurbsidePressurePSI;
    public int primaryCurbsideTemperature;
    public float primaryCurbsideTemperatureF;
    public float primaryCurbsideTemperatureK;
    public int primaryRoadsidePressure;
    public float primaryRoadsidePressurePSI;
    public int primaryRoadsideTemperature;
    public float primaryRoadsideTemperatureF;
    public float primaryRoadsideTemperatureK;
    public int innerRoadsidePressure;
    public float innerRoadsidePressurePSI;
    public int innerRoadsideTemperature;
    public float innerRoadsideTemperatureF;
    public float innerRoadsideTemperatureK;
    public int innerCurbsidePressure;
    public float innerCurbisdePressurePSI;
    public int innerCurbsideTemperature;
    public float innerCurbsideTemperatureF;
    public float innerCurbsideTemperatureK;
}

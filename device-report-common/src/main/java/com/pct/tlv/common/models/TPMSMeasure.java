package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class TPMSMeasure {
	  public int[]    axleTireireLocationCode;
      public String[] tireLocationStr;
      public int[]    axleLocation;
      public String[] locationCodeHexStr;
      public int[]    tirePressureMBAR;
      public int[]    tireLocation;
      public float[] tirePressurePSI;
      public float[] tireTemperatureF;
}

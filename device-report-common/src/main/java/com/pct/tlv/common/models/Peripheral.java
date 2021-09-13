package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class Peripheral {
    private int             peripheralPortNum;
    private int             peripheralDriver;
    private String          peripheralDesc;
    private String          peripheralModel;
    private String          peripheralRev;
    private int peripheralPortTypeIndex = -1;
}

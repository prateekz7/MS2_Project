package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class TractorPairing {
    private String   condition;
    private byte[]   MACAddress;
    private String   MACAddressStr;
}

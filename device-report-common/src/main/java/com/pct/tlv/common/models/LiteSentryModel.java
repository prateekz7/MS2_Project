package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class LiteSentryModel {
	public boolean communicating;
	public boolean misWired;
	public boolean genericFault;
	public boolean markerFault;

    //public boolean deadBulb;
    //public boolean deadMarkerBulb;
	public boolean deadRightMarkerBulb;
	public boolean deadStopBulb;
	public boolean deadLeftMarkerBulb;
	public boolean deadLicenseBulb;
	public String lightSentryError;
	
}

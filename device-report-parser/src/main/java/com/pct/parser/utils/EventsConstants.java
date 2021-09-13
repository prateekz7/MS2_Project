package com.pct.parser.utils;

/**
 * Created by sarit.
 */
public class EventsConstants {

	public static final String MAINTENANCE_REPORT = "Maintenance";
	public static final String TFTP_FAIL_REPORT = "TFTP Failed";
	public static final String TFTP_OK = "TFTP OK";

	public static String[] EVENT_TYPES = new String[0xFFFF];

	static {

		for (int i = 0; i < EVENT_TYPES.length; i++) {
			EVENT_TYPES[i] = Integer.toHexString(i);
		}
	}
//
//    public static final String[] MAINTENANCE_REPORTS = new String[]{MAINTENANCE_REPORT, TFTP_FAIL_REPORT, TFTP_OK};
//
//    // Note that even type String cannot exceed length equal to 32 characters
//    public static String[] EVENT_TYPES = new String[] {
//            "UNDEFINED", "Power-Up", "Heart Beat", "Heart-Beat on Battery",         // 0 - 3
//            "Power Disconnect",   "Power Connect",  "Power Drop", "Power Restore",    // 4 - 7
//            "UNDEFINED", "UNDEFINED", "Going to Sleep", "Waking Up",                 // 8 - 11
//            "Relay High", "Relay Low", "Ignition High", "Ignition Low",         // 12 - 15
//            "GPS Locked", "GPS Unlocked", "IP Changed", "Speeding Start",       // 16 - 19
//            "Speeding Stop", "UNDEFINED", "Drive Trip - Start", "Drive Trip - End",    // 20 - 23
//            "Moving", "Stopped", "Heading Change", "Drive Distance",            // 24 - 27
//            "Tow Alert", "UNDEFINED", "Idling Alert", "Now Report",             // 28 - 31
//            "Periodic reset", "Trip odometer", MAINTENANCE_REPORT, "UNDEFINED",      // 32 - 35
//            "UNDEFINED", "GP1 High", "GP1 Low",  "GP2 High",                    // 36 - 39
//            "GP2 Low", "GP3 High", "GP3 Low",  "GP4 High",                      // 40 - 43
//            "GP4 Low", TFTP_FAIL_REPORT, TFTP_OK, "VIN via OBD",                   // 44 - 47
//            "Engine Codes via OBD", "G-Sensor Wake-up", "UNDEFINED", "UNDEFINED",                 // 48 - 51
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",                 // 52 - 55
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",                 // 56 - 59
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",           // 60 - 63
//            "Power Save", "Power Normal", "Unauthorized Movement", "UNDEFINED",       // 64 - 67
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",           // 68 - 71
//            "GPIO Chnage", "UNDEFINED", "UNDEFINED", "UNDEFINED",           // 72 - 75
//            "Geo-Fence Change", "UNDEFINED", "UNDEFINED", "UNDEFINED",      // 76 - 79   / not implemented yet in firmware
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",           // 80 - 83
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",           // 84 - 87
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",           // 88 - 91
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",           // 92 - 95
//            "Geofence Enter 1", "Geofence Enter 2", "Geofence Enter 3", "Geofence Enter 4",  // 96 - 99
//            "Geofence Enter 5", "Geofence Enter 6", "Geofence Enter 7", "Geofence Enter 8",  // 100 - 103
//            "Geofence Enter 9", "Geofence Enter 10", "Geofence Enter 11", "GGeofence Enter 12",  // 104 - 107
//            "Geofence Enter 13", "Geofence Enter 14", "Geofence Enter 15", "Geofence Enter 16",  // 108 - 111
//            "Geofence Exit 1", "Geofence Exit 2", "Geofence Exit 3", "Geofence Exit 4",      // 112 - 115
//            "Geofence Exit 5", "Geofence Exit 6", "Geofence Exit 7", "Geofence Exit 8",      // 116 - 119
//            "Geofence Exit 9", "Geofence Exit 10", "Geofence Exit 11", "Geofence Exit 12",      // 120 - 123
///* 127*/    "Geofence Exit 13", "Geofence Exit 14", "Geofence Exit 15", "Geofence Exit 16",      // 124 - 127
//
//            "RUN", "SAVE", "BYPASS", "OVERRIDE",                                         // 128 - 131
//            "OFF", "FORCE SAVE ON", "FORCE SAVE OFF", "UNDEFINED",                      // 132 - 135
//            "Tamper Detected", "Tamper Removed", "Cargo Sensor", "UNDEFINED",              // 136 - 139
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",      // 140 - 143
//            "Cell On", "Cell Off", "UNDEFINED", "UNDEFINED",      // 144 - 147
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED",
//            "UNDEFINED", "UNDEFINED", "UNDEFINED", "UNDEFINED"
//    };
}

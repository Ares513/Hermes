package com.team1ofus.hermes;

public enum SEVERITY_LEVEL {
	/*
	 * Indicates a problematic edge case that may affect program function. Similar to compiler warnings.
	 */
	WARNING,
	/*
	 * Indicates corrupted data that may prevent the program from functioning.
	 */
	CORRUPTED,
	/*
	 * A genuine error that can cause problems, but has been handled and the program will proceed.
	 */
	ERROR,
	/*
	 * A severe error that indicates serious problems. The program may crash or features may be compromised.
	 */
	SEVERE,
	/*
	 * A critical error that completely disables or breaks program functionality. 
	 */
	CRITICAL,
	/*
	 * Fatal errors indicate an imminent runtime error.
	 */
	FATAL
	
}

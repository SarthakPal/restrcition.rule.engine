package com.tesco.restriction.model;

/**
 * Outcome of a restriction check.
 */
public enum RestrictionStatus {
    /** The basket satisfies the restriction. */
    MET,
    /** The basket violates the restriction. */
    BREACHED
}
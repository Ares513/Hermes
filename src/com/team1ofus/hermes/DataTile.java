package com.team1ofus.hermes;

import java.io.Serializable;

/*
 * We only need a single DataTile class.
 * The methods that require us to make an interface revolve around A*.
 * It makes more sense to just have a DataTile that has the tile type- this is all Apollo
 * needs to do its job.
 * @author Evan King
 */
public class DataTile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TILE_TYPE type;
	public DataTile(TILE_TYPE type) {
		this.type = type;
	}
	public TILE_TYPE getType() {
		return type;
	}
}

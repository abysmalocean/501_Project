package vcu.cs531.Project;

import java.util.HashSet;
import java.util.LinkedList;

public class Graph {
	
	private HashSet<Vertex> VertexRoster = new HashSet<Vertex>();
	private ParamaterClass Patamater;
	
	private LinkedList<Integer> path = new LinkedList<Integer>();
	
	public Graph(HashSet<Vertex> InVertexRoster,ParamaterClass InPatamater)
	{
		this.VertexRoster = InVertexRoster;
		this.Patamater = InPatamater;
	}
	
	public LinkedList<Integer> BFSGetPath()
	{
		return path;
		
	}

}

package fr.univavignon.graphcentr.g07.core;

/**
 * 
 * @author JackassDestroyer
 * Base interface for data structure
 */
interface AbstractDataStructure 
{
	/**
	 * Copy given simple-like-graph into other type of data
	 * @param inGraph Graph to copy
	 */
	public <NodeType extends AbstractNode<LinkType>, LinkType extends AbstractLink<NodeType>> 
	void copyGraph(Graph<NodeType, LinkType> inGraph);
	
	/**
	 * Convert intern data structure to given simple-like-graph
	 * @param inOutGraph Graph to update
	 */
	public <NodeType extends Node, LinkType extends Link> 
	void updateGraph(Graph<NodeType, LinkType> inOutGraph);
	
	/**
	 * Prints data structure
	 */
	public void print();
}

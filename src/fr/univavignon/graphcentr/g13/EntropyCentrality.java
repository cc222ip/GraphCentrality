package fr.univavignon.graphcentr.g13;

import java.util.ArrayList;
import java.util.List;

import fr.univavignon.graphcentr.g07.core.Link;
import fr.univavignon.graphcentr.g07.core.Node;
import fr.univavignon.graphcentr.g07.core.centrality.CentralityResult;
import fr.univavignon.graphcentr.g07.core.centrality.SimpleCentrality;
import fr.univavignon.graphcentr.g07.core.graphs.SimpleGraph;

public class EntropyCentrality implements SimpleCentrality
{


	public void ElemPath(SimpleGraph inGraph, int v1, int v2,  ArrayList Parcours, ArrayList[] Res, int v3)
	{
		 Parcours.add(v3);
		 if(v2 == v3)
		 {
			 boolean b = false;
			 int i = 0;
			 while (b == false)
			 {
				 if(Res[i].isEmpty())
				 {
					 b = true;
				 }
				 else
				 {
					 i++;
				 }
			 }
			 Res[i+1].add(Parcours);
		 }
		 else
		 {
			 ArrayList Tab = new ArrayList();
			 for (int i = 0; i < Parcours.size(); i++)
			 {
				 Tab.add(Parcours.get(i));
			 }
			 Node n = inGraph.getNodeAt(v3);
			 inGraph.getNodeLinks(n);
			 List<Link> links = inGraph.getNodeLinks(n);
			 for (int j = 0; j < links.size(); j++)
			 {
				 if(!Tab.contains( links.get(j).getDestinationIdentifier()) )
				 {
					 ElemPath(inGraph, v1, v2, Tab, Res, links.get(j).getDestinationIdentifier());
				 }
			 }
		 }
	 }
	
	public int tau(SimpleGraph inGraph , int a)
	{
		if( inGraph.getNodeDegree(a) == 0  )
		{
			return 0;
		}
		else
		{
			double[][] matrice = inGraph.toAdjacencyMatrix();
			return ((int)matrice[a][a+1]/inGraph.getNodeDegree(a));
		}
	}
	
	public int sigma(SimpleGraph inGraph , int a)
	{
		if( inGraph.getNodeDegree(a) == 0  )
		{
			return 1;
		}
		else
		{
			double[][] matrice = inGraph.toAdjacencyMatrix();
			
			return ((int)matrice[a][a]/inGraph.getNodeDegree(a));
		}
	}
	
	public int calculProba(SimpleGraph inGraph,int i,int j)
	{
		int z = 0;
		int b;
		
		if(i != j)
		{
				ArrayList[] res = new ArrayList[inGraph.getNodeCount()];
				ArrayList parcours = new ArrayList();
				
				ElemPath(inGraph,i,j,parcours,res,i);
				
				int rsize=0;
				for (int a=0; a<inGraph.getNodeCount(); a++)
				{
					if (!res[a].isEmpty())
						rsize++;
					else
						break;
				}
				
				int k=0;
				for( k=0 ; k<rsize; k++)
				{
					int y=1;
					int t=1;
					for(t=1;t<res[1].size()-1;t++)
					{
						b=tau(inGraph,(int)res[k].get(t));
						y=y*b;
					}
					y=y*sigma(inGraph,j);
					z=z+y;
				}
		}
		else
		{
			Node v=inGraph.getNodeAt(i);
			z= 1 / (inGraph.getNodeDegree(v));
		}
		
		return z;	
	}

	public void AlgoPrincipale(SimpleGraph inGraph,boolean normalise)
	{
		int n = inGraph.getNodeCount();

		double[] C_Ent = new double[n];
		
		double x=0;

		for(int i = 0; i <= n ; i++)
		{
			for(int j = 0; j <=n ; j++)
			{
				x = x + calculProba(inGraph,i,j) * Math.log( calculProba(inGraph,i,j) );
			}
			if(normalise == true)
			{
				x = x * (-1) / Math.log(n);
			}

			C_Ent[i] = x;	  			
		}
	}

	@Override
	public CentralityResult evaluate(SimpleGraph inGraph) {
		

		
		// TODO Auto-generated method stub
		return null;
	}

}
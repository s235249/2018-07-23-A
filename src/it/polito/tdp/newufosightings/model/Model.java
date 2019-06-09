package it.polito.tdp.newufosightings.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	Map<String,State> stati= new HashMap<>();
	NewUfoSightingsDAO dao= new NewUfoSightingsDAO();
	SimpleWeightedGraph<State,DefaultWeightedEdge> graph;
	int anno;
	
	public List<String> formeAnno(int anno) {
		
		List<String> forme= dao.listFormePerAnno(anno);
		this.anno=anno;
		
		return forme;
	}

	public String creaGrafo(String shape) {
		graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<State> stati= dao.loadAllStates();
		
		Graphs.addAllVertices(this.graph, stati);
		String result = "Lista:\n";
		
		for (State s: stati)
		{
			List<State> vicini= dao.loadAllVicini(s);
			System.out.println(vicini);
			int ris = 0;
			
			for (State v: vicini)
			{
				DefaultWeightedEdge e= graph.addEdge(s, v);
				double peso= dao.loadPeso(s,v,anno,shape);
				try {
				graph.setEdgeWeight(e, peso);
				}catch(NullPointerException ne) {}
				ris+= (int) peso;
			}
			
			result+= s + " " +ris+"\n";
		}
		
		return result;
		
	}
		
		
		
}
	



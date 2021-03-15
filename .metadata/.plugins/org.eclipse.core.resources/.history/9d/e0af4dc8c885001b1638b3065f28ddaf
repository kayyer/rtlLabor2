package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import java.util.*;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;


public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		
		int nameInd = 0;
		ArrayList<String> names = new ArrayList<String>();
		boolean uj = true;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(state.getName());
			
				if(state.getOutgoingTransitions().isEmpty())
				{
					System.out.println("Csapda allapot: " + state.getName());
				}
				if(state.getName().equals(""))
				{
					String nameSug = new String("new_name" + nameInd++);
					for(String name : names)
					{
						if(name.equals(nameSug) )
						{
							uj = false;
						}
					}
					if(uj)
					{
					names.add(nameSug);
					System.out.println("Javasolt nev a nevtelen allapotnak : " + nameSug );
					}
					uj = true;
				}
				else {
					names.add(state.getName());
				}
			}
			else if(content instanceof Transition)
			{
				Transition transition = (Transition)content;
				System.out.println(transition.getSource().getName() + " -> " + transition.getTarget().getName());
				
			}
			
		}
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}

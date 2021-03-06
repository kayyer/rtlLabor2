package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import java.io.*;
import java.util.*;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



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
		
		//4.3 Feladat
		valtozo(s,43);
		esemeny(s,43);
		//4.4 Feladat
		valtozo(s,44);
		esemeny(s,44);
		generateJava(s);
	}
	
	//4.3, 4.4 feladat
	public static void valtozo(Statechart st,int feladat) {
		TreeIterator<EObject> iter = st.eAllContents();
		int valtozoElejeVege = 0;
		while(iter.hasNext())
		{
			EObject cont = iter.next();
			if(cont instanceof VariableDefinition  )
			{
				VariableDefinition v = (VariableDefinition)cont;
				if(feladat == 43)
					System.out.println("V??ltoz??: " + v.getName());
				else if(feladat == 44)
					print44(v.getName(),valtozoElejeVege);
				valtozoElejeVege = 1;
			}
		}
		if(feladat == 44)
			print44("",2);
		
	}
	//4.3  4.4 Feladat
	public static void esemeny(Statechart st,int feladat) {
		TreeIterator<EObject> iter = st.eAllContents();
		int esemenyElejeVege = 0;
		while(iter.hasNext())
		{
			EObject cont = iter.next();
			if(cont instanceof EventDefinition  )
			{
				EventDefinition v = (EventDefinition)cont;
				if(feladat == 43)
					System.out.println("Esem??ny: " + v.getName());
				if(feladat == 44)
					print44(v.getName(),esemenyElejeVege);
				esemenyElejeVege = 1;
			}
		}
		if(feladat == 44)
			print44("",2);
	
	}
	//4.4 Feladat
	public static void print44(String name,int allapot) {
		if(allapot == 2)
		{
			System.out.println("}");
			return;
		}
		if(allapot == 0)
			System.out.println("public static void print(IExampleStatemachine s) {");
		System.out.println("System.out.println(\""+String.valueOf(name.charAt(0)).toUpperCase() + " = \" + s.getSCInterface().get" + name + "());" );
	}
	public static void generateJava(Statechart st) {
		File file = new File("/home/cloud/Documents/REMO_WS/hu.bme.mit.yakindu.analysis/src/hu/bme/mit/yakindu/analysis/workhere/generated.java");
		
		try {
			file.createNewFile();
			FileWriter tmp = new FileWriter(file);
			tmp.write("");
		FileOutputStream irt = new FileOutputStream(file,true);
	
		String alapImport = new String("package hu.bme.mit.yakindu.analysis.workhere;\r\n" + 
				"import java.io.IOException;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\r\n" + 
				"\r\n" + 
				"import java.io.*;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"// import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"// import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\n\n\n\n");
		
		String alap = new String("public class generated {\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) throws IOException {\r\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"		s.init();\r\n" + 
				"		s.enter();\r\n" + 
				"		s.runCycle();\n");
		String ciklus = new String("while(true)\r\n" + 
				"		{\r\n" + 
				"	\r\n" + 
				"			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));\r\n" + 
				"			String command = reader.readLine();\r\n" + 
				"			switch(command) {");
			
	
		irt.write(alapImport.getBytes());
		irt.write(alap.getBytes());
		irt.write(ciklus.getBytes());
		
			ArrayList<String> esemenyek = new ArrayList<>();
			ArrayList<String> valtozok = new ArrayList<>();
			TreeIterator<EObject> iter = st.eAllContents();
			while(iter.hasNext())
			{
				EObject cont = iter.next();
				if(cont instanceof EventDefinition  )
				{
					EventDefinition v = (EventDefinition)cont;
					esemenyek.add(v.getName());	
				}
				else if(cont instanceof VariableDefinition)
				{
					VariableDefinition v = (VariableDefinition)cont;
					String nb = v.getName().substring(0, 1).toUpperCase() + v.getName().substring(1);
					valtozok.add(nb);
				}
			}
			
			for(String es : esemenyek)
			{
				String nagyB = es.substring(0, 1).toUpperCase() + es.substring(1);
				String cases = new String("\ncase " + "\"" + es + "\" :\n\rs.raise" + nagyB + "();\r\n" + 
						"				s.runCycle();\r\n" + 
						"				break;");
				irt.write(cases.getBytes());
			}
			String caseVege = new String("\ncase \"exit\":\r\n" + 
					"				System.exit(0);\r\n" + 
					"				\r\n" + 
					"			}\r\n" + 		
					"			print(s);\r\n" + 
					"		}\r\n" + 
					"	}\r\n" + 
					"");
			
			irt.write(caseVege.getBytes());
			
			String valtozoEleje = new String("\r\n" + 
					"	public static void print(IExampleStatemachine s) {");
			
			irt.write(valtozoEleje.getBytes());
			
			for(String va : valtozok)
			{
				String varia = new String("\nSystem.out.println(\""+va.charAt(0) + " = \" + s.getSCInterface().get" + va + "());");
				irt.write(varia.getBytes());
			}
			
			
			String vege = new String("}\r\n" + 
					"}");
		
			irt.write(vege.getBytes());
			irt.close();
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			while(line != null)
			{
			  System.out.println(line);
			  line = in.readLine();
			}
			in.close();
	}
	catch(Exception e) {
		System.out.println("Hiba");
	}
		
		
	}
	
}

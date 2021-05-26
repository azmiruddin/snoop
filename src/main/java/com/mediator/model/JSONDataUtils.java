package com.mediator.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.mediator.common.ObjectFileTransaction;



public class JSONDataUtils {
	
	@SuppressWarnings("rawtypes")
	public boolean editDataToFile(String file , HashMap<String, Object> value ) throws IOException{
		
		
		if(value != null && value.entrySet().size() > 0) {
			BufferedReader reader = null;
		     
			File filetobereplace = new File(file);
			
			reader = new BufferedReader(new FileReader(filetobereplace));
			
			String line = reader.readLine();
			String oldContent = "";

			while (line != null)
			{
			        oldContent = oldContent + line;
			        line = reader.readLine();
			}
			
			 String modifiedFileContent = oldContent.replaceAll("}}", "},"+"\""+"other_values"+"\""+":");

	         //8
			 FileWriter writer = new FileWriter(file);
			 writer.write(modifiedFileContent);
			 
			 writer.flush();
			 writer.close();
			 reader.close();
			 
			 
			 JsonWriter  writerJson = new JsonWriter(new FileWriter(file,true)); 
			 writerJson.beginObject();
			Iterator it = value.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				writerJson.name(pair.getKey().toString()).value(String.valueOf(pair.getValue()));
				it.remove(); // avoids a ConcurrentModificationException
			}

			 writerJson.endObject();  
			 writerJson.close();
			 
			 BufferedReader readerNew = null;

			 readerNew = new BufferedReader(new FileReader(filetobereplace));

			 String lineNew = readerNew.readLine();

			 String modifiedFileContentNew = lineNew + "}";

			// 8
			 FileWriter writerNew = new FileWriter(file);
			 writerNew.write(modifiedFileContentNew);

			 writerNew.flush();
			 writerNew.close();
			 readerNew.close();
		} else {
			return false;
		}
		
		
		
		return true;
	}
	
	public ObjectFileTransaction convertFileJSONtoObject(String file) throws IOException{
		ObjectFileTransaction result = new ObjectFileTransaction();
		 
		File filetobereplace = new File(file);
	
		Gson gson = new Gson();

		result = gson.fromJson(new FileReader(filetobereplace), ObjectFileTransaction.class);
		
		return result;
	}
	
	public String createDataToFile(String fileName, HashMap<String, Object> value  ) throws IOException{
		//
	        
	        File file = new File(fileName+".json"); //initialize File object and passing path as argument 
	        
	        Boolean flag =  file.createNewFile();
	        System.out.println(flag);
	        
	       
			
			 JsonWriter  writerJson = new JsonWriter(new FileWriter(file,true)); 
			 writerJson.beginObject();
			Iterator it = value.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				writerJson.name(pair.getKey().toString()).value(String.valueOf(pair.getValue()));
				it.remove(); // avoids a ConcurrentModificationException
			}

			 writerJson.endObject();  
			 writerJson.close();
		
		return file.toString();
	}

}

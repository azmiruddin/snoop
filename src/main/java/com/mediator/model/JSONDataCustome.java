package com.mediator.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import com.google.gson.stream.JsonWriter;

public class JSONDataCustome {
	
	public JSONDataCustome(String file, String coinBaseAddress, BigInteger trxCount, String credentialsAddrs, BigInteger gasPrice, BigInteger gasLimit, BigInteger valueTrx, BigInteger balanceFrom, BigInteger balanceTo) throws IOException{
	 	super();
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
		 writerJson.name("coinbaseAddress").value(coinBaseAddress);
		 writerJson.name("transactionCount").value(trxCount.toString());
		 writerJson.name("credentialsAddress").value(credentialsAddrs);
		 writerJson.name("gasPrice").value(gasPrice.toString());
		 writerJson.name("gasLimit").value(gasLimit.toString());
		 writerJson.name("valueTrx").value(valueTrx.toString());
		 writerJson.name("balanceFrom").value(String.valueOf(balanceFrom));
		 writerJson.name("balanceTo").value(String.valueOf(balanceTo));
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
	}

}

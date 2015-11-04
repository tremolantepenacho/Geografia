/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// http://programmaremobile.blogspot.com.es/2009/01/java-and-openoffice-base-db-through.html
//http://digiassn.blogspot.com.es/2006/07/java-creating-jdbc-connection-to.html
package geografia;

/**
 *
 * @author hector
 */


import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Geografia {

    static Connection con  = null;
    static Statement  st   = null;  
    static ResultSet  res  = null; 
    static String     sql  = null;
    private static final String DBH = "jdbc:hsqldb:file:geografia/geografia";

	public static void main(String[] args) throws Exception {

		//
		// An excel file name. You can create a file name with a full
		// path information.
		//
		String filename = "pobmun14.xls";
		//
		// Create an ArrayList to store the data read from excel sheet.
		//
		List sheetData = new ArrayList();
		FileInputStream fis = null;
                ArrayList pueblos=new ArrayList();
                FileWriter insertSQL=null;
		try {
			//
			// Create a FileInputStream that will be use to read the
			// excel file.
			//
			fis = new FileInputStream(filename);
			//
			// Create an excel workbook from the file system.
			//
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			//
			// Get the first sheet on the workbook.
			//
			HSSFSheet sheet = workbook.getSheetAt(0);
			//
			// When we have a sheet object in hand we can iterator on
			// each sheet's rows and on each row's cells. We store the
			// data read on an ArrayList so that we can printed the
			// content of the excel to the console.
			//
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				
				Iterator cells = row.cellIterator();
				List data = new ArrayList();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();
					data.add(cell);
				}
				sheetData.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		obtieneDatos(sheetData,pueblos);
                for (Object item : pueblos) {
                 //   System.out.println(item);
                }
           //     llenaFichero(pueblos,insertSQL);
                
                loadHSQLDB();
                connectDB();
                createStatement();
                insertData();
                closeHSQLDB();
	}

	private static void obtieneDatos(List sheetData, ArrayList pueblos) {
		            
                        Municipio nuevoMunicipio;
                        String nomTemp;
                        int codTemp,provTemp,pobTemp,homTemp,mujTemp;
                        
			for (int i = 3; i < sheetData.size(); i++) {
			List list = (List) sheetData.get(i);
                       
                            provTemp=Integer.parseInt(((Cell) list.get(0)).getStringCellValue());
                            codTemp=Integer.parseInt(((Cell) list.get(2)).getStringCellValue());
                            pobTemp=(int)((Cell) list.get(4)).getNumericCellValue();
                            homTemp=(int)((Cell) list.get(5)).getNumericCellValue();
                            mujTemp=(int)((Cell) list.get(6)).getNumericCellValue();
                            nomTemp=((Cell) list.get(3)).getStringCellValue();

                            nuevoMunicipio=new Municipio(provTemp,codTemp,pobTemp,homTemp,mujTemp,nomTemp);
                            pueblos.add(nuevoMunicipio);
			
			}
                        
                        
		}
        private static void llenaFichero(ArrayList pueblos, FileWriter fich){
        
           
            try
             {
                PrintWriter pw=null;
                fich = new FileWriter("insertLocalidad.txt");
                pw = new PrintWriter(fich);

                
                 for (Object item : pueblos) {
                   // System.out.println(item);
                    pw.println(((Municipio)item).insertLocalidad());
                }
                //for (int i = 0; i < 10; i++)
                  //  pw.println("Linea " + i);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
               try {
               // Nuevamente aprovechamos el finally para 
               // asegurarnos que se cierra el fichero.
               if (null != fich)
                  fich.close();
               } catch (Exception e2) {
                  e2.printStackTrace();
               }
            }
            }
        
         public static void loadHSQLDB()
    {
        System.out.println("* Starting...");
        try
        {
            Class.forName("org.hsqldb.jdbcDriver"); //Load HSQLDB driver
            System.out.println("* Loading HSQLDB driver...");
        } 
        catch(Exception e)
        {  
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;  
        } 
    }
    
    public static void connectDB()
    {
        try
        {   // Connect to the database or create if it don't exist 
            con = DriverManager.getConnection(DBH,"SA", ""); 
            System.out.println("* Creating HSQLDB connection...");
           
        }
        catch(Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            return;
        }
    }
    
    public static void createStatement()
    {
        try
        {
            st  = con.createStatement(); 
        }
        catch (Exception e)
        {  
            System.err.println("Error: createStatement: " + e.getMessage());   
            return;
        }
    }
    
    
     public static void closeHSQLDB()
    {
        // Save temporal data and close
        try
        {
            st = con.createStatement();  
            st.executeUpdate("SHUTDOWN");  
            st.close(); 
        }
        catch(Exception e)
        {
            System.out.println("Error: save temporal data" + e.getMessage());
        }
        
        try
        {
            con.close();
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return; 
        }
    }
 
      public static void insertData()
    {
        try 
        {   // Drop tables
            st.executeUpdate("INSERT INTO \"localidad\" (\"nombre\",\"provincia\") VALUES ('gilipollia',46)");
            System.out.println("Insertandoprueba");
        }
        catch (Exception e)
        {  
            System.err.println("Warning: drop table: " + e.getMessage());   
        }  
    }
}


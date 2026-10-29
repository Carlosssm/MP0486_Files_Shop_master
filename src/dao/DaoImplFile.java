package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.time.LocalDate;
 
import main.Shop;
import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplFile implements Dao{

	public ArrayList<Product> getInventory() {
		File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
		ArrayList<Product> inventoryfromFile= new ArrayList<Product>();
		try {
			// wrap in proper classes
			FileReader fr;
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
 
			// read first line
			String line = br.readLine();
 
			// process and read next line until end of file
			while (line != null) {
				// split in sections
				String[] sections = line.split(";");
 
				String name = "";
				double wholesalerPrice = 0.0;
				int stock = 0;
 
				// read each sections
				for (int i = 0; i < sections.length; i++) {
					// split data in key(0) and value(1)
					String[] data = sections[i].split(":");
 
					switch (i) {
					case 0:
						// format product name
						name = data[1];
						break;
 
					case 1:
						// format price
						wholesalerPrice = Double.parseDouble(data[1]);
						break;
 
					case 2:
						// format stock
						stock = Integer.parseInt(data[1]);
						break;
 
					default:
						break;
					}
				}
				// add product to inventory
				inventoryfromFile.add(new Product(name, new Amount(wholesalerPrice), true, stock));
 
				// read next line
				line = br.readLine();
			}
			fr.close();
			br.close();
 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inventoryfromFile;
	}

	@Override
	public void connect() {
	}

	@Override
	public void disconnect() {}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {

	    String fileName = "Inventory_" + LocalDate.now() + ".txt";

	    File folder = new File(
	        System.getProperty("user.dir") + File.separator + "files"
	    );

	    if (!folder.exists() && !folder.mkdirs()) {
	        return false;
	    }

	    File f = new File(folder, fileName);

	    try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {

	        for (Product product : inventory) {
	            pw.println(
	                "name:" + product.getName()
	                + ";wholesalerPrice:" + product.getWholesalerPrice()
	                + ";stock:" + product.getStock() + ";"
	            );
	        }

	        return true;

	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
	


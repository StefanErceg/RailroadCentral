package org.unibl.etf.mdp.railroad.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.unibl.etf.mdp.railroad.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Users {
	
	private static final String FILE = System.getProperty("user.home") + File.separator + "Railroad" + File.separator + "users.xml";
	
	public static void main(String[] args) {
//		createUser(new User("test1", "test1", "test1", "test1", "0e3bef74-c3e2-4e76-80dd-5c41c6407b77"));
//		createUser(new User("test2", "test2", "test2", "test2", "8b5c6952-c822-4fce-9988-2e02506da4cd"));
//		createUser(new User("test3", "test3", "test3", "test3", "8b5c6952-c822-4fce-9988-2e02506da4cd"));
//		System.out.println(login("aaa", "aaa"));
//		System.out.println(login("test3", "test4"));
//		System.out.println(login("test1", "test1"));
//		System.out.println(login("test2", "test2"));
//		deactivate("test2");
//		System.out.println(login("test2", "test2"));
//		getUsers().forEach((user) -> {System.out.println(user);});
	}
	
	public static ArrayList<User> getUsers() {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  ArrayList<User> users = new ArrayList<User>();
 	      try {
 	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

 	          DocumentBuilder db = dbf.newDocumentBuilder();
 	          Document doc = db.parse(new File(FILE));
 	          doc.getDocumentElement().normalize();
 
 	          NodeList list = doc.getElementsByTagName("user");
 
 	          for (int index = 0; index < list.getLength(); index++) {
 
 	              Node node = list.item(index);
 	              if (node.getNodeType() == Node.ELEMENT_NODE) {
 
 	                  Element element = (Element) node;
 	                  String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
 	                  String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
 	                  String username = element.getElementsByTagName("username").item(0).getTextContent();
 	                  String password = element.getElementsByTagName("password").item(0).getTextContent();
 	                  String locationId = element.getElementsByTagName("locationId").item(0).getTextContent();
 	                  String active = element.getElementsByTagName("active").item(0).getTextContent();
 	                  if ("1".equals(active)) {
 	                	  	users.add(new User(firstName, lastName, username, locationId));
 	                  }
 	              }
 	          }
 	          return users;
 
 	      } catch (ParserConfigurationException | SAXException | IOException e) {
 	          e.printStackTrace();
 	          return null;
 	      }
	}
	
	public static boolean createUser(User user) {
		try {

	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	        Document doc = docBuilder.parse(new File(FILE));
	        
	        Node users = doc.getElementsByTagName("users").item(0);
	        if (users == null) {
	        	users =  doc.createElement("users");
	        }
	        Node newUser = doc.createElement("user");
	        Node firstName = doc.createElement("firstName");
	        firstName.setTextContent(user.getFirstName());
	        Node lastName = doc.createElement("lastName");
	        lastName.setTextContent(user.getLastName());
	        Node username = doc.createElement("username");
	        username.setTextContent(user.getUsername());
	        Node password = doc.createElement("password");
	        password.setTextContent(getHash(user.getPassword()));
	        Node locationId = doc.createElement("locationId");
	        locationId.setTextContent(user.getLocationId());
	        Node active = doc.createElement("active");
	        active.setTextContent(user.getActive().toString());
	        
	        newUser.appendChild(firstName);
	        newUser.appendChild(lastName);
	        newUser.appendChild(username);
	        newUser.appendChild(password);
	        newUser.appendChild(locationId);
	        newUser.appendChild(active);
	        
	        users.appendChild(newUser);

	        try (FileOutputStream output = new FileOutputStream(FILE)) {
	            writeXml(doc, output);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
		} catch(ParserConfigurationException | TransformerException | SAXException | IOException  e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static User login(String username, String password) {
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  
		  	      try {
		  	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

		  	          DocumentBuilder db = dbf.newDocumentBuilder();
		  	          Document doc = db.parse(new File(FILE));
		  	          doc.getDocumentElement().normalize();
		  
		  	          NodeList list = doc.getElementsByTagName("user");
		  
		  	          for (int index = 0; index < list.getLength(); index++) {
		  
		  	              Node node = list.item(index);
		  	              if (node.getNodeType() == Node.ELEMENT_NODE) {
		  
		  	                  Element element = (Element) node;
		  	                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
		 	                  String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
		  	                  String usernameValue = element.getElementsByTagName("username").item(0).getTextContent();
		  	                  String passwordValue = element.getElementsByTagName("password").item(0).getTextContent();
		  	                  String locationId = element.getElementsByTagName("locationId").item(0).getTextContent();
		  	                  String active = element.getElementsByTagName("active").item(0).getTextContent();
		  	                  if (username.equals(usernameValue) && (getHash(password)).equals(passwordValue)) {
		  	                	   return "1".equals(active) ? new User(firstName, lastName, usernameValue, locationId) : null;
		  	                  }
		  	              }
		  	          }
		  	          return null;
		  
		  	      } catch (ParserConfigurationException | SAXException | IOException e) {
		  	          e.printStackTrace();
		  	          return null;
		  	      }
	}
	
	public static boolean deactivate(String username) {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  
 	      try {
 	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

 	          DocumentBuilder db = dbf.newDocumentBuilder();
 	          Document doc = db.parse(new File(FILE));
 	          doc.getDocumentElement().normalize();
 
 	          NodeList list = doc.getElementsByTagName("user");
 
 	          for (int index = 0; index < list.getLength(); index++) {
 
 	              Node node = list.item(index);
 	              if (node.getNodeType() == Node.ELEMENT_NODE) {
 
 	                  Element element = (Element) node;

 	                  String usernameValue = element.getElementsByTagName("username").item(0).getTextContent();
 	                  String active = element.getElementsByTagName("active").item(0).getTextContent();
 	                  if (username.equals(usernameValue) && "1".equals(active)) {
 	                	   if ("1".equals(active)) {
 	                		   element.getElementsByTagName("active").item(0).setTextContent("0");
 	                		  try (FileOutputStream output = new FileOutputStream(FILE)) {
 	             	            writeXml(doc, output);
 	             	            return true;
 	             	        } catch (IOException | TransformerException e) {
 	             	            e.printStackTrace();
 	             	            return false;
 	             	        }
 	                	   }
 	                  }
 	              }
 	          }
 	          return false;
 
 	      } catch (ParserConfigurationException | SAXException | IOException e) {
 	          e.printStackTrace();
 	          return false;
 	      }
	}
	
	private static void writeXml(Document doc, OutputStream output) throws TransformerException {
		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
		 Transformer transformer = transformerFactory.newTransformer();
		 DOMSource source = new DOMSource(doc);
		 StreamResult result = new StreamResult(output);

		 transformer.transform(source, result);
		 }
	
	private static String getHash(String text) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(text.getBytes());
			String encoded = Base64.getEncoder().encodeToString(hash);
			return encoded;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}

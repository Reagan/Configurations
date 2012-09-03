package org.aprilsecond.asremind.configurations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 * This class is used to read and write the config options for 
 * the application to the config file
 *  
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class Configurations {
       
    /**
     * stores the path to the config file to the application
     */
    private static String CONFIG_FILE = null  ;
    
    /**
     * stores the properties object with access to the 
     * application settings
     */
    private Properties appProperties  ;
       
    /**
     * stores an instance of this class
     */
    private static Configurations thisInstance  = null ;
    
    /**
     * constructor initializes a different config file 
     * for the application
     */
    private Configurations(String pathToConfig) {
        CONFIG_FILE = pathToConfig ;
    }
    
    /**
     * constructor uses default location for config file
     */
    private Configurations() {}
    
    /**
     * implements a singleton pattern to initialize the class
     */
    public static Configurations getInstance() {
        if (null == thisInstance) {
            thisInstance = new Configurations() ;            
            
            // load the application settings
            thisInstance.loadConfigSettings() ;
        }
        
        return thisInstance ;
    }
    
    /**
     * uses a singleton design to initialize the class
     * and a new config file
     */
    public static Configurations getInstance(String configFile) {
        if (null == thisInstance) {
            thisInstance = new Configurations(configFile) ;
            
            // load the application settings
            thisInstance.loadConfigSettings() ;
        }
        
        return thisInstance ;
    }
    
    /**
     * This method obtains the value of a specific key in the config file
     * @param properties
     * @return 
     */
    public String getValueOf(String key, String defaultValue)
    {
        String value = null ;
        
        if(appProperties.containsKey(key))
        {
            value = appProperties.getProperty(key,defaultValue);
        }
        
        return value ;
    }
    
    /**
     * gets the value for a property without setting a default value
     */
    public String getValueOf(String key)
    {
        String value = null ;
        
        if(appProperties.containsKey(key))
        {
            value = appProperties.getProperty(key);
        }
        
        return value ;
    }
    
    /**
     * This method sets the value for a specific key or adds that key if
     * it is not found in the properties file
     * @param key
     * @param value
     * @return 
     */
    public boolean setValueOf(String key, String value)
    {
        if(appProperties.containsKey(key))
        {
            appProperties.setProperty(key, value);
        }
                
        return false ;
    }
    
    /**
     * This method returns to the config file
     */
    public void writePropertyToConfigFile(String key, String value) {
        
        FileOutputStream out ;
        
        // add properties to config file
        appProperties.put(key, value) ;
        
        // initialize the config object
        try {
            out = new FileOutputStream(CONFIG_FILE);
            
            // write to file
            appProperties.store(out, null);       
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error accessing config file "
                    + "to write: " + ex);
        } catch (IOException ex) {
                System.out.println("Error accessing config file "
                    + "to write: " + ex);
        }
    }
    
    /**
     * This method loads the client secret and ID from the 
     * application config file
     */
    public Properties loadConfigSettings() {
        
        // initialize the application properties object
        appProperties = new Properties() ;
        
        // get the app properties from the contained file
        FileInputStream in ;
        
        try {
            in = new FileInputStream(CONFIG_FILE);
            
            // check the path to the app settings
            if (null == in) {
                // alert user
                JOptionPane.showMessageDialog(null, "There was an error "
                        + "creating the properties object",
                        "Error", JOptionPane.ERROR_MESSAGE);

                return null;
            }
            
            // load the properties for the application
            appProperties.load(in);            

        } catch (FileNotFoundException ex) {
            System.out.println("Error creating the input stream : " 
                    + ex);
        } catch (IOException ex) {
            System.out.println("Error loading the config file settings : "
                    + ex);
        }
              
        return appProperties ;
    }
}

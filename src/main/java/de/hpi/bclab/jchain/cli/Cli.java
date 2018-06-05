package de.hpi.bclab.jchain.cli;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

/**
 * 
 * @author Alexander Mühle
 *
 */
public class Cli {
	
	private static final Logger log = Logger.getLogger(Cli.class.getName());
	
	private String[] args;
	private Options options = new Options();
	
	public Cli(String[] args) {
		this.args = args;
		options.addOption("h", "help", false, "show help.");
		options.addOption("c", "config", true, "load config");
		options.addOption("p", "port", true, "set port");
		options.addOption("g", "group", true, "set multicast group");
		options.addOption("pm", "peermode", true, "set peermode");
		options.addOption("sm", "statemodel", true, "set statemodel");
		options.addOption("d", "data", true, "set database location");

	}
	
	public Configuration parse() {
		CommandLineParser parser = new BasicParser();
		CommandLine cmd;
		PropertiesConfiguration config;
		try {
			cmd = parser.parse(options, args);	
			
			//PARSE OPTIONS
			if(cmd.hasOption("h")) {
				help();
			}
			
			if(cmd.hasOption("c")) {
				return loadConfig(cmd.getOptionValue("c"));
			}
			else {
				config = new PropertiesConfiguration();
				
			}
			
			if(cmd.hasOption("p")) {
				config.addProperty("port", cmd.getOptionValue("p"));
			}
			else {
				config.addProperty("port", 7499);
			}
			
			if(cmd.hasOption("g")) {
				config.addProperty("group", cmd.getOptionValue("g"));
			}
			else {
				config.addProperty("group", "233.0.0.0");
			}
			
			if(cmd.hasOption("pm")) {
				config.addProperty("peermode", cmd.getOptionValue("pm"));
			}
			else {
				config.addProperty("peermode", "multicast");
			}
			
			if(cmd.hasOption("sm")) {
				config.addProperty("statemodel", cmd.getOptionValue("sm"));
			}
			else {
				config.addProperty("statemodel", "accounts");
			}
			
			if(cmd.hasOption("d")) {
				config.addProperty("db", cmd.getOptionValue("d"));
			}
			else {
				config.addProperty("db", "~/.data"); //TODO: platform independence
			}
			
			return config;
			
		}
		catch (ParseException e) {
			log.error("Failed to parse command line arguments");
			log.debug(e);
		}
		return null;
	}
	
	private void help() {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.exit(0);
	}
	
	private Configuration loadConfig(String arg) {
		Configurations configs = new Configurations();
		Configuration config = null;
		try {
			config = configs.properties(arg);
		} catch (ConfigurationException e) {
			log.error("Failed to load config file " + arg);
			log.debug(e);
		}
		return config;
	}

}

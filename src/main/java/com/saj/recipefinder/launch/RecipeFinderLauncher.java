package com.saj.recipefinder.launch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
/**
 * @author Sajan
 * @Description Launcher Class for recipe-finder project
 * Run this as a Java class 
 * or
 * from command Line using java command
 */
public class RecipeFinderLauncher {

	public static final Integer PORT_NUMBER=8002;

    public static void main(String[] args) throws Exception{
        final Server recipeFinderServer = new Server(PORT_NUMBER);
        final WebAppContext webAppContext = new WebAppContext();
        webAppContext.setParentLoaderPriority(true);
        webAppContext.setContextPath("/");
        final String webApplicationDirectory = "src/main/webapp/";
        webAppContext.setDescriptor(webApplicationDirectory + "/WEB-INF/web.xml");
        webAppContext.setResourceBase(webApplicationDirectory);
        recipeFinderServer.setHandler(webAppContext);
        recipeFinderServer.start();
        recipeFinderServer.join();
    }
}

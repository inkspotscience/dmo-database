/*
 * Copyright (C) 2025 Inkspot Science.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.inkspot.tests.dmo.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * This class runs a dummy server for unit testing
 *
 * @author hugo
 */
public class DummyServer {
    Server server;
    WeldContainer container;
    
    public DummyServer() {
    }

    public void start() throws Exception {
        // Start CDI (Weld)
        Weld weld = new Weld();
        container = weld.initialize();

        // Jetty setup
        server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(server, "/");

        // Setup Jersey servlet
        ServletHolder jersey = new ServletHolder(new ServletContainer(new DummyServerApplication()));
        context.addServlet(jersey, "/*");

        // Start Jetty
        
        server.start();
        

        
    }
    
    public void stop(){
        try {
            server.stop();
            container.shutdown();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        try {
            final DummyServer s = new DummyServer();
            s.start();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the application...");
                    s.stop();
                    System.out.println("Done, exit.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
          
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}

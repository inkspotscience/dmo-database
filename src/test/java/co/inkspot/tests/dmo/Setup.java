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
package co.inkspot.tests.dmo;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Set up / check test DB
 * @author hugo
 */
public class Setup implements BeforeAllCallback, ExtensionContext.Store.CloseableResource{
    private static Logger logger = Logger.getLogger(Setup.class.getName());
    private static volatile boolean started = false;
    public static EntityManager em;
    public static EntityManagerFactory emf;
    static EmbeddedPostgres postgres;
    
    @Override
    public void beforeAll(ExtensionContext ec) throws Exception {
       if (!started) {
            try {
                
                File dataDir = new File(System.getProperty("user.home"), "osm/tests/dmo");
                                
                postgres = EmbeddedPostgres.builder()
                        .setPort(5432)
                        .setDataDirectory(dataDir)
                        .setCleanDataDirectory(true)
                        .start();
                
                        try ( Connection c = postgres.getPostgresDatabase().getConnection()) {
                            try ( Statement s = c.createStatement()) {
                                s.execute("CREATE USER osm WITH PASSWORD 'osmuser'");
                                s.execute("CREATE DATABASE dmo");
                                s.execute("GRANT ALL PRIVILEGES ON DATABASE dmo TO osm");
                            } catch (Exception ex){
                                throw new Exception("SQL Setup Error", ex);
                            }
                        }                
                logger.info("Started embedded PostgreSQL");
                emf = Persistence.createEntityManagerFactory("dmo");
                em = emf.createEntityManager();
                TestSuite.em = em;
                started = true;
            } catch (Exception e) {
                started = false;
                e.printStackTrace();
            }
        }        
    }

    @Override
    public void close() throws Throwable {
        
    }
    
    public static void shutdown(){
        if(started){
            try {
                postgres.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }    
    
}

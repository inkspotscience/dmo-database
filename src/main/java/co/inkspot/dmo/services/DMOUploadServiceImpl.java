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
package co.inkspot.dmo.services;

import co.inkspot.dmo.api.DMOUploadService;
import co.inkspot.dmo.dao.DMODataAccess;
import co.inkspot.dmo.model.Collection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;

/**
 * Service that is used to upload DMO data
 * @author hugo
 */
@Path("/dmos/upload")
@ApplicationScoped
public class DMOUploadServiceImpl implements DMOUploadService {
    @Inject
    DMODataAccess dmoAccess;
    
    @Override
    public String test(){
        return "Hello!";
    }

    public DMOUploadServiceImpl() {
    }
    
    
    @Override
    @Transactional
    public String createCollectionWithMetadata(String metadata) throws Exception {
        return dmoAccess.populateCollectionFromMobGapMetadata(metadata).getId();
    }

    @Override
    public Collection getCollection(String id) throws Exception {
        return dmoAccess.getCollection(id);
    }
    
    
}

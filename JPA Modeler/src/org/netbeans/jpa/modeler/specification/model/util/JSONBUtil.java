/**
 * Copyright [2017  ] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jpa.modeler.specification.model.util;

import java.util.Date;
import org.netbeans.jsonb.modeler.manager.JSONBModelerRequestManager;
import org.netbeans.jpa.modeler.spec.EntityMappings;
import org.netbeans.jpa.modeler.spec.workspace.WorkSpace;
import org.netbeans.jpa.modeler.specification.model.scene.JPAModelerScene;
import org.netbeans.modeler.core.ModelerFile;
import org.openide.util.Lookup;

/**
 *
 * @author jGauravGupta
 */
public class JSONBUtil {

    public static void openJSONBViewer(ModelerFile file) {
        EntityMappings entityMappings = (EntityMappings) file.getModelerScene().getBaseElementSpec();
        openJSONBViewer(file, entityMappings, entityMappings.getCurrentWorkSpace());
    }

    public static void openJSONBViewer(ModelerFile file, EntityMappings entityMappings, WorkSpace workSpace) {
        if (!((JPAModelerScene) file.getModelerScene()).compile()) {
            return;
        }
        WorkSpace paramWorkSpace = entityMappings.getRootWorkSpace() == workSpace ? null : workSpace;
        try {
            PreExecutionUtil.preExecution(file);
            JSONBModelerRequestManager jsonbModelerRequestManager = Lookup.getDefault().lookup(JSONBModelerRequestManager.class);

            //close diagram and reopen 
            long st = new Date().getTime();
            file.getChildrenFile("JSONB").ifPresent(modelerFile -> modelerFile.getModelerPanelTopComponent().close());
            System.out.println("openJSONBViewer close Total time : " + (new Date().getTime() - st) + " ms");
            jsonbModelerRequestManager.init(file, entityMappings, paramWorkSpace);
            System.out.println("openJSONBViewer Total time : " + (new Date().getTime() - st) + " ms");
        } catch (Throwable t) {
            file.handleException(t);
        }
    }

}

   
/**
 * Copyright [2016] Gaurav Gupta
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
package org.netbeans.db.modeler.core.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.db.modeler.spec.Column;
import org.netbeans.db.modeler.spec.Table;
import org.netbeans.db.modeler.specification.model.scene.DBModelerScene;
import org.netbeans.jpa.modeler.core.widget.*;
import org.netbeans.jpa.modeler.rules.entity.EntityValidator;
import org.netbeans.modeler.core.NBModelerUtil;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.context.ContextPaletteModel;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.openide.util.NbBundle;

public class TableWidget extends FlowNodeWidget<Table, DBModelerScene> {

    private final Map<String, ColumnWidget> columnWidgets = new HashMap<>();
    private final Map<String, ForeignKeyWidget> foreignKeyWidgets = new HashMap<>();//ForeignKey Column
    private final Map<String, PrimaryKeyWidget> primaryKeyWidgets = new HashMap<>();//PrimaryKey Column

    public TableWidget(DBModelerScene scene, NodeWidgetInfo node) {
        super(scene, node);
        this.addPropertyChangeListener("name", (PropertyChangeListener<String>) (String value) -> {
            if (value == null || value.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, NbBundle.getMessage(EntityValidator.class, EntityValidator.EMPTY_CLASS_NAME));
                setName(TableWidget.this.getLabel());//rollback
            } else {
                setName(value);
                setLabel(value);
            }
        });
        this.setImage(this.getNodeWidgetInfo().getModelerDocument().getImage());
    }

    public ColumnWidget addNewColumn(String name, Column column) {
        Table table = this.getBaseElementSpec();

        if (column == null) {
            column = new Column();
            column.setId(NBModelerUtil.getAutoGeneratedStringId());
            column.setName(name);
            table.addColumn(column);
        }

        ColumnWidget widget = (ColumnWidget) createPinWidget(ColumnWidget.create(column.getId(), name,column));
        widget.setDatatypeTooltip();
        columnWidgets.put(column.getId(), widget);
        return widget;
    }
    
    

    public ColumnWidget addNewForeignKey(String name, Column column) {
        Table table = this.getBaseElementSpec();

        if (column == null) {
            column = new Column();
            column.setId(NBModelerUtil.getAutoGeneratedStringId());
            column.setName(name);
            table.addColumn(column);
        }

        ForeignKeyWidget widget = (ForeignKeyWidget) createPinWidget(ForeignKeyWidget.create(column.getId(), name,column));
        widget.setDatatypeTooltip();
        foreignKeyWidgets.put(column.getId(), widget);
        return widget;
    }
    
        public ColumnWidget addNewPrimaryKey(String name, Column column) {
        Table table = this.getBaseElementSpec();

        if (column == null) {
            column = new Column();
            column.setId(NBModelerUtil.getAutoGeneratedStringId());
            column.setName(name);
            table.addColumn(column);
        }

        PrimaryKeyWidget widget = (PrimaryKeyWidget) createPinWidget(PrimaryKeyWidget.create(column.getId(), name,column));
        widget.setDatatypeTooltip();
        primaryKeyWidgets.put(column.getId(), widget);
        
        return widget;
    }

//    public abstract void deleteAttribute(AttributeWidget attributeWidget);
    
       // method should be called only onec in case of loadDocument
    public void sortAttributes() {
        Map<String, List<Widget>> categories = new LinkedHashMap<>();
       
        if (!primaryKeyWidgets.isEmpty()) {
            List<Widget> primaryKeyCatWidget = new ArrayList<>();
            primaryKeyWidgets.values().stream().forEach((primaryKeyWidget) -> {
                primaryKeyCatWidget.add(primaryKeyWidget);
            });
            categories.put("Primary Key", primaryKeyCatWidget);
        }
        
        if (!columnWidgets.isEmpty()) {
            List<Widget> columnCatWidget = new ArrayList<>();
            columnWidgets.values().stream().forEach((columnWidget) -> {
                columnCatWidget.add(columnWidget);
            });
            categories.put("Basic", columnCatWidget);
        }
        
        if (!foreignKeyWidgets.isEmpty()) {
            List<Widget> foreignKeyCatWidget = new ArrayList<>();
            foreignKeyWidgets.values().stream().forEach((foreignKeyWidget) -> {
                foreignKeyCatWidget.add(foreignKeyWidget);
            });
            categories.put("Foreign Key", foreignKeyCatWidget);
        }

        
        this.sortPins(categories);
    }
        
        
    @Override
    public void setName(String name) {

        if (name != null && !name.trim().isEmpty()) {
            this.name = name.replaceAll("\\s+", "");
            getBaseElementSpec().setName(this.name);
        }
//        if (JavaPersistenceQLKeywords.isKeyword(TableWidget.this.getName())) {
//            throwError(EntityValidator.CLASS_NAME_WITH_JPQL_KEYWORD);
//        } else {
//            clearError(EntityValidator.CLASS_NAME_WITH_JPQL_KEYWORD);
//        }
//        DBMapping entityMapping = TableWidget.this.getModelerScene().getBaseElementSpec();
//        if (entityMapping.findAllEntity(TableWidget.this.getName()).size() > 1) {
//            throwError(EntityValidator.NON_UNIQUE_ENTITY_NAME);
//        } else {
//            clearError(EntityValidator.NON_UNIQUE_ENTITY_NAME);
//        }

    }

    @Override
    public void setLabel(String label) {
        if (label != null && !label.trim().isEmpty()) {
            this.setNodeName(label.replaceAll("\\s+", ""));
        }
    }

    /**
     * @return the columnWidgets
     */
    public ColumnWidget getColumnWidget(String id) {
        return columnWidgets.get(id);
    }

    /**
     * @return the referenceColumnWidgets
     */
    public ForeignKeyWidget getForeignKeyWidget(String id) {
        return foreignKeyWidgets.get(id);
    }

    /**
     * @return the columnWidgets
     */
    public Collection<ColumnWidget> getColumnWidgets() {
        return columnWidgets.values();
    }

    /**
     * @return the referenceColumnWidgets
     */
    public Collection<ForeignKeyWidget> getForeignKeyWidgets() {
        return foreignKeyWidgets.values();
    }
    
    /**
     * @return the primaryKeyWidgets
     */
    public PrimaryKeyWidget getPrimaryKeyWidget(String id) {
        return primaryKeyWidgets.get(id);
    }
    
     /**
     * @return the primaryKeyWidgets
     */
    public Collection<PrimaryKeyWidget> getPrimaryKeyWidgets() {
        return primaryKeyWidgets.values();
    }    

    @Override
    public ContextPaletteModel getContextPaletteModel() {

        return null;
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {

    }

}
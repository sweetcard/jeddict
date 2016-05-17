//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.08.18 at 01:46:14 PM IST
//
package org.netbeans.jpa.modeler.spec;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.jpa.source.JavaSourceParserUtil;
import org.netbeans.modeler.properties.type.Enumy;

/**
 * <p>
 * Java class for constraint-mode.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * <
 * pre>
 * &lt;simpleType name="constraint-mode"> &lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}token"> &lt;enumeration
 * value="CONSTRAINT"/> &lt;enumeration value="NO_CONSTRAINT"/> &lt;enumeration
 * value="PROVIDER_DEFAULT"/> &lt;/restriction> &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "constraint-mode")
@XmlEnum
public enum ConstraintMode implements Enumy {

    CONSTRAINT("Constraint"),
    NO_CONSTRAINT("No Constraint"),
    PROVIDER_DEFAULT("Provider Default");

    private final String display;

    private ConstraintMode(String display) {
        this.display = display;
    }

    public static ConstraintMode load(Element element, AnnotationMirror annotationMirror) {
        ConstraintMode constraintMode = null;
        if (annotationMirror != null) {
            Object value = JavaSourceParserUtil.findAnnotationValue(annotationMirror, "value");
            if (value != null) {
                constraintMode = ConstraintMode.valueOf(value.toString());
            }
        }
        return constraintMode;
    }

    public String value() {
        return name();
    }

    public static ConstraintMode fromValue(String v) {
        return valueOf(v);
    }

    @Override
    public String getDisplay() {
        return display;
    }

    @Override
    public Enumy getDefault() {
        return PROVIDER_DEFAULT;
    }

}

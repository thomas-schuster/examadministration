/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hspf.swt.exam.util;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;

/**
 * Create text field in a table cell. @see
 * <a href="
 * https://itextpdf.com/en/resources/examples/itext-7/create-fields-table">iText
 * create fields in table</a>
 *
 * @author ThomasSchuster
 */
public class FieldCellRenderer extends CellRenderer {

    protected String fieldName;

    public FieldCellRenderer(Cell modelElement, String fieldName) {
        super(modelElement);
        this.fieldName = fieldName;
    }

    @Override
    public void draw(DrawContext drawContext) {
        super.draw(drawContext);
        PdfTextFormField field = PdfFormField.createText(drawContext.getDocument(), getOccupiedAreaBBox(), fieldName, "");
        field.setFontSizeAutoScale();
        
        PdfAcroForm form = PdfAcroForm.getAcroForm(drawContext.getDocument(), true);
        form.addField(field);
    }
}

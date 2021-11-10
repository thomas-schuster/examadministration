package de.hspf.swt.exam.util;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TableRenderer;
import lombok.Getter;
import lombok.Setter;

/**
 * *
 * Adjusted example found in iText documentation. @see
 * <a href="https://itextpdf.com/en/resources/books/itext-7-building-blocks/chapter-5-adding-abstractelement-objects-part-2">iText
 * Examples</a>
 *
 * @author ThomasSchuster
 */
public class AlternatingTableRenderer extends TableRenderer {

    private boolean isOdd = true;
    @Getter
    @Setter
    private Color oddColor;
    @Getter
    @Setter
    private Color evenColor;

    public AlternatingTableRenderer(
            Table modelElement, Table.RowRange rowRange) {
        super(modelElement, rowRange);
    }

    public AlternatingTableRenderer(Table modelElement) {
        super(modelElement);
    }

    @Override
    public AlternatingTableRenderer getNextRenderer() {
        return new AlternatingTableRenderer(
                (Table) modelElement);
    }

    @Override
    public void draw(DrawContext drawContext) {
        for (int i = 1;
                i < rows.size() && null != rows.get(i) && null != rows.get(i)[0];
                i++) {
            CellRenderer[] renderers = rows.get(i);
            Rectangle leftCell = renderers[0].getOccupiedAreaBBox();
            //renderers.length - 1
            Rectangle rightCell = renderers[renderers.length - 1].getOccupiedAreaBBox();
            Rectangle rect = new Rectangle(leftCell.getLeft(), leftCell.getBottom(), rightCell.getRight() - leftCell.getLeft(), leftCell.getHeight());

            PdfCanvas canvas = drawContext.getCanvas();
            canvas.saveState();
            if (isOdd) {
                canvas.setFillColor(oddColor);
                isOdd = false;
            } else {
                canvas.setFillColor(evenColor);
                isOdd = true;
            }
            canvas.rectangle(rect);
            canvas.fill();
            canvas.restoreState();
        }
        super.draw(drawContext);
    }
}

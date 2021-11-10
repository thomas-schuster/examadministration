package de.hspf.swt.exam.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import de.hspf.swt.exam.administration.control.ApplicationStartupController;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ThomasSchuster
 */
public class FooterRenderer implements IEventHandler {

    private final PdfFormXObject placeholder;
    private final float side = 20;
    private final float space = 4.5f;
    private final float descent = 3;
    private float x;
    private float y;

    private static final Logger logger = LogManager.getLogger(ApplicationStartupController.class);
    
    public FooterRenderer(PdfDocument pdf) {
        placeholder = new PdfFormXObject(new Rectangle(0, 0, side, side));
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = docEvent.getDocument();
        PdfPage page = docEvent.getPage();

        Rectangle pageSize = page.getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(page.getLastContentStream(), page.getResources(), pdf);
        Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);

        int pageNumber = pdf.getPageNumber(page);
        Paragraph pFooter = new Paragraph().add("Agile objektorientierte Software-Entwicklung");
        Paragraph pPages = new Paragraph().add("Page ").add(String.valueOf(pageNumber)).add(" of");

        x = pageSize.getWidth() - 50;
        y = pageSize.getBottom() + 18;

        try {
            canvas.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
        } catch (IOException ex) {
            logger.error(ex);
        }
        canvas.setFontSize(8f);

        canvas.showTextAligned(pFooter, pageSize.getWidth() / 2, y, TextAlignment.CENTER);
        canvas.showTextAligned(pPages, x, y, TextAlignment.RIGHT);

        pdfCanvas.addXObject(placeholder, x + space, y - descent);
        pdfCanvas.release();
    }

    public void writeTotal(PdfDocument pdf) {
        Canvas canvas = new Canvas(placeholder, pdf);
        try {
            canvas.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
            canvas.setFontSize(8f);
        } catch (IOException ex) {
            logger.error(ex);
        }
        canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()), 0, descent, TextAlignment.LEFT);
    }
}

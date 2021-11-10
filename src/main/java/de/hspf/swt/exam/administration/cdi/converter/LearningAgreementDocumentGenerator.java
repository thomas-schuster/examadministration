package de.hspf.swt.exam.administration.cdi.converter;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.property.TextAlignment;
import de.hspf.swt.exam.administration.dao.LearningAgreement;
import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import de.hspf.swt.exam.util.AlternatingTableRenderer;
import de.hspf.swt.exam.util.FieldCellRenderer;
import de.hspf.swt.exam.util.FooterRenderer;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author karl-heinz.rau
 * @author thomas.schuster
 */
public class LearningAgreementDocumentGenerator {

    private final PdfFont font;
    private final PdfFont bold;

    public LearningAgreementDocumentGenerator() throws IOException {
        this.font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        this.bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
    }

    public Document createPdf(LearningAgreement learningAgreement, OutputStream output) throws IOException {

        PdfWriter writer = new PdfWriter(output);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4.rotate());

        Document document = new Document(pdf);
        
        //prepare footert
        FooterRenderer footer = new FooterRenderer(pdf);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, footer);

        //add content
        document.add(createHeaderTable(learningAgreement));
        addlineSeparator(document);

        document.add(createItemTable(learningAgreement));
        addlineSeparator(document);

        document.add(createSignatureTable());
        addlineSeparator(document);

        // write number of pages information for footer
        footer.writeTotal(pdf);
        document.close();
        return document;
    }

    private void addlineSeparator(Document document) {
        document.add(new Paragraph("\n"));
        SolidLine line = new SolidLine(1f);
        line.setColor(new DeviceRgb(51, 153, 255));
        LineSeparator ls = new LineSeparator(line);
        document.add(ls);
        document.add(new Paragraph("\n"));
    }

    public Table createHeaderTable(LearningAgreement la) throws IOException {

        Table table = new Table(4);
        table.setWidth(UnitValue.createPercentValue(100));

        Color color = new DeviceRgb(222, 234, 246);
        table.addHeaderCell(createCell("Learning Agreement - No. " + la.getId(), 1, 4, bold, TextAlignment.CENTER, Border.NO_BORDER, color).setPadding(10));

        table.addCell(createCell("Student ID:", bold, TextAlignment.RIGHT, Border.NO_BORDER));
        table.addCell(createCell("" + la.getStudent().getStudentID(), Border.NO_BORDER));

        table.addCell(createCell("Name of Student:", bold, TextAlignment.RIGHT, Border.NO_BORDER));
        table.addCell(createCell(la.getStudent().getFirstName() + " " + la.getStudent().getLastName(), Border.NO_BORDER));

        table.addCell(createCell("Study Program:", bold, TextAlignment.RIGHT, Border.NO_BORDER));
        table.addCell(createCell(la.getStudent().getStudyProgram().getProgramTitle(), Border.NO_BORDER));

        table.addCell(createCell("Study Period:", bold, TextAlignment.RIGHT, Border.NO_BORDER));
        table.addCell(createCell(la.getApplicationItem().getApplication().getStartSemester() + " to " + la.getApplicationItem().getApplication().getLastSemester(), Border.NO_BORDER));

        table.addCell(createCell("Host University:", bold, TextAlignment.RIGHT, Border.NO_BORDER));
        table.addCell(createCell(la.getHostUniverstiy().getUniversityName(), Border.NO_BORDER));

        table.addCell(createCell("Country:", bold, TextAlignment.RIGHT, Border.NO_BORDER));
        table.addCell(createCell(la.getHostUniverstiy().getCountry().getCountryName(), Border.NO_BORDER));

        return table;
    }

    private Table createItemTable(LearningAgreement la) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 4, 2, 2, 4, 2}));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addCell(createCell(" ", bold, TextAlignment.CENTER, Border.NO_BORDER));
        table.addCell(createCell("Home Course", 1, 3, bold, TextAlignment.CENTER, Border.NO_BORDER));
        table.addCell(createCell("Host Course", 1, 3, bold, TextAlignment.CENTER, Border.NO_BORDER));

        table.addCell(createCell("Item", Border.NO_BORDER));
        table.addCell(createCell("ID", Border.NO_BORDER));
        table.addCell(createCell("Title", Border.NO_BORDER));
        table.addCell(createCell("Credits", Border.NO_BORDER));

        table.addCell(createCell("ID", Border.NO_BORDER));
        table.addCell(createCell("Title", Border.NO_BORDER));
        table.addCell(createCell("Credits", Border.NO_BORDER));

        addItemDetails(table, la);
        AlternatingTableRenderer tableRenderer = new AlternatingTableRenderer(table);
        tableRenderer.setOddColor(new DeviceRgb(222, 234, 246));
        tableRenderer.setEvenColor(new DeviceRgb(255, 255, 255));
        table.setNextRenderer(tableRenderer);
        return table;
    }

    private Table createSignatureTable() {
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 3, 1, 1, 1, 3}));

        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(createBorderlessCell(""));

        // signature line
        table.addCell(createBorderlessCell("").setHeight(15f));
        Cell signedDate = createBottomBorderCell("", new DottedBorder(1f));
        signedDate.setNextRenderer(new FieldCellRenderer(signedDate, "signedDate"));
        table.addCell(signedDate);
        table.addCell(createBorderlessCell(""));
        table.addCell(createBottomBorderCell("", new DottedBorder(1f)));
        table.addCell(createBorderlessCell(""));
        Cell approvedDate = createBottomBorderCell("", new DottedBorder(1f));
        approvedDate.setNextRenderer(new FieldCellRenderer(approvedDate, "approvedDate"));
        table.addCell(approvedDate);
        table.addCell(createBorderlessCell(""));
        table.addCell(createBottomBorderCell("", new DottedBorder(1f)));
        // description line
        table.addCell(createBorderlessCell(""));
        table.addCell(createBorderlessCell("Date"));
        table.addCell(createBorderlessCell(""));
        table.addCell(createBorderlessCell("Signature Student"));
        table.addCell(createBorderlessCell(""));
        table.addCell(createBorderlessCell("Date"));
        table.addCell(createBorderlessCell(""));
        table.addCell(createBorderlessCell("Approved by Academic Advisor"));

        table.addFooterCell(createBorderlessCell(""));

        return table;
    }

    
    private void addItemDetails(Table table, LearningAgreement la) {
        int i = 1;
        for (LearningAgreementItem item : la.getLearningAgreementItems()) {
            table.addCell(createCell(i + "", Border.NO_BORDER));
            addHomeCourses(item, new Paragraph(), new Paragraph(), new Paragraph(), table);
            addHostCourses(item, new Paragraph(), new Paragraph(), new Paragraph(), table);
            i++;
        }
    }

    private void addHomeCourses(LearningAgreementItem item, Paragraph contentId, Paragraph contentTitle, Paragraph contentCredits, Table table) {
        item.getHomeCourses().stream().map((course) -> {
            contentId.add(course.getId() + "\n");
            return course;
        }).map((course) -> {
            contentTitle.add(course.getCourseTitle() + "\n");
            return course;
        }).forEachOrdered((course) -> {
            contentCredits.add(course.getEcts() + "\n");
        });
        table.addCell(createCell(contentId, Border.NO_BORDER));
        table.addCell(createCell(contentTitle, Border.NO_BORDER));
        table.addCell(createCell(contentCredits, Border.NO_BORDER));
    }

    private void addHostCourses(LearningAgreementItem item, Paragraph contentId, Paragraph contentTitle, Paragraph contentCredits, Table table) {
        item.getHostCourses().stream().map((course) -> {
            contentId.add(course.getId() + "\n");
            return course;
        }).map((course) -> {
            contentTitle.add(course.getCourseTitle() + "\n");
            return course;
        }).forEachOrdered((course) -> {
            contentCredits.add(course.getEcts() + "\n");
        });
        table.addCell(createCell(contentId, Border.NO_BORDER));
        table.addCell(createCell(contentTitle, Border.NO_BORDER));
        table.addCell(createCell(contentCredits, Border.NO_BORDER));
    }

    private Cell createCell(String paragraph, int rowSpan, int colSpan, PdfFont font, TextAlignment alignment, Border border, Color backgroundColor) {
        Paragraph content = new Paragraph(paragraph);
        content.setFont(font);

        Cell cell = createCell(content, rowSpan, colSpan);
        cell.setBorder(border);
        cell.setBackgroundColor(backgroundColor);

        return cell;
    }

    private Cell createCell(String paragraph, int rowSpan, int colSpan, PdfFont font, TextAlignment alignment, Border border) {
        Paragraph content = new Paragraph(paragraph);
        content.setFont(font);

        Cell cell = createCell(content, rowSpan, colSpan);
        cell.setBorder(border);
        return cell;
    }

    private Cell createCell(String paragraph, PdfFont font, TextAlignment alignment, Border border) {
        Paragraph content = new Paragraph(paragraph);
        content.setFont(font);

        Cell cell = createCell(content);
        cell.setBorder(border);
        cell.setTextAlignment(alignment);

        return cell;
    }

    private Cell createBottomBorderCell(String paragraph, PdfFont font, TextAlignment alignment, Border borderBottom) {

        Cell cell = createBottomBorderCell(paragraph, borderBottom);

        cell.setFont(font);
        cell.setTextAlignment(alignment);

        return cell;
    }

    private Cell createBottomBorderCell(String paragraph, int rowSpan, int colSpan, Border borderType) {
        Paragraph content = new Paragraph(paragraph);
        content.setFont(font);

        Cell cell = createCell(content, rowSpan, colSpan);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderBottom(borderType);
        return cell;
    }

    private Cell createBottomBorderCell(String paragraph, Border borderBottom) {
        Paragraph content = new Paragraph(paragraph);
        content.setFont(font);

        Cell cell = createCell(content);

        cell.setBorder(Border.NO_BORDER);
        cell.setBorderBottom(borderBottom);

        return cell;
    }

    private Cell createCell(String paragraph, PdfFont font, TextAlignment alignment, Border borderBottom, Border borderTop, Border borderLeft, Border borderRight) {
        Paragraph content = new Paragraph(paragraph);
        content.setFont(font);

        Cell cell = createCell(content);

        cell.setTextAlignment(alignment);

        cell.setBorderBottom(borderBottom);
        cell.setBorderTop(borderTop);
        cell.setBorderRight(borderLeft);
        cell.setBorderLeft(borderRight);

        return cell;
    }

    private Cell createCell(String paragraph, Border border) {
        Cell cell = createCell(paragraph);
        cell.setBorder(border);

        return cell;
    }

    private Cell createCell(Paragraph paragraph, Border border) {
        Cell cell = new Cell();
        cell.setBorder(border);
        cell.add(paragraph);

        return cell;
    }

    private Cell createCell(String paragraph) {
        Cell cell = new Cell();
        Paragraph content = new Paragraph(paragraph);
        cell.add(content);

        return cell;
    }

    private Cell createBorderlessCell(String paragraph) {
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);

        Paragraph content = new Paragraph(paragraph);
        cell.add(content);

        return cell;
    }

    private Cell createCell(Paragraph paragraph) {
        Cell cell = new Cell();
        cell.add(paragraph);

        return cell;
    }

    private Cell createCell(Paragraph paragraph, int rowSpan, int colSpan) {
        Cell cell = new Cell(rowSpan, colSpan);
        cell.add(paragraph);

        return cell;
    }

    private Cell createCell(String paragraph, int rowSpan, int colSpan) {
        Cell cell = new Cell(rowSpan, colSpan);
        Paragraph content = new Paragraph(paragraph);
        cell.add(content);

        return cell;
    }
}

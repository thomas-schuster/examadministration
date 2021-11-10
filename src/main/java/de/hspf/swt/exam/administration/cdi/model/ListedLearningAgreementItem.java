package de.hspf.swt.exam.administration.cdi.model;

/**
 *
 * @author karl-heinz.rau
 */
public class ListedLearningAgreementItem {

    private String itemNo;
    private String homeCourseId;
    private String homeCourseTitle;
    private String homeCourseCredits;
    private String hostCourseId;
    private String hostCourseTitle;
    private String hostCourseCredits;
    private boolean renderedDeleteButton;

    public ListedLearningAgreementItem() {
        this.renderedDeleteButton = false;
    }

    public String getHomeCourseCredits() {
        return homeCourseCredits;
    }

    public void setHomeCourseCredits( double homeCourseCredits ) {
        this.homeCourseCredits = String.valueOf(homeCourseCredits);
    }

    public String getHomeCourseTitle() {
        return homeCourseTitle;
    }

    public void setHomeCourseTitle( String homeCourseTitle ) {
        this.homeCourseTitle = homeCourseTitle;
    }

    public String getHomeCourseId() {
        return homeCourseId;
    }

    public void setHomeCourseId( String homeCourseId ) {
        this.homeCourseId = homeCourseId;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo( int itemNo ) {
        this.itemNo = String.valueOf(itemNo);
    }

    public String getHostCourseId() {
        return hostCourseId;
    }

    public void setHostCourseId( String hostCourseId ) {
        this.hostCourseId = hostCourseId;
    }

    public String getHostCourseTitle() {
        return hostCourseTitle;
    }

    public void setHostCourseTitle( String hostCourseTitle ) {
        this.hostCourseTitle = hostCourseTitle;
    }

    public String getHostCourseCredits() {
        return hostCourseCredits;
    }

    public void setHostCourseCredits( double hostCourseCredits ) {
        this.hostCourseCredits = String.valueOf(hostCourseCredits);
    }

    public boolean isRenderedDeleteButton() {
        return renderedDeleteButton;
    }

    public void setRenderedDeleteButton( boolean renderedDeleteButton ) {
        this.renderedDeleteButton = renderedDeleteButton;
    }
}

package com.example.spring.eventos.response;

import java.time.LocalDate;
import java.util.Date;

public class EventoDTOCarga {
	
    private int eventId;
    private String eventDate;
    private String startTime;
    private String endTime;
    private String eventName;
    private String eventShortName;
    private String eventDescription;
    private String eventLocation;
    private String admission;
    private String alternateEventLocation;

    public EventoDTOCarga() {
    }

	public EventoDTOCarga(int eventId, String eventDate, String startTime, String endTime, String eventName,
			String eventShortName, String eventDescription, String eventLocation, String admission,
			String alternateEventLocation) {
		this.eventId = eventId;
		this.eventDate = eventDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventName = eventName;
		this.eventShortName = eventShortName;
		this.eventDescription = eventDescription;
		this.eventLocation = eventLocation;
		this.admission = admission;
		this.alternateEventLocation = alternateEventLocation;
	}


	    public int getEventId() {
	        return eventId;
	    }

	    public void setEventId(int eventId) {
	        this.eventId = eventId;
	    }

	    public String getEventDate() {
	        return eventDate;
	    }

	    public void setEventDate(String eventDate) {
	        this.eventDate = eventDate;
	    }

	    public String getStartTime() {
	        return startTime;
	    }

	    public void setStartTime(String startTime) {
	        this.startTime = startTime;
	    }

	    public String getEndTime() {
	        return endTime;
	    }

	    public void setEndTime(String endTime) {
	        this.endTime = endTime;
	    }

	    public String getEventName() {
	        return eventName;
	    }

	    public void setEventName(String eventName) {
	        this.eventName = eventName;
	    }

	    public String getEventShortName() {
	        return eventShortName;
	    }

	    public void setEventShortName(String eventShortName) {
	        this.eventShortName = eventShortName;
	    }

	    public String getEventDescription() {
	        return eventDescription;
	    }

	    public void setEventDescription(String eventDescription) {
	        this.eventDescription = eventDescription;
	    }

	    public String getEventLocation() {
	        return eventLocation;
	    }

	    public void setEventLocation(String eventLocation) {
	        this.eventLocation = eventLocation;
	    }

	    public String getAdmission() {
	        return admission;
	    }

	    public void setAdmission(String admission) {
	        this.admission = admission;
	    }

	    public String getAlternateEventLocation() {
	        return alternateEventLocation;
	    }

	    public void setAlternateEventLocation(String alternateEventLocation) {
	        this.alternateEventLocation = alternateEventLocation;
	    }

	    

	    @Override
	    public String toString() {
	        return "EventoDTO{" +
	                "eventId=" + eventId +
	                ", eventDate=" + eventDate +
	                ", startTime='" + startTime + '\'' +
	                ", endTime='" + endTime + '\'' +
	                ", eventName='" + eventName + '\'' +
	                ", eventShortName='" + eventShortName + '\'' +
	                ", eventDescription='" + eventDescription + '\'' +
	                ", eventLocation='" + eventLocation + '\'' +
	                ", admission='" + admission + '\'' +
	                ", alternateEventLocation='" + alternateEventLocation + '\'' +
	                '}';
	    }


}

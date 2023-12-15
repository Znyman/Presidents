package com.skilldistillery.history;

import java.time.LocalDate;
import java.time.Period;

public class President implements Comparable<President> {
	private int termNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate termStart;
	private LocalDate termEnd;
	private int electionsWon;
	private String whyLeftOffice;
	private String party;
	
	public President(int termNumber, String firstName, String middleName, String lastName, int electionsWon,
			String whyLeftOffice, String party) {
		super();
		this.termNumber = termNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.electionsWon = electionsWon;
		this.whyLeftOffice = whyLeftOffice;
		this.party = party;
	}

	public President(int termNumber, String firstName, String middleName, String lastName, LocalDate termStart,
			LocalDate termEnd, int electionsWon, String whyLeftOffice, String party) {
		super();
		this.termNumber = termNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.termStart = termStart;
		this.termEnd = termEnd;
		this.electionsWon = electionsWon;
		this.whyLeftOffice = whyLeftOffice;
		this.party = party;
	}

	public int getTermNumber() {
		return termNumber;
	}

	public void setTermNumber(int termNumber) {
		this.termNumber = termNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getTermStart() {
		return termStart;
	}

	public void setTermStart(LocalDate termStart) {
		this.termStart = termStart;
	}

	public LocalDate getTermEnd() {
		return termEnd;
	}

	public void setTermEnd(LocalDate termEnd) {
		this.termEnd = termEnd;
	}

	public int getElectionsWon() {
		return electionsWon;
	}

	public void setElectionsWon(int electionsWon) {
		this.electionsWon = electionsWon;
	}

	public String getWhyLeftOffice() {
		return whyLeftOffice;
	}

	public void setWhyLeftOffice(String reasonLeftOffice) {
		this.whyLeftOffice = reasonLeftOffice;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}
	
	public Period getTermLength() {
		if (termEnd == null) {
			LocalDate now = LocalDate.now();
			return Period.between(termStart, now);
		}
		return Period.between(termStart, termEnd);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(termNumber + ": ");
		builder.append(firstName + " ");
		if (middleName.length() > 0) {
			builder.append(middleName + " ");
		}
		builder.append(lastName);
		builder.append(" (" + party + ")");
		builder.append(", electionsWon=");
		builder.append(electionsWon);
		builder.append(", whyLeftOffice=");
		builder.append(whyLeftOffice);
		builder.append(", term start: " + termStart);
		builder.append(", term end: " + termEnd);
		builder.append(", length in office: " + getTermLength()); 
		return builder.toString();
	}

	@Override
	public int compareTo(President other) {
		if (this.termNumber > other.termNumber) {
			return 1;
		} else if (this.termNumber < other.termNumber) {
			return -1;
		}
		return 0;
	}

}
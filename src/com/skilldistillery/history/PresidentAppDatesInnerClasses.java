package com.skilldistillery.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PresidentAppDatesInnerClasses {
	private static final String fileName = "resources" + File.separator + "presidents.tsv";
	private List<President> presidents = new ArrayList<>();

	public PresidentAppDatesInnerClasses() {
		this.loadPresidents(fileName);
	}

	public static void main(String[] args) {
		PresidentAppDatesInnerClasses app = new PresidentAppDatesInnerClasses();
		app.start();
	}

	private void loadPresidents(String fileName) {
		// File format (tab-separated):
		// # First Middle Last Inaugurated Left office Elections won Reason left office
		// Party
		// 1 George Washington July 1, 1789 March 4, 1797 2 Did not seek re-election
		// Independent
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String record = reader.readLine(); // Read and discard header line
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
			while ((record = reader.readLine()) != null) {
				String[] col = record.split("\\t");

				int term = Integer.parseInt(col[0]);
				String fName = col[1];
				String mName = col[2];
				String lName = col[3];
				LocalDate termStart = LocalDate.parse(col[4], dateFormatter);
				LocalDate termEnd = col[5].isEmpty() ? LocalDate.now() : LocalDate.parse(col[5], dateFormatter);
				int won = Integer.parseInt(col[6]);
				String whyLeft = col[7];
				String party = col[8];

				President pres = new President(term, fName, mName, lName, termStart, termEnd, won, whyLeft, party);
				presidents.add(pres);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
	}

	public void start() {
//		this.printPresidents(this.getPresidents());
		
//		// SORTING 1 MEMBER CLASS
//		System.out.println("------------ Sorted by party and term ------------");
//		this.printPresidents(this.sortByPartyAndTerm());

		// SORTING 2 LOCAL CLASS
//		System.out.println("------------ Sorted by reason and term ------------");
//		this.printPresidents(this.sortByReasonAndTermNumber());

		// SORTING 3 ANONYMOUS CLASS
//		System.out.println("------------ Sorted by last name ------------");
//		this.printPresidents(this.sortByLastName());
		
		// FILTERING 1 SEPERATE CLASS
//		System.out.println("------------ Whig results ------------");
//		this.printPresidents(filter("Whig", new PresidentPartyMatcher()));

		// FILTERING 2 MEMBER CLASS
//		System.out.println("------------ C Presidents ------------");
//		this.printPresidents(filter("C", new PresidentNameMatcher()));

		// FILTERING 3 LOCAL CLASS
//		System.out.println("------------ Term ended ------------");
//		class WhyLeftOfficeMatcher implements PresidentMatcher {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getWhyLeftOffice().equalsIgnoreCase(string);
//			}
//		}
//		this.printPresidents(filter("Term ended", new WhyLeftOfficeMatcher()));

		// FILTERING 4.i ANONYMOUS CLASS
//		System.out.println("------------ C Presidents Anonymous ------------");
//		PresidentMatcher cNamePresMatcher = new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getFirstName().startsWith(string);
//			}
//		};
//		this.printPresidents(filter("C", cNamePresMatcher));

		// FILTERING 4.ii ANONYMOUS CLASS
//		System.out.println("------------ Democrat Presidents Anonymous ------------");
//		PresidentMatcher democratPresMatcher = new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getParty().contains(string);
//			}
//		};
//		this.printPresidents(filter("Democrat", democratPresMatcher));

		// FILTERING 4.iii ANONYMOUS CLASS
//		System.out.println("------------ Presidents Died in office Anonymous ------------");
//		PresidentMatcher diedInOfficePresMatcher = new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				return pres.getWhyLeftOffice().equalsIgnoreCase(string);
//			}
//		};
//		this.printPresidents(filter("Died in office", diedInOfficePresMatcher));

		// FILTERING 4.iv ANONYMOUS CLASS
//		System.out.println("------------ Presidents Single Election Anonymous ------------");
//		PresidentMatcher singleElectionPresMatcher = new PresidentMatcher() {
//			@Override
//			public boolean matches(President pres, String string) {
//				int electionsWon = Integer.parseInt(string);
//				return pres.getElectionsWon() == electionsWon;
//			}
//		};
//		this.printPresidents(filter("1", singleElectionPresMatcher));

		// FILTERING 4.v ANONYMOUS CLASS
		System.out.println("------------ Presidents 19th Century Anonymous ------------");
		PresidentMatcher nineTeenthCenturyPresMatcher = new PresidentMatcher() {
			@Override
			public boolean matches(President pres, String century) {
				int centuryEnd = Integer.parseInt(century) * 100;
				int centuryStart = centuryEnd - 99;
				return pres.getTermStart().isAfter(LocalDate.of(centuryStart - 1, 12, 31)) && pres.getTermStart().isBefore(LocalDate.of(centuryEnd, 12, 31));
			}
		};
		this.printPresidents(filter("19", nineTeenthCenturyPresMatcher));
		
		

	}

	// SORTING 1
	private class PartyAndTermComparator implements Comparator<President> {

		@Override
		public int compare(President arg0, President arg1) {
			int result = arg0.getParty().compareTo(arg1.getParty());
			if (result == 0) {
				result = arg0.getTermNumber() - arg1.getTermNumber();
			}
			return result;
		}
	}

	// SORTING 1
	public List<President> sortByPartyAndTerm() {
		List<President> presCopy = new ArrayList<>(presidents);
		presCopy.sort(new PartyAndTermComparator());

		return presCopy;
	}

	// SORTING 2
	private List<President> sortByReasonAndTermNumber() {
		List<President> presCopy = new ArrayList<>(presidents);
		class ReasonTermComparator implements Comparator<President> {
			@Override
			public int compare(President o1, President o2) {
				int reasonComp = o1.getWhyLeftOffice().compareTo(o2.getWhyLeftOffice());
				if (reasonComp == 0) {
					reasonComp = Integer.compare(o1.getTermNumber(), o2.getTermNumber());
				}
				return reasonComp;
			}

		}
		presCopy.sort(new ReasonTermComparator());
		return presCopy;
	}

	// SORTING 3
	private List<President> sortByLastName() {
		List<President> presCopy = new ArrayList<>(presidents);
		Comparator<President> comp = new Comparator<President>() {
			@Override
			public int compare(President o1, President o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		};
		presCopy.sort(comp);

		return presCopy;
	}

	// FILTERING 2
	private class PresidentNameMatcher implements PresidentMatcher {
		@Override
		public boolean matches(President pres, String string) {
			return pres.getFirstName().startsWith(string);
		}
	}

	public List<President> getPresidents() {
		return this.presidents;
	}

	public void printPresidents(List<President> pres) {
		for (President p : pres) {
			System.out.println(p);
		}
	}

	public List<President> filter(String string, PresidentMatcher matcher) {
		List<President> filtered = new ArrayList<>();
		for (President p : presidents) {
			if (matcher.matches(p, string)) {
				filtered.add(p);
			}
		}
		return filtered;
	}

}
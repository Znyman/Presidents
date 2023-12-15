package com.skilldistillery.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

// start:234 lines
public class PresidentAppDatesLambda {
	private static final String fileName = "resources" + File.separator + "presidents.tsv";
	private List<President> presidents = new ArrayList<>();

	public PresidentAppDatesLambda() {
		this.loadPresidents(fileName);
	}

	public static void main(String[] args) {
		PresidentAppDatesLambda app = new PresidentAppDatesLambda();
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

//		// SORTING 1
		System.out.println("------------ Sorted by party and term ------------");
		this.printPresidents(this.sortByPartyAndTerm());

		// SORTING 2
		System.out.println("------------ Sorted by reason and term ------------");
		this.printPresidents(this.sortByReasonAndTermNumber());

		// SORTING 3
		System.out.println("------------ Sorted by last name ------------");
		this.printPresidents(this.sortByLastName());

		// FILTERING 1
		System.out.println("------------ Whig results ------------");
		this.printPresidents(filter("Whig", (pres, string) -> pres.getParty().equalsIgnoreCase(string)));

		// FILTERING 2
		System.out.println("------------ C Presidents ------------");
		this.printPresidents(filter("C", (pres, string) -> pres.getFirstName().startsWith(string)));

		// FILTERING 3
		System.out.println("------------ Term ended ------------");
		this.printPresidents(filter("Term ended",
				(President pres, String string) -> pres.getWhyLeftOffice().equalsIgnoreCase(string)));

		// FILTERING 4.i
		System.out.println("------------ C Presidents ------------");
		BiPredicate<President, String> cNamePresMatcher = (pres, string) -> pres.getFirstName().startsWith(string);
		this.printPresidents(filter("C", cNamePresMatcher));

		// FILTERING 4.ii
		System.out.println("------------ Democrat Presidents ------------");
		BiPredicate<President, String> democratPresMatcher = (pres, string) -> pres.getParty().contains(string);
		this.printPresidents(filter("Democrat", democratPresMatcher));

		// FILTERING 4.iii
		System.out.println("------------ Presidents Died in office ------------");
		BiPredicate<President, String> diedInOfficePresMatcher = (pres, string) -> pres.getWhyLeftOffice()
				.equalsIgnoreCase(string);
		this.printPresidents(filter("Died in office", diedInOfficePresMatcher));

		// FILTERING 4.iv
		System.out.println("------------ Presidents Single Election ------------");
		this.printPresidents(filter("1", (pres, string) -> {
			int electionsWon = Integer.parseInt(string);
			return pres.getElectionsWon() == electionsWon;
		}));

		// FILTERING 4.v
		System.out.println("------------ Presidents 19th Century ------------");
		BiPredicate<President, String> nineTeenthCenturyPresMatcher = (pres, century) -> {
			int centuryEnd = Integer.parseInt(century) * 100;
			int centuryStart = centuryEnd - 99;
			return pres.getTermStart().isAfter(LocalDate.of(centuryStart - 1, 12, 31))
					&& pres.getTermStart().isBefore(LocalDate.of(centuryEnd, 12, 31));
		};
		this.printPresidents(filter("19", nineTeenthCenturyPresMatcher));

	}

	// SORTING 1
	public List<President> sortByPartyAndTerm() {
		List<President> presCopy = new ArrayList<>(presidents);
		presCopy.sort((arg0, arg1) -> {
			int result = arg0.getParty().compareTo(arg1.getParty());
			if (result == 0) {
				result = arg0.getTermNumber() - arg1.getTermNumber();
			}
			return result;
		});

		return presCopy;
	}

	// SORTING 2
	private List<President> sortByReasonAndTermNumber() {
		List<President> presCopy = new ArrayList<>(presidents);
		presCopy.sort((o1, o2) -> {
			int reasonComp = o1.getWhyLeftOffice().compareTo(o2.getWhyLeftOffice());
			if (reasonComp == 0) {
				reasonComp = Integer.compare(o1.getTermNumber(), o2.getTermNumber());
			}
			return reasonComp;
		});
		return presCopy;
	}

	// SORTING 3
	private List<President> sortByLastName() {
		List<President> presCopy = new ArrayList<>(presidents);
		presCopy.sort((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));
		return presCopy;
	}

	public List<President> getPresidents() {
		return this.presidents;
	}

	public void printPresidents(List<President> pres) {
		for (President p : pres) {
			System.out.println(p);
		}
	}

	public List<President> filter(String string, BiPredicate<President, String> matcher) {
		List<President> filtered = new ArrayList<>();
		for (President p : presidents) {
			if (matcher.test(p, string)) {
				filtered.add(p);
			}
		}
		return filtered;
	}

}
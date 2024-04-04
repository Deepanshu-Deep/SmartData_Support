package com.smartdatasolutions.test.impl;

import com.smartdatasolutions.test.Member;
import com.smartdatasolutions.test.MemberExporter;
import com.smartdatasolutions.test.MemberFileConverter;
import com.smartdatasolutions.test.MemberImporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends MemberFileConverter {

	@Override
	protected MemberExporter getMemberExporter( ) {
		// TODO
		return new MemberExporterImpl();
	}

	@Override
	protected MemberImporter getMemberImporter( ) {
		// TODO
		return new MemberImporterImpl();
	}

	@Override
	protected List< Member > getNonDuplicateMembers( List< Member > membersFromFile ) {

		// TODO
		return new ArrayList<>(new HashSet<>(membersFromFile));
	}

	@Override
	protected Map< String, List< Member >> splitMembersByState( List< Member > validMembers ) {
		// TODO
		return validMembers.stream().collect(Collectors.groupingBy(Member::getState));

	}

	public static void main( String[] args ) throws Exception {
		//TODO
		Main converter = new Main();

		MemberImporter importer = converter.getMemberImporter();

		File membersFile = new File("Members.txt");

		List<Member> membersFromFile = importer.importMembers(membersFile);

		Map<String, List<Member>> membersByState = converter.splitMembersByState(membersFromFile);

		MemberExporter exporter = converter.getMemberExporter();

		for (Map.Entry<String, List<Member>> entry : membersByState.entrySet()) {

			String state = entry.getKey();

			List<Member> nonDuplicateMembers = converter.getNonDuplicateMembers(entry.getValue());

			File outputFile = new File(state + "_outputFile.csv");

			try (Writer writer = new FileWriter(outputFile)) {

				for (Member member : nonDuplicateMembers) {

					exporter.writeMember(member, writer);
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}

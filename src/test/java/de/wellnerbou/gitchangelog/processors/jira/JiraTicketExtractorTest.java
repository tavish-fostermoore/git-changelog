package de.wellnerbou.gitchangelog.processors.jira;

import com.google.common.collect.Lists;
import de.wellnerbou.gitchangelog.model.CommitDataModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JiraTicketExtractorTest {

	public static final List<String> NO_TAGS = Collections.<String>emptyList();
	private JiraTicketExtractor jiraTicketExtractor;

	@Before
	public void setUp() throws Exception {
		jiraTicketExtractor = new JiraTicketExtractor("TEST", "ANOTHERPROJECT");
	}

	@Test
	public void testExtractRevCommitList() {
		Iterable<CommitDataModel> commits = Lists.newArrayList(
				new CommitDataModel(1, "hash1", "[TEST-1234] Fixed Bugs and did something else for TEST-345", NO_TAGS),
				new CommitDataModel(1, "hash2", "ANOTHERPROJECT-345", NO_TAGS)
		);
		final Collection<String> res = jiraTicketExtractor.extract(commits);
		assertThat(res).contains("ANOTHERPROJECT-345");
		assertThat(res).contains("TEST-1234");
		assertThat(res).contains("TEST-345");
	}

	@Test
	public void testExtractRevCommitListWithSameTicketMoreThanOnce() {
		Iterable<CommitDataModel> commits = Lists.newArrayList(
				new CommitDataModel(1, "hash1", "[TEST-1234] Fixed Bugs", NO_TAGS),
				new CommitDataModel(1, "hash2", "TEST-1234", NO_TAGS)
		);
		final Collection<String> res = jiraTicketExtractor.extract(commits);
		assertThat(res).containsOnly("TEST-1234");
		assertThat(res).hasSize(1);
	}

	@Test
	public void testExtractSingleString_oneTicket() throws Exception {
		final String teststr = "[TEST-1234] Fixed Bugs.";
		final Collection<String> res = jiraTicketExtractor.extract(teststr);
		assertThat(res).contains("TEST-1234");
	}

	@Test
	public void testExtractSingleString_twoTickets() throws Exception {
		final String teststr = "[TEST-1234] Fixed Bugs and did something else for TEST-345";
		final Collection<String> res = jiraTicketExtractor.extract(teststr);
		assertThat(res).contains("TEST-1234");
		assertThat(res).contains("TEST-345");
	}

	@Test
	public void testExtractSingleString_twoTicketsFromDifferentProjects() throws Exception {
		final String teststr = "[TEST-1234] Fixed Bugs and did something else for ANOTHERPROJECT-345";
		final Collection<String> res = jiraTicketExtractor.extract(teststr);
		assertThat(res).contains("TEST-1234");
		assertThat(res).contains("ANOTHERPROJECT-345");
	}
}

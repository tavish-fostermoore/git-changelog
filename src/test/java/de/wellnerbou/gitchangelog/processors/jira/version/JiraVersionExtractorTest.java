package de.wellnerbou.gitchangelog.processors.jira.version;

import com.google.common.collect.Lists;
import de.wellnerbou.gitchangelog.model.CommitDataModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class JiraVersionExtractorTest {
  public static final List<String> NO_TAGS = Collections.<String>emptyList();
  public static final String VERSION_1 = "1.0.1";
  public static final String VERSION_2 = "1.0.2";
  private JiraVersionExtractor jiraVersionExtractor;

  @Before
  public void setUp() throws Exception {
    jiraVersionExtractor = new JiraVersionExtractor();
  }

  @Test
  public void testExtractRevCommitList() {
    Iterable<CommitDataModel> commits = Lists.newArrayList(
            new CommitDataModel(1, "hash1", "[TEST-1234] Fixed Bugs and did something else for TEST-345", Arrays.asList(VERSION_1)),
            new CommitDataModel(1, "hash2", "ANOTHERPROJECT-345", NO_TAGS)
    );
    final List<String> versions = jiraVersionExtractor.extractVersions(commits);
    assertThat(versions).containsOnly(VERSION_1);
  }

  @Test
  public void testExtractRevCommitList_twoVersions() {
    Iterable<CommitDataModel> commits = Lists.newArrayList(
            new CommitDataModel(1, "hash1", "[TEST-1234] Fixed Bugs and did something else for TEST-345", Arrays.asList(VERSION_1)),
            new CommitDataModel(1, "hash2", "ANOTHERPROJECT-345", Arrays.asList(VERSION_2))
    );
    final List<String> versions = jiraVersionExtractor.extractVersions(commits);
    assertThat(versions).containsOnly(VERSION_1, VERSION_2);
  }

}
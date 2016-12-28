package de.wellnerbou.gitchangelog.processors.jira.version;

import de.wellnerbou.gitchangelog.processors.jira.JiraFilterLinkCreator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class JiraVersionFilterLinkCreatorTest {
  JiraVersionFilterLinkCreator jiraFilterLinkCreator = new JiraVersionFilterLinkCreator("https://jira.example.com/", Arrays.asList("PROJ1", "PROJ2"));


  @Test
  public void testCreateFilterLink() {
    final List<String> versionList = Arrays.asList("1.0");
    final String actual = jiraFilterLinkCreator.createFilterLink(versionList);
    assertThat(actual).isEqualTo("https://jira.example.com/issues/?jql=project%20in%20%28PROJ1,PROJ2%29%20AND%20fixVersion%20in%20%281.0%29");
  }

  @Test
  public void testCreateJoinedVersions() {
    final List<String> versionList = Arrays.asList("1.0.1", "1.0.2");
    final String actual = jiraFilterLinkCreator.createFilterLink(versionList);
    assertThat(actual).isEqualTo("https://jira.example.com/issues/?jql=project%20in%20%28PROJ1,PROJ2%29%20AND%20fixVersion%20in%20%281.0.1,1.0.2%29");
  }

}
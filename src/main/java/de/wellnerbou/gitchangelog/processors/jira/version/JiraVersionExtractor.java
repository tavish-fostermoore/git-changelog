package de.wellnerbou.gitchangelog.processors.jira.version;

import de.wellnerbou.gitchangelog.model.CommitDataModel;

import java.util.ArrayList;
import java.util.List;

public class JiraVersionExtractor {
  List<String> extractVersions(final Iterable<CommitDataModel> revisions) {
    final ArrayList<String> result = new ArrayList<>();
    for (final CommitDataModel revision : revisions) {
      final List<String> tagNames = revision.getTagNames();
      if (null!= tagNames) {
        result.addAll(tagNames);
      }
    }
    return result;
  }
}

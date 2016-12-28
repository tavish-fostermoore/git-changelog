package de.wellnerbou.gitchangelog.processors.jira.version;

import com.google.common.base.Joiner;

import java.util.Collection;

public class JiraVersionFilterLinkCreator {

	private final String jiraBaseUrl;
	private final Collection<String> jiraProjectCodes;

	public JiraVersionFilterLinkCreator(final String jiraBaseUrl, final Collection<String> jiraProjectCodes) {
		this.jiraBaseUrl = jiraBaseUrl + (jiraBaseUrl != null && jiraBaseUrl.endsWith("/") ? "" : "/");
		this.jiraProjectCodes = jiraProjectCodes;
	}

	public String createFilterLink(final Collection<String> versions) {
		//E.g. https://jira.fostermoore.com/issues/?jql=project%20in%20(NZCOMP)%20AND%20fixVersion%20in%20(2.0.74)
		return jiraBaseUrl + "issues/?jql=project%20in%20%28"+ createJoinedQueryItems(jiraProjectCodes)+ "%29%20AND%20fixVersion%20in%20%28" + createJoinedQueryItems(versions) + "%29";
	}

	protected String createJoinedQueryItems(final Collection<String> queryItems) {
		return Joiner.on(",").join(queryItems);
	}
}

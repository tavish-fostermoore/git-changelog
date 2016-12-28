package de.wellnerbou.gitchangelog.jgit;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import de.wellnerbou.gitchangelog.model.CommitDataModel;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommitDataModelMapper {

	private final Map<ObjectId, List<String>> tagsByCommitId;

	public CommitDataModelMapper(final Repository repository) throws GitAPIException {
		this.tagsByCommitId = tagsByObjectId(repository);
	}

	public Iterable<CommitDataModel> map(final Iterable<RevCommit> jGitLogBetween) {
		return FluentIterable.from(jGitLogBetween).transform(new Function<RevCommit, CommitDataModel>() {
			@Override
			public CommitDataModel apply(final RevCommit input) {

				return new CommitDataModel(input.getCommitTime(), input.getName(), input.getFullMessage(), tagsByCommitId.get(input.getId()));
			}
		});
	}

	private static Map<ObjectId, List<String>> tagsByObjectId(Repository repository) throws GitAPIException {
		final List<Ref> tags = new Git(repository).tagList().call();
		final Map<ObjectId, List<String>> result = new HashMap<>();
		for (final Ref tag : tags) {
			final ObjectId key;
			final Ref peeledRef = repository.peel(tag);
			if (peeledRef.getPeeledObjectId() != null) {
				key = peeledRef.getPeeledObjectId();
			} else {
				key = tag.getObjectId();
			}
			if (null==result.get(key)) {
				result.put(key, new ArrayList<String>());
			}
			String readableName = tag.getName().substring("refs/tags/".length());
			if (readableName.startsWith("fmit-enterprise-")) {
				readableName = readableName.substring("fmit-enterprise-".length());
			}
			result.get(key).add(readableName);
		}
		return result;
	}

}

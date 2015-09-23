package ut.com.atlassian.maven.plugins.jgitflow;

import com.atlassian.jgitflow.core.JGitFlowInfo;
import com.atlassian.jgitflow.core.extension.BranchCreatingExtension;
import com.atlassian.maven.jgitflow.api.StartBranchExtension;
import com.atlassian.maven.jgitflow.api.exception.MavenJGitFlowExtensionException;

public class NoopBranchCreateExtension implements StartBranchExtension
{

    @Override
    public void onTopicBranchVersionChange(String newVersion, String oldVersion, JGitFlowInfo flow) throws MavenJGitFlowExtensionException {
        
    }
}
